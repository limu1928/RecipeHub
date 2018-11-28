package edu.neu.recipehub;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import edu.neu.recipehub.fragments.CommunicationFragment;
import edu.neu.recipehub.fragments.FavoriteFragment;
import edu.neu.recipehub.fragments.ForksFragment;
import edu.neu.recipehub.fragments.HomeFragment;
import edu.neu.recipehub.fragments.RecipeFragment;
import edu.neu.recipehub.fragments.UserCenterFragment;

public class MainActivity extends AppCompatActivity
    implements HomeFragment.OnFragmentInteractionListener {

    private Context mContext;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBottomNavigationView();
    }

    private void initializeBottomNavigationView(){
        fragmentManager = getSupportFragmentManager();

        // Make the activity display default fragment.
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, HomeFragment.newInstance()).commit();

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homeMenuItem:
                        fragment = HomeFragment.newInstance();
                        break;
                    case R.id.favouriteMenuItem:
                        fragment = FavoriteFragment.newInstance();
                        break;
                    case R.id.forksMenuItem:
                        fragment = ForksFragment.newInstance();
                        break;
                    case R.id.communicationMenuItem:
                        fragment = CommunicationFragment.newInstance();
                        break;
                    case R.id.usercenterMenuItem:
                        fragment = UserCenterFragment.newInstance();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentFrameLayout, fragment).commit();
                return true;
            }
        });
    }


    private void changeCurrentFragment(Fragment fragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, fragment).commit();
    }


    @Override
    public void changeFragmentInHomeFragment(Fragment fragment) {
        changeCurrentFragment(fragment);
    }
}
