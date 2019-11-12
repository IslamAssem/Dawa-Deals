package me.hashcode.dawadeals.interfaces;

import android.text.TextUtils;

import me.hashcode.dawadeals.utils.LocaleHelper;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class HasArEnName {
    public abstract String getArName();
    public abstract String getEnName();
    public String getName(){
        if(LocaleHelper.getLanguage().toLowerCase().contains("en"))
            return TextUtils.isEmpty(getEnName())?getArName():getEnName();
        else return TextUtils.isEmpty(getArName())?getEnName():getArName();

    }
    public String getArDescription(){
        return "";
    }
    public  String getEnDescription(){
        return "";
    }
    public String getDescription(){
        if(LocaleHelper.getLanguage().toLowerCase().contains("en"))
            return TextUtils.isEmpty(getEnDescription())?getArName():getArDescription();
        else return TextUtils.isEmpty(getArDescription())?getEnDescription():getArDescription();

    }
}
