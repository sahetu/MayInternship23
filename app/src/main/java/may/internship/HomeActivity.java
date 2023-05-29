package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView name;
    SharedPreferences sp;

    Button logout;

    RecyclerView categoryRecyclerview;

    String[] categoryNameArray = {"Food & Drink","Fashion","Beauty","Travel","Health","Bakery"};
    int[] categoryImageArray = {R.drawable.food_drink,R.drawable.fashion,R.drawable.beauty,R.drawable.travel,R.drawable.health,R.drawable.bakery};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        logout = findViewById(R.id.home_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sp.edit().remove(ConstantData.CONTACT).commit();
                sp.edit().clear().commit();
                new CommonMethod(HomeActivity.this,MainActivity.class);
            }
        });

        name = findViewById(R.id.home_name);

        /*Bundle bundle = getIntent().getExtras();
        name.setText("Welcome "+bundle.getString("NAME"));*/

        name.setText("Welcome "+sp.getString(ConstantData.NAME,""));

        categoryRecyclerview = findViewById(R.id.home_category);
        categoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        categoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter catAdapter = new CategoryAdapter(HomeActivity.this,categoryNameArray,categoryImageArray);
        categoryRecyclerview.setAdapter(catAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}