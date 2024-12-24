package sample.demo3;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Customer extends Person {
    public enum Gender {
        Male, Female, Other;
    }

    private double balance;
    private String address;
    private Gender gender;


    Customer(String username, String password, String dateofbirth,double balance, String address, Gender gender) {
        super(username, password,dateofbirth);
        this.address=address;
        this.balance=balance;
        this.gender=gender;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        if (balance < 0) {
            System.out.println("Balance cannot be negative.");
        } else {
            this.balance = balance;
        }
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void customerOptions(Scanner scanner) {
        Cart cart =new Cart();
        int choice;
        do {
            System.out.println("\nCustomer Menu:");
            System.out.println("[1] View Products\n[2] Add product to cart\n[3] Remove product from cart\n[4] View cart\n[5]Checkout\n[6]Logout");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    ValidateAddProductToCart(scanner,cart);
                    break;
                case 3:
                    ValidateRemoveProductFromCart(scanner,cart);
                    break;
                case 4:
                    cart.displayCart();
                    break;
                case 5:
                    checkout(scanner,cart);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (true);

    }
    private void viewProducts(){
        System.out.println("Available Products:");
        for (int i = 0; i < Database.products.length; i++) {
            if (Database.products[i] != null) {
                System.out.println(Database.products[i]);
            }
        }
    }
    private void ValidateAddProductToCart(Scanner scanner,Cart cart) {

        System.out.println("Enter the name of the product to add to your cart:");
        String productName = "";
        boolean validateproductname = false;
        while (!validateproductname) {
            try {
                productName = scanner.nextLine();
                if (productName.isEmpty()) {
                    System.out.println("Product name cannot be empty. Please enter a valid product name.");
                } else if (!productName.matches("[a-zA-Z ]+")) {
                    System.out.println("Product name can only contain letters and spaces. Please enter a valid name.");
                } else {
                    validateproductname = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid name  . ");
            }
        }
        Products selectedProduct = null;

        for (Products product : Database.products) {
            if (product != null && product.getProductname().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct != null) {
            System.out.println("Enter quantity to add:");
            int quantity = getValidatedQuantity(scanner);
            if (quantity > 0 && quantity <= selectedProduct.getQuantity()) {
                cart.addItem(selectedProduct, quantity);
                System.out.println("Product added to cart.");
            } else {
                System.out.println("Invalid quantity. Please try again.");
            }
        } else {
            System.out.println("Product not found.");
        }

    }
    private void ValidateRemoveProductFromCart(Scanner scanner,Cart cart){
        if(cart.getItems().isEmpty()){
            System.out.println("Your cart is empty. Nothing to remove.");
            return;
        }
        System.out.println("Enter the product name you want to remove: ");
        String removeProductname = "";
        boolean validateremoved = false;
        while (!validateremoved) {
            try {
                removeProductname = scanner.nextLine();
                if (removeProductname.isEmpty()) {
                    System.out.println("Product name cannot be empty. Please enter a valid product name.");
                } else {
                    validateremoved = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid name. ");
            }
        }

        System.out.println("Enter quantity to remove:");
        int removeQuantity = getValidatedQuantity(scanner);
        boolean removed = cart.removeItem(removeProductname, removeQuantity);
        if (removed) {
            System.out.println("Product removed from cart.");
        } else {
            System.out.println("Failed to remove product. Either it doesn't exist in the cart or an invalid quantity was provided.");
        }

    }


    private void checkout(Scanner scanner,Cart cart){
        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty. Please add items before checking out.");
        } else {
            System.out.println("Enter payment method (Credit/Debit/PayPal):");
            String paymentMethod = scanner.nextLine();
            Order order = new Order(this, cart, paymentMethod);

            if (order.processOrder()) {
                System.out.println(order);
                cart.clearCart();
            } else {
                System.out.println("Checkout failed. Please try again.");
            }
        }
    }

    private int getValidatedQuantity(Scanner scanner) {
        while (true) {
            try {
                int quantity = scanner.nextInt();
                scanner.nextLine();
                if (quantity > 0) {
                    return quantity;
                } else {
                    System.out.println("Quantity must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public String toString() {
        return super.toString() + "\n" + "Gender: " + getGender() + "\n" + "Addres: " + getAddress() + "\n"   + "Balance: " + getBalance();

    }
}
