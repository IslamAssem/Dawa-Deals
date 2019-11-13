package me.hashcode.dawadeals.ui;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.ui.base.BaseActivity;

@SuppressLint("ViewConstructor")
public class CustomMarkerView extends ConstraintLayout implements IMarker {
    BaseActivity activity;
    Chart chart;
    TextView mtext;
    View markerView;

    public CustomMarkerView(BaseActivity context, Chart chart1) {
        super(context);
        activity = context;
        markerView = LayoutInflater.from(getContext()).inflate(R.layout.marker_view, this);

        markerView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
        markerView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
        measure(getWidth(), getHeight());
        markerView.layout(0, 0, markerView.getMeasuredWidth(), markerView.getMeasuredHeight());

        chart = chart1;
        mtext = findViewById(R.id.marker_text);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(markerView.getWidth() / 2), -markerView.getHeight());
    }

    @Override
    public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
        MPPointF offset = getOffset();
        MPPointF mOffset2 = new MPPointF(offset.x, offset.y);
        float width = markerView.getWidth();
        float height = markerView.getHeight();

        if (posX + mOffset2.x < 0) {
            mOffset2.x = -posX;
        } else if (chart != null && posX + width + mOffset2.x > chart.getWidth()) {
            mOffset2.x = chart.getWidth() - posX - width;
        }

        if (posY + mOffset2.y < 0) {
            mOffset2.y = -posY;
        } else if (chart != null && posY + height + mOffset2.y > chart.getHeight()) {
            mOffset2.y = chart.getHeight() - posY - height;
        }

        return mOffset2;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        String data;
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            data = Utils.formatNumber(ce.getHigh(), 0, true);
        } else {

            data = Utils.formatNumber(e.getX(), 0, true).concat(",").concat(Utils.formatNumber(e.getY(), 0, true));

        }
        activity.runOnUiThread(() -> {
            markerView.setVisibility(View.VISIBLE);
            mtext.setText(data);
            mtext.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: 06/12/2018 fix wrap content static  https://developer.android.com/reference/android/text/Layout#draw(android.graphics.Canvas)
        //https://stackoverflow.com/questions/18453948/android-drawtext-including-text-wrapping
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        int saveId = canvas.save();
        MPPointF offset = getOffsetForDrawingAtPoint(posX, posY);
        // translate to the correct position and draw
        canvas.translate(posX + offset.x, posY + offset.y);
        draw(canvas);
        canvas.restoreToCount(saveId);
    }
}