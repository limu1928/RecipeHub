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

import edu.neu.recipehub.fragments.CommunicationFragment;
import edu.neu.recipehub.fragments.FavoriteFragment;
import edu.neu.recipehub.fragments.ForksFragment;
import edu.neu.recipehub.fragments.HomeFragment;
import edu.neu.recipehub.fragments.UserCenterFragment;
import edu.neu.recipehub.objects.User;
import edu.neu.recipehub.users.UserEntry;

public class MainActivity extends AppCompatActivity
    implements HomeFragment.OnFragmentInteractionListener,
                UserCenterFragment.OnFragmentInteractionListener{


    public static final String USER_NAME = "userName";


    private User mCurrentUser;

    private Context mContext;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser(getIntent().getStringExtra(UserEntry.USER_NAME));
        initializeBottomNavigationView();
    }

    private void initializeBottomNavigationView(){
        mFragmentManager = getSupportFragmentManager();

        // Make the activity display default mFragment.
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, HomeFragment.newInstance()).commit();

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.homeMenuItem:
                        mFragment = HomeFragment.newInstance();
                        break;
                    case R.id.favouriteMenuItem:
                        mFragment = FavoriteFragment.newInstance();
                        break;
                    case R.id.forksMenuItem:
                        mFragment = ForksFragment.newInstance();
                        break;
                    case R.id.communicationMenuItem:
                        mFragment = CommunicationFragment.newInstance();
                        break;
                    case R.id.usercenterMenuItem:
                        mFragment = UserCenterFragment.newInstance(mCurrentUser);
                        break;
                }
                final FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentFrameLayout, mFragment).commit();
                return true;
            }
        });
    }

    private void changeCurrentFragment(Fragment fragment){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, fragment).commit();
    }

    private void getUser(String userName){
        mCurrentUser = new User(userName);
    }


    @Override
    public void changeFragmentInHomeFragment(Fragment fragment) {
        changeCurrentFragment(fragment);
    }

    @Override
    public void logOut() {
        finish();
    }
}
