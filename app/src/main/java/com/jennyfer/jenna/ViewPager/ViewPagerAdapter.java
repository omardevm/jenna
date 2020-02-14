package com.jennyfer.jenna.ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.jennyfer.jenna.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<Modelo> modelos;
    private LayoutInflater inflater;
    private Context ct;

    public ViewPagerAdapter(List<Modelo> modelos , Context ct) {
        this.modelos = modelos;
        this.ct = ct;
    }

    @Override
    public int getCount() { return modelos.size(); }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater  = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.viewpager_actividad,container,false);

        ImageView imageView;
        TextView title;

        imageView = view.findViewById(R.id.imagenA);
        title     = view.findViewById(R.id.titleCapitulo);

        imageView.setImageResource(modelos.get(position).getImg());
        title.setText(modelos.get(position).getTitle());

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
