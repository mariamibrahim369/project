package sample.demo3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Optional;

public class Homepage {

    private ObservableList<String> cart = FXCollections.observableArrayList();
    private ListView<String> cartListView;
    private Label cartCountLabel;
    private Label totalPriceLabel;
    private Button checkoutButton;

    public void displayHomepage(Stage primaryStage) {
        primaryStage.setTitle("Product Homepage");

        // Accessing products from the Database class
        Products[] products = Database.products;

        // Setting up the main layout (VBox)
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: #f9fafb;");

        // Header section
        HBox header = new HBox(20);
        header.setStyle("-fx-alignment: center; -fx-padding: 0 20 20 20;");
        Label welcomeLabel = new Label("Welcome Back!");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");
        header.getChildren().add(welcomeLabel);

        // Shopping Cart icon with item count
        VBox cartIcon = new VBox();
        cartIcon.setStyle("-fx-alignment: center;");
        Button cartButton = new Button("🛒");
        cartButton.setStyle("-fx-font-size: 20px; -fx-background-color: transparent; -fx-border: none; -fx-text-fill: #4b5563;");

        cartCountLabel = new Label("0");
        cartCountLabel.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 2 5;");

        cartIcon.getChildren().addAll(cartButton, cartCountLabel);
        header.getChildren().add(cartIcon);

        // Product display section
        VBox productList = new VBox(10);
        productList.setStyle("-fx-alignment: center;");

        // Add products to the list dynamically
        for (Products product : products) {
            if (product != null) {
                VBox productCard = createProductCard(product);
                productList.getChildren().add(productCard);
            }
        }

        // Wrap the product list in a ScrollPane
        ScrollPane productScrollPane = new ScrollPane(productList);
        productScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        productScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        productScrollPane.setFitToWidth(true);

        // Cart section
        Label cartLabel = new Label("Shopping Cart");
        cartLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        cartListView = new ListView<>(cart);
        cartListView.setPrefHeight(200);
        cartListView.setStyle("-fx-border-color: #d1d5db;");
        cartListView.setPrefWidth(300);
        cartListView.setMaxWidth(300);
        cartListView.setFixedCellSize(40);

        // Use custom ListCells for cart items
        cartListView.setCellFactory(lv -> new CartListCell());

        ScrollPane cartScrollPane = new ScrollPane(cartListView);
        cartScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cartScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Total price label
        totalPriceLabel = new Label("Total: $0.00");
        totalPriceLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1f2937;");

        // Checkout button
        checkoutButton = new Button("Checkout");
        checkoutButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px;");
        checkoutButton.setOnAction(e -> checkout(primaryStage));

        // Adding everything to the main layout
        mainLayout.getChildren().addAll(header, productScrollPane, cartLabel, cartScrollPane, totalPriceLabel, checkoutButton);

        // Creating the scene
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private VBox createProductCard(Products product) {
        VBox productCard = new VBox(15);  // Increased spacing for a modern look
        productCard.setStyle("-fx-background-color: #ffffff; -fx-padding: 15; -fx-border-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 10, 0, 0, 5);");

        // Product Image
        ImageView productImage = new ImageView(new Image(getClass().getResource(product.getImageURL()).toExternalForm()));
        productImage.setFitWidth(220);
        productImage.setFitHeight(220);
        productImage.setPreserveRatio(true);
        productImage.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 5);");

        // Product Name
        Label productName = new Label(product.getProductname());
        productName.setStyle("-fx-font-size: 18px; -fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-text-fill: #333333;");

        // Product Price
        Label productPrice = new Label("$" + product.getProductprice());
        productPrice.setStyle("-fx-font-size: 16px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #007BFF;");

        Label stockLabel = new Label("In Stock: " + product.getQuantity());
        stockLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Segoe UI'; -fx-text-fill: #6C757D; -fx-background-color: #f8f9fa; -fx-padding: 5 10; -fx-border-radius: 20;");

        // Add to Cart Button
        Button addButton = new Button("Add to Cart");
        addButton.setStyle("-fx-background-color: linear-gradient(#56CCF2, #2F80ED); -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-border-radius: 25;");
        addButton.setMinWidth(200);
        addButton.setMaxWidth(200);
        addButton.setOnAction(e -> addToCart(product));

        // Add all elements to the product card
        productCard.getChildren().addAll(productImage, productName, productPrice, stockLabel, addButton);

        return productCard;
    }

    private void addToCart(Products product) {
        // Create a custom TextInputDialog with modern styles
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Select Quantity");
        dialog.setHeaderText("How many " + product.getProductname() + " would you like to add?");
        dialog.setContentText("Enter quantity (Available: " + product.getQuantity() + "):");

        // Style the dialog for a modern look
        dialog.getDialogPane().setStyle("-fx-background-color: #f9fafb;");
        dialog.getDialogPane().lookup(".text-input").setStyle("-fx-border-color: #4CAF50; -fx-font-size: 14px; -fx-background-color: #ffffff;");

        // Show the dialog and handle user input
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(quantityStr -> {
            try {
                int requestedQuantity = Integer.parseInt(quantityStr);

                // Check for invalid input
                if (requestedQuantity <= 0) {
                    showAlert("Please enter a positive number.");
                    return;
                }

                // Check for insufficient stock
                if (requestedQuantity > product.getQuantity()) {
                    showAlert("Insufficient stock! Only " + product.getQuantity() + " available.");
                    return;
                }

                // Format cart item and add it
                String cartItem = String.format("%s - $%.2f x %d = $%.2f",
                        product.getProductname(),
                        product.getProductprice(),
                        requestedQuantity,
                        product.getProductprice() * requestedQuantity);

                cart.add(cartItem);
                cartListView.setItems(cart);
                cartCountLabel.setText(String.valueOf(cart.size()));
                updateTotalPrice();

            } catch (NumberFormatException e) {
                showAlert("Please enter a valid number.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void checkout(Stage primaryStage) {
        if (cart.isEmpty()) {
            showAlert("Your cart is empty!");
            return;
        }

        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Proceed with payment?", ButtonType.YES, ButtonType.NO);

        paymentAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    for (String cartItem : cart) {
                        String[] parts = cartItem.split(" - \\$|x|=");
                        String productName = parts[0].trim();
                        int quantity = Integer.parseInt(parts[2].trim());

                        for (Products product : Database.products) {
                            if (product != null && product.getProductname().equals(productName)) {
                                product.reduceStock(quantity);
                                break;
                            }
                        }
                    }

                    cart.clear();
                    cartListView.setItems(cart);
                    cartCountLabel.setText("0");
                    updateTotalPrice();

                    Checkout checkoutPage = new Checkout();
                    Scene checkoutScene = new Scene(checkoutPage.getRoot(), 600, 400);
                    Stage checkoutStage = new Stage();
                    checkoutStage.setScene(checkoutScene);
                    checkoutStage.setTitle("Checkout");
                    checkoutStage.show();

                    primaryStage.close();

                } catch (IllegalArgumentException e) {
                    showAlert("Error processing order: " + e.getMessage());
                }
            }
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (String item : cart) {
            String[] parts = item.split("\\$");
            total += Double.parseDouble(parts[parts.length - 1].trim());
        }
        totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void removeFromCart(String item) {
        cart.remove(item);
        cartListView.setItems(cart);
        cartCountLabel.setText(String.valueOf(cart.size()));
        updateTotalPrice();
    }

    private class CartListCell extends ListCell<String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellLayout = new HBox(10); // Add spacing between elements
                cellLayout.setStyle("-fx-alignment: center-left;"); // Align items

                Label label = new Label(item);
                label.setStyle("-fx-padding: 0 10;"); // Add some padding

                Button removeButton = new Button("Remove");
                removeButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
                removeButton.setOnAction(e -> removeFromCart(item));

                cellLayout.getChildren().addAll(label, removeButton);
                setGraphic(cellLayout);
            }
        }
    }
}