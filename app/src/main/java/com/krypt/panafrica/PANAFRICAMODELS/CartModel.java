package com.krypt.panafrica.PANAFRICAMODELS;


public class CartModel {

    private final String weight;
    private final String productName;

    private final String price;

    private final String itemID;
    private final String subToatl;

    public CartModel(String weight, String productName, String price, String itemID, String subToatl) {
        this.weight = weight;
        this.productName = productName;
        this.price = price;
        this.itemID = itemID;
        this.subToatl = subToatl;
    }

    public String getWeight() {
        return weight;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getItemID() {
        return itemID;
    }

    public String getSubToatl() {
        return subToatl;
    }


}
