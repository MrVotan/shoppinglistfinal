package com.example.arsonist.shoppinglistfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arsonist.shoppinglistfinal.R;
import com.example.arsonist.shoppinglistfinal.bean.Product;
import com.example.arsonist.shoppinglistfinal.thread.ImageHttpThread;

import java.util.List;

/**
 * Created by Arsonist on 2018/12/15.
 */
public class ProductShowAdapter extends ArrayAdapter {
    private int resourceId;
    public ProductShowAdapter(Context context,
                              int resource,
                              List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {
        Product product = (Product) getItem(position);
        ProductLayout productLayout = new ProductLayout();
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            productLayout.titleView =(TextView) view.findViewById(R.id.product_title_text_view);
            productLayout.priceView =(TextView) view.findViewById(R.id.product_price_text_view);
            productLayout.imgView=(ImageView)view.findViewById(R.id.product_image_view);
            view.setTag(productLayout);
        } else {
            view = convertView;
            productLayout = (ProductLayout) view.getTag();
        }
        productLayout.titleView.setText(product.getTitle());
        productLayout.priceView.setText(product.getPrice());
        ImageHttpThread imageHttpThread = new ImageHttpThread(product.getImage());
        imageHttpThread.start();
        try {
            imageHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        productLayout.imgView.setImageBitmap(imageHttpThread.getResultBitmap());

        return view;
    }

    class ProductLayout {
        TextView titleView;
        TextView priceView;
        ImageView imgView;
        Button addButton;
    }
}