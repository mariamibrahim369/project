package sample.demo3;

import java.util.ArrayList;
public interface CartInterface {

    ArrayList<CartItem> getItems();
    void addItem(Products product, int quantity);
    boolean removeItem(String productName,int quantityToRemove);
    double calculateTotal();
    void displayCart();
    void clearCart();


}
