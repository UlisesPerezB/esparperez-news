package com.perez.ulises.esparpereznews.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.bookmarks.BookmarksFragment;
import com.perez.ulises.esparpereznews.preferences.PreferenceFragment;
import com.perez.ulises.esparpereznews.search.SearchFragment;
import com.perez.ulises.esparpereznews.search.SearchInterface;
import com.perez.ulises.esparpereznews.terms.TermsFragment;
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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.title_container)
    LinearLayout titleContainer;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    private String mTitle = "";

    private ActionBarDrawerToggle toggle;
    private SearchInterface.ISearchInterface searchInterface;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation.setNavigationItemSelectedListener(this);
        Fragment fragment = SearchFragment.newInstance();
        mTitle = getString(R.string.label_trending);
        inflateFragment(fragment, mTitle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_section_home:
                fragment = TrendingFragment.newInstance();
                mTitle = getString(R.string.label_trending);
                break;
            case R.id.menu_section_preferences:
                fragment = PreferenceFragment.newInstance();
                mTitle = "";
                break;
            case R.id.menu_section_search:
                fragment = instanceSearchFragment();
                mTitle = getString(R.string.label_search);
                break;
            case R.id.menu_section_bookmarks:
                fragment = BookmarksFragment.newInstance();
                mTitle = getString(R.string.label_bookmarks);
                break;
            case R.id.menu_section_terms:
                fragment = TermsFragment.newInstance();
                mTitle = getString(R.string.label_terms);
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        if (fragment != null) {
            if (fragment instanceof PreferenceFragment) {
                titleContainer.setVisibility(View.GONE);
                Log.d("MAIN", "GONE");
            } else {
                Log.d("MAIN", "VISIBLE: " + fragment);
                titleContainer.setVisibility(View.VISIBLE);
                tvTitle.setText(mTitle);
            }
            appBarLayout.setExpanded(true);
            inflateFragment(fragment, mTitle);
        }
        return true;
    }

    private void inflateFragment(Fragment fragment, String title) {
        if (title.isEmpty()) {
//            linearTitle.setVisibility(View.GONE);
        } else {
//            linearTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar,menu);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                onBackPressed();
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                mNavigation.getMenu().findItem(R.id.menu_section_search).setChecked(true);
                Fragment searchFragment = instanceSearchFragment();
                String title = getString(R.string.label_search);
                inflateFragment(searchFragment, title);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Fragment instanceSearchFragment() {
        SearchFragment fragment = SearchFragment.newInstance();
        searchInterface = fragment;
        return fragment;
    }

//    @Override
//    public void setWord(String word) {
//        tvTitle.setText(word);
////        mSearchView.setQuery(word,false);
//    }
}
