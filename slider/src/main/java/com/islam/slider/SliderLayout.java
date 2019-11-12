package com.islam.slider;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.islam.slider.Animations.BaseAnimationInterface;
import com.islam.slider.Indicators.PagerIndicator;
import com.islam.slider.SliderTypes.BaseSliderView;
import com.islam.slider.Transformers.AccordionTransformer;
import com.islam.slider.Transformers.BackgroundToForegroundTransformer;
import com.islam.slider.Transformers.BaseTransformer;
import com.islam.slider.Transformers.CubeInTransformer;
import com.islam.slider.Transformers.DefaultTransformer;
import com.islam.slider.Transformers.DepthPageTransformer;
import com.islam.slider.Transformers.FadeTransformer;
import com.islam.slider.Transformers.FlipHorizontalTransformer;
import com.islam.slider.Transformers.FlipPageViewTransformer;
import com.islam.slider.Transformers.ForegroundToBackgroundTransformer;
import com.islam.slider.Transformers.RotateDownTransformer;
import com.islam.slider.Transformers.RotateUpTransformer;
import com.islam.slider.Transformers.StackTransformer;
import com.islam.slider.Transformers.TabletTransformer;
import com.islam.slider.Transformers.ZoomInTransformer;
import com.islam.slider.Transformers.ZoomOutSlideTransformer;
import com.islam.slider.Transformers.ZoomOutTransformer;
import com.islam.slider.Tricks.FixedSpeedScroller;
import com.islam.slider.Tricks.InfinitePagerAdapter;
import com.islam.slider.Tricks.InfiniteViewPager;
import com.islam.slider.Tricks.ViewPagerEx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SliderLayout is compound layout. This is combined with {@link com.islam.slider.Indicators.PagerIndicator}
 * and {@link com.islam.slider.Tricks.ViewPagerEx} .
 *
 * There is some properties you can set in XML:
 *
 * indicator_visibility
 *      visible
 *      invisible
 *
 * indicator_shape
 *      oval
 *      rect
 *
 * indicator_selected_color
 *
 * indicator_unselected_color
 *
 * indicator_selected_drawable
 *
 * indicator_unselected_drawable
 *
 * pager_animation
 *      Default
 *      Accordion
 *      Background2Foreground
 *      CubeIn
 *      DepthPage
 *      Fade
 *      FlipHorizontal
 *      FlipPage
 *      Foreground2Background
 *      RotateDown
 *      RotateUp
 *      Stack
 *      Tablet
 *      ZoomIn
 *      ZoomOutSlide
 *      ZoomOut
 *
 * pager_animation_span
 *
 *
 */

@SuppressWarnings({"FieldCanBeLocal","JavaDoc","NullableProblems","unused"})
public class SliderLayout extends RelativeLayout{

    View.OnClickListener onImageClick;
    private Context mContext;
    /**
     * InfiniteViewPager is extended from ViewPagerEx. As the name says, it can scroll without bounder.
     */
    private InfiniteViewPager mViewPager;
    View mNext,mPrevious;

    /**
     * InfiniteViewPager adapter.
     */
    private SliderAdapter mSliderAdapter;

    /**
     * {@link com.islam.slider.Tricks.ViewPagerEx} indicator.
     */
    private PagerIndicator mIndicator;


    /**
     * A timer and a TimerTask using to cycle the {@link com.islam.slider.Tricks.ViewPagerEx}.
     */
    private Timer mCycleTimer;
    private TimerTask mCycleTask;

    /**
     * For resuming the cycle, after user touch or click the {@link com.islam.slider.Tricks.ViewPagerEx}.
     */
    private Timer mResumingTimer;
    private TimerTask mResumingTask;

    /**
     * If {@link com.islam.slider.Tricks.ViewPagerEx} is Cycling
     */
    private boolean mCycling;

    /**
     * Determine if auto recover after user touch the {@link com.islam.slider.Tricks.ViewPagerEx}
     */
    private boolean mAutoRecover = true;

    private int mTransformerId;

    /**
     * {@link com.islam.slider.Tricks.ViewPagerEx} transformer time span.
     */
    private int mTransformerSpan = 1100;

    private boolean mAutoCycle;


    private boolean mShowArrows;
    private boolean mUnderSlider;

    /**
     * the duration between animation.
     */
    private long mSliderDuration = 4000;

    /**
     * Visibility of {@link com.islam.slider.Indicators.PagerIndicator}
     */
    private PagerIndicator.IndicatorVisibility mIndicatorVisibility = PagerIndicator.IndicatorVisibility.Visible;

    /**
     * {@link com.islam.slider.Tricks.ViewPagerEx} 's transformer
     */
    private BaseTransformer mViewPagerTransformer;

    /**
     * @see com.islam.slider.Animations.BaseAnimationInterface
     */
    private BaseAnimationInterface mCustomAnimation;

    /**
     * {@link com.islam.slider.Indicators.PagerIndicator} shape, rect or oval.
     */

    public SliderLayout(Context context) {
        this(context,null);
    }

    public SliderLayout(Context context, AttributeSet attrs) {
        this(context,attrs,R.attr.SliderStyle);
    }

    public SliderLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.slider_layout, this, true);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,R.styleable.SliderLayout,
                defStyle,0);

        mTransformerSpan = attributes.getInteger(R.styleable.SliderLayout_pager_animation_span, 1100);
        mTransformerId = attributes.getInt(R.styleable.SliderLayout_pager_animation, Transformer.Default.ordinal());
        mAutoCycle = attributes.getBoolean(R.styleable.SliderLayout_auto_cycle,true);
        mShowArrows = attributes.getBoolean(R.styleable.SliderLayout_show_arrows,true);
        mUnderSlider = attributes.getBoolean(R.styleable.SliderLayout_under_slider,true);
        int visibility = attributes.getInt(R.styleable.SliderLayout_indicator_visibility,0);
        for(PagerIndicator.IndicatorVisibility v: PagerIndicator.IndicatorVisibility.values()){
            if(v.ordinal() == visibility){
                mIndicatorVisibility = v;
                break;
            }
        }
        mSliderAdapter = new SliderAdapter(mContext);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(mSliderAdapter);

        mViewPager = findViewById(R.id.islam_slider_viewpager);
        mNext=findViewById(R.id.islam_slider_next);
        mPrevious=findViewById(R.id.islam_slider_previous);
        mViewPager.setAdapter(wrappedAdapter);

        mViewPager.setOnTouchListener((v, event) -> {
            mViewPager.performClick();
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    recoverCycle();
                    break;
            }
            return false;
        });

        attributes.recycle();
        if(mUnderSlider)
        setPresetIndicator(PresetIndicators.Center_Bottom);
        else setPresetIndicator(PresetIndicators.AlterCenter_Bottom);
        setPresetTransformer(mTransformerId);
        setSliderTransformDuration(mTransformerSpan,null);
        setIndicatorVisibility(mIndicatorVisibility);
        if(mAutoCycle){
            startAutoCycle();
        }
        if(!mShowArrows){
            mNext.setVisibility(GONE);
            mPrevious.setVisibility(GONE);
        }
        else {
            mNext.setOnClickListener(v -> moveNextPosition(true));
            mPrevious.setOnClickListener(v -> movePrevPosition(true));
        }
    }

    public void addOnPageChangeListener(ViewPagerEx.OnPageChangeListener onPageChangeListener){
        if(onPageChangeListener != null){
            mViewPager.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public void removeOnPageChangeListener(ViewPagerEx.OnPageChangeListener onPageChangeListener) {
        mViewPager.removeOnPageChangeListener(onPageChangeListener);
    }

    private void setCustomIndicator(PagerIndicator indicator){
        if(mIndicator != null){
            mIndicator.destroySelf();
        }
        mIndicator = indicator;
        mIndicator.setIndicatorVisibility(mIndicatorVisibility);
        mIndicator.setViewPager(mViewPager);
        mIndicator.redraw();
    }

    public <T extends BaseSliderView> void addSlider(T imageContent){
        mSliderAdapter.addSlider(imageContent);
        if(onImageClick!=null)
            imageContent.setOnImageClickListener(onImageClick);
    }

    private android.os.Handler mh = new android.os.Handler();

    private void startAutoCycle(){
        startAutoCycle(mSliderDuration, mSliderDuration, mAutoRecover);
    }

    public ArrayList<String> getUrls(){
        ArrayList<String>list=new ArrayList<>();
        for(int i=0;i<mSliderAdapter.getCount();i++)
            if(!TextUtils.isEmpty(mSliderAdapter.getSliderView(i).getUrl()))
            list.add(mSliderAdapter.getSliderView(i).getUrl());
        return list;
    }
    /**
     * start auto cycle.
     * @param delay delay time
     * @param duration animation duration time.
     * @param autoRecover if recover after user touches the slider.
     */
    private void startAutoCycle(long delay, long duration, boolean autoRecover){
        if(mCycleTimer != null) mCycleTimer.cancel();
        if(mCycleTask != null) mCycleTask.cancel();
        if(mResumingTask != null) mResumingTask.cancel();
        if(mResumingTimer != null) mResumingTimer.cancel();
        mSliderDuration = duration;
        mCycleTimer = new Timer();
        mAutoRecover = autoRecover;
        mCycleTask = new TimerTask(){
            @Override
            public void run() {
                mh.post(() -> moveNextPosition(true));
            }
        };
        mCycleTimer.schedule(mCycleTask,delay,mSliderDuration);
        mCycling = true;
        mAutoCycle = true;
    }

    /**
     * pause auto cycle.
     */
    public void pauseAutoCycle(){
        if(mCycling){
            mCycleTimer.cancel();
            mCycleTask.cancel();
            mCycling = false;
        }else{
            if(mResumingTimer != null && mResumingTask != null){
                recoverCycle();
            }
        }
    }

    public void setDuration(long duration){
        if(duration >= 500){
            mSliderDuration = duration;
            if(mAutoCycle && mCycling){
                startAutoCycle();
            }
        }
    }

    /**
     * stop the auto circle
     */
    public void stopAutoCycle(){
        if(mCycleTask!=null){
            mCycleTask.cancel();
        }
        if(mCycleTimer!= null){
            mCycleTimer.cancel();
        }
        if(mResumingTimer!= null){
            mResumingTimer.cancel();
        }
        if(mResumingTask!=null){
            mResumingTask.cancel();
        }
        mAutoCycle = false;
        mCycling = false;
    }

    /**
     * when paused cycle, this method can weak it up.
     */
    private void recoverCycle(){
        if(!mAutoRecover || !mAutoCycle){
            return;
        }

        if(!mCycling){
            if(mResumingTask != null && mResumingTimer!= null){
                mResumingTimer.cancel();
                mResumingTask.cancel();
            }
            mResumingTimer = new Timer();
            mResumingTask = new TimerTask() {
                @Override
                public void run() {
                    startAutoCycle();
                }
            };
            mResumingTimer.schedule(mResumingTask, 6000);
        }
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                pauseAutoCycle();
                break;
        }
        return false;
    }

    private void setPagerTransformer(boolean reverseDrawingOrder, BaseTransformer transformer){
        mViewPagerTransformer = transformer;
        mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        mViewPager.setPageTransformer(reverseDrawingOrder,mViewPagerTransformer);
    }



    /**
     * set the duration between two slider changes.
     * @param period period
     * @param interpolator interpolator
     */
    private void setSliderTransformDuration(int period, Interpolator interpolator){
        try{
            Field mScroller = ViewPagerEx.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mViewPager.getContext(),interpolator, period);
            mScroller.set(mViewPager,scroller);
        }catch (Exception ignored){

        }
    }

    /**
     * preset transformers and their names
     */
    public enum Transformer{
        Default("Default"),
        Accordion("Accordion"),
        Background2Foreground("Background2Foreground"),
        CubeIn("CubeIn"),
        DepthPage("DepthPage"),
        Fade("Fade"),
        FlipHorizontal("FlipHorizontal"),
        FlipPage("FlipPage"),
        Foreground2Background("Foreground2Background"),
        RotateDown("RotateDown"),
        RotateUp("RotateUp"),
        Stack("Stack"),
        Tablet("Tablet"),
        ZoomIn("ZoomIn"),
        ZoomOutSlide("ZoomOutSlide"),
        ZoomOut("ZoomOut");

        private final String name;

        Transformer(String s){
            name = s;
        }
        public String toString(){
            return name;
        }

        boolean equals(String other){
            return name.equals(other);
        }
    }

    /**
     * set a preset viewpager transformer by id.
     * @param transformerId transformerId
     */
    private void setPresetTransformer(int transformerId){
        for(Transformer t : Transformer.values()){
            if(t.ordinal() == transformerId){
                setPresetTransformer(t);
                break;
            }
        }
    }

    /**
     * set preset PagerTransformer via the name of transforemer.
     * @param transformerName transformerName
     */
    public void setPresetTransformer(String transformerName){
        for(Transformer t : Transformer.values()){
            if(t.equals(transformerName)){
                setPresetTransformer(t);
                return;
            }
        }
    }

    /**
     * Inject your custom animation into PageTransformer, you can know more details in
     * {@link com.islam.slider.Animations.BaseAnimationInterface},
     * and you can see a example in {@link com.islam.slider.Animations.DescriptionAnimation}
     * @param  animation animation
     */
    public void setCustomAnimation(BaseAnimationInterface animation){
        mCustomAnimation = animation;
        if(mViewPagerTransformer != null){
            mViewPagerTransformer.setCustomAnimationInterface(mCustomAnimation);
        }
    }

    /**
     * pretty much right? enjoy it. :-D
     *
     * @param ts
     */
    private void setPresetTransformer(Transformer ts){
        //
        // special thanks to https://github.com/ToxicBakery/ViewPagerTransforms
        //
        BaseTransformer t = null;
        switch (ts){
            case Default:
                t = new DefaultTransformer();
                break;
            case Accordion:
                t = new AccordionTransformer();
                break;
            case Background2Foreground:
                t = new BackgroundToForegroundTransformer();
                break;
            case CubeIn:
                t = new CubeInTransformer();
                break;
            case DepthPage:
                t = new DepthPageTransformer();
                break;
            case Fade:
                t = new FadeTransformer();
                break;
            case FlipHorizontal:
                t = new FlipHorizontalTransformer();
                break;
            case FlipPage:
                t = new FlipPageViewTransformer();
                break;
            case Foreground2Background:
                t = new ForegroundToBackgroundTransformer();
                break;
            case RotateDown:
                t = new RotateDownTransformer();
                break;
            case RotateUp:
                t = new RotateUpTransformer();
                break;
            case Stack:
                t = new StackTransformer();
                break;
            case Tablet:
                t = new TabletTransformer();
                break;
            case ZoomIn:
                t = new ZoomInTransformer();
                break;
            case ZoomOutSlide:
                t = new ZoomOutSlideTransformer();
                break;
            case ZoomOut:
                t = new ZoomOutTransformer();
                break;
        }
        setPagerTransformer(true,t);
    }



    /**
     * Set the visibility of the indicators.
     * @param visibility
     */
    private void setIndicatorVisibility(PagerIndicator.IndicatorVisibility visibility){
        if(mIndicator == null){
            return;
        }

        mIndicator.setIndicatorVisibility(visibility);
    }

    public PagerIndicator.IndicatorVisibility getIndicatorVisibility(){
        if(mIndicator != null){
            return mIndicator.getIndicatorVisibility();
        }
        return PagerIndicator.IndicatorVisibility.Invisible;

    }

    /**
     * get the {@link com.islam.slider.Indicators.PagerIndicator} instance.
     * You can manipulate the properties of the indicator.
     * @return
     */
    public PagerIndicator getPagerIndicator(){
        return mIndicator;
    }

    public enum PresetIndicators{
        Center_Bottom("Center_Bottom",R.id.default_center_bottom_indicator),
        AlterCenter_Bottom("Center_Bottom",R.id.alter_center_bottom_indicator);
        private final String name;
        private final int id;

        PresetIndicators(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String toString(){
            return name;
        }

        int getResourceId(){
            return id;
        }
    }
    private void setPresetIndicator(PresetIndicators presetIndicator){
        PagerIndicator pagerIndicator = findViewById(presetIndicator.getResourceId());
        pagerIndicator.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        setCustomIndicator(pagerIndicator);
    }

    private InfinitePagerAdapter getWrapperAdapter(){
        PagerAdapter adapter = mViewPager.getAdapter();
        if(adapter!=null){
            return (InfinitePagerAdapter)adapter;
        }else{
            return null;
        }
    }

    private SliderAdapter getRealAdapter(){
        PagerAdapter adapter = mViewPager.getAdapter();
        if(adapter!=null){
            return ((InfinitePagerAdapter)adapter).getRealAdapter();
        }
        return null;
    }

    /**
     * get the current item position
     * @return
     */
    public int getCurrentPosition(){

        if(getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");

        return mViewPager.getCurrentItem() % getRealAdapter().getCount();

    }

    /**
     * get current slider.
     * @return
     */
    public BaseSliderView getCurrentSlider(){

        if(getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");

        int count = getRealAdapter().getCount();
        int realCount = mViewPager.getCurrentItem() % count;
        return  getRealAdapter().getSliderView(realCount);
    }

    /**
     * remove  the slider at the position. Notice: It's a not perfect method, a very small bug still exists.
     */
    public void removeSliderAt(int position){
        if(getRealAdapter()!=null){
            getRealAdapter().removeSliderAt(position);
            mViewPager.setCurrentItem(mViewPager.getCurrentItem(),false);
        }
    }

    /**
     * remove all the sliders. Notice: It's a not perfect method, a very small bug still exists.
     */
    public void removeAllSliders(){
        if(getRealAdapter()!=null){
            int count = getRealAdapter().getCount();
            getRealAdapter().removeAllSliders();
            //a small bug, but fixed by this trick.
            //bug: when remove adapter's all the sliders.some caching slider still alive.
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() +  count,false);
        }
    }

    /**
     *set current slider
     * @param position
     */
    private void setCurrentPosition(int position, boolean smooth) {
        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");
        if(position >= getRealAdapter().getCount()){
            throw new IllegalStateException("Item position is not exist");
        }
        int p = mViewPager.getCurrentItem() % getRealAdapter().getCount();
        int n = (position - p) + mViewPager.getCurrentItem();
        mViewPager.setCurrentItem(n, smooth);
    }

    public void setCurrentPosition(int position) {
        setCurrentPosition(position, true);
    }

    /**
     * move to prev slide.
     */
    private void movePrevPosition(boolean smooth) {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, smooth);
    }

    public void movePrevPosition(){
        movePrevPosition(true);
    }

    /**
     * move to next slide.
     */
    private void moveNextPosition(boolean smooth) {

        if (getRealAdapter() == null)
            throw new IllegalStateException("You did not set a slider adapter");

        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, smooth);
    }

    public boolean isZoomable() {
        return getRealAdapter().isZoomable();
    }

    public void setZoomable(boolean zoomable) {
        getRealAdapter().setZoomable( zoomable);
    }

    public void setOnImageClick(OnClickListener onImageClick) {
        this.onImageClick = onImageClick;
    }

    public void moveNextPosition() {
        moveNextPosition(true);
    }
}
