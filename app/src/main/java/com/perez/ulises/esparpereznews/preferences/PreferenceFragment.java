package com.perez.ulises.esparpereznews.preferences;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;

import java.util.Calendar;

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
    @BindView(R.id.pref_category)
    Spinner spCategory;
    @BindView(R.id.pref_freshnes)
    Spinner spFreshnes;
    @BindView(R.id.pref_language)
    Spinner spLanguage;
    @BindView(R.id.pref_since)
    TextView edtSince;

    private int mDay, mMonth, mYear;

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
        tvSwitch.setText(getString(R.string.pref_label_safe_search));
        inflateMaps();
        initSpinners();
        calendarDialog();
    }

    private void inflateMaps() {
        Fragment fragment = new MapsFragment().getInstance();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.map_container, fragment)
                .commit();
    }

    private void initSpinners() {
        String [] category =
                {getString(R.string.cat_all), getString(R.string.cat_business), getString(R.string.cat_entertainment),
                        getString(R.string.cat_health), getString(R.string.cat_lifestyle), getString(R.string.cat_politics),
                        getString(R.string.cat_science), getString(R.string.cat_sports), getString(R.string.cat_world)};
        String [] language =
                {getString(R.string.lang_english), getString(R.string.lang_spanish)};
        String [] freshness =
                {getString(R.string.freshness_day), getString(R.string.freshness_week), getString(R.string.freshness_month)};

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_preferences, category);
        spCategory.setAdapter(categoryAdapter);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_preferences, language);
        spLanguage.setAdapter(languageAdapter);
        ArrayAdapter<String> freshnessAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_preferences, freshness);
        spFreshnes.setAdapter(freshnessAdapter);


    }

    private void calendarDialog() {
        edtSince.setOnClickListener(view -> {
            {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                            {
                                edtSince.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        Calendar milis = Calendar.getInstance();
        milis.set(Calendar.YEAR, mYear);
        milis.set(Calendar.MONTH, mMonth);
        milis.set(Calendar.DAY_OF_MONTH, mDay);
    }
}