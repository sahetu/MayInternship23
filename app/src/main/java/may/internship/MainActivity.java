package may.internship;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    //RelativeLayout relativeLayout;
    EditText email,password;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextView createAccount;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        //relativeLayout = findViewById(R.id.main_relative);
        login = findViewById(R.id.main_login);
        email = findViewById(R.id.main_email);
        password = findViewById(R.id.main_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Email Id Required");
                }
                /*else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }*/
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Min. 6 Character Required");
                }
                else {
                    String loginQuery = "SELECT * FROM RECORD WHERE (EMAIL='"+email.getText().toString()+"' OR CONTACT='"+email.getText().toString()+"') AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(loginQuery,null);
                    if(cursor.getCount()>0){

                        if(cursor.moveToFirst()){
                            while (cursor.moveToNext()){
                                String sName = cursor.getString(0);
                                String sEmail = cursor.getString(1);
                                String sContact = cursor.getString(2);
                                String sPassword = cursor.getString(3);
                                String sDob = cursor.getString(4);
                                String sGender = cursor.getString(5);
                                String sCity = cursor.getString(6);
                                Log.d("LOGIN_USER_DATA",sName+"\n"+sEmail+"\n"+sContact+"\n"+sPassword+"\n"+sDob+"\n"+sGender+"\n"+sCity);

                                System.out.println("Login Successfully");
                                Log.d("LOGIN", "Login Successfully");
                                Log.e("LOGIN", "Login Successfully");
                                new CommonMethod(MainActivity.this, "Login Successfully");
                                new CommonMethod(view,"Login Successfully");
                                //new CommonMethod(MainActivity.this,HomeActivity.class);
                                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("NAME",sName);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    }
                    else{
                        new CommonMethod(MainActivity.this, "Login Unsuccessfully");
                        new CommonMethod(view,"Login Unsuccessfully");
                    }
                    /*if (email.getText().toString().trim().equals("admin@gmail.com") && password.getText().toString().trim().equalsIgnoreCase("Admin@123")) {
                        System.out.println("Login Successfully");
                        Log.d("LOGIN", "Login Successfully");
                        Log.e("LOGIN", "Login Successfully");
                        new CommonMethod(MainActivity.this, "Login Successfully");
                        new CommonMethod(view,"Login Successfully");
                        new CommonMethod(MainActivity.this,HomeActivity.class);
                    }
                    else{
                        new CommonMethod(MainActivity.this, "Login Unsuccessfully");
                        new CommonMethod(view,"Login Unsuccessfully");
                    }*/
                }
            }
        });

        createAccount = findViewById(R.id.main_create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Use Explicite Intent
                /*Intent intent = new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);*/
                new CommonMethod(MainActivity.this,SignupActivity.class);
            }
        });

    }
}