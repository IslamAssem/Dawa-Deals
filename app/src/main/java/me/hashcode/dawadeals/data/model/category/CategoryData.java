
package me.hashcode.dawadeals.data.model.category;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.Utils;


public class CategoryData {
    private static final int count = 41;

    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("data")
    @Expose
    private List<CategoryDetails> categories;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categoriesCount")
    @Expose
    private int categoriesCount;

    public CategoryData() {
    }
    public static CategoryData getDummy() {

        return new CategoryData(1,getDummyCategories(),"",count);
    }

    private static List<CategoryDetails> getDummyCategories() {
        idd = 100;
        List<CategoryDetails> categories = new ArrayList<>();
        for (int i=1;i<count/4;i++){
            CategoryDetails categoryDetails = new CategoryDetails();
            categoryDetails.setParentId(0);
            categoryDetails.setId(i);
            categoryDetails.setName("Category : "+categoryDetails.getId());
            categories.add(categoryDetails);
            for (int j = 0;j<4;j++){
                CategoryDetails childCategoryDetails = new CategoryDetails();
                childCategoryDetails.setParentId(categoryDetails.getId());
                Log.e("testCats","i+j = "+(i+j));
                Log.e("testCats","categories.size() = "+categories.size());
                childCategoryDetails.setId(getNext());
                childCategoryDetails.setName("Child Category : "+childCategoryDetails.getId());
                categories.add(childCategoryDetails);

            }
        }
        return categories;
    }
    static  int idd = 100;
    private static int getNext(){
        return idd++;
    }

    public CategoryData(int success, List<CategoryDetails> categories, String message, int categoriesCount) {
        this.success = success;
        this.categories = categories;
        this.message = message;
        this.categoriesCount = categoriesCount;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDetails> categories) {
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCategoriesCount() {
        return categoriesCount;
    }

    public void setCategoriesCount(int categoriesCount) {
        this.categoriesCount = categoriesCount;
    }

    public void save(){
        Gson g=new Gson();
        Utils.saveSharedPrefrences(Constants.CATEGORIES,g.toJson(this));
    }
    public static List<CategoryDetails> read() {
        Gson g=new Gson();
        try {
            CategoryData details=
                    g.fromJson(
                            Utils.getSharedPrefrences
                                    (Constants.CATEGORIES,"")
                            ,CategoryData.class);
            if(details==null||details.getCategories()==null)
                return new ArrayList<>();
            return details.getCategories();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
