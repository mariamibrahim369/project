package sample.demo3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*\nWelcome to E-Commerce");
        int choice;
        do {
            System.out.println("[1] Register as Admin\n" +
                    "[2] Register as Customer\n" +
                    "[3] Login as Admin\n" +
                    "[4] Login as Customer\n" +
                    "[5] Exit");
            choice = scanner.nextInt();
            while (choice < 1 || choice > 5) {
                System.out.println("Enter a valid number (1-5)");
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    Admin admin = validateRegisterAdmin(scanner);
                    Database.addAdmin(admin);
                    System.out.println(admin.toString());
                    break;
                case 2:
                    Customer customer = validateRegisterCustomer(scanner);
                    Database.addCustomer(customer);
                    System.out.println(customer.toString());
                    System.out.println("Number of customers in the database is: " + Database.customerCount);
                    break;
                case 3:
                    Admin tempAdminLogin = validateAdminLogin(scanner);
                    if (tempAdminLogin != null) {
                        tempAdminLogin.adminOptions(scanner);
                    }
                    break;
                case 4:
                    Customer tempCustomerLogin = validateCustomerLogin(scanner);
                    if (tempCustomerLogin != null) {
                        tempCustomerLogin.customerOptions(scanner);
                    }
                    break;

                case 5:
                    System.out.println("You're exiting the program");
                    return;
            }
        } while (choice != 5);
    }

    public static Admin validateRegisterAdmin(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter username");
        String username = scanner.nextLine();
        while (true) {
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Enter again:");
            } else if (!Character.isLetter(username.charAt(0))) {
                System.out.println("Username must start with a letter. Enter again:");
            } else if (username.equals(username.toUpperCase())) {
                System.out.println("Username cannot be all uppercase letters. Enter again:");
            } else {
                break;
            }
            username = scanner.nextLine();
        }

        System.out.println("Enter password");
        String password = scanner.nextLine();
        while (password.length() < 5) {
            System.out.println("Password must be at least 5 characters long. Enter again:");
            password = scanner.nextLine();
        }

        System.out.println("Enter your date of birth in the following format: (dd/MM/yyyy)");
        String dateofbirth = scanner.nextLine();
        while (true) {
            if (dateofbirth.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate parsedDate = LocalDate.parse(dateofbirth, formatter);

                    if (parsedDate.isAfter(LocalDate.now())) {
                        System.out.println("Date of birth cannot be in the future. Enter again:");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date. Ensure it's a real date and use the format (dd/MM/yyyy):");
                }
            } else {
                System.out.println("Invalid format. Enter again (dd/MM/yyyy):");
            }
            dateofbirth = scanner.nextLine();
        }

        System.out.println("Enter your role");
        String role = scanner.nextLine();
        System.out.println("Enter your working hours");
        double workingHours = scanner.nextDouble();

        return new Admin(username, password, dateofbirth, role, workingHours);
    }

    public static Customer validateRegisterCustomer(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter username");
        String username = scanner.nextLine();
        while (true) {
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty. Enter again:");
            } else if (!Character.isLetter(username.charAt(0))) {
                System.out.println("Username must start with a letter. Enter again:");
            } else if (username.equals(username.toUpperCase())) {
                System.out.println("Username cannot be all uppercase letters. Enter again:");
            } else {
                break;
            }
            username = scanner.nextLine();
        }

        System.out.println("Enter password");
        String password = scanner.nextLine();
        while (password.length() < 5) {
            System.out.println("Password must be at least 5 characters long. Enter again:");
            password = scanner.nextLine();
        }

        System.out.println("Enter your date of birth in the following format: (dd/MM/yyyy)");
        String dateofbirth = scanner.nextLine();
        while (true) {
            if (dateofbirth.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                    LocalDate parsedDate = LocalDate.parse(dateofbirth, formatter);

                    if (parsedDate.isAfter(LocalDate.now())) {
                        System.out.println("Date of birth cannot be in the future. Enter again:");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date. Ensure it's a real date and use the format (dd/MM/yyyy):");
                }
            } else {
                System.out.println("Invalid format. Enter again (dd/MM/yyyy):");
            }
            dateofbirth = scanner.nextLine();
        }

        System.out.println("Enter gender (Male/Female)");
        String genderInput = scanner.nextLine();
        Customer.Gender gender;
        while (true) {
            try {
                gender = Customer.Gender.valueOf(genderInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid gender. Enter 'Male' or 'Female':");
                genderInput = scanner.nextLine();
            }
        }

        System.out.println("Enter your address");
        String address = scanner.nextLine();
        System.out.println("Enter your balance");
        double balance = scanner.nextDouble();

        return new Customer(username, password, dateofbirth, balance, address, gender);

    }
    public static Admin validateAdminLogin(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();

        for (Admin admin : Database.admins) {
            if (admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                System.out.println("You're successfully logged in!");
                return admin;
            }

        }
        System.out.println("Username or password is incorrect.");
        return null;

    }
    public static Customer validateCustomerLogin(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter username");
        String username = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();

        for (Customer customer : Database.customers) {
            if (customer != null && customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                System.out.println("You're successfully logged in!");
                return customer;
            }
        }

        System.out.println("Username or password is incorrect.");
        return null;
    }




}

