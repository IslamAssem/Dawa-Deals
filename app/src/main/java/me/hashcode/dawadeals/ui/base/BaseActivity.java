package me.hashcode.dawadeals.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import dagger.Module;
import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.data.model.user.UserDetails;
import me.hashcode.dawadeals.interfaces.OnConnectionStatusChanged;
import me.hashcode.dawadeals.recievers.ConnectivityReceiver;
import me.hashcode.dawadeals.ui.mainActivity.MainActivity;
import me.hashcode.dawadeals.ui.login.LoginActivity;
import me.hashcode.dawadeals.ui.mainActivity.MainActivityGoogleSample;
import me.hashcode.dawadeals.ui.splash.Splash;
import me.hashcode.dawadeals.utils.Constants;
import me.hashcode.dawadeals.utils.LocaleHelper;
import me.hashcode.dawadeals.utils.MessagesHandler;
import timber.log.Timber;

@Module
public abstract class BaseActivity extends AppCompatActivity
        implements OnConnectionStatusChanged {

    public static Class homeClass = MainActivity.class;
    public final BaseActivity activity;
    @SuppressWarnings("unused")
//    @Nullable
//    @BindView(R.id.lang)
    TextView lang;
//    @Nullable
    //@BindView(R.id.basic_mode)
    View basic_mode;
//    @Nullable
//    @BindView(R.id.user_name)
    TextView user_name;
//    @Nullable
//    @BindView(R.id.user_email)
    TextView user_email;
    final String className = getClass().getName();
    static BaseActivity instance;
    protected Bundle data;
    static int selected = -1;
    public MessagesHandler messagesHandler;
    public boolean selfCheck = false;
    public BaseActivity() {
        activity = this;
        instance = activity;
        ConnectivityReceiver.observeNetworkStatus(this);
        messagesHandler = new MessagesHandler(this);
    }
    public boolean isRunning = false;

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

    }

    @Optional
    @OnClick(R.id.back)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
/*

    public void onReceive(List<AppSetting> settingResponse) {
        shouldUpdate(null);
    }

    public void shouldUpdate(Intent intent) {
        int pVersion = SettingResponse.minVersionPrefer();
        int fVersion = SettingResponse.minVersionForce();
        int versionCode = 1;
        try {
            versionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (versionCode < fVersion)
            forceUpdate();
        else if (versionCode < pVersion) {
            messagesHandler.showMessageDialog(R.string.app_update_required
                    , R.string.app_update_prefered_message, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (intent == null)
                                return;
                            startActivity(intent);
                            finishAffinity();

                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(intent);
                        }
                    }, false,true);
        } else {
            if(intent==null)
                return;
            startActivity(intent);
            finishAffinity();
        }

    }

    public void forceUpdate() {
        messagesHandler.showMessageDialog(R.string.app_update_required
                , R.string.app_update_required_message, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        finishAffinity();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishAffinity();
                    }
                }, false,false);
    }
    */
    public void observe(BaseViewModel model) {
        model.getProgress().observe(this, s -> messagesHandler.showProgressBar(s));
        model.getMessage().observe(this, s -> messagesHandler.showMessageDialog(s));
        model.getToast().observe(this, s -> messagesHandler.showToast(s));
    }

//    @Optional
//    @OnClick(R.id.lang)
    public void changeLanguage(TextView view) {
        if (view.getText().toString().toLowerCase().contains("e"))
            LocaleHelper.setLocale(this, "en");
        else LocaleHelper.setLocale(this, "ar");
        Intent intent = new Intent(this, Splash.class);
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        this.finishAffinity();
    }

    public abstract void saveInstanceState(Bundle outState, PersistableBundle outPersistentState);

    @Override
    public final void onSaveInstanceState(@NonNull Bundle outState, PersistableBundle outPersistentState) {
        saveInstanceState(outState, outPersistentState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected final void onSaveInstanceState(@NonNull Bundle outState) {
        saveInstanceState(outState, null);
        super.onSaveInstanceState(outState);
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static BaseActivity getInstance() {
        return instance;
    }

    public void updateSettings() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag("fuckingonCreate").e("onCreate : "+getClass().getSimpleName());
        if (LocaleHelper.getLanguage().toLowerCase().contains("e")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        if (savedInstanceState != null)
            data = savedInstanceState;
        else data = getIntent().getBundleExtra(Constants.DATA);
        if (data == null)
            data = new Bundle();
//        ProcessLifecycleOwner.get()
//                .getLifecycle()
//                .addObserver(LifecycleObserver.getInstance(new LifecycleObserver() {
//                    @Override
//                    public void connectListener() {
//                        updateSettings();
//                    }
//                }));
    }

    boolean hasStarted = false;

    @Override
    protected void onStart() {
        super.onStart();
        if (hasStarted)
            return;
        hasStarted = true;
        getWindow().getDecorView().setFocusable(true);
        getWindow().getDecorView().setFocusableInTouchMode(true);
        ButterKnife.bind(this);
        initViews();
//        if (basic_mode!=null) {
//            if (HomeBasic.class.equals(homeClass))
//                basic_mode.setVisibility(View.GONE);
//            else
//                basic_mode.setVisibility(View.VISIBLE);
//        }
        // Obtain the FirebaseAnalytics instance.
        if (lang == null)
            return;
//        lang.setText(R.string.lang);
//        if (user_name != null)
//            if (UserDetails.readUser() != null){
//                user_name.setText(UserDetails.readUser().getFullName() + "");
//                user_email.setText(UserDetails.readUser().getEmail() + "");
//            }
//        if (lang!=null)
//        lang.setVisibility(View.VISIBLE);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(/*ViewPumpContextWrapper.wrap*/(newBase)));
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (activity instanceof Home)
//            moveTaskToBack(true);
//        else if (activity instanceof LoginActivity)
//            moveTaskToBack(true);
//        else if (activity instanceof Splash
//                || activity instanceof Login
//                || activity instanceof OTP || true) {
//
//
//            super.onBackPressed();
//        } else {
//            activity.startActivity(homeClass);
//            finishAffinity();
//        }
//    }

//
//    @Optional
//    @OnClick(R.id.my_account)
//    public void my_account() {
//        if (UserDetails.readUser() == null) {
//            startActivity(Login.class);
//            finishAffinity();
//            return;
//        }
//        startActivity(MyAccount.class);
//    }
//    @Optional
//    @OnClick(R.id.basic_mode)
//    public void basic_mode() {
//        Utils.saveSharedPrefrences("mode","0");
//        homeClass=HomeBasic.class;
//        home();
//    }
//
//    @Optional
//    @OnClick({R.id.base_view,R.id.advanced_view})
//    public void advanced_view(){
//        Utils.saveSharedPrefrences("mode","1");
//        homeClass=Home.class;
//        home();
//    }
   // @Optional
    //@OnClick(R.id.hometitle)
    public void home() {
//        if ("0".equalsIgnoreCase(Utils.getSharedPrefrences("introoo", "0"))) {
//            UserDetails.removeUser();
//            startActivity(Intro.class);
//            finishAffinity();
//            return;
//        }
        if (false&&UserDetails.readUser() == null) {
            startActivity(LoginActivity.class);
            finishAffinity();
            return;
        }
        if (!className.equalsIgnoreCase(homeClass.getName())) {
            startActivitySingleInstance(homeClass);
        }
    }

//    @Optional
//    @OnClick(R.id.logout)
//    public void menu_item_logout() {
//        messagesHandler.showMessageDialog(Utils.getStringRes(R.string.confirm_logout),
//                Utils.getStringRes(R.string.logout_confirm_message),
//                v -> {
//                    UserDetails.removeUser();
//                    startActivity(LoginActivity.class);
//                    finishAffinity();
//                });
//    }

    public void initDrawer() {

/*
        RecyclerView menu = findViewById(R.id.recycler_menu);
        if (menu != null) {
            menu.setLayoutManager(new LinearLayoutManager(activity));
            MenuAdapter adapter = new MenuAdapter(this, new OnItemClickListener() {
                @Override
                protected void OnItemClick(Object data) {
                    boolean shouldFinish = true;
                    super.OnItemClick(data);
                    String id = (String) data;
                    if (PreviousOrders.TAG.equalsIgnoreCase(id))
                        activity.startActivity(PreviousOrders.class);
                    else if (SavedOrders.TAG.equalsIgnoreCase(id))
                        activity.startActivity(SavedOrders.class);
                    else if (FAQ.TAG.equalsIgnoreCase(id))
                        activity.startActivity(FAQ.class);
                    else if (Home.TAG.equalsIgnoreCase(id))
                        home();
                    else if (About.TAG.equalsIgnoreCase(id))
                        activity.startActivity(About.class);
                    else if (MyAccount.TAG.equalsIgnoreCase(id))
                        activity.startActivity(MyAccount.class);
                    else if (Addresses.TAG.equalsIgnoreCase(id))
                        activity.startActivity(Addresses.class);
                    else if (RunningOrders.TAG.equalsIgnoreCase(id)) {
                        activity.startActivity(RunningOrders.class);
                        //                        //////////////////////////
//                        selfCheck=false;
//                        shouldFinish=false;
//                        model.getRunningOrders();
                    } else if (Support.TAG.equalsIgnoreCase(id))
                        activity.startActivity(Support.class);
                    if (!(activity instanceof Home) && shouldFinish)
                        finish();
                }
            });
            menu.setAdapter(adapter);
            adapter.reload();

        }*/
    }
    protected abstract void initVaribles();

    public abstract void initViewModels();

    @CallSuper
    public void initViews() {
        initVaribles();
        initDrawer();
        initViewModels();

        messagesHandler.hideDialog();
//        try {
//            if (settingReciever != null)
//                registerReceiver(settingReciever, new IntentFilter(Constants.SETTING_FILTER));
//        } catch (Exception ignorede) {
//
//        }
        ConnectivityReceiver.getInstance().onReceive(this, getIntent());
    }

    //abstract methods
    ////////////////////////////////////////////////
    @Override
    public void OnConnectionStatusChanged(boolean networkStatus) {
    }

    public void startActivity(Class homeClass) {
        Intent u = new Intent(this, homeClass);
        startActivity(u);
    }
    public void startActivitySingleInstance(Class homeClass) {
        finishAffinity();
        Intent u = new Intent(this, homeClass);
        u.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(u);
    }

//    @Override
//    public void finishAffinity() {
//        try {
//            super.finishAffinity();
//        } catch (Exception ignored) {
//        }
//    }
//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        /*ComponentName c = intent.getComponent();
//        if (c == null)
//            return;
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, c.getClassName());
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, c.getClassName());
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "class name");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);*/
//    }

}
