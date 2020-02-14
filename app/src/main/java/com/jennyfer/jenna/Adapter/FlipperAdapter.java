package com.jennyfer.jenna.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Tools.FlipperImage;

import java.util.ArrayList;

public class FlipperAdapter extends BaseAdapter {
    private Context mCtx;
    private ArrayList<FlipperImage> flipperI;

    public FlipperAdapter(Context mCtx, ArrayList<FlipperImage> flipperI){
        this.mCtx = mCtx;
        this.flipperI = flipperI;
    }

    @Override
    public int getCount() {
        return flipperI.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FlipperImage flipperImage = flipperI.get(position);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view           = inflater.inflate(R.layout.flipper_items,null);
        TextView textView   = view.findViewById(R.id.flipperText);
        ImageView imageView = view.findViewById(R.id.flipperImage);
        textView.setText((flipperImage.getName()));


        Glide.with(mCtx).load(flipperImage.getUrl()).into(imageView);
        return view;
    }
}
