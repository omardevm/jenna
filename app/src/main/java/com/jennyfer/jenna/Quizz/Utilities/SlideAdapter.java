package com.jennyfer.jenna.Quizz.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.button.MaterialButton;
import com.jennyfer.jenna.Quizz.Models.Dimension;
import com.jennyfer.jenna.Quizz.Presentation.PresenterF;
import com.jennyfer.jenna.R;

/**
 * Adaptador del ViewPager, carga el contexto de la vista para futuras implementaciones URL, el presentador para
 * comunicar listeners al fragmento y se encarga de manipular los datos de cada "slide" en la pantalla.
 *
 * @author Omar Dominguez
 * @since 01/12/19
 */
public class SlideAdapter extends PagerAdapter {

    private Context ctx;
    private PresenterF presenterF;
    private Dimension dimension;

    private String[] slideTitles;
    private int[] slideImages;
    // private lateinit var layoutInflater: LayoutInflater

    public SlideAdapter(Context ctx, PresenterF presenter, Dimension dimension) {
        this.ctx = ctx;
        this.presenterF = presenter;
        this.dimension = dimension;
        slideTitles = dimension.getFactores();
        slideImages = dimension.getFactores_imagenes();
    }


    @Override
    public int getCount() {
        return slideTitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater ly = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = ly.inflate(R.layout.slide_layout, container, false);
        ImageView image = view.findViewById(R.id.slide_image);
        TextView txt = view.findViewById(R.id.slide_title);
        final Button button = view.findViewById(R.id.btn_slide_ask);
        final int pposition = position;
        txt.setText(slideTitles[position]);
        image.setImageResource(slideImages[position]);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterF.trigger(slideTitles[pposition], button);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
