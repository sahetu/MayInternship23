package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    TextView orderId,name,email,contact,address,city,paymentMethod;

    RecyclerView recyclerView;
    ArrayList<CartList> arrayList;

    int position = 0;
    SharedPreferences sp;
    SQLiteDatabase db;

    String[] productNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productPriceArray = {"10000","2000","150","1500","100"};
    String[] productUnitArray = {"Package","Pair","500 GM","Box","Packet"};
    String[] productDescriptionArray = {
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = openOrCreateDatabase("MayInternship", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

        String orderTableQuery = "CREATE TABLE IF NOT EXISTS SHIPPING(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),ADDRESS VARCHAR(255),CITY VARCHAR(100),PAYMENTMETHOD VARCHAR(50),TRANSACTIONID VARCHAR(255))";
        db.execSQL(orderTableQuery);

        orderId = findViewById(R.id.order_detail_id);
        name = findViewById(R.id.order_detail_name);
        email = findViewById(R.id.order_detail_email);
        contact = findViewById(R.id.order_detail_contact);
        address = findViewById(R.id.order_detail_address);
        city = findViewById(R.id.order_detail_city);
        paymentMethod = findViewById(R.id.order_detail_payment_method);

        position = Integer.parseInt(sp.getString(ConstantData.ORDER_POSITION,""));
        
        orderId.setText("Order Id : "+OrderHistoryActivity.arrayList.get(position).getId());
        name.setText(OrderHistoryActivity.arrayList.get(position).getName());
        email.setText(OrderHistoryActivity.arrayList.get(position).getEmail());
        contact.setText(OrderHistoryActivity.arrayList.get(position).getContact());
        address.setText(OrderHistoryActivity.arrayList.get(position).getAddress());
        city.setText(OrderHistoryActivity.arrayList.get(position).getCity());
        if(OrderHistoryActivity.arrayList.get(position).getPaymentMethod().equalsIgnoreCase(getResources().getString(R.string.online))) {
            paymentMethod.setText(OrderHistoryActivity.arrayList.get(position).getPaymentMethod()+" ("+OrderHistoryActivity.arrayList.get(position).getTransactionId()+")");
        }
        else{
            paymentMethod.setText(OrderHistoryActivity.arrayList.get(position).getPaymentMethod());
        }
        
        recyclerView = findViewById(R.id.order_detail_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(false);

        String selectQuery = "SELECT * FROM CART WHERE ORDERID='"+OrderHistoryActivity.arrayList.get(position).getId()+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                for (int i = 0; i < productNameArray.length; i++) {
                    if (cursor.getString(2).equalsIgnoreCase(productNameArray[i])) {
                        CartList list = new CartList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        list.setQty(cursor.getString(3));
                        arrayList.add(list);
                    }
                }
            }
            OrderDetailAdapter prodAdapter = new OrderDetailAdapter(OrderDetailActivity.this, arrayList);
            recyclerView.setAdapter(prodAdapter);
        }
    }
}