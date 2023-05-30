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

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView name;
    SharedPreferences sp;

    Button logout;

    RecyclerView categoryRecyclerview;

    String[] categoryNameArray = {"Food & Drink","Fashion","Beauty","Travel","Health","Bakery"};
    int[] categoryImageArray = {R.drawable.food_drink,R.drawable.fashion,R.drawable.beauty,R.drawable.travel,R.drawable.health,R.drawable.bakery};

    RecyclerView productRecyclerview;

    String[] productNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productPriceArray = {"10000","2000","150","1500","100"};
    String[] productUnitArray = {"Package","Pair","500 GM","Box","Packet"};

    ArrayList<ProductList> productArrayList;

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

        setCategoryData();
        setProductData();
    }

    private void setCategoryData() {
        categoryRecyclerview = findViewById(R.id.home_category);
        categoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        categoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter catAdapter = new CategoryAdapter(HomeActivity.this,categoryNameArray,categoryImageArray);
        categoryRecyclerview.setAdapter(catAdapter);
    }

    private void setProductData() {
        productRecyclerview = findViewById(R.id.home_product_recyclerview);
        productRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        productRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productRecyclerview.setNestedScrollingEnabled(false);

        productArrayList = new ArrayList<>();
        for(int i=0;i<productNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setImage(productImageArray[i]);
            list.setPrice(productPriceArray[i]);
            list.setUnit(productUnitArray[i]);
            productArrayList.add(list);
        }
        ProductAdapter prodAdapter = new ProductAdapter(HomeActivity.this,productArrayList);
        productRecyclerview.setAdapter(prodAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}