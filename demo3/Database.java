package sample.demo3;

public class Database {
    public static Customer[] customers = new Customer[10];
    public static Admin[] admins = new Admin[50];
    public static Products[] products = new Products[50];

    static int customerCount = 0;
    static int adminCount = 0;
    static int productCount = 0;

    public static void addCustomer(Customer customer) {
        if (customerCount < customers.length) {
            customers[customerCount] = customer;
            customerCount++;
        } else {
            System.out.println("No more space for any new customers.");
        }
    }

    public static void addAdmin(Admin admin) {
        if (adminCount < admins.length) {
            admins[adminCount] = admin;
            adminCount++;
        } else {
            System.out.println("No more space to add any more admins.");
        }
    }

    public static void addProduct(Products product) {
        if (productCount < products.length) {
            products[productCount] = product;
            productCount++;
        } else {
            System.out.println("No more space to add new products.");
        }
    }

    /**
     * Validate admin login credentials.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     * @return The matching Admin object if credentials are valid; otherwise, null.
     */
    public static Admin validateAdminLogin(String username, String password) {
        for (int i = 0; i < adminCount; i++) {
            Admin admin = admins[i];
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return admin; // Admin credentials are valid
            }
        }
        return null; // Admin credentials are invalid
    }

    static {
        addCustomer(new Customer("john_doe", "password123", "1/5/2005", 500.0, "123 Elm Street", Customer.Gender.Male));
        addCustomer(new Customer("jane_smith", "securePass", "2/6/2005", 300.0, "456 Maple Avenue", Customer.Gender.Female));

        addAdmin(new Admin("admin1", "adminPass", "12/5/2005", "Manager", 8));
        addAdmin(new Admin("admin2", "adminPass2", "14/5/2005", "Supervisor", 6));

        addProduct(new Products("Laptop", 100.0, "Electronics", 50, "/sample/demo3/laptop.jpg"));
        addProduct(new Products("Java Programming Book", 40.0, "Books", 100, "/sample/demo3/book.jpeg"));
        addProduct(new Products("TV", 150.0, "Home Appliances", 20, "/sample/demo3/tv.jpeg"));
        addProduct(new Products("Mouse", 35.0, "Electronics", 50, "/sample/demo3/mouse.jpg"));
    }
    }
