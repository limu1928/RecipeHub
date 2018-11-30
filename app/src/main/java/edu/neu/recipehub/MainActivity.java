package edu.neu.recipehub;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import edu.neu.recipehub.utils.UIUtils;

public class MainActivity extends AppCompatActivity
    implements SensorListener,
            HomeFragment.OnFragmentInteractionListener,
            UserCenterFragment.OnFragmentInteractionListener{


    public static final String USER_NAME = "userName";
    private static final float SHAKE_THRESHOLD = 1;

    private User mCurrentUser;

    private Context mContext;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private BottomNavigationView mBottomNavigationView;

    private SensorManager mSensorManager;
    private float mXLocation;
    private float mYLocation;
    private float mZLocation;
    private long mLastUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUser(getIntent().getStringExtra(UserEntry.USER_NAME));
        initializeBottomNavigationView();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(this,SensorManager.SENSOR_ACCELEROMETER);
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
                changeCurrentFragment(mFragment);
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

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("It is a dialog");
        builder.show();
    }


    @Override
    public void changeFragmentInHomeFragment(Fragment fragment) {
        changeCurrentFragment(fragment);
    }

    @Override
    public void logOut() {
        finish();
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER){
            long currentTime = System.currentTimeMillis();
            long diffTime = currentTime - mLastUpdate;

            if (diffTime>1000){
                mLastUpdate = currentTime;
                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = (Math.abs(x+y+z - mXLocation - mYLocation - mZLocation) / diffTime) * 10000;
                if (speed > SHAKE_THRESHOLD) {
                    UIUtils.showToast(this,"!23");
                    showDialog();
                }

                mXLocation = x;
                mYLocation = y;
                mZLocation = z;
            }
        }
    }



    public User getmCurrentUser() {
        return mCurrentUser;
    }
}
