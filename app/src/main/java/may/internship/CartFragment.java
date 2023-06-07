package may.internship;

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

    String[] productNameArray = {"Butter", "Makup Kit", "Bread"};
    int[] productImageArray = {R.drawable.butter, R.drawable.makup_kit, R.drawable.bread};
    String[] productPriceArray = {"150", "1500", "100"};
    String[] productUnitArray = {"500 GM", "Box", "Packet"};
    String[] productDescriptionArray = {
            "Butter, a yellow-to-white solid emulsion of fat globules, water, and inorganic salts produced by churning the cream from cows' milk. Butter has long been used as a spread and as a cooking fat. It is an important edible fat in northern Europe, North America, and other places where cattle are the primary dairy animals.",
            "According to Oxford.com, makeup is defined as cosmetics such as lipstick or powder applied to the face, used to enhance or alter the appearance",
            "Bread, baked food product made of flour or meal that is moistened, kneaded, and sometimes fermented. A major food since prehistoric times, it has been made in various forms using a variety of ingredients and methods throughout the world."
    };

    ArrayList<ProductList> productArrayList;

    public static TextView totalAmount;
    public static Button checkout;

    public static int iTotalAmount = 0;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        productArrayList = new ArrayList<>();
        for (int i = 0; i < productNameArray.length; i++) {
            ProductList list = new ProductList();
            list.setName(productNameArray[i]);
            list.setImage(productImageArray[i]);
            list.setPrice(productPriceArray[i]);
            list.setUnit(productUnitArray[i]);
            list.setDescription(productDescriptionArray[i]);
            productArrayList.add(list);

            iTotalAmount += Integer.parseInt(productPriceArray[i]);

        }
        CartAdapter prodAdapter = new CartAdapter(getActivity(), productArrayList);
        recyclerView.setAdapter(prodAdapter);

        totalAmount = view.findViewById(R.id.cart_total);
        checkout = view.findViewById(R.id.cart_checkout);

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