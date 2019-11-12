package me.hashcode.dawadeals.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.List;

import me.hashcode.dawadeals.R;

public class NavigationSetup implements FragmentManager.OnBackStackChangedListener {


    private String selectedItemTag;
    private String firstFragmentTag;
    private boolean isOnFirstFragment;
    BottomNavigationView bottomNavigationView;
    public List<Integer> navGraphIds;
    FragmentManager fragmentManager;
    int containerId;
    Intent intent;
    private int firstFragmentGraphId;

    public MutableLiveData<NavController> selectedNavController = new MutableLiveData<NavController>();
    public NavigationSetup(BottomNavigationView bottomNavigationView, FragmentManager fragmentManager, int containerId, Intent intent) {
        this.bottomNavigationView = bottomNavigationView;
        this.fragmentManager = fragmentManager;
        this.containerId = containerId;
        this.intent = intent;
    }

    /**
     * Manages the various graphs needed for a [BottomNavigationView].
     *
     * This sample is a workaround until the Navigation Component supports multiple back stacks.
     */
    public void setupWithNavController(

    ) {

        // Map of tags
        SparseArray<String> graphIdToTagMap = new SparseArray<String>();
        // Result. Mutable live data with the selected controlled

        firstFragmentGraphId = 0;
        for (int index = 0; index<navGraphIds.size(); index++){
            Integer navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(index);

            // Find or create the Navigation host fragment
            NavHostFragment navHostFragment = obtainNavHostFragment(
                    fragmentManager,
                    fragmentTag,
                    navGraphId,
                    containerId
            );
            // Obtain its id
            int graphId = navHostFragment.getNavController().getGraph().getId();
            if (index == 0)
                firstFragmentGraphId = graphId;

            Log.e("graphId","navGraphId  : "+ navGraphId);
            Log.e("graphId","graphId     : "+ graphId);
            Log.e("graphId","fragmentTag : "+ fragmentTag);
            Log.e("graphId","-----------------------------------------");
            // Save to the map
            graphIdToTagMap.append(graphId,fragmentTag);
            // Attach or detach nav host fragment depending on whether it's the selected item.
            if (bottomNavigationView.getSelectedItemId() == graphId){
                // Update livedata with the selected graph
                selectedNavController.setValue(navHostFragment.getNavController());
                attachNavHostFragment(fragmentManager,navHostFragment,index == 0);
            }
            else detachNavHostFragment(fragmentManager,navHostFragment);

        }

        // Now connect selecting an item with swapping Fragments
        selectedItemTag = graphIdToTagMap.get(bottomNavigationView.getSelectedItemId());
        firstFragmentTag = graphIdToTagMap.get(firstFragmentGraphId);
        isOnFirstFragment = TextUtils.equals(selectedItemTag ,firstFragmentTag ) ;

        // When a navigation item is selected
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (fragmentManager.isStateSaved())
                return false;
            String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
            if (!TextUtils.equals(selectedItemTag,newlySelectedItemTag)){
                // Pop everything above the first fragment (the "fixed start destination")
                fragmentManager.popBackStack(firstFragmentTag,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                NavHostFragment selectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(newlySelectedItemTag);

                // Exclude the first fragment tag because it's always in the back stack.
                if (!TextUtils.equals(firstFragmentTag , newlySelectedItemTag) ){
                    // Commit a transaction that cleans the back stack and adds the first fragment
                    // to it, creating the fixed started destination.
                    FragmentTransaction ft = fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    R.anim.nav_default_enter_anim,
                                    R.anim.nav_default_exit_anim,
                                    R.anim.nav_default_pop_enter_anim,
                                    R.anim.nav_default_pop_exit_anim)
                            .attach(selectedFragment)
                            .setPrimaryNavigationFragment(selectedFragment);
                    for (int j=0;j<graphIdToTagMap.size();j++){
                        String fragmentTagIter = graphIdToTagMap.get(j);
                        if (!TextUtils.equals(fragmentTagIter,  newlySelectedItemTag) ){
                            Fragment fragment = fragmentManager.findFragmentByTag(firstFragmentTag);
                            if (fragment !=null)
                            ft.detach(fragment);
                        }

                    }
                        ft.addToBackStack(firstFragmentTag)
                            .setReorderingAllowed(true)
                            .commit();
                }
                selectedItemTag = newlySelectedItemTag;
                isOnFirstFragment = TextUtils.equals(selectedItemTag ,firstFragmentTag);
                selectedNavController.setValue(selectedFragment.getNavController());
                return true;
            }
            return false;
        });
        // Optional: on item reselected, pop back stack to the destination of the graph
        setupItemReselected(bottomNavigationView,graphIdToTagMap, fragmentManager);

        // Handle deep link
        setupDeepLinks(bottomNavigationView,navGraphIds, fragmentManager, containerId, intent);

        // Finally, ensure that we update our BottomNavigationView when the back stack changes
        fragmentManager.addOnBackStackChangedListener(this);
    }

    private void setupDeepLinks(
            BottomNavigationView bottomNavigationView,
            List<Integer> navGraphIds,
            FragmentManager fragmentManager,
            int containerId,
            Intent intent
    ) {
        for (int index = 0; index<navGraphIds.size(); index++){
            Integer navGraphId = navGraphIds.get(index);
            String fragmentTag = getFragmentTag(index);
            NavHostFragment navHostFragment = obtainNavHostFragment(
                    fragmentManager,
                    fragmentTag,
                    navGraphId,
                    containerId
            );
            // Handle Intent
            if (navHostFragment.getNavController().handleDeepLink(intent)
                    && bottomNavigationView.getSelectedItemId() != navHostFragment.getNavController().getGraph().getId()) {
                bottomNavigationView.setSelectedItemId(navHostFragment.getNavController().getGraph().getId());
            }
        }
    }

    private  void setupItemReselected(
            com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView,
            SparseArray<String> graphIdToTagMap,
            FragmentManager fragmentManager
    ) {
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
            String newlySelectedItemTag = graphIdToTagMap.get(item.getItemId());
            NavHostFragment selectedFragment = (NavHostFragment) fragmentManager.
                    findFragmentByTag(newlySelectedItemTag);
            NavController navController = selectedFragment.getNavController();
            // Pop the back stack to the start destination of the current navController graph
            navController.popBackStack(
                    navController.getGraph().getStartDestination(), false
            );
        });
    }

    private  void detachNavHostFragment(
            FragmentManager fragmentManager,
            NavHostFragment navHostFragment
    ) {
        fragmentManager.beginTransaction()
                .detach(navHostFragment)
                .commitNow();
    }

    private   void attachNavHostFragment(
            FragmentManager fragmentManager,
            NavHostFragment navHostFragment,
            boolean isPrimaryNavFragment
    ) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .attach(navHostFragment);
        fragmentTransaction.setPrimaryNavigationFragment(navHostFragment);
        fragmentTransaction.commitNow();

    }

    private  NavHostFragment obtainNavHostFragment(
            FragmentManager fragmentManager,
            String fragmentTag,
            int navGraphId,
            int containerId)  {
        // If the Nav Host fragment exists, return it
        NavHostFragment fragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment!=null)
            return fragment;

        // Otherwise, create it and return it.
        NavHostFragment navHostFragment = NavHostFragment.create(navGraphId);
        fragmentManager.beginTransaction()
                .add(containerId, navHostFragment, fragmentTag)
                .commitNow();
        return navHostFragment;
    }
    private  boolean isOnBackStack(FragmentManager fragmentManager,String backStackName)  {
        int backStackCount = fragmentManager.getBackStackEntryCount();
        for (int index =0;index<backStackCount;index++) {
            if (TextUtils.equals(fragmentManager.getBackStackEntryAt(index).getName(),backStackName)) {
                return true;
            }
        }
        return false;
    }

    private  String getFragmentTag(int index ) {
            return "bottomNavigation#"+index;
    }

    @Override
    public void onBackStackChanged() {
        if (!isOnFirstFragment && !isOnBackStack(fragmentManager,firstFragmentTag)) {
            bottomNavigationView.setSelectedItemId(firstFragmentGraphId);
        }

        // Reset the graph if the currentDestination is not valid (happens when the back
        // stack is popped after using the back button).
        if (selectedNavController.getValue()!=null){
            NavController controller =selectedNavController.getValue();
            if (controller.getCurrentDestination()==null)
                controller.navigate(controller.getGraph().getId());
        }
    }
}
