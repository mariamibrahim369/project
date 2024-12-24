package sample.demo3;

public class Products {
    private String productname;
    private double productprice;
    private String prodcategory;
    private int quantity;
    private String imageURL;

    Products(String productname,double productprice, String prodcategory,int quantity,String imageURL){
        this.productname =productname ;
        this.productprice = productprice;
        this.quantity = quantity;
        this.prodcategory = prodcategory;
        this.imageURL=imageURL;
    }
    public void setImageURL(String imageURL){
        this.imageURL=imageURL;
    }
    public String getImageURL(){
        return imageURL;
    }
    public void setProdcategory(String prodcategory) {

        this.prodcategory = prodcategory;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
    public void setQuantity(int quantity ){
        this.quantity = quantity;
    }

    public void setProductprice(double productprice) {
        this.productprice = productprice;
    }
    public String getProdcategory() {
        return prodcategory;
    }

    public String getProductname() {
        return productname;
    }

    public double getProductprice() {
        return productprice;
    }
    public int getQuantity(){
        return quantity;
    }


    public void reduceStock(int quantity) {
        if (quantity <= 0 || quantity > this.quantity) {
            throw new IllegalArgumentException("Insufficient stock for " + productname);
        }
        this.quantity -= quantity;
    }

    public String toString() {
        return "Product Name: " + productname +
                ", Price: " + productprice +
                ", Category: " + prodcategory+
                ", Quantity: " + quantity;
    }

}
