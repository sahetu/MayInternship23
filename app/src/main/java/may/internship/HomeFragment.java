package may.internship;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    TextView name;
    SharedPreferences sp;

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

    SQLiteDatabase db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sp = getActivity().getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

        name = view.findViewById(R.id.home_name);

        /*Bundle bundle = getIntent().getExtras();
        name.setText("Welcome "+bundle.getString("NAME"));*/

        name.setText("Welcome "+sp.getString(ConstantData.NAME,""));

        setCategoryData(view);
        setProductData(view);
        setProductTrendingData(view);

        return view;
    }

    private void setCategoryData(View view) {
        categoryRecyclerview = view.findViewById(R.id.home_category);
        categoryRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        categoryRecyclerview.setItemAnimator(new DefaultItemAnimator());

        CategoryAdapter catAdapter = new CategoryAdapter(getActivity(),categoryNameArray,categoryImageArray);
        categoryRecyclerview.setAdapter(catAdapter);
    }

    private void setProductData(View view) {

        productViewAll = view.findViewById(R.id.home_product_view_all);
        productViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(),ProductActivity.class);
            }
        });

        productRecyclerview = view.findViewById(R.id.home_product_recyclerview);
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

            String wishlishCheckQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productNameArray[i]+"'";
            Cursor cursor = db.rawQuery(wishlishCheckQuery,null);
            if(cursor.getCount()>0){
                list.setWishlist(true);
            }
            else{
                list.setWishlist(false);
            }

            String cartCheckQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productNameArray[i]+"' AND ORDERID='0'";
            Cursor cursorCart = db.rawQuery(cartCheckQuery,null);
            if(cursorCart.getCount()>0){
                list.setCart(true);
            }
            else{
                list.setCart(false);
            }

            productArrayList.add(list);
        }
        ProductAdapter prodAdapter = new ProductAdapter(getActivity(),productArrayList);
        productRecyclerview.setAdapter(prodAdapter);
    }

    private void setProductTrendingData(View view) {

        trendingProductViewAll = view.findViewById(R.id.home_product_trending_view_all);
        trendingProductViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(getActivity(),TrendingProductActivity.class);
            }
        });

        productTrendingRecyclerview = view.findViewById(R.id.home_product_trending_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
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

            String wishlishCheckQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productTrendingNameArray[i]+"'";
            Cursor cursor = db.rawQuery(wishlishCheckQuery,null);
            if(cursor.getCount()>0){
                list.setWishlist(true);
            }
            else{
                list.setWishlist(false);
            }

            String cartCheckQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND PRODUCTNAME='"+productTrendingNameArray[i]+"' AND ORDERID='0'";
            Cursor cursorCart = db.rawQuery(cartCheckQuery,null);
            if(cursorCart.getCount()>0){
                list.setCart(true);
            }
            else{
                list.setCart(false);
            }

            productTrendingArrayList.add(list);
        }
        ProductTrendingAdapter prodAdapter = new ProductTrendingAdapter(getActivity(),productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);
    }

}