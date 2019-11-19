package me.hashcode.dawadeals.ui.trade_details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.SimpleFragmentPagerAdapter;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.ui.trades.TradesViewModel;
import me.hashcode.dawadeals.utils.KeyboardUtils;
import me.hashcode.dawadeals.utils.Utils;
import timber.log.Timber;

@Module
public class TradeDetailsFragment extends BaseFragment {
    private static final String TAG = "TradeDetailsFragment";
    //private lateinit var
    @Inject
    TradesViewModel tradesViewModel;

    @BindView(R.id.trade_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.chartp)
    LineChart chartp;
    @BindView(R.id.constraint_layout)
    MotionLayout constraintLayout;
    View loading;
    TradeDetailsPage tradeDetailsPage;


    public TradeDetailsFragment() {
    }

    TradesBuyFragment tradesBuyFragment;

    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity) context).observe(tradesViewModel);

    }

    TradesSellFragment tradesSellFragment;
    AddNewTradeFragment addNewTradeFragment;
    int prevState;
    private SimpleFragmentPagerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Timber.tag(this.getClass().getSimpleName()).e("onCreateView");
        return inflater.inflate(R.layout.fragment_trade, container, false);
    }

    @Override
    public void initViews() {

        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).setTextTitle(null, false, true);
        initChart(chartp, Utils.getColorRes(context, R.color.purble));

        viewPager.setOffscreenPageLimit(5);
        if (adapter == null)
            adapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());
        if (tradeDetailsPage == null)
            tradeDetailsPage = new TradeDetailsPage();
        if (adapter.getCount() == 0)
            adapter.add(tradeDetailsPage, "Trade Details");
        if (tradesBuyFragment == null)
            tradesBuyFragment = new TradesBuyFragment();
        if (tradesSellFragment == null)
            tradesSellFragment = new TradesSellFragment();
        if (addNewTradeFragment == null)
            addNewTradeFragment = new AddNewTradeFragment();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        new KeyboardUtils((AppCompatActivity) context) {
            @Override
            public void onShowKeyboard(int keyboardHeight) {
                toggleViews(keyboardHeight);

            }

            @Override
            public void onHideKeyboard() {
                toggleViews(0);
            }
        };

        prevState = 0;
    }

    public void toggleViews(int height) {
        if (!isRunning)
            return;
        boolean hasFocus = height > 0;
        if (prevState == 1 && hasFocus)
            return;
        if (prevState == -1 && !hasFocus)
            return;
        if (prevState == 0 && height == 0) {
            prevState = -1;
            return;
        }
        Log.e("toggleViews", "height hasFocus " + hasFocus + " : " + height);
        if (context instanceof MainActivity)
            ((MainActivity) context).hideBottom(hasFocus);
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).hideBottom(hasFocus);
        constraintLayout.transitionToStart();
        constraintLayout.transitionToEnd();
        if (hasFocus)
            prevState = 1;
        else prevState = -1;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof TradeDetailsPage) {
            ((TradeDetailsPage) childFragment).setListener(new OnItemClickListener() {
                @Override
                protected void OnItemClick() {
                    super.OnItemClick();
                    adapter.remove(tradeDetailsPage);
                    adapter.add(tradesBuyFragment, "Buy");
                    adapter.add(tradesSellFragment, "Sell");
                    adapter.add(addNewTradeFragment, "Add New", true);
                }
            });
        }

        if (childFragment instanceof TradesBuyFragment) {
            ((TradesBuyFragment) childFragment).setListener(new OnItemClickListener() {
                @Override
                protected void OnItemClick() {
                    super.OnItemClick();
                }
            });
        }

        if (childFragment instanceof TradesSellFragment) {
            ((TradesSellFragment) childFragment).setListener(new OnItemClickListener() {
                @Override
                protected void OnItemClick() {
                    super.OnItemClick();
                }
            });
        }
    }

    private void initChart(LineChart chart, int color) {
        //ViewCompat.setNestedScrollingEnabled(chart, true);
        Random random = new Random();
        List<Entry> entries = new ArrayList<Entry>();

        for (int i = 50; i < 200; i = i + 10) {

            entries.add(new Entry(random.nextFloat() * 10 + i, i));
        }
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries, "DataSet 1");

        set1.setDrawIcons(false);
        // draw dashed line
        // set1.enableDashedLine(10f, 5f, 0f);

        // black lines and points
        set1.setColor(color);
        set1.setCircleColor(color);

        // line thickness and point size
        //set1.setLineWidth(1f);
        set1.setCircleRadius(3f);

        // draw points as solid circles
        set1.setDrawCircleHole(false);

        // customize legend entry
        // set1.setFormLineWidth(1f);
        // set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);

        // text size of values
        set1.setValueTextSize(1f);
        set1.setValueTextColor(color);

        // draw selection line as dashed
        //   set1.enableDashedHighlightLine(10f, 5f, 0f);

        // set the filled area
        set1.setDrawFilled(true);
        set1.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return chart.getAxisLeft().getAxisMinimum();
            }
        });

        // drawables only supported on api level 18 and above
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
        set1.setFillDrawable(drawable);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        chart.setData(data);
        chart.setDrawGridBackground(false);
//        chart.getLegend().setTextColor(color);
        chart.getDescription().setText("");
        // set color of filled area

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // force pinch zoom along both axis
        chart.setPinchZoom(true);
        chart.setBorderColor(color);
        chart.setNoDataTextColor(color);
        //CustomMarkerView markerView = new CustomMarkerView((BaseActivity) context, chart);
        //activity.addContentView(markerView,new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        //chart.setMarker(markerView);
        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            // vertical grid lines
            // xAxis.enableGridDashedLine(10f, 10f, 0f);
//            xAxis.enableAxisLineDashedLine(0,0,0);
            xAxis.removeAllLimitLines();
            xAxis.setDrawGridLines(false);
            xAxis.setAxisLineColor(Utils.getColorRes(context, R.color.colorAccent));
            // axis range
            xAxis.setAxisLineWidth(2);
            xAxis.setAxisMaximum(200f);
            xAxis.setAxisMinimum(0);
            xAxis.setTextColor(color);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);
            // horizontal grid lines
            //yAxis.enableGridDashedLine(10f, 10f, 0f);
//            yAxis.enableAxisLineDashedLine(0,0,0);
            yAxis.removeAllLimitLines();
            yAxis.setDrawGridLines(false);
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            // axis range
            yAxis.setAxisLineColor(Utils.getColorRes(context, R.color.colorAccent));
            yAxis.setAxisLineWidth(2);

            yAxis.setAxisMaximum(200f);
            yAxis.setAxisMinimum(0);
            yAxis.setTextColor(color);
        }
        chart.invalidate(); // refresh
    }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

        Timber.tag(this.getClass().getSimpleName()).e("initData");
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}