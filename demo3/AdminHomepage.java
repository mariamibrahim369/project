package sample.demo3;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminHomepage {
    private Stage stage;
    private Scene scene;
    private AnchorPane root;

    public AdminHomepage() {
        root = new AnchorPane();
        root.setStyle("-fx-background-color: #f5f6fa;");
        initializeUI();
        scene = new Scene(root, 800, 600);
    }

    public void show(Stage stage) {
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    private void initializeUI() {
        // Create header
        HBox header = createHeader();
        AnchorPane.setTopAnchor(header, 0.0);
        AnchorPane.setLeftAnchor(header, 0.0);
        AnchorPane.setRightAnchor(header, 0.0);

        // Create main content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        AnchorPane.setTopAnchor(scrollPane, 60.0);
        AnchorPane.setBottomAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);
        AnchorPane.setRightAnchor(scrollPane, 0.0);

        // Create grid for dashboard items
        GridPane gridPane = createDashboardGrid();
        scrollPane.setContent(gridPane);

        root.getChildren().addAll(header, scrollPane);
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPrefHeight(60);
        header.setStyle("-fx-background-color: #2d8f92;");
        header.setPadding(new Insets(0, 0, 0, 20));

        Label title = new Label("Admin Dashboard");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 24px;");

        header.getChildren().add(title);
        return header;
    }

    private GridPane createDashboardGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(30));
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // Create dashboard tiles
        VBox displayUsersBox = createDashboardTile("Display Users", "/sample/demo3/group.png", e -> handleDisplayUsers());
        VBox addProductBox = createDashboardTile("Add Product", "/sample/demo3/plus.png", e -> handleAddProduct());
        VBox displayProductBox = createDashboardTile("Display Product", "/sample/demo3/view.png", e -> handleDisplayProducts());
        VBox editProductBox = createDashboardTile("Edit Product", "/sample/demo3/edit.png", e -> handleEditProduct());
        VBox logoutBox = createDashboardTile("Logout", "/sample/demo3/exit.png", e -> handleLogout());


        // Add tiles to grid
        gridPane.add(displayUsersBox, 0, 0);
        gridPane.add(addProductBox, 1, 0);
        gridPane.add(displayProductBox, 2, 0);
        gridPane.add(editProductBox, 0, 1);
        gridPane.add(logoutBox, 1, 1);

        return gridPane;
    }

    private VBox createDashboardTile(String title, String iconPath, EventHandler<MouseEvent> clickHandler) {
        VBox tile = new VBox(15);
        tile.setAlignment(Pos.CENTER);
        tile.setPrefSize(200, 150);
        tile.setPadding(new Insets(20));
        tile.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");

        // Add drop shadow
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.1));
        shadow.setRadius(10);
        tile.setEffect(shadow);

        // Create icon
        ImageView icon = new ImageView();
        try {
            icon.setImage(new Image(getClass().getResourceAsStream(iconPath)));
        } catch (Exception ex) {
            System.out.println("Error loading icon: " + iconPath);
        }
        icon.setFitHeight(50);
        icon.setFitWidth(50);

        // Create label
        Label label = new Label(title);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #2d8f92; -fx-font-weight: bold;");

        tile.getChildren().addAll(icon, label);

        // Add hover effect
        tile.setOnMouseEntered(e -> {
            tile.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #e6f3f3); " +
                    "-fx-background-radius: 8px; -fx-cursor: hand;");
            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setColor(Color.rgb(45, 143, 146, 0.4));
            hoverShadow.setRadius(15);
            tile.setEffect(hoverShadow);
            tile.setTranslateY(-2);
        });

        tile.setOnMouseExited(e -> {
            tile.setStyle("-fx-background-color: white; -fx-background-radius: 8px;");
            tile.setEffect(shadow);
            tile.setTranslateY(0);
        });

        tile.setOnMouseClicked(clickHandler);

        return tile;
    }

    // Handler methods for each tile
    private void handleDisplayUsers() {
        Stage productsStage = new Stage();
        productsStage.initModality(Modality.APPLICATION_MODAL);
        productsStage.setTitle("Users List");

        VBox mainContainer = new VBox(20);
        mainContainer.setStyle("-fx-background-color: #f5f6fa;");
        mainContainer.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2d8f92; -fx-padding: 15px;");
        Label headerLabel = new Label("Available Products");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(headerLabel);

        // Products list container
        VBox productsContainer = new VBox(15);
        productsContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(productsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Add products
        for (Customer customer : Database.customers) {
            if (customer != null) {
                // Create product card with white background
                VBox productCard = new VBox(10);
                productCard.setStyle("-fx-background-color: white; -fx-padding: 15px; " +
                        "-fx-background-radius: 5px; -fx-border-radius: 5px;");
                productCard.setPrefWidth(500);
                productCard.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.1)));

                // Grid for organizing product details
                GridPane detailsGrid = new GridPane();
                detailsGrid.setHgap(15);
                detailsGrid.setVgap(8);
                detailsGrid.setPadding(new Insets(10));

                // Style for labels
                String labelStyle = "-fx-font-weight: bold; -fx-text-fill: #2d8f92;";
                String valueStyle = "-fx-text-fill: #333333;";

                // Add details to grid
                Label nameLabel = new Label("Name:");
                nameLabel.setStyle(labelStyle);
                Label nameValue = new Label(customer.getUsername());
                nameValue.setStyle(valueStyle);
                detailsGrid.addRow(0, nameLabel, nameValue);

                Label birthLabel = new Label("Date of Birth:");
                birthLabel.setStyle(labelStyle);
                Label birthValue = new Label(customer.getDateofbirth());
                birthValue.setStyle(valueStyle);
                detailsGrid.addRow(1, birthLabel, birthValue);

                Label balanceLabel = new Label("Balance:");
                balanceLabel.setStyle(labelStyle);
                Label balanceValue = new Label(String.valueOf(customer.getBalance()));
                balanceValue.setStyle(valueStyle);
                detailsGrid.addRow(2, balanceLabel, balanceValue);

                Label addressLabel = new Label("Address:");
                addressLabel.setStyle(labelStyle);
                Label addressValue = new Label(customer.getAddress());
                addressValue.setStyle(valueStyle);
                detailsGrid.addRow(3, addressLabel, addressValue);

                Label genderLabel = new Label("Gender:");
                genderLabel.setStyle(labelStyle);
                Label genderValue = new Label(customer.getGender().name());
                genderValue.setStyle(valueStyle);
                detailsGrid.addRow(4, genderLabel, genderValue);

                productCard.getChildren().add(detailsGrid);
                productsContainer.getChildren().add(productCard);
            }
        }

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #2d8f92; -fx-text-fill: white; " +
                "-fx-padding: 10px 20px; -fx-font-size: 14px;");
        closeButton.setOnAction(e -> productsStage.close());

        HBox buttonContainer = new HBox(closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        mainContainer.getChildren().addAll(header, scrollPane, buttonContainer);

        Scene scene = new Scene(mainContainer, 600, 700);
        productsStage.setScene(scene);
        productsStage.show();
    }

    private void handleAddProduct() {
        Stage addProductStage = new Stage();
        addProductStage.initModality(Modality.APPLICATION_MODAL);
        addProductStage.setTitle("Add Product");

        VBox mainContainer = new VBox(20);
        mainContainer.setStyle("-fx-background-color: #f5f6fa;");
        mainContainer.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2d8f92; -fx-padding: 15px;");
        Label headerLabel = new Label("Add New Product");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(headerLabel);

        // Form container
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");

        // Product Name
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter product name");
        form.addRow(0, nameLabel, nameField);

        // Price
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        priceField.setPromptText("Enter price");
        form.addRow(1, priceLabel, priceField);

        // Category
        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter category");
        form.addRow(2, categoryLabel, categoryField);

        // Quantity
        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        form.addRow(3, quantityLabel, quantityField);

        // Error label
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: red;");

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add Product");
        addButton.setStyle("-fx-background-color: #2d8f92; -fx-text-fill: white;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        buttonBox.getChildren().addAll(addButton, cancelButton);

        // Add validation and product creation logic
        addButton.setOnAction(e -> {
            try {
                // Validate name
                String name = nameField.getText().trim();
                if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                    throw new IllegalArgumentException("Product name must contain only letters and spaces");
                }

                // Validate price
                double price = Double.parseDouble(priceField.getText().trim());
                if (price < 0) {
                    throw new IllegalArgumentException("Price cannot be negative");
                }

                // Validate category
                String category = categoryField.getText().trim();
                if (category.isEmpty() || category.matches(".*\\d.*")) {
                    throw new IllegalArgumentException("Category must contain only letters");
                }

                // Validate quantity
                int quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity < 0) {
                    throw new IllegalArgumentException("Quantity cannot be negative");
                }

                // Check if product already exists
                Products existingProduct = null;
                for (Products product : Database.products) {
                    if (product != null && product.getProductname().equalsIgnoreCase(name)) {
                        existingProduct = product;
                        break;
                    }
                }

                if (existingProduct != null) {
                    // Update existing product quantity
                    existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
                    errorLabel.setStyle("-fx-text-fill: green;");
                    errorLabel.setText("Product quantity updated successfully!");
                } else {
                    // Add new product
                    // Note: Using a placeholder image path - you'll need to implement image selection
                    Products newProduct = new Products(name, price, category, quantity, "placeholder_image_path");
                    Database.addProduct(newProduct);
                    errorLabel.setStyle("-fx-text-fill: green;");
                    errorLabel.setText("Product added successfully!");
                }

                // Clear fields after successful addition
                nameField.clear();
                priceField.clear();
                categoryField.clear();
                quantityField.clear();

            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers for price and quantity");
            } catch (IllegalArgumentException ex) {
                errorLabel.setText(ex.getMessage());
            }
        });

        cancelButton.setOnAction(e -> addProductStage.close());

        mainContainer.getChildren().addAll(header, form, errorLabel, buttonBox);

        Scene scene = new Scene(mainContainer, 500, 500);
        addProductStage.setScene(scene);
        addProductStage.show();
    }

    private void handleDisplayProducts() {
        Stage productsStage = new Stage();
        productsStage.initModality(Modality.APPLICATION_MODAL);
        productsStage.setTitle("Products List");

        VBox mainContainer = new VBox(20);
        mainContainer.setStyle("-fx-background-color: #f5f6fa;");
        mainContainer.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2d8f92; -fx-padding: 15px;");
        Label headerLabel = new Label("Available Products");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(headerLabel);

        // Products list container
        VBox productsContainer = new VBox(15);
        productsContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(productsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Add products
        for (Products product : Database.products) {
            if (product != null) {
                // Create product card with white background
                VBox productCard = new VBox(10);
                productCard.setStyle("-fx-background-color: white; -fx-padding: 15px; " +
                        "-fx-background-radius: 5px; -fx-border-radius: 5px;");
                productCard.setPrefWidth(500);
                productCard.setEffect(new DropShadow(5, Color.rgb(0, 0, 0, 0.1)));

                // Grid for organizing product details
                GridPane detailsGrid = new GridPane();
                detailsGrid.setHgap(15);
                detailsGrid.setVgap(8);
                detailsGrid.setPadding(new Insets(10));

                // Style for labels
                String labelStyle = "-fx-font-weight: bold; -fx-text-fill: #2d8f92;";
                String valueStyle = "-fx-text-fill: #333333;";

                // Add details to grid
                Label nameLabel = new Label("Name:");
                nameLabel.setStyle(labelStyle);
                Label nameValue = new Label(product.getProductname());
                nameValue.setStyle(valueStyle);
                detailsGrid.addRow(0, nameLabel, nameValue);

                Label priceLabel = new Label("Price:");
                priceLabel.setStyle(labelStyle);
                Label priceValue = new Label(String.format("$%.2f", product.getProductprice()));
                priceValue.setStyle(valueStyle);
                detailsGrid.addRow(1, priceLabel, priceValue);

                Label categoryLabel = new Label("Category:");
                categoryLabel.setStyle(labelStyle);
                Label categoryValue = new Label(product.getProdcategory());
                categoryValue.setStyle(valueStyle);
                detailsGrid.addRow(2, categoryLabel, categoryValue);

                Label stockLabel = new Label("Stock:");
                stockLabel.setStyle(labelStyle);
                Label stockValue = new Label(String.valueOf(product.getQuantity()));
                stockValue.setStyle(valueStyle);
                detailsGrid.addRow(3, stockLabel, stockValue);

                productCard.getChildren().add(detailsGrid);
                productsContainer.getChildren().add(productCard);
            }
        }

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #2d8f92; -fx-text-fill: white; " +
                "-fx-padding: 10px 20px; -fx-font-size: 14px;");
        closeButton.setOnAction(e -> productsStage.close());

        HBox buttonContainer = new HBox(closeButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        mainContainer.getChildren().addAll(header, scrollPane, buttonContainer);

        Scene scene = new Scene(mainContainer, 600, 700);
        productsStage.setScene(scene);
        productsStage.show();
    }

    private void handleEditProduct() {
        Stage editProductStage = new Stage();
        editProductStage.initModality(Modality.APPLICATION_MODAL);
        editProductStage.setTitle("Edit Product");

        VBox mainContainer = new VBox(20);
        mainContainer.setStyle("-fx-background-color: #f5f6fa;");
        mainContainer.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #2d8f92; -fx-padding: 15px;");
        Label headerLabel = new Label("Edit Product");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        header.getChildren().add(headerLabel);

        // Search section
        VBox searchBox = new VBox(10);
        searchBox.setStyle("-fx-background-color: white; -fx-padding: 15px; -fx-background-radius: 5px;");

        Label searchLabel = new Label("Enter Product Name to Edit:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #2d8f92; -fx-text-fill: white;");

        searchBox.getChildren().addAll(searchLabel, searchField, searchButton);

        // Edit form container (initially hidden)
        VBox editFormContainer = new VBox(15);
        editFormContainer.setVisible(false);
        editFormContainer.setStyle("-fx-background-color: white; -fx-padding: 15px; -fx-background-radius: 5px;");

        // Create edit form
        GridPane editForm = new GridPane();
        editForm.setHgap(10);
        editForm.setVgap(15);
        editForm.setPadding(new Insets(10));

        // Edit fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        editForm.addRow(0, nameLabel, nameField);

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();
        editForm.addRow(1, priceLabel, priceField);

        Label quantityLabel = new Label("Quantity:");
        TextField quantityField = new TextField();
        editForm.addRow(2, quantityLabel, quantityField);

        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField();
        editForm.addRow(3, categoryLabel, categoryField);

        // Buttons for edit form
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #2d8f92; -fx-text-fill: white;");
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        buttonBox.getChildren().addAll(saveButton, cancelButton);

        // Error/Success message label
        Label messageLabel = new Label();
        messageLabel.setWrapText(true);

        editFormContainer.getChildren().addAll(editForm, buttonBox, messageLabel);

        // Add all containers to main container
        mainContainer.getChildren().addAll(header, searchBox, editFormContainer);

        // Product reference to store found product
        final Products[] currentProduct = new Products[1];

        // Search button action
        searchButton.setOnAction(e -> {
            String productName = searchField.getText().trim();
            currentProduct[0] = null;

            // Search for product
            for (Products product : Database.products) {
                if (product != null && product.getProductname().equalsIgnoreCase(productName)) {
                    currentProduct[0] = product;
                    break;
                }
            }

            if (currentProduct[0] != null) {
                // Product found - show edit form and populate fields
                editFormContainer.setVisible(true);
                nameField.setText(currentProduct[0].getProductname());
                priceField.setText(String.valueOf(currentProduct[0].getProductprice()));
                quantityField.setText(String.valueOf(currentProduct[0].getQuantity()));
                categoryField.setText(currentProduct[0].getProdcategory());
                messageLabel.setText("");
                messageLabel.setStyle("-fx-text-fill: black;");
            } else {
                // Product not found - show error
                editFormContainer.setVisible(false);
                messageLabel.setText("Product not found!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        // Save button action
        saveButton.setOnAction(e -> {
            try {
                if (currentProduct[0] != null) {
                    // Validate name
                    String newName = nameField.getText().trim();
                    if (!newName.isEmpty() && newName.matches("[a-zA-Z ]+")) {
                        currentProduct[0].setProductname(newName);
                    } else {
                        throw new IllegalArgumentException("Invalid name format");
                    }

                    // Validate price
                    double newPrice = Double.parseDouble(priceField.getText().trim());
                    if (newPrice >= 0) {
                        currentProduct[0].setProductprice(newPrice);
                    } else {
                        throw new IllegalArgumentException("Price cannot be negative");
                    }

                    // Validate quantity
                    int newQuantity = Integer.parseInt(quantityField.getText().trim());
                    if (newQuantity >= 0) {
                        currentProduct[0].setQuantity(newQuantity);
                    } else {
                        throw new IllegalArgumentException("Quantity cannot be negative");
                    }

                    // Validate category
                    String newCategory = categoryField.getText().trim();
                    if (!newCategory.isEmpty()) {
                        currentProduct[0].setProdcategory((newCategory));
                    } else {
                        throw new IllegalArgumentException("Category cannot be empty");
                    }

                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText("Product updated successfully!");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText("Please enter valid numbers for price and quantity");
            } catch (IllegalArgumentException ex) {
                messageLabel.setStyle("-fx-text-fill: red;");
                messageLabel.setText(ex.getMessage());
            }
        });

        // Cancel button action
        cancelButton.setOnAction(e -> editProductStage.close());

        Scene scene = new Scene(mainContainer, 500, 600);
        editProductStage.setScene(scene);
        editProductStage.show();
    }

    private void handleLogout() {
        // Create new HelloController with current stage
        HelloController controller = new HelloController(stage);
        // Show login screen which will set up all button handlers properly
        controller.showLoginScreen();

    }

}
