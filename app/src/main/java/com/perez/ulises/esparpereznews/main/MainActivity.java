package com.perez.ulises.esparpereznews.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.trending.TrendingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.navigation_view)
    NavigationView mNavigation;

    private ActionBarDrawerToggle toggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setTitle(null);
        mNavigation.setNavigationItemSelectedListener(this);
        getSupportActionBar().setHomeButtonEnabled(true);


        Fragment fragment = TrendingFragment.newInstance();
        inflateFragment(fragment);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.menu_section_home:
                fragment = TrendingFragment.newInstance();
                break;

            case R.id.menu_section_preferences:
                Toast.makeText(this, "preferences", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_section_search:
                loadSearchFragment();
                break;

            case R.id.menu_section_bookmarks:
                Toast.makeText(this, "bookmarks", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_section_terms:
                Toast.makeText(this, "T&C", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        inflateFragment(fragment);
        return true;
    }


    private void inflateFragment(Fragment fragment) {
        if (fragment != null) {
            
        }
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    void loadSearchFragment() {
        Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
    }

}
