package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static ArrayList<OrderList> arrayList;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

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

        recyclerView = findViewById(R.id.order_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String selectQuery = "SELECT * FROM SHIPPING WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' ORDER BY ID DESC";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                OrderList list = new OrderList();
                list.setId(cursor.getString(0));
                list.setName(cursor.getString(1));
                list.setEmail(cursor.getString(2));
                list.setContact(cursor.getString(3));
                list.setAddress(cursor.getString(4));
                list.setCity(cursor.getString(5));
                list.setPaymentMethod(cursor.getString(6));
                list.setTransactionId(cursor.getString(7));
                arrayList.add(list);
            }
            OrderAdapter adapter = new OrderAdapter(OrderHistoryActivity.this,arrayList);
            recyclerView.setAdapter(adapter);
        }

    }
}