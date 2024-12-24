package sample.demo3;

import java.util.ArrayList;

public class Cart implements CartInterface{
    private ArrayList<CartItem> items;
    private double discount;


    public Cart() {
        this.items = new ArrayList<>();
        this.discount = 0.0;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        if (discount < 0) {
            System.out.println("Discount cannot be negative.");
        } else {
            this.discount = discount;
        }
    }


    public void addItem(Products product, int quantity) {

        if (quantity > 0) {
            for (CartItem item : items) {
                if (item.getProduct().getProductname().equalsIgnoreCase(product.getProductname())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return;
                }
            }

            items.add(new CartItem(product, quantity));
        } else {
            System.out.println("Quantity should be greater than 0.");
        }
    }


    public boolean removeItem(String productName, int quantityToRemove) {
        boolean itemFound = false;

        for (CartItem item : items) {
            if (item.getProduct().getProductname().equalsIgnoreCase(productName)) {
                itemFound = true;

                if (quantityToRemove <= 0) {
                    System.out.println("Invalid quantity. Please enter a positive number.");
                } else if (quantityToRemove >= item.getQuantity()) {
                    items.remove(item);
                    System.out.println("Removed all '" + productName + "' from your cart.");
                } else {
                    item.setQuantity(item.getQuantity() - quantityToRemove);
                    System.out.println("Updated quantity of '" + productName + "': " + item.getQuantity());
                }
                break;
            }
        }

        if (!itemFound) {
            System.out.println("The product '" + productName + "' is not in your cart.");
        }
        return itemFound;
    }

    public double calculateTotal() {
        return calculateTotalWithNoDiscount() - discount;
    }

    public double calculateTotalWithNoDiscount() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getProduct().getProductprice() * item.getQuantity();
        }
        return total;
    }


    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (CartItem item : items) {
                System.out.println(item.getProduct().getProductname() + " - " + item.getQuantity() + " x " + item.getProduct().getProductprice() + " = " + (item.getProduct().getProductprice() * item.getQuantity()));
            }
            System.out.println("Total: " + calculateTotal());
        }
    }

    public void clearCart() {
        items.clear();
        discount=0.0;
    }

}
class CartItem {
    private Products product;
    private int quantity;

    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Products getProduct() {
        return product;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }
}

