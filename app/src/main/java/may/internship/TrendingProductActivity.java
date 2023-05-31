package may.internship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class TrendingProductActivity extends AppCompatActivity {

    RecyclerView productTrendingRecyclerview;
    String[] productTrendingNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread","Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productTrendingImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread,R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productTrendingPriceArray = {"10000","2000","150","1500","100","10000","2000","150","1500","100"};
    String[] productTrendingUnitArray = {"Package","Pair","500 GM","Box","Packet","Package","Pair","500 GM","Box","Packet"};
    String[] productTrendingDescriptionArray = {
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world.",
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };
    ArrayList<ProductList> productTrendingArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trending_product);
        productTrendingRecyclerview = findViewById(R.id.trending_product_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(TrendingProductActivity.this));
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
        ProductTrendingAdapter prodAdapter = new ProductTrendingAdapter(TrendingProductActivity.this,productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);
    }
}