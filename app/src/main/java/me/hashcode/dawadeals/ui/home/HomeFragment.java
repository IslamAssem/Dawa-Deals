package me.hashcode.dawadeals.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.AdsAdapter;
import me.hashcode.dawadeals.adapters.TransactionsAdapter;
import me.hashcode.dawadeals.data.model.ads.Ad;
import me.hashcode.dawadeals.data.model.trade.Transaction;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.utils.Utils;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFraGMENT";
    @BindView(R.id.search_btn)
    View search_btn;
    @BindView(R.id.search_layout)
    View search_layout;
    @BindView(R.id.last_transactions_recycler)
    RecyclerView latestTransaction;
    @BindView(R.id.sponser_ads_recycler)
    RecyclerView adsRecycler;
    private AdsAdapter adsAdapter;
    private TransactionsAdapter transactionsAdapter;
    SnapHelper adSnap;

    @Inject
    HomeViewModel homeViewModel;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager tranactiopnsLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {
/*
        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = get;
        int cy = (int) (fab.getY())+ fab.getHeight() + 56;


        if(b){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }*/

    }

    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity)context).observe(homeViewModel);
        homeViewModel.adsResponseLiveData.observe(this, new Observer<List<Ad>>() {
            @Override
            public void onChanged(List<Ad> ads) {
                if (ads == null)
                    showAdsError();
                else if (ads.size()==0)
                    showNoAds();
                else showAds(ads);
            }
        });
        homeViewModel.transactionsResponseLiveData.observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                if (transactions == null)
                    showTransactionsError();
                else if (transactions.size()==0)
                    showNoTransactions();
                else showTransactions(transactions);
            }
        });
    }

    private void showAdsError() {

    }

    private void showNoAds() {

    }

    private void showAds(List<Ad> ads) {
        adsAdapter.add(ads);
    }

    private void showTransactionsError() {

    }

    private void showNoTransactions() {

    }

    private void showTransactions(List<Transaction> transactions) {
        transactionsAdapter.add(transactions);
    }

    @Override
    public void initViews() {
        adSnap = new PagerSnapHelper();
        adSnap.attachToRecyclerView(adsRecycler);
        adsRecycler.setLayoutManager(layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        latestTransaction.setLayoutManager(tranactiopnsLayoutManager = new LinearLayoutManager(context));

        adsRecycler.setAdapter(adsAdapter = new AdsAdapter(new OnItemClickListener(){
            @Override
            public void OnItemClick(Object data, View view, int position) {
                super.OnItemClick(data, view, position);
            }
        }));
        //todo fix here
        if (false)
            latestTransaction.setAdapter(transactionsAdapter = new TransactionsAdapter(new OnItemClickListener(){
                @Override
                public void OnItemClick(Object data, View view, int position) {
                    super.OnItemClick(data, view, position);
                }
            }));
        latestTransaction.setAdapter(transactionsAdapter = new TransactionsAdapter(new OnItemClickListener(){
            @Override
            public void OnItemClick(Object data, View view, int position) {
                super.OnItemClick(data, view, position);
            }
        }));

       // latestTransaction.addItemDecoration(new StickHeaderItemDecoration(transactionsAdapter));
        DividerItemDecoration adsDividerItemDecoration = new DividerItemDecoration(adsRecycler.getContext(),
                layoutManager.getOrientation());
        adsDividerItemDecoration.setDrawable(Utils.getDrawableRes(context,R.drawable.ads_divider));
        adsRecycler.addItemDecoration(adsDividerItemDecoration);
        DividerItemDecoration transactionDividerItemDecoration = new DividerItemDecoration(latestTransaction.getContext(),
                tranactiopnsLayoutManager.getOrientation());
        transactionDividerItemDecoration.setDrawable(Utils.getDrawableRes(context,R.drawable.transactions_divider));
        //latestTransaction.addItemDecoration(transactionDividerItemDecoration);
        homeViewModel.getLatestTranactions();
        homeViewModel.getHomeAds();
     }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @OnClick(R.id.search_btn)
    public void showSearch() {

         search_layout.setVisibility(View.VISIBLE);
         TranslateAnimation animate = new TranslateAnimation(
                 0,
                 0,
                 search_layout.getHeight(),
                 0);
         animate.setDuration(500);
         animate.setFillAfter(true);
         search_layout.startAnimation(animate);
     }
//         if(!opened){
//
//         } else {
//             view.setVisibility(View.INVISIBLE);
//             TranslateAnimation animate = new TranslateAnimation(
//                     0,
//                     0,
//                     0,
//                     view.getHeight());
//             animate.setDuration(500);
//             animate.setFillAfter(true);
//             view.startAnimation(animate);
//         }


    @Override
    public String getTAG() {
        return TAG;
    }
}