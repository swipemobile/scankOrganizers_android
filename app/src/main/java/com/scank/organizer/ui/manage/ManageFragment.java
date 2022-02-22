package com.scank.organizer.ui.manage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.scank.organizer.R;
import com.scank.organizer.Utility.Constants;
import com.scank.organizer.model.EventData;

public class ManageFragment extends Fragment {

    private ManageViewModel manageViewModel;

    private WebView webView;
    private ProgressBar progressBar;

    private EventData eventData;

    public ManageFragment(EventData eventData) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.INTENT_KEY.EVENT_DATA, eventData);
        setArguments(bundle);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {



        manageViewModel = new ViewModelProvider(this).get(ManageViewModel.class);

        Bundle bundle = getArguments();
        eventData = bundle.getParcelable(Constants.INTENT_KEY.EVENT_DATA);

        View root = inflater.inflate(R.layout.fragment_manage, container, false);

        manageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        getActivity().setTitle(getContext().getString(R.string.manage));

     //   Log.w("ManageData", eventData.getManageUrl());

        init(root);
        setListener();

        return root;
    }

    public void init(View root) {

        webView = root.findViewById(R.id.webView);
        progressBar = root.findViewById(R.id.progressBar);

    }

    public void setListener() {

        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(eventData.getManageUrl());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }
        });

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
          /*  if ("https://scank.azurewebsites.net".equals(Uri.parse(url).getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);*/
            return false;
        }


    }


}