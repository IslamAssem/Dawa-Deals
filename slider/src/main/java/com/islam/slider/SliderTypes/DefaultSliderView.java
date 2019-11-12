package com.islam.slider.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.islam.slider.R;

/**
 * a simple slider view, which just show an image. If you want to make your own slider view,
 *
 * just extend BaseSliderView, and implement getView() method.
 */
public class DefaultSliderView extends BaseSliderView{

    View v;
    boolean zoom;
    private View.OnClickListener onImageClick;

    public DefaultSliderView(Context context) {
        super(context);
        zoom=true;
    }
    public DefaultSliderView(Context context,boolean zoom) {
        super(context);
        this.zoom=zoom;
    }

    @Override
    public View getView() {
        if(zoom)
        v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default_zoom,null);
        else v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_default,null);
        target = v.findViewById(R.id.islam_slider_image);
        bindEventAndShow(v, target);

        if(onImageClick!=null)
            v.setOnClickListener(onImageClick);
        return v;
    }

    @Override
    public void setOnImageClickListener(View.OnClickListener onImageClick) {
        this.onImageClick=onImageClick;
    }
}
