package com.example.online_shopping.Model;

public class FinalCartModel {

    private int id,total_price,total_qty,order_id;

    public FinalCartModel(int total_price, int total_qty,int order_id) {
        this.total_price = total_price;
        this.total_qty = total_qty;
        this.order_id = order_id;


    }


    public int getProduct_qty() {
        return total_price;
    }

    public int getProduct_id() {
        return total_qty;
    }

    public int getCustomer_id() {
        return order_id;
    }

}
