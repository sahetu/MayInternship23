package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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

    Button logout,profile,changePassword;

    RecyclerView categoryRecyclerview;

    String[] categoryNameArray = {"Food & Drink","Fashion","Beauty","Travel","Health","Bakery"};
    int[] categoryImageArray = {R.drawable.food_drink,R.drawable.fashion,R.drawable.beauty,R.drawable.travel,R.drawable.health,R.drawable.bakery};

    RecyclerView productRecyclerview;

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

    ArrayList<ProductList> productArrayList;
    TextView productViewAll;

    RecyclerView productTrendingRecyclerview;
    String[] productTrendingNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productTrendingImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productTrendingPriceArray = {"10000","2000","150","1500","100"};
    String[] productTrendingUnitArray = {"Package","Pair","500 GM","Box","Packet"};
    String[] productTrendingDescriptionArray = {
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };

    ArrayList<ProductList> productTrendingArrayList;
    TextView trendingProductViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        changePassword = findViewById(R.id.home_change_password);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(HomeActivity.this, ChangePasswordActivity.class);
            }
        });

        profile = findViewById(R.id.home_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(HomeActivity.this, ProfileActivity.class);
            }
        });

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
        setProductTrendingData();
    }

    private void setCategoryData() {
        categoryRecyclerview = findViewById(R.id.home_category);
        categoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        categoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter catAdapter = new CategoryAdapter(HomeActivity.this,categoryNameArray,categoryImageArray);
        categoryRecyclerview.setAdapter(catAdapter);
    }

    private void setProductData() {

        productViewAll = findViewById(R.id.home_product_view_all);
        productViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(HomeActivity.this,ProductActivity.class);
            }
        });

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
            list.setDescription(productDescriptionArray[i]);
            productArrayList.add(list);
        }
        ProductAdapter prodAdapter = new ProductAdapter(HomeActivity.this,productArrayList);
        productRecyclerview.setAdapter(prodAdapter);
    }

    private void setProductTrendingData() {

        trendingProductViewAll = findViewById(R.id.home_product_trending_view_all);
        trendingProductViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(HomeActivity.this,TrendingProductActivity.class);
            }
        });

        productTrendingRecyclerview = findViewById(R.id.home_product_trending_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        productTrendingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productTrendingRecyclerview.setNestedScrollingEnabled(false);

        productTrendingArrayList = new ArrayList<>();
        for(int i=0;i<productTrendingNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productTrendingNameArray[i]);
            list.setImage(productTrendingImageArray[i]);
            list.setPrice(productTrendingPriceArray[i]);
            list.setUnit(productTrendingUnitArray[i]);
            list.setDescription(productTrendingDescriptionArray[i]);
            productTrendingArrayList.add(list);
        }
        ProductTrendingAdapter prodAdapter = new ProductTrendingAdapter(HomeActivity.this,productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }
}