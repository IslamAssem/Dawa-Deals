package com.islam.slider.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.islam.slider.R;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView{
    public TextSliderView(Context context) {
        super(context);
    }

    View v;
    @Override
    public View getView() {
        v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text,null);
        target = v.findViewById(R.id.islam_slider_image);
        TextView description = v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }

    @Override
    public void setOnImageClickListener(View.OnClickListener onImageClick) {
        if(onImageClick!=null)
            v.setOnClickListener(onImageClick);
    }
}
