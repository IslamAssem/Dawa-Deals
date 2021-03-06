package me.hashcode.dawadeals.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.adapters.CategoriesAdapter;
import me.hashcode.dawadeals.data.model.category.CategoryData;
import me.hashcode.dawadeals.data.model.category.CategoryDetails;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseFragment;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;

public class CategoriesFragment extends BaseFragment {

    private static final String TAG = "CategoriesFragment";
    @BindView(R.id.categories_recycler)
    RecyclerView categoriesRecycler;
    private CategoriesAdapter expandableAdapter;

    public CategoriesFragment() {
    }

    @Inject
    CategoriesViewModel categoriesViewModel;
    private LinearLayoutManager layoutManager;
     private List<CategoryDetails> parentCats = new ArrayList<>();
    private List<CategoryDetails> subCats = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);
        return root;
    }
    @Override
    public void initViewModel() {
        if (context instanceof BaseActivity)
            ((BaseActivity)context).observe(categoriesViewModel);
        categoriesViewModel.categoriesResponseLiveData.observe(this,
                categoriesModels -> {
                    if(categoriesModels==null){
                        showCatsError();
                     }
                    else if(categoriesModels.getCategories()==null||categoriesModels.getCategories().size()==0)
                    {
                        showNoCats();
                    }
                    else showCats(categoriesModels);
                });
    }

    private void showCatsError() {

    }

    private void showNoCats() {

    }

    private void showCats(CategoryData categoriesModels) {
        if (expandableAdapter!=null){
        categoriesRecycler.setAdapter(expandableAdapter);
        return;
        }
        for (int i = 0; i < categoriesModels.getCategories().size(); i++) {
            if (categoriesModels.getCategories().get(i).getParentId()==0)
                parentCats.add(categoriesModels.getCategories().get(i));
            else subCats.add(categoriesModels.getCategories().get(i));
        }
        for (int i = 0; i < parentCats.size(); i++) {
            for (int j = 0;j < subCats.size(); j++) {
                if(parentCats.get(i).getId()==(subCats.get(j).getParentId())){
                    parentCats.get(i).addSubCategory(subCats.get(j));
                    subCats.remove(j);
                    j=j-1;
                }
            }
        }
        for (int i = 0; i < parentCats.size(); i++)
            parentCats.set(i,CategoryDetails.createExpandable(parentCats.get(i)));
        expandableAdapter=new CategoriesAdapter(parentCats,false);


//        adapter.setOnCategoryClickListener(new OnItemClickListener() {
//            @Override
//            public void OnItemClick(Object data) {
//                super.OnItemClick(data);
//                activity.addPage(NewRequest.createInstance((CategoryDetails) data));
//            }
//        });
        categoriesRecycler.setAdapter(expandableAdapter);
    }

    private void showTransactionsError() {

    }

    private void showNoTransactions() {

    }

    @Override
    public void initViews() {
        if (context instanceof MainActivityGoogleSample)
            ((MainActivityGoogleSample) context).setTextTitle("Categories", true, false);
        categoriesRecycler.setLayoutManager(layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
         categoriesViewModel.getCategories();
        }

    @Override
    public void initVariables() {

    }

    @Override
    public void initData(@NonNull Bundle data) {

    }

    @Override
    public String getTAG() {
        return TAG;
    }
}