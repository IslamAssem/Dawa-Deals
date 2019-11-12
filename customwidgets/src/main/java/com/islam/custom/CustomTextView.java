package com.islam.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView {
    boolean removePadding;
    public CustomTextView(Context context) {
        super(context);
    }
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text==null)
            text="";
        String string=text.toString();
        String newText="";
        String []strings=string.split(" ");
        boolean first=true;
        for(String s:strings){
            if(s==null)
                continue;
            if(s.length()==0)
                s=" ";
            String x=s.charAt(0)+"";
            x=x.toUpperCase();
            if(s.length()>1)
                x=x+s.substring(1);
            if(first)
            newText=newText+x;
            else newText=newText+" "+x;
            first=false;
        }
        super.setText(newText, type);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
