package me.hashcode.dawadeals.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.category.CategoryDetails;
import me.hashcode.dawadeals.interfaces.OnItemClickListener;


public class CategoriesAdapter extends ExpandableRecyclerViewAdapter<CategoriesAdapter.CategoryViewHolder, CategoriesAdapter.SubCategoryViewHolder> {
    ArrayList<CategoryDetails> categories;
    private OnItemClickListener onItemClickListener,OnCheckedItemsCountChanged;
    boolean showCheckBox;

    public CategoriesAdapter(List<? extends ExpandableGroup> groups, boolean allowMultipleExpand) {
        super(groups, allowMultipleExpand);
        categories = (ArrayList<CategoryDetails>) groups;

    }

    public boolean isShowCheckBox() {
        return showCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        setExpandaple(!showCheckBox);
        this.showCheckBox = showCheckBox;
        notifyDataSetChanged();
    }
    public int getSelectedItems(){
        int count=0;
        if(getItemCount()==count)return count;
        for(int i=0;i<categories.size();i++)
        {
            if(categories.get(i).isSelected())
                count++;
        }
        return count;
    }
    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_title, parent, false));
    }

    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new SubCategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_subcategory_title, parent, false));
    }

    @Override
    public void onBindChildViewHolder(SubCategoryViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        holder.title.setText(group.getItems().get(childIndex).toString());
        if (onItemClickListener != null)
            holder.itemView.setOnClickListener(v ->
                    {
                        Log.e("subcategory","subcategory click");
                       // onItemClickListener.OnItemClick(group.getItems().get(childIndex));
                    });
    }
//
//    @Override
//    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
//        if(holder instanceof CategoryViewHolder)
//            ((CategoryViewHolder) holder).category_check.setChecked(false);
//        super.onViewRecycled(holder);
//    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.title.setText(group.getTitle());
    }

    public void setOnCategoryClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCheckedItemsCountChanged(OnItemClickListener OnCheckedItemsCountChanged) {
        this.OnCheckedItemsCountChanged=OnCheckedItemsCountChanged;
    }

    public int getCategoriesCount() {
        return categories==null?0:categories.size();
    }

    public void selectNone() {
        if(getSelectedItems()==0)return ;
        for(int i=0;i<categories.size();i++)
            categories.get(i).setSelected(false);
        notifyDataSetChanged();
    }
    public void selectAll() {
        if(categories==null||categories.size()==0)return ;
        for(int i=0;i<categories.size();i++)
            categories.get(i).setSelected(true);
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends GroupViewHolder {
        @BindView(R.id.category_name)
        TextView title;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class SubCategoryViewHolder extends ChildViewHolder {
        @BindView(R.id.category_name)
        TextView title;
        public SubCategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
