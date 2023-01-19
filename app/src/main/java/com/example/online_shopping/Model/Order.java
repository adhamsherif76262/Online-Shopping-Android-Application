package com.example.online_shopping.Model;

public class Order  {

    private int id,product_qty,product_id,customer_id;

    public Order(int product_id, int product_qty,int customer_id) {
        this.product_id = product_id;
        this.product_qty = product_qty;
        this.customer_id = customer_id;


    }


    public int getProduct_qty() {
        return product_qty;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void serProduct_qty() {
        this.product_qty=product_qty;
    }

    public void setProduct_id() {
        this.product_id = product_id;
    }

    public void setCustomer_id() {
        this.customer_id = customer_id;
    }



    //    public void setCustId(int custId) {
//        this.custId = custId;
//    }

    //    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }

//    public void setOrderAddress(String orderAddress) {
//        this.orderAddress = orderAddress;
//    }
//
//    public Date getOrderDate() {
//        return orderDate;
//    }
//
//    public void setOrderDate(Date orderDate) {
//        this.orderDate = orderDate;
//    }
}
