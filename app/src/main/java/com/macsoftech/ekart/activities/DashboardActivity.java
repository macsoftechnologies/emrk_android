package com.macsoftech.ekart.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.macsoftech.ekart.R;
import com.macsoftech.ekart.fragments.HelpFragment;
import com.macsoftech.ekart.fragments.HomeSearchFragment;
import com.macsoftech.ekart.fragments.MyEntityFragment;
import com.macsoftech.ekart.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.menu_search) {
                    fragment = new HomeSearchFragment();
                } else if (item.getItemId() == R.id.menu_entity) {
                    fragment = new MyEntityFragment();
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
    }

    private void replaceFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commitAllowingStateLoss();
    }
}