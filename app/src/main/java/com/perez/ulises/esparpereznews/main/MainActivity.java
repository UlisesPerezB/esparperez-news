package com.perez.ulises.esparpereznews.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.bookmarks.BookmarksFragment;
import com.perez.ulises.esparpereznews.preferences.PreferenceFragment;
import com.perez.ulises.esparpereznews.search.SearchFragment;
import com.perez.ulises.esparpereznews.search.SearchInterface;
import com.perez.ulises.esparpereznews.terms.TermsFragment;
import com.perez.ulises.esparpereznews.trending.TrendingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.perez.ulises.esparpereznews.utils.Constants.INSERT_WORD;
import static com.perez.ulises.esparpereznews.utils.Constants.SEARCH_WORD;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.RecyclerWord {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.navigation_view)
    NavigationView mNavigation;

    private ActionBarDrawerToggle toggle;
    private SearchInterface.ISearchInterface searchInterface;
//    private SearchView mSearchView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigation.setNavigationItemSelectedListener(this);
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
                fragment = PreferenceFragment.getInstance();
                break;
            case R.id.menu_section_search:
                fragment = instanceSearchFragment();
                break;
            case R.id.menu_section_bookmarks:
                fragment = BookmarksFragment.newInstance();
                break;
            case R.id.menu_section_terms:
                fragment = TermsFragment.newInstance();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        if (fragment != null)
            inflateFragment(fragment);
        return true;
    }

    private void inflateFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Fragment searchFragment = instanceSearchFragment();
                inflateFragment(searchFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Fragment instanceSearchFragment() {
        SearchFragment fragment = SearchFragment.newInstance();
        searchInterface = fragment;
        return fragment;
    }

//    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
//        @Override
//        public boolean onQueryTextSubmit(String query) {
//            if (!query.isEmpty()) {
//                searchInterface.searchForWord(query, INSERT_WORD);
//            } else {
//                Toast.makeText(MainActivity.this, "Ingresa una palabra", Toast.LENGTH_SHORT).show();
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onQueryTextChange(String newText) {
//                if (getSupportFragmentManager().findFragmentById(R.id.content_fragment) != instanceSearchFragment()) {
//                    Fragment searchFragment = instanceSearchFragment();
//                    inflateFragment(searchFragment);
//                } else {
//                    searchInterface.searchForWord(newText, SEARCH_WORD);
//                }
//            return false;
//        }
//    };

    @Override
    public void setWord(String word) {
//        mSearchView.setQuery(word,false);
    }
}
