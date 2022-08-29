package com.macsoftech.ekart.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.macsoftech.ekart.BuildConfig;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.fragments.HelpFragment;
import com.macsoftech.ekart.fragments.HomeSearchFragment;
import com.macsoftech.ekart.fragments.MyEntityFragment;
import com.macsoftech.ekart.fragments.MyEntityTrailFragment;
import com.macsoftech.ekart.fragments.ProfileFragment;
import com.macsoftech.ekart.helper.SettingsPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    public static boolean isTrailStarted;
    private String TAG = "DashboardActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        isTrailStarted = false;
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.menu_search) {
                    fragment = new HomeSearchFragment();
                } else if (item.getItemId() == R.id.menu_entity) {
                    if (!isTrailStarted) {
                        fragment = new MyEntityTrailFragment();
                    } else {
                        fragment = new MyEntityFragment();
                    }

                } else if (item.getItemId() == R.id.menu_help) {
                    fragment = new HelpFragment();
                } else if (item.getItemId() == R.id.menu_profile) {
                    fragment = new ProfileFragment();
                }
                replaceFragment(fragment);
                return true;
            }
        });

        navigation.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });
        replaceFragment(new HomeSearchFragment());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, token);
                        }

                        SettingsPreferences.saveString(DashboardActivity.this, SettingsPreferences.GCM_TOKEN,
                                token);
                        SplashScreenActivity.saveGCM(getApplicationContext());
//                        Toast.makeText(DashboardActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void replaceFragment(Fragment fragment) {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count - 1; i++) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceBackStackFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}