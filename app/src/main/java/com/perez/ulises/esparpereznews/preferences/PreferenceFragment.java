package com.perez.ulises.esparpereznews.preferences;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreferenceFragment extends Fragment {
    public static PreferenceFragment getInstance() {
        PreferenceFragment fragment = new PreferenceFragment();
        return fragment;
    }
    public PreferenceFragment() { }

    @BindView(R.id.tv_pref_switch)
    TextView tvSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSwitch.setText("Prueba");
        inflateMaps();
    }

    private void inflateMaps() {
        Fragment fragment = new MapsFragment().getInstance();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.map_container, fragment)
                .commit();
    }
}