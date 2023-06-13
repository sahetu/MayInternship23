package may.internship;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;

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

    ArrayList<CartList> productArrayList;

    public static TextView totalAmount;
    public static Button checkout;

    public static int iTotalAmount = 0;

    SQLiteDatabase db;
    SharedPreferences sp;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        sp = getActivity().getSharedPreferences(ConstantData.PREF,MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MayInternship",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS RECORD(NAME VARCHAR(100),EMAIL VARCHAR(100),CONTACT BIGINT(10),PASSWORD VARCHAR(15),DOB VARCHAR(10),GENDER VARCHAR(6),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String wishlistTableQuery = "CREATE TABLE IF NOT EXISTS WISHLIST(CONTACT INT(10),PRODUCTNAME VARCHAR(100))";
        db.execSQL(wishlistTableQuery);

        String cartTableQuery = "CREATE TABLE IF NOT EXISTS CART(CONTACT INT(10),ORDERID INT(10),PRODUCTNAME VARCHAR(100),QTY INT(10),PRODUCTPRICE INT(100),PRODUCTUNIT VARCHAR(100),PRODUCTIMAGE INT(100))";
        db.execSQL(cartTableQuery);

        recyclerView = view.findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        String selectQuery = "SELECT * FROM CART WHERE CONTACT='"+sp.getString(ConstantData.CONTACT,"")+"' AND ORDERID='0'";
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            productArrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                for(int i =0 ; i<productNameArray.length;i++){
                    if(cursor.getString(2).equalsIgnoreCase(productNameArray[i])){
                        CartList list = new CartList();
                        list.setName(productNameArray[i]);
                        list.setImage(productImageArray[i]);
                        list.setPrice(productPriceArray[i]);
                        list.setUnit(productUnitArray[i]);
                        list.setDescription(productDescriptionArray[i]);
                        list.setQty(cursor.getString(3));
                        iTotalAmount += Integer.parseInt(productPriceArray[i]);
                        productArrayList.add(list);
                    }
                }
            }
            CartAdapter prodAdapter = new CartAdapter(getActivity(), productArrayList);
            recyclerView.setAdapter(prodAdapter);
        }
        else{
            iTotalAmount=0;
        }

        totalAmount = view.findViewById(R.id.cart_total);
        checkout = view.findViewById(R.id.cart_checkout);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putString(ConstantData.CART_TOTAL, String.valueOf(iTotalAmount)).commit();
                new CommonMethod(getActivity(),ShippingActivity.class);
            }
        });

        totalAmount.setText("Total : "+ConstantData.PRICE_SYMBOL+iTotalAmount);

        if(iTotalAmount>0){
            totalAmount.setVisibility(View.VISIBLE);
            checkout.setVisibility(View.VISIBLE);
        }
        else{
            totalAmount.setVisibility(View.GONE);
            checkout.setVisibility(View.GONE);
        }

        return view;
    }
}