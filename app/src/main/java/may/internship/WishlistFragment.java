package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class WishlistFragment extends Fragment {

    RecyclerView productTrendingRecyclerview;
    /*String[] productTrendingNameArray = {"Travel Package","Cloth","Butter","Makup Kit","Bread"};
    int[] productTrendingImageArray = {R.drawable.travel_package,R.drawable.cloth,R.drawable.butter,R.drawable.makup_kit,R.drawable.bread};
    String[] productTrendingPriceArray = {"10000","2000","150","1500","100"};
    String[] productTrendingUnitArray = {"Package","Pair","500 GM","Box","Packet"};
    String[] productTrendingDescriptionArray = {
            "Choose from dozens of Holiday Packages &amp; Book your Dream Vacation with MakeMyTrip. Grab exciting discounts for your upcoming Holidays to our most-loved destinations. Customized Tour Packages. Best Deals Guaranteed. Special Online Discounts.",
            "Cloth is fabric, a woven material. When you sew your own clothes, you start with a piece of cloth. Cloth is made from some sort of fiber, often cotton or wool",
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };*/

    ArrayList<ProductList> productTrendingArrayList;

    SQLiteDatabase db;
    SharedPreferences sp;

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

    public WishlistFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        sp = getActivity().getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        productTrendingRecyclerview = view.findViewById(R.id.wishlist_recyclerview);
        productTrendingRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        productTrendingRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productTrendingRecyclerview.setNestedScrollingEnabled(false);

        String selectQuery = "SELECT * FROM WISHLIST WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            productTrendingArrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                for(int i =0 ; i<productNameArray.length;i++){
                    if(cursor.getString(1).equalsIgnoreCase(productNameArray[i])){
                        ProductList list = new ProductList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        productTrendingArrayList.add(list);
                    }
                }
            }
            WishlistAdapter prodAdapter = new WishlistAdapter(getActivity(),productTrendingArrayList);
            productTrendingRecyclerview.setAdapter(prodAdapter);
        }

        /*productTrendingArrayList = new ArrayList<>();
        for(int i=0;i<productTrendingNameArray.length;i++){
            ProductList list = new ProductList();
            list.setName(productTrendingNameArray[i]);
            list.setImage(productTrendingImageArray[i]);
            list.setPrice(productTrendingPriceArray[i]);
            list.setUnit(productTrendingUnitArray[i]);
            list.setDescription(productTrendingDescriptionArray[i]);
            productTrendingArrayList.add(list);
        }
        WishlistAdapter prodAdapter = new WishlistAdapter(getActivity(),productTrendingArrayList);
        productTrendingRecyclerview.setAdapter(prodAdapter);*/
        return view;
    }
}