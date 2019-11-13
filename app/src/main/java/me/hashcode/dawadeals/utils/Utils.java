package me.hashcode.dawadeals.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import me.hashcode.dawadeals.App;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.network.APIClient;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import timber.log.Timber;

@SuppressWarnings({"unused", "WeakerAccess", "SafeVarargs", "unchecked"})
public class Utils {
//
//    public static void initSlider(Context context, SliderLayout sliderLayout, String baseURl, String... images) {
//        if (images == null || images.length == 0)
//            return;
//        for (String img : images) {
//            if (TextUtils.isEmpty(img))
//                continue;
//
//            Log.e("slider", baseURl.concat(img));
//            sliderLayout.addSlider(new DefaultSliderView(context).image(baseURl.concat(img), false));
//        }
//    }
public static boolean showPasswordCheckBoxListener(boolean prevStatus, EditText password, ImageView view) {
    if (prevStatus) {
        view.setImageResource(R.drawable.ic_visibility_off);
        password.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
    } else {
        view.setImageResource(R.drawable.ic_visibility_on);
        password.setTransformationMethod(null); //show the password from the edit text
    }
    return !prevStatus;
}

    public static boolean showPasswordCheckBoxListener(boolean prevStatus, EditText password, EditText passwordConfrim, ImageView view) {
        if (prevStatus) {
            view.setImageResource(R.drawable.ic_visibility_off);
            password.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
            passwordConfrim.setTransformationMethod(new PasswordTransformationMethod());  //hide the password from the edit text
        } else {
            view.setImageResource(R.drawable.ic_visibility_on);
            password.setTransformationMethod(null); //show the password from the edit text
            passwordConfrim.setTransformationMethod(null); //show the password from the edit text
        }
        return !prevStatus;
    }
    public static Type getType(final Class<?> rawClass, final Class<?> parameterClass) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{parameterClass};
            }

            @Override
            public Type getRawType() {
                return rawClass;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

        };
    }
    public static void openShareIntent(Context context, String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "please check out our product at: " + url);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }
    public static String getFixedLengthString(String string, int length) {
        if (string==null)
            string="   ";
        if(string.length()==0)
            string=string+"   ";
        if(string.length()==1)
            string=string+"  ";
        if(string.length()==2)
            string=string+" ";
        return String.format("%1$"+length+ "s", string);
    }
    public static String getFixedLengthString(Integer integer, int length) {
        String string = integer.toString();
        if(string.length()==0)
            string=string+"   ";
        if(string.length()==1)
            string=string+"  ";
        if(string.length()==2)
            string=string+" ";
        return String.format("%1$"+length+ "s", string);
    }
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public static String getAm(int hour){
        String am=Utils.getStringRes(R.string.am);
        if(hour>11){
            am=Utils.getStringRes(R.string.pm);
            hour=hour-12;
        }
        return hour+" "+am;
    }
    private static boolean isOpen(int start, int end, int time) {
        if (start>end) {
            return time>(start) || time<(end);
        } else {
            return time>(start) && time<(end);
        }
    }
    public static boolean checkOpen(int open,int close){

        long yourmilliseconds = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.UK);
        Date resultdate = new Date(yourmilliseconds);
        String hour = sdf.format(resultdate);
        int h = Integer.valueOf(hour);
        //date.after()
//        if(close<open)
//            close=close+24;
//        h=1;
        return isOpen(open, close, h) ||
                close == open;
    }

    public static String validatePhone(String phone) {
        if(phone.length()!=11){
            return getStringRes(R.string.enter_valid_phone);
        }
        String s=phone.substring(0,3);
        if("010".equalsIgnoreCase(s))
            return null;
        if("011".equalsIgnoreCase(s))
            return null;
        if("012".equalsIgnoreCase(s))
            return null;
        if("015".equalsIgnoreCase(s))
            return null;
        return getStringRes(R.string.phone_number_start_with_);
    }

    public static <T>boolean isEmpty(List<T> list) {
        return list == null||list.size() == 0;
    }
    public static <T>void removeRedundant(List<T> list,List<T> anotherList) {
        if (Utils.isEmpty(list)||isEmpty(anotherList))
            return;
        for (int i=0;i<anotherList.size();i++)
            if (list.contains(anotherList.get(i)))
            {
                anotherList.remove(i--);
            }
    }

    public static <T>boolean isEmpty(T...arr) {
        return arr == null||arr.length == 0;
    }


    public static <T>boolean isEmpty(T[] arr,int minLength) {
        return arr == null||arr.length <minLength;
    }


    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }
    public static boolean isEmpty(TextView text) {
        return TextUtils.isEmpty(text.getText().toString());
    }

    public static boolean isEmpty(Editable text) {
        return TextUtils.isEmpty(text.toString());
    }

    public static boolean isValidPassword(String password) {
        if(TextUtils.isEmpty(password))
            return Boolean.FALSE;
        return password.length() > 5;
    }

     @Nullable
    public static<T> T firstNonNull(T...ts) {
        if (Utils.isEmpty(ts))
            return null;
        for (T t : ts)
            if (t!=null)
                return t;
        return null;
    }

    public File getCacheFolder(Context context) {

        File cacheDir = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");

            if(!cacheDir.isDirectory()) {

                cacheDir.mkdirs();

            }

        }


        if(!cacheDir.isDirectory()) {

            cacheDir = context.getCacheDir(); //get system cache folder

        }



        return cacheDir;
    }
    public static void loadImage(ImageView imageView,View loader,String imageUrl) {
        if(TextUtils.isEmpty(imageUrl)){
            if(loader!=null)
                loader.setVisibility(View.GONE);
            return;
        }
        if (loader!=null)
        loader.setVisibility(View.VISIBLE);
        if(!imageUrl.toLowerCase().contains(APIClient.BASE_URL_IMAGES))
            imageUrl= APIClient.BASE_URL_IMAGES+imageUrl;
        Timber.tag("loadImage").e((imageUrl));
// Set Product Image on ImageView with Glide Library
        String finalImageUrl = imageUrl;
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_logo)
                .error(R.drawable.default_image_thumbnail)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(loader!=null)
                            loader.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(loader!=null)
                            loader.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);

                        return false;
                    }
                })
                .into(imageView);


    }
/*
    public static void initSlider(Context context, SliderLayout sliderLayout, String image) {
        if (!TextUtils.isEmpty(image))
        sliderLayout.addSlider(new DefaultSliderView(context).image(image, true));
    }
    public static void initSlider(Context context, SliderLayout sliderLayout, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            sliderLayout.addSlider(new DefaultSliderView(context).image(img, true));
        }
    }

    public static void initSlider(Context context, SliderLayout sliderLayout, boolean concatToBaseUrl, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;
            sliderLayout.addSlider(new DefaultSliderView(context).image(img, concatToBaseUrl));
        }
    }
    public static void initSlider(Context context, SliderLayout sliderLayout, String baseURl, String... images) {
        if (images == null || images.length == 0)
            return;
        for (String img : images) {
            if (TextUtils.isEmpty(img))
                continue;

            Log.e("slider",baseURl.concat(img));
            sliderLayout.addSlider(new DefaultSliderView(context).image(baseURl.concat(img), false));
        }
    }
*/


    //*********** Used to Animate the MenuIcons ********//

    /*public static void animateView(View view) {
        Animation animation = AnimationUtils.loadAnimation(App.getInstance(), R.anim.shake_icon);
        animation.setRepeatMode(Animation.ZORDER_TOP);
        animation.setRepeatCount(2);
        view.startAnimation(animation);

    }*/
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static String getFontPath(){
        if(LocaleHelper.getLanguage().toLowerCase().contains("ar"))
        return "fonts/sc_dubai.ttf";
        else return "fonts/bebasneue_regular.otf";
    }
    public static void saveSharedPrefrences(String key, String value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static void saveSharedPrefrences(Context context, String key, String value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getSharedPrefrences(String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        return sharedPreferences.getString(key, defValue);
    }
    public static String getSharedPrefrences(Context context,String key, String defValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);
    }
    public static Set<String>  getSharedPrefrences(String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        try{
            return sharedPreferences.getStringSet(key, null);

        }catch(Exception e) {
        }
            return null;
    }
    public static void saveSharedPrefrences(String key, Set<String> value) {

        //save to value with the key in SharedPreferences
        SharedPreferences.Editor editor
                = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        editor.putStringSet(key, value);
        editor.commit();
    }
    public static boolean getRead() {
        String s = getSharedPrefrences(Constants.ReadKey, "0");
        return !s.equals("0");

    }

    public static void setRead(boolean read) {
        if (read) saveSharedPrefrences(Constants.ReadKey, "1");
        else saveSharedPrefrences(Constants.ReadKey, "0");

    }

    public static boolean isAndroidO26() {
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.O;
    }


    public static boolean isAndroidN24() {
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.N;
    }

    public static boolean isAndroidL21() {
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP;
    }

    // To animate view slide out from top to bottom
    public static void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation
                (0, 0, 0,1000);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    // slide the view from its current position to below itself
    public static void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                1000); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.post(()->view.setVisibility(View.GONE));
                Log.e("test","setVisibility : GONE");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }

    // slide the view from below itself to the current position
    public static void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        Log.e("test","setVisibility : VISIBLE");
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
    }
    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int fetchAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }

    public static int fetchPrimayDarkColor(Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
    //*********** Returns the current DataTime of Device ********//

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }
    //*********** Used to Calculate the Discount Percentage between New and Old Prices ********//

    public static String checkDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice)/oldPrice * 100;

        return (discount > 0) ? Math.round(discount) +"% " + App.getInstance().getString(R.string.off) : null;
    }
    private static double getDiscount(String actualPrice, String discountedPrice) {

        if (discountedPrice == null) {
            discountedPrice = actualPrice;
        }

        Double oldPrice = Double.parseDouble(actualPrice);
        Double newPrice = Double.parseDouble(discountedPrice);

        double discount = (oldPrice - newPrice)/oldPrice * 100;
        return round(discount,2);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String getRandomNonce(int length) {

        //random max 32
        final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm-";

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder(length);

        for(int i = 0; i< length; ++i)
            randomStringBuilder.append(ALLOWED_CHARACTERS.charAt(generator.nextInt(ALLOWED_CHARACTERS.length())));

        return randomStringBuilder.toString();
    }
    public static String getRandomNonce(int length,String moreCharacters) {

        //random max 32
        final String ALLOWED_CHARACTERS = TextUtils.concat(moreCharacters,"0123456789qwertyuiopasdfghjklzxcvbnm-").toString();

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder(length);

        for(int i = 0; i< length; ++i)
            randomStringBuilder.append(ALLOWED_CHARACTERS.charAt(generator.nextInt(ALLOWED_CHARACTERS.length())));

        return randomStringBuilder.toString();
    }

    //*********** Convert given String to Md5Hash ********//

    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32)
                md5 = "0".concat(md5);

            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }

    public static String getStringRes(@StringRes int string) {
        if(BaseActivity.getInstance()!=null)
        return BaseActivity.getInstance().getString(string);
        else return App.getInstance().getString(string);
    }
    public static String getStringRes(@Nullable Context context,@StringRes int string) {
        if(context!=null)
        return context.getString(string);
        else return getStringRes(string);
    }

    @ColorInt
    public static int getColorRes(int color) {

        return ContextCompat.getColor(App.getInstance(),color);
    }

    @ColorInt
    public static int getColorRes(Context context, int color) {

        return ContextCompat.getColor(context, color);
    }

     public static Drawable getDrawableRes(int drawableRes) {

        return ContextCompat.getDrawable(App.getInstance(),drawableRes);
    }
     public static Drawable getDrawableRes(Context context,int drawableRes) {

        return ContextCompat.getDrawable(context,drawableRes);
    }
    public static void logE(String message){
        Log.e("Utils",message);
    }

    public static String getAuthorization() {
        String tokenType=getSharedPrefrences(Constants.KEY_TOKEN_TYPE,Constants.KEY_TOKEN_DEFAULT);
        String token=getSharedPrefrences(Constants.KEY_TOKEN,"");
        return tokenType+" "+token;
    }
    public static String getUserTokenType() {
        return getSharedPrefrences(Constants.KEY_TOKEN_TYPE,Constants.KEY_TOKEN_DEFAULT);
    }
    public static String formatNumber(int myNumber){
        return NumberFormat.getInstance().format(myNumber);
    }
    public static String getUserToken() {
        return getSharedPrefrences(Constants.KEY_TOKEN,"");
    }

    public static String getLE() {
        return getStringRes(R.string.le);
    }


    //*********** Used to check if the App is running in Foreground ********//

    public static boolean isAppInForeground() {
        ActivityManager activityManager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();
        boolean isActivityFound = false;

        if (services.get(0).processName.equalsIgnoreCase(App.getInstance().getPackageName())) {
            Log.i("VC_Shop", "isAppInForeground_PackageName= "+ App.getInstance().getPackageName());
            isActivityFound = true;
        }

        return isActivityFound;
    }



    //*********** Used to Print the HashKey (SHA) ********//

    public static void printHashKey() {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = App.getInstance().getPackageManager()
                    .getPackageInfo(App.getInstance().getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Timber.tag("SHA").e(Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //*********** Returns information of the Device ********//
    private static boolean isEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
/*    public static DeviceInfo getDeviceInfo(@NonNull Context context,String token) {

        double lat = 0;
        double lng = 0;
        String IMEI = "";
        String NETWORK = "";
        String PROCESSORS = "";



        String UNIQUE_ID ="";// Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if(TextUtils.isEmpty(UNIQUE_ID))
            UNIQUE_ID = id(context);//
        else {
            if(TextUtils.isEmpty(UNIQUE_ID.toLowerCase().replace("unknown","")))
                UNIQUE_ID = id(context);//
        }
        PROCESSORS = String.valueOf(Runtime.getRuntime().availableProcessors());

        ActivityManager actManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        double totalRAM = Math.round( ((memInfo.totalMem /1024.0) /1024.0)  /1024.0 );


        if (CheckPermissions.is_PHONE_STATE_PermissionGranted()) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                IMEI = telephonyManager.getDeviceId();
                NETWORK = telephonyManager.getNetworkOperatorName();

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        if (CheckPermissions.is_LOCATION_PermissionGranted()) {
            LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            try {
                boolean gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Location location = null;
                String provider = locationManager.getBestProvider(new Criteria(), true);
                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location loc) {}
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                    @Override
                    public void onProviderEnabled(String provider) {}
                    @Override
                    public void onProviderDisabled(String provider) {}
                };
//                locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);

                if (gps_enabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if (network_enabled) {
                    location = locationManager.getLastKnownLocation(provider);
                }

                if (location != null) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                }
                locationManager.removeUpdates(locationListener);

            } catch (SecurityException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        DeviceInfo device = new DeviceInfo();

        device.setDeviceID(UNIQUE_ID);

        if(isEmulator())
            device.setDeviceType("And_emulator");
        else device.setDeviceType("Android");
        device.setDeviceUser(Build.USER);
        device.setFirebaseToken(token);
        device.setDeviceModel(Build.BRAND +" "+Build.MODEL);
        device.setDeviceBrand(Build.BRAND);
        try {
            device.setDeviceSerial(Build.SERIAL);
        }
        catch (Exception e){

        }
        device.setDeviceSystemOS(System.getProperty("os.name"));
        device.setDeviceAndroidOS("Android "+ Build.VERSION.RELEASE);
        device.setDeviceManufacturer(Build.MANUFACTURER);
        device.setDeviceIMEI(IMEI);
        device.setDeviceRAM(totalRAM +"GB");
        device.setDeviceCPU(Build.UNKNOWN);
        device.setDeviceStorage(Build.UNKNOWN);
        device.setDeviceProcessors(PROCESSORS);
        device.setDeviceIP(Build.UNKNOWN);
        device.setDeviceMAC(Build.UNKNOWN);
        device.setDeviceNetwork(NETWORK);
        device.setDeviceLocation(lat +","+ lng);
        device.setDeviceBatteryLevel(Build.UNKNOWN);
        device.setDeviceBatteryStatus(Build.UNKNOWN);
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                device.setVersionCode(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).getLongVersionCode());
            }
            else
                device.setVersionCode(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        }catch (Exception e){
            device.setVersionCode("error");
        }
        try{
            device.setVersionName(""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        }catch (Exception e){
            device.setVersionCode("error");

        }
        return device;
    }


    public static void RegisterDeviceForFCM(final Context context,String token) {

        if(TextUtils.isEmpty(token))
            return;
        DeviceInfo device = Utils.getDeviceInfo(context,token);
        Timber.e(device.toString());
        String userID="";
        UserDetails userDetails=UserDetails.readUser();
        if(userDetails!=null&&!TextUtils.isEmpty(userDetails.getUser_id()))
            userID=userDetails.getUser_id();
        else return;
        Call<EmptyResponse> call = APIClient.getInstance()
                .registerDevice
                        (
                                device.getDeviceID(),
                                device.getDeviceIMEI(),
                                device.getFirebaseToken(),
                                device.getDeviceModel(),
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceLocation(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                "1",
                                "0",
                                "0",
                                userID,
                                device.getVersionCode(),
                                device.getVersionName()
                        );

        call.enqueue(new retrofit2.Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, retrofit2.Response<EmptyResponse> response) {
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
            }
        });

    }
*/

    public static int dpToPX(int dp) {
        int pixeldpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return (pixeldpi* DisplayMetrics.DENSITY_DEFAULT)/dp;
    }

    public static int pxToDp(int px){
        int pixeldpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return  (px / pixeldpi) *  DisplayMetrics.DENSITY_DEFAULT;
    }
}
