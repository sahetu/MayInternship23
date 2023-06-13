package may.internship;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ShippingActivity extends AppCompatActivity {

    EditText name, email, contact, address;
    Button pay;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    //RadioButton male,female;
    RadioGroup paymentType;

    Spinner spinner;
    //String[] cityArray = {"Ahmedabad","Vadodara","Surat","Rajkot"};
    ArrayList<String> arrayList;

    SQLiteDatabase db;

    String sPaymentType, sCity;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        sp = getSharedPreferences(ConstantData.PREF, MODE_PRIVATE);

        db = openOrCreateDatabase("MayInternship", MODE_PRIVATE, null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

        String orderTableQuery = "CREATE TABLE IF NOT EXISTS SHIPPING(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT INT(10),ADDRESS VARCHAR(255),CITY VARCHAR(100),PAYMENTMETHOD VARCHAR(50),TRANSACTIONID VARCHAR(255))";
        db.execSQL(orderTableQuery);

        name = findViewById(R.id.shipping_name);
        email = findViewById(R.id.shipping_email);
        contact = findViewById(R.id.shipping_contact);
        address = findViewById(R.id.shipping_address);

        spinner = findViewById(R.id.shipping_city);

        arrayList = new ArrayList<>();
        arrayList.add("Ahmedabad");
        arrayList.add("Anand");
        arrayList.add("Vadodara");
        arrayList.add("Test");
        arrayList.add("Surt");
        arrayList.add("Rajkot");

        arrayList.remove(3);
        arrayList.set(3, "Surat");

        arrayList.add(0, "Gandhinagar");

        ArrayAdapter adapter = new ArrayAdapter(ShippingActivity.this, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String selectedCity = cityArray[i];
                sCity = arrayList.get(i);
                new CommonMethod(ShippingActivity.this, sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*male = findViewById(R.id.shipping_male);
        female = findViewById(R.id.shipping_female);*/

        paymentType = findViewById(R.id.shipping_payment_type);
        paymentType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(i);
                new CommonMethod(ShippingActivity.this, radioButton.getText().toString());
                sPaymentType = radioButton.getText().toString();
            }
        });

        name.setText(sp.getString(ConstantData.NAME, ""));
        email.setText(sp.getString(ConstantData.EMAIL, ""));
        contact.setText(sp.getString(ConstantData.CONTACT, ""));

        contact.setEnabled(false);

        pay = findViewById(R.id.shipping_button);

        pay.setText("Pay " + ConstantData.PRICE_SYMBOL + sp.getString(ConstantData.CART_TOTAL, ""));

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("")) {
                    name.setError("Name Required");
                } else if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Email Id Required");
                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Valid Email Id Required");
                } else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Contact No. Required");
                } else if (contact.getText().toString().length() < 10) {
                    contact.setError("Valid Contact No. Required");
                } else if (address.getText().toString().trim().equals("")) {
                    address.setError("Address Required");
                } else if (paymentType.getCheckedRadioButtonId() == -1) {
                    new CommonMethod(ShippingActivity.this, "Please Select Payment Type");
                } else {
                    if (sPaymentType.equalsIgnoreCase(getResources().getString(R.string.cod))) {
                        proceedForOrder(sPaymentType, "");
                    } else {

                    }
                }
            }
        });

    }

    private void proceedForOrder(String sPaymentType, String sTransactionId) {
        String insertQuery = "INSERT INTO SHIPPING VALUES(NULL,'" + name.getText().toString() + "','" + email.getText().toString() + "','" + contact.getText().toString() + "','" + address.getText().toString() + "','" + sCity + "','" + sPaymentType + "','" + sTransactionId + "')";
        db.execSQL(insertQuery);

        String selectQuery = "SELECT MAX(ID) FROM SHIPPING";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String sOrderId = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                sOrderId = cursor.getString(0);
                //Log.d("SHIPPING_ORDER_ID",sOrderId);
            }
        }

        if (sOrderId.equalsIgnoreCase("")) {

        } else {
            String updateQuery = "UPDATE CART SET ORDERID='" + sOrderId + "' WHERE CONTACT='" + sp.getString(ConstantData.CONTACT, "") + "' AND ORDERID='0'";
            db.execSQL(updateQuery);
            new CommonMethod(ShippingActivity.this, "Order Placed Successfully");
            new CommonMethod(ShippingActivity.this, DashboardActivity.class);
        }
    }
}