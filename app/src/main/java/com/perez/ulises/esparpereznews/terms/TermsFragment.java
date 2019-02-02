package com.perez.ulises.esparpereznews.terms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.perez.ulises.esparpereznews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TermsFragment extends Fragment{

    public static TermsFragment newInstance() {
        TermsFragment fragment = new TermsFragment();
        return fragment;
    }

    public TermsFragment(){}

    @BindView(R.id.wv_terms)
    WebView webView;

    private String url = "https://developer.android.com/studio/terms";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.terms_fragment, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        webView.setOnKeyListener((view1, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()){
                webView.goBack();
            }
            else {
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
