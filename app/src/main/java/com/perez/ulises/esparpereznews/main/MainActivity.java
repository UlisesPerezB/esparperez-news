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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.bookmarks.BookmarksFragment;
import com.perez.ulises.esparpereznews.search.SearchFragment;
import com.perez.ulises.esparpereznews.search.SearchInterface;
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
    @BindView(R.id.imgSearch)
    ImageView imgSearch;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    private ActionBarDrawerToggle toggle;
    private SearchInterface.ISearchInterface searchInterface;

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

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment searchFragment = instanceSearchFragment();
                inflateFragment(searchFragment);
            }
        });

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
                fragment = instanceSearchFragment();
                break;

            case R.id.menu_section_bookmarks:
                fragment = BookmarksFragment.newInstance();
                break;

            case R.id.menu_section_terms:
                Toast.makeText(this, "T&C", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawer.closeDrawer(GravityCompat.START);
        if (fragment != null)
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
        if (!(fragment instanceof SearchFragment)) {
            edtSearch.setVisibility(View.GONE);
            tvTitle.setVisibility(View.VISIBLE);
            imgSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private Fragment instanceSearchFragment() {
        SearchFragment fragment = SearchFragment.newInstance();
        searchInterface = fragment;
        imgSearch.setVisibility(View.GONE);
        tvTitle.setVisibility(View.GONE);
        edtSearch.setVisibility(View.VISIBLE);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_NULL) {
                            Log.i("MAIN_EDT", "NULL");
                        } else if (actionId == EditorInfo.IME_ACTION_SEARCH)
                            if (!s.toString().isEmpty()) {
                                searchInterface.addNewSearch(s.toString());
                            } else {
                                Toast.makeText(MainActivity.this, "Ingresa una palabra", Toast.LENGTH_SHORT).show();
                            }
                        return false;
                    }
                });
                searchInterface.searchForWord(s.toString());
            }
        });
        return fragment;
    }
}
