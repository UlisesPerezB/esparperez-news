package com.perez.ulises.esparpereznews.preferences;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.perez.ulises.esparpereznews.R;
import com.perez.ulises.esparpereznews.trending.TrendingFragment;
import com.perez.ulises.esparpereznews.utils.Util;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreferenceFragment extends Fragment implements PreferenceInterfaces.IPreferenceView, MapsFragment.setLocation {
    public static PreferenceFragment newInstance() {
        PreferenceFragment fragment = new PreferenceFragment();
        return fragment;
    }
    public PreferenceFragment() { }

    @BindView(R.id.tv_pref_switch)
    TextView tvSwitch;
    @BindView(R.id.pref_switch)
    SwitchCompat swSafe;
    @BindView(R.id.pref_location)
    TextView tvLocation;
    @BindView(R.id.pref_category)
    Spinner spCategory;
    @BindView(R.id.pref_freshnes)
    Spinner spFreshnes;
    @BindView(R.id.pref_language)
    Spinner spLanguage;
    @BindView(R.id.pref_since)
    TextView tvSince;

    @OnClick(R.id.pref_since)
    void setDate() {
        EditDateDialogFragment fragment = new EditDateDialogFragment();
        fragment.setTargetFragment(this,0);
        fragment.show(getFragmentManager(),"");
    }

    TextView tvTitle;

    private int mDay, mMonth, mYear;
    private PreferenceInterfaces.IPreferencePresenter mPresenter;
    private MapsFragment mapsFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preference_fragment, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSwitch.setText(getString(R.string.pref_label_safe_search));
        inflateMaps();
        initSpinners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter == null) {
            mPresenter = new PreferencePresenter(this, getContext());
        }
        mPresenter.loadSettings();
        mapsFragment.loadLocation();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.search).setVisible(false);
    }

    private void inflateMaps() {
        mapsFragment = new MapsFragment().getInstance();
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .replace(R.id.map_container, mapsFragment)
                .commit();
    }

    private void initSpinners() {
        String [] category = getResources().getStringArray(R.array.pref_categories);
        String [] language = getResources().getStringArray(R.array.pref_langs);
        String [] freshness = getResources().getStringArray(R.array.pref_freshness);
        String [] since = getResources().getStringArray(R.array.pref_since);
        ArrayAdapter<String> categoryAdapter =
                new SpinnerAdapter(getContext(), R.layout.item_spinner_preferences, category);
        spCategory.setAdapter(categoryAdapter);
        ArrayAdapter<String> languageAdapter =
                new SpinnerAdapter(getContext(), R.layout.item_spinner_preferences, language);
        spLanguage.setAdapter(languageAdapter);
        ArrayAdapter<String> freshnessAdapter =
                new SpinnerAdapter(getContext(), R.layout.item_spinner_preferences, freshness);
        spFreshnes.setAdapter(freshnessAdapter);
//        ArrayAdapter<String> sinceAdapter =
//                new SpinnerAdapter(getContext(), R.layout.item_spinner_preferences, since);
//        spSince.setAdapter(sinceAdapter);
    }

    private void calendarDialog() {
//        SpinnerAdapter adapter = (SpinnerAdapter) spSince.getAdapter();
//        String [] temp = {getResources().getStringArray(R.array.pref_since)[0],"",getResources().getStringArray(R.array.pref_since)[1]};

//        tvSince.setOnClickListener(view ->{
//            {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getContext(), (view1, year, monthOfYear, dayOfMonth) -> {
                            {
                                tvSince.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                                adapter.clear();
//                                adapter.add(temp);
//                                adapter.notifyDataSetChanged();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
//            }
//        });
    }

    @Override
    public void showLocation(String location) {
        tvLocation.setText(location);
    }

    @Override
    public void showSettings(boolean safe, int category, int language, int freshness, String since, String location) {
        swSafe.setChecked(safe);
        spCategory.setSelection(category);
        spLanguage.setSelection(language);
        spFreshnes.setSelection(freshness);
        tvSince.setText(since);
        tvLocation.setText(location);
        mapsFragment.loadLocation();
    }

    @OnClick (R.id.btn_reset)
    public void resetSettings() {
        mPresenter.resetSettings();
    }

    @OnClick (R.id.btn_save)
    public void saveSettings() {
        boolean safe = swSafe.isChecked();
        int category = spCategory.getSelectedItemPosition();
        int language = spLanguage.getSelectedItemPosition();
        int freshness = spFreshnes.getSelectedItemPosition();
        String sSince = tvSince.getText().toString();
        long since = Util.format(sSince);
        String location = tvLocation.getText().toString();
        mPresenter.saveSettings(safe, category, language, freshness, since, location );
        Fragment fragment = TrendingFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment,fragment)
                .commit();

        Toast.makeText(getContext(), R.string.pref_label_saved_successful, Toast.LENGTH_SHORT).show();
    }

    public static class SpinnerAdapter extends ArrayAdapter {
        private ContextCompat mCompatcontext;
        private Context mContext;

        public SpinnerAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
            super(context, resource, objects);
            this.mContext = context;
        }

        @Override
        public boolean isEnabled(int position) {
            if (position == 0) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            view.setPadding(0,0,0,0);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
                tv.setTextColor(Color.GRAY);

            } else {
                tv.setTextColor(mCompatcontext.getColor(mContext, R.color.colorPrimaryText));
            }
            return view;
        }
    }

    public static class EditDateDialogFragment extends DialogFragment {

        PreferenceFragment parent = PreferenceFragment.newInstance();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            parent = (PreferenceFragment) getTargetFragment();
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.pref_date_title));

            builder.setPositiveButton(getString(R.string.pref_date_edit), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    parent.calendarDialog();
                }
            });

            builder.setNegativeButton(getString(R.string.pref_date_reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    parent.tvSince.setText("");
                }
            });
            return builder.create();
        }
    }
}