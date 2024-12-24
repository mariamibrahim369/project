package sample.demo3;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.*;
public class Admin extends Person {
    private String role;
    private double workingHours;

    Admin(String username, String password, String dateofbirth, String role, double workingHours) {
        super(username, password, dateofbirth);
        this.role = role;
        this.workingHours = workingHours;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    public void adminOptions(Scanner scanner) {
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("[1]Display users\n[2] Add product\n[3]Display products\n[4] Edit product\n[5] Logout");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    displayUsers();
                    break;
                case 2:
                    addProducttoStock(scanner);
                    break;

                case 3:
                    displayProducts();
                    break;

                case 4:
                    editProducts(scanner);
                    break;
                case 5:
                    System.out.println("Logging out");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (true);
    }


    public static void displayUsers() {
        System.out.println("List of all users: ");
        for (int i = 0; i < Database.customers.length && Database.customers[i] != null; i++) {
            System.out.println(Database.customers[i]);
        }
    }


    public void addProducttoStock(Scanner scanner) {
        System.out.println("Enter the product name you want to add to stock:");
        String productName =" ";
        boolean validateproductname=false;
        while(!validateproductname){
            try{
                productName=scanner.nextLine();
                if (productName.isEmpty()) {
                    System.out.println("Product name cannot be empty. Please enter a valid product name.");
                } else if (!productName.matches("[a-zA-Z ]+")) {
                    System.out.println("Product name can only contain letters and spaces. Please enter a valid name.");
                } else {
                    validateproductname = true;
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter a valid name.");
            }
        }
        Products existingProduct = null;
        for (Products product : Database.products) {
            if (product != null && product.getProductname().equalsIgnoreCase(productName)) {
                existingProduct = product;
                break;
            }
        }
        if (existingProduct != null) {
            System.out.println("Product found in stock.");
            System.out.println("Current quantity: " + existingProduct.getQuantity());
            System.out.println("Enter the quantity to add:");
            int addquantity = 0;
            boolean validQuantity = false;
            while (!validQuantity) {
                try {
                    addquantity = scanner.nextInt();
                    scanner.nextLine();
                    if (addquantity < 0) {
                        System.out.println("Quantity cannot be negative. Please enter a valid quantity:");
                    } else {
                        validQuantity = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number for quantity.");
                    scanner.nextLine();
                }
            }

            existingProduct.setQuantity(existingProduct.getQuantity() + addquantity);
            System.out.println("Quantity updated successfully. New quantity: " + existingProduct.getQuantity());

        } else {
            System.out.println("Product not found in stock. Adding a new product:");
            double price = 0.0;
            boolean validPrice = false;
            while (!validPrice) {
                try {
                    System.out.println("Enter the product price you want to add to stock:");
                    price = scanner.nextDouble();
                    scanner.nextLine();
                    if (price < 0) {
                        System.out.println("Price cannot be negative. Please enter a valid price:");
                    } else {
                        validPrice = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number for price.");
                    scanner.nextLine();
                }
            }
            int quantity = 0;
            boolean validQuantity = false;
            while (!validQuantity) {
                try {
                    System.out.println("Enter the product quantity you want to add to stock:");
                    quantity = scanner.nextInt();
                    scanner.nextLine();
                    if (quantity < 0) {
                        System.out.println("Quantity cannot be negative. Please enter a valid quantity:");
                    } else {
                        validQuantity = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number for quantity.");
                    scanner.nextLine();
                }
            }
            System.out.println("Enter the product category you want to add to stock:");
            String category = null;
            boolean validCategory = false;

            while (!validCategory) {
                try {
                    category = scanner.nextLine();
                    if (category.matches(".*\\d.*")) {
                        throw new IllegalArgumentException("Category should not contain numbers. Please enter a valid category:");
                    } else if (category.isEmpty()) {
                        throw new IllegalArgumentException("Category cannot be empty. Please enter a valid category:");
                    }
                    validCategory = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            Database.addProduct(new Products(productName, price, category, quantity, existingProduct.getImageURL()));
            System.out.println("Product added successfully.");
        }
    }

    public void editProducts(Scanner scanner){
        System.out.println("Enter the product name you want to edit ");
        String pname=scanner.nextLine();
        Products exist= null;
        for(Products product : Database.products) {
            if (product != null && product.getProductname().equalsIgnoreCase(pname)) {
                exist = product;
                break;
            }
        }
        if(exist!= null){
            System.out.println("Product found in stock");
            boolean editAdmin = true;
            while (editAdmin) {
                System.out.println("\nSelect an option: ");
                System.out.println("[1] Name\n[2] Price\n[3] Quantity\n[4] Category\n[5] Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new product name: ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty() && newName.matches("[a-zA-Z ]+")) {
                            exist.setProductname(newName);
                            System.out.println("Product name updated successfully.");
                        } else {
                            System.out.println("Invalid name. Please try again.");
                        }
                        break;
                    case 2:
                        System.out.println("Enter the new product price:");
                        try {
                            double newPrice = scanner.nextDouble();
                            scanner.nextLine();
                            if (newPrice >= 0) {
                                exist.setProductprice(newPrice);
                                System.out.println("Product price updated successfully.");
                            } else {
                                System.out.println("Price cannot be negative.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid price.");
                            scanner.nextLine();
                        }
                        break;
                    case 3:
                        System.out.println("Enter the new product quantity:");
                        try {
                            int newQuantity = scanner.nextInt();
                            scanner.nextLine();
                            if (newQuantity >= 0) {
                                exist.setQuantity(newQuantity);
                                System.out.println("Product quantity updated successfully.");
                            } else {
                                System.out.println("Quantity cannot be negative.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid quantity.");
                            scanner.nextLine();
                        }
                        break;
                    case 4:
                        System.out.println("Enter the new product category:");
                        String newCategory = scanner.nextLine();
                        if (!newCategory.isEmpty()) {
                            exist.setProdcategory(newCategory);
                            System.out.println("Product category updated successfully.");
                        } else {
                            System.out.println("Invalid category. Please try again.");
                        }
                        break;
                    case 5:
                        System.out.println("Exiting edit mode.");
                        editAdmin = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Product not found in the Stock.");
            return;

        }
    }

    public void displayProducts() {
        System.out.println("\nList of Products in Stock:");
        for (Products product : Database.products) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }



    public String toString() {
        return super.toString() + "\nRole: " + getRole() + "\nWorking Hours: " + getWorkingHours();

    }
}










