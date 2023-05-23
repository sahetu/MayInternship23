package may.internship;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Min. 6 Character Required");
                }
                else {
                    if (email.getText().toString().trim().equals("admin@gmail.com") && password.getText().toString().trim().equalsIgnoreCase("Admin@123")) {
                        System.out.println("Login Successfully");
                        Log.d("LOGIN", "Login Successfully");
                        Log.e("LOGIN", "Login Successfully");

                        //Toast.makeText(MainActivity.this, "Login Successfuly", Toast.LENGTH_LONG).show();
                        new CommonMethod(MainActivity.this, "Login Successfully");
                        /*Snackbar snackbar = Snackbar.make(relativeLayout,"Login Successfully",Snackbar.LENGTH_LONG);
                        snackbar.show();*/
                        //Snackbar.make(view, "Login Successfully", Snackbar.LENGTH_LONG).show();
                        new CommonMethod(view,"Login Successfully");

                        /*Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);*/
                        new CommonMethod(MainActivity.this,HomeActivity.class);

                        //startActivity(new Intent(MainActivity.this,HomeActivity.class));

                    }
                    else{
                        //Toast.makeText(MainActivity.this, "Login Unsuccessfuly", Toast.LENGTH_LONG).show();
                        new CommonMethod(MainActivity.this, "Login Unsuccessfully");
                        //Snackbar.make(view, "Login Unsuccessfully", Snackbar.LENGTH_LONG).show();
                        new CommonMethod(view,"Login Unsuccessfully");
                    }
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