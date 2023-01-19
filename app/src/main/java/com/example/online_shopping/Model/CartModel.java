package com.example.online_shopping.Model;


public class CartModel {

        private int cart_id;
        private int products_quantity;
        private String productname;
        private int Total_Price;

        public CartModel() {
        }



    public CartModel(String productname) {
        this.productname = productname;
    }

        public int getCart_id() {
            return cart_id;
        }

        public void setCart_id(int cat_id) {
            this.cart_id = cat_id;
        }
        public void setCart_prod_name(String productname) {
        this.productname = productname;
    }
        public void setQty(int products_quantity) {
            this.products_quantity =products_quantity;
        }
        public void setPrice(int Total_Price) {
        this.Total_Price = Total_Price;
    }



    public int getCart_quantity() {
            return products_quantity;
        }

        public int getCart_price() {
        return Total_Price;
    }

        public String getProductname(){return productname;}


//        public void setCart_name(String cat_name) {
//            this.cat_name = cat_name;
//        }

}
