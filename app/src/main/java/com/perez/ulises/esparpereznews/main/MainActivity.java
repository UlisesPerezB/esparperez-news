package com.perez.ulises.esparpereznews.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainInterface.IMainClickListener {

    String[] mItems;


    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.list_drawer)
    ListView mListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        mItems = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_drawer, mItems));

    }

    @Override
    public void onClick() {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG);
    }
}
