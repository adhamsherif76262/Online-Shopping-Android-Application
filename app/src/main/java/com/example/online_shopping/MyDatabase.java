package com.example.online_shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.online_shopping.Model.CartModel;
import com.example.online_shopping.Model.CategoryModel;
import com.example.online_shopping.Model.CustomerModel;
import com.example.online_shopping.Model.ProductModel;

public class MyDatabase extends SQLiteOpenHelper {

    final static String dataName = "Mydatabase";
    SQLiteDatabase database;

    public MyDatabase(@Nullable Context context) {
        super(context, dataName, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  customer (id integer primary key  autoincrement , name text not null, email text not null," +
                "password text not null, gender text not null, birthdate text , jop text )");

        db.execSQL("create table category (id integer primary key  autoincrement , name text not null )");

        db.execSQL("create table product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");

        db.execSQL("create table cart_items (id integer primary key autoincrement , productname text," +
                "productid integer,productprice integer)");

        db.execSQL("create table user_cart (id integer primary key autoincrement , username text , productname text," +
                "productid integer,productprice integer, prodcutqty integer)");

//        database.execSQL("create table orders (id integer primary key autoincrement , product_qty integer," +
//                "foreign key (product_id)references product(id), foreign key (customer_id)references customer(id))");
//
//        database.execSQL("create table final_Cart (id integer primary key autoincrement ,total_price integer,total_qty integer ," +
//                "foreign key(order_id)references orders(id))");
//        db.execSQL("create table orders (id integer primary key autoincrement , product_qty integer," +
//                "foreign key (product_id)references product (id), foreign key (customer_id)references customer (id))");
//
//        db.execSQL("create table final_Cart (id integer primary key autoincrement ,total_price integer," +
//                "total_qty integer , foreign key (order_id) references orders (id))");

//        db.execSQL("create table orders (id integer primary key autoincrement, productqty integer ," +
//                "foreign key (product_id)references product (id),foreign key (id)references customer (id))");

     //   database.execSQL("CREATE TABLE orders(id INTEGER PRIMARY KEY,\n" +
//                "  product_qty  INTEGER,\n" +
//                "  product_id  INTEGER,\n" +
//                "  customer_id INTEGER,\n" +
//                "  FOREIGN KEY (product_id) REFERENCES product (id),\n" +
//                "  FOREIGN KEY (customer_id) REFERENCES customer (id))");
//
//        database.execSQL("CREATE TABLE final_cart(id INTEGER PRIMARY KEY,\n" +
//                "  total_price  INTEGER,\n" +
//                "  total_qty  INTEGER,\n" +
//                "  order_id INTEGER,\n" +
//                "  FOREIGN KEY (order_id) REFERENCES orders (id))");
    }



    // zizo final edits
    public void AddToCartZizo(String ProductName,String User){
        database = getReadableDatabase();
        ContentValues values = new ContentValues();

        String[] Prodargs = {ProductName};
        String[] Custargs ={User};


        Cursor cursorProd=  database.rawQuery("select id from product where name =? ",Prodargs);

        Cursor cursorCust=  database.rawQuery("select id from customer where name =? ",Custargs);

        cursorProd.moveToFirst();
        cursorCust.moveToFirst();

        String[] args ={cursorProd.getString(0),cursorCust.getString(0)};

        Cursor orders = database.rawQuery("select product_qty from orders where product_id =? and customer_id =?",args);
//        Log.e("cart",cursorCust.getString(0));
//        Log.e("cart",cursorProd.getString(0));
        orders.moveToFirst();

        if(orders.getCount() > 0){
            String[] prod ={String.valueOf(cursorProd.getString(0))};
            int value = Integer.parseInt(orders.getString(0)) + 1;
            values.put("product_qty", value);
            database.update("orders",values,"product_id =?",prod);

//            Log.e("zizo",String.valueOf(orders.getString(0)));

        }
        else {
            if (cursorProd.getCount() > 0) {
                values.put("product_qty", 1);
                values.put("product_id", cursorProd.getString(0));
                values.put("customer_id", cursorCust.getString(0));
                database.insert("orders", null, values);
            }
//        }
        }

        database.close();
    }

    public Cursor GetCartOfZizo(String User){
        database = getReadableDatabase();
//        ContentValues values = new ContentValues();

        String[] UserArgs = {User};
        Cursor cursorCust=  database.rawQuery("select id from customer where name =? ",UserArgs);
        cursorCust.moveToFirst();


        String [] user={cursorCust.getString(0)};
//        String [] columns = {"product_id,customer_id,product_qty"};

        Cursor cursorCart=  database.rawQuery("select product_id,product_qty from orders where customer_id =? ",user);

//        Cursor cursor= database.query("orders",columns,null,null,null,null,null);

        if (cursorCart!=null)
            cursorCart.moveToFirst();

        return cursorCart;

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        onCreate(db);

    }

    public void DeleteCartItems(){
        database = getWritableDatabase();
        database.delete("cart_items","id > 0",null);
        database.close();
    }
    public void DeleteProducts(){
        database = getWritableDatabase();
        database.delete("product","id > 0",null);
        database.close();
    }

    public void DeleteAllCartItems(){
        database = getWritableDatabase();
        database.delete("orders","id > 0",null);
        database.close();
    }

    public void DeleteOrderItem(int id){
        database = getWritableDatabase();
        String[] newid= {String.valueOf(id)};
        database.execSQL("Delete from orders where product_id =?",newid);
        database.close();
    }

    // new delete Cart
    public void DeleteCart(){
        database = getWritableDatabase();
        database.delete("user_cart","id > 0",null);
        database.close();
    }



    public void insertCustomer(CustomerModel cust) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cust.getCustName());
        values.put("email", cust.getMail());
        values.put("password", cust.getPassword());
        values.put("birthdate", cust.getCustBirthDate());
        values.put("gender", cust.getGender());
        values.put("jop", cust.getCustJop());

        database.insert("customer", null, values);
        database.close();

    }



    public String IncreamentProductCart(int product_id) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();


        String[] id = {String.valueOf(product_id)};
        Cursor cursor = database.rawQuery("select product_qty from orders where product_id =?",id);
        cursor.moveToFirst();

        String qty = String.valueOf(cursor.getInt(0)+1);

        values.put("product_qty",qty);

        database.update("orders",values,"product_id =?",id);

//        database.insert("customer", null, values);
//        database.close();

        return cursor.getString(0);

    }

    public String DecreamentProductCart(int product_id) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();


        String[] id = {String.valueOf(product_id)};
        Cursor cursor = database.rawQuery("select product_qty from orders where product_id =?",id);
        cursor.moveToFirst();

        String qty = String.valueOf(cursor.getInt(0)-1);

        values.put("product_qty",qty);

        database.update("orders",values,"product_id =?",id);

//        database.insert("customer", null, values);
//        database.close();

        return cursor.getString(0);

    }



    // cart insert
    public void insertCartItem(String Name) {
        database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("productname", Name);
//        values.put("quantity", cart.getCart_quantity());
//        values.put("price", cart.getCart_price());

        database.insert("cart_items", null, values);
        database.close();

    }

    // new cart insert
    public void insertIntoCart(String Name,String username,String price) {
        database = getWritableDatabase();
//
        ContentValues values = new ContentValues();
        String[] args = {Name};

//        Cursor cursor = database.rawQuery("select price from product where name =? ", args);
        values.put("username", username);
        values.put("productname", Name);
        Integer newpreice = Integer.parseInt(price);
        values.put("productprice",newpreice);
//       values.put("productprice",cursor.getString(0));
//       values.put("quantity", cart.getCart_quantity());
//       values.put("price", cart.getCart_price());

        database.insert("user_cart", null, values);
        database.close();

    }


    public Cursor userLogin(String username, String pass) {
        database = getReadableDatabase();
        String[] args = {username, pass};
        //database.query("customer","id",raw,null,null,null,null,null);
        Cursor cursor = database.rawQuery("select id from customer where name =? and  password =? ", args);

        if (cursor != null)
            cursor.moveToFirst();


        database.close();

        return cursor;

    }

    public void newdb(){
//        database = getWritableDatabase();
//
//        database.execSQL("Drop table orders");
//        database.execSQL("Drop table final_cart");
//
//
//        database.execSQL("CREATE TABLE orders(id INTEGER PRIMARY KEY,\n" +
//                "  product_qty  INTEGER,\n" +
//                "  product_id  INTEGER,\n" +
//                "  customer_id INTEGER,\n" +
//                "  FOREIGN KEY (product_id) REFERENCES product (id),\n" +
//                "  FOREIGN KEY (customer_id) REFERENCES customer (id))");
//
//        database.execSQL("CREATE TABLE final_cart(id INTEGER PRIMARY KEY,\n" +
//                "  total_price  INTEGER,\n" +
//                "  total_qty  INTEGER,\n" +
//                "  order_id INTEGER,\n" +
//                "  FOREIGN KEY (order_id) REFERENCES orders (id))");






        //       String tableorders = "create table orders (id integer primary key autoincrement , product_qty integer," +
//                "foreign key (product_id)references product(id), foreign key (customer_id)references customer(id))";

//                database.execSQL("CREATE TABLE orders(id INTEGER PRIMARY KEY," +
//                        "  product_qty  INTEGER," +
//                        "  FOREIGN KEY (prod_id) REFERENCES product (id))");

//        database.execSQL("Drop table newtest2");
//        database.execSQL("Drop table timesheets");
//
//        database.execSQL("CREATE TABLE orders(Id INTEGER PRIMARY KEY," +
//                "  Product_Qty  INTEGER," +
//                "  User_Id INTEGER," +
//                "  FOREIGN KEY (Prod_Id) REFERENCES product (Id))");
//        database.execSQL("CREATE TABLE myorders(Id INTEGER PRIMARY KEY," +
//                "  Project_Id  INTEGER," +
//                "  Week_Id INTEGER," +
//                "  FOREIGN KEY (Product_Id) REFERENCES product (id))");

//        database.execSQL("CREATE TABLE orders(Id INTEGER PRIMARY KEY," +
//                "  Product_Qty  INTEGER," +
//                "  User_Id INTEGER," +
//                "  FOREIGN KEY (Product_Id) REFERENCES product (id))");

//        database.execSQL("CREATE TABLE final_cart(Id INTEGER PRIMARY KEY," +
//                "  Total_Price INTEGER," +
//                "  Total_Qty INTEGER," +
//                "  FOREIGN KEY (Order_Id) REFERENCES orders (id))");
        //
//        dat.execSQL("PRAGMA foreign_keys=ON");
//        database.execSQL("create table orders (id integer primary key autoincrement , product_qty Integer ," +
//                " product_id integer,customer_id integer,foreign key (product_id ) " +
//                "references product (id), foreign key (customer_id)references customer (id))");
//
//        database.execSQL("create table final_Cart (id integer primary key autoincrement ,total_price Integer," +
//                "total_qty integer ,order_id integer ,foreign key (order_id) references orders (id))");

//        database.execSQL("Drop table orders");
//        database.execSQL("create table orders (id integer primary key autoincrement, productqty integer)");
//
//        String TASK_TABLE_CREATE = "create table orders (id integer primary key autoincrement, "
//                + "product_qty integer, "
//                + " product_id)";

//        String[]fields={"id"};
//        Cursor cursor = database.query("product",fields,null,null,null,null,null);
//        Cursor cursor = database.query("select password from customer where email =?", args);

//        database.execSQL("create table orders (id INTEGER PRIMARY KEY AUTOINCREMENT, product_qty INTEGER, " +
//                "FOREIGN KEY (product_id) REFERENCES product (_id)," +
//        "FOREIGN KEY (USER_ID) REFERENCES customer (_id))");
//        database.execSQL(TASK_TABLE_CREATE);
//        database.execSQL("create table user_cart (id integer primary key autoincrement , username text , productname text," +
//                "productid integer,productprice integer, prodcutqty integer)");
//        database = getWritableDatabase();
//        database.execSQL("create table orders (id integer primary key autoincrement , product_qty integer," +
//                "foreign key (product_id)references product(id), foreign key (customer_id)references customer(id))");
//
//        database.execSQL("create table final_Cart (id integer primary key autoincrement ,total_price integer,total_qty integer ," +
//                "foreign key(order_id)references orders(id))");
        database.close();


//        database.execSQL("ALTER TABLE  cart_items   ADD COLUMN username text ");
//        database.execSQL("ALTER TABLE  cart_items   ADD COLUMN prodcutqty integer ");
//        onCreate(database);
    }

    public String getPassword(String mail) {
        database = getReadableDatabase();
        String[] args = {mail};
        Cursor cursor = database.rawQuery("select password from customer where email =?", args);
        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;
    }


    public void insertProduct(ProductModel product){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();

       values.put("name",product.getProName());
        values.put("image",product.getProImage());
        values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());

        database.insert("product",null,values);

        database.close();
    }

    public Cursor getProducts(){
        database=getReadableDatabase();
        String[]fields={"name","image","price","quantity","cate_id"};
       Cursor cursor= database.query("product",fields,null,null,null,null,null);

       if (cursor!=null)
           cursor.moveToFirst();

      // database.close();

       return cursor;


    }

    public Cursor getWomenProducts(){
        database=getReadableDatabase();
        String [] args={"2"};
        Cursor cursor=  database.rawQuery("select name,image,price,quantity,cate_id from product where cate_id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;


    }

    public Cursor getMenProducts(){
        database=getReadableDatabase();
        String [] args={"1"};
        Cursor cursor=  database.rawQuery("select name,image,price,quantity,cate_id from product where cate_id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;


    }


    public Cursor getCartItems(){
        database=getReadableDatabase();
        String[]fields={"productname"};
        Cursor cursor= database.query("cart_items",fields,null,null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        // database.close();

        return cursor;
    }

    // new Get Cart
    public Cursor getCart(String name){
//        database=getReadableDatabase();
//        String[]fields={"username","productname","productprice"};
//        Cursor cursor= database.query("user_cart",fields,"username ="+name,null,null,null,null);
//
//        if (cursor!=null)
//            cursor.moveToFirst();
//
//        // database.close();
//
//        return cursor;
        database=getReadableDatabase();
        String []args={name};
        String[]fields={"username","productname","productprice"};
        Cursor cursor=  database.rawQuery("select Distinct username , productname , productprice from user_cart where username =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }

    public void insertCategory(CategoryModel cate){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",cate.getCat_name());
        database.insert("category",null,values);
        database.close();
    }
    public Cursor getCategory(){
        database=getReadableDatabase();
        String []fields={"id","name"};
       Cursor cursor= database.query("category",fields,null,null,null,null,null);

       if (cursor.getCount()>0)
            cursor.moveToFirst();

       database.close();

       return cursor;
    }
    public Cursor getProductbyCategor(String cate){
        database=getReadableDatabase();
        String []args={cate};
      Cursor cursor=  database.rawQuery("select * from product where cate_id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    public String getProductbyId(int id){
        database=getReadableDatabase();
        String []args={String.valueOf(id)};
        Cursor cursor=  database.rawQuery("select name from product where id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor.getString(0);

    }

    public Cursor getProductByName(String name){
        database=getReadableDatabase();
        String []args={name};
        Cursor cursor=  database.rawQuery("select * from product where name =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    public String getProductIDByName(String name){
        database=getReadableDatabase();
        String []args={name};
        Cursor cursor=  database.rawQuery("select id from product where name =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor.getString(0);

    }

    public Cursor getProductbyId(String id){
        database=getReadableDatabase();
        String []args={id};
        Cursor cursor=  database.rawQuery("select * from product where id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    // get Product Price
    public Cursor getProductPrice(String name){
        database=getReadableDatabase();
        String []args={name};
        Cursor cursor=  database.rawQuery("select price from product where name =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }

    public String getSingleOrderPice(String name, int qty){
        database=getReadableDatabase();
        String []args={name};
        Cursor cursor=  database.rawQuery("select price from product where name =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();


        Integer Price = qty * cursor.getInt(0);

        return String.valueOf(Price);
    }

    public String getCatId(String name ){
        database=getReadableDatabase();
        String[]args={name};
        Cursor cursor=database.rawQuery("select id from category where name =?",args);

        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;

    }
}
