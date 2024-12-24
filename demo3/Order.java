package sample.demo3;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Order {
    public enum OrderStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    private static int orderCounter = 1;
    private int orderId;
    private Customer customer;
    private Cart cart;
    private String paymentMethod;
    private OrderStatus status;
    private LocalDateTime orderDate;

    public Order(Customer customer, Cart cart, String paymentMethod) {
        this.orderId = orderCounter++;
        this.customer = customer;
        this.cart = cart;
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();
    }

    public int getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }


    public boolean processOrder() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Processing order for customer: " + customer.getUsername());
        System.out.println("Customer balance: " + customer.getBalance());
        System.out.println("Cart total before discount: " + cart.calculateTotalWithNoDiscount());

        System.out.print("Enter promo code (or press Enter to skip): ");
        String promoCode = scanner.nextLine();
        if (!promoCode.isEmpty()) {
            if (!applyPromoCode(promoCode)) {
                System.out.println("Invalid promo code. Continuing without discount.");
            }
        } else {
            System.out.println("No promo code entered. Continuing without discount.");
        }


        double finalTotal = cart.calculateTotal();
        System.out.println("Cart total after discount: " + finalTotal);

        if (!isValidPaymentMethod()) {
            System.out.println("Invalid payment method. Order failed.");
            this.status = OrderStatus.FAILED;
            return false;
        }

        if (!validatePayment(scanner)) {
            System.out.println("Payment validation failed.");
            this.status = OrderStatus.FAILED;
            return false;
        }

        if (customer.getBalance() < finalTotal) {
            System.out.println("Insufficient balance. Order failed.");
            this.status = OrderStatus.FAILED;
            return false;
        }

        for (CartItem item : cart.getItems()) {
            Products product = item.getProduct();
            if (product.getQuantity() < item.getQuantity()) {
                System.out.println("Insufficient stock for " + product.getProductname() + ". Order failed.");
                this.status = OrderStatus.FAILED;
                return false;
            }
        }

        for (CartItem item : cart.getItems()) {
            Products product = item.getProduct();
            product.reduceStock(item.getQuantity());
        }

        customer.setBalance(customer.getBalance() - finalTotal);
        this.status = OrderStatus.COMPLETED;

        System.out.println("Order processed successfully.");
        System.out.println("New balance after deduction: " + customer.getBalance());
        return true;
    }

    private boolean validatePayment(Scanner scanner) {
        if (paymentMethod.equalsIgnoreCase("PayPal")) {
            System.out.print("Enter PayPal email: ");
            String email = scanner.nextLine();
            System.out.print("Enter PayPal password: ");
            String password = scanner.nextLine();
            if (email.isEmpty() || password.isEmpty() || !email.contains("@") || !email.contains(".")) {
                System.out.println("Invalid PayPal credentials.");
                return false;
            }
            System.out.println("PayPal validation successful.");
        } else if (paymentMethod.equalsIgnoreCase("Debit") || paymentMethod.equalsIgnoreCase("Credit")) {
            System.out.print("Enter card number: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Enter card CVV: ");
            String cvv = scanner.nextLine();

            if (!validateCardDetails(cardNumber, cvv)) {
                return false;
            }
            System.out.println("Card validation successful.");
        } else {
            System.out.println("Invalid payment method.");
            return false;
        }
        return true;

    }
    private boolean validateCardDetails(String cardNumber, String cvv) {
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            System.out.println("Invalid card number.");
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            System.out.println("Invalid CVV.");
            return false;
        }

        return true;
    }

    private boolean isValidPaymentMethod() {
        return paymentMethod.equalsIgnoreCase("Credit") ||
                paymentMethod.equalsIgnoreCase("Debit") ||
                paymentMethod.equalsIgnoreCase("PayPal");
    }

    public boolean applyPromoCode(String promoCode) {
        int discountPercentage = 0;

        if (promoCode.equalsIgnoreCase("SAVE10")) {
            discountPercentage = 10;
        } else if (promoCode.equalsIgnoreCase("SAVE20")) {
            discountPercentage = 20;
        } else if (promoCode.equalsIgnoreCase("FREESHIP")) {
            discountPercentage = 15;
        } else {
            System.out.println("Invalid promo code.");
            return false;
        }

        double discountAmount = cart.calculateTotalWithNoDiscount() * discountPercentage / 100.0;
        cart.setDiscount(discountAmount);
        System.out.println("Promo code applied! Discount: " + discountAmount);
        return true;
    }




    public String toString() {
        return "Order ID: " + orderId + "\n" +
                "Customer: " + customer.getUsername() + "\n" +
                "Order Date: " + orderDate + "\n" +
                "Payment Method: " + paymentMethod + "\n" +
                "Status: " + status + "\n" +
                "Total: " + cart.calculateTotal();

    }
}