package com.ufo.learnchinese.fragment;

import com.ufo.learnchinese.MainActivity;
import com.ufo.learnchinese.R;
import com.ufo.learnchinese.R.drawable;
import com.ufo.learnchinese.R.id;
import com.ufo.learnchinese.R.layout;
import com.ufo.learnchinese.database.Database;
import com.ufo.learnchinese.utils.Utils;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.webkit.WebViewClient;

public class WebviewFragment extends Fragment {
	WebView mWebview = null;
	String title = "";
	String content;
	int grammarId ;
	MainActivity mActivity;
	
	Database mDatabase;
	
	ProgressDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		grammarId = getArguments().getInt(Utils.GRAMMAR_COLUMN_ID);
		mActivity = (MainActivity) getActivity();
		// mActivity.findViewById(android.R.id.home).setBackgroundResource(R.drawable.nav_arrow_back);

		mActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
		mActivity.getActionBar().setHomeButtonEnabled(true);

		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		title = getArguments().getString("TITLE");
//		getActivity().setTitle(title);
		
		mDatabase = new Database(mActivity, Utils.GRAMMAR_DATABASE_NAME);
		mDatabase.open();
		this.content = mDatabase.getGrammarContent(grammarId);
		
		loadingDialog = ProgressDialog.show(mActivity, null, null);

		//mActivity.showBannerAd();
//		mActivity.increaseCounter();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.webview, container, false);

		mWebview = (WebView) v.findViewById(R.id.web_view);
//		mWebview.loadUrl("file:///android_asset/" + url);
		mWebview.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
		mWebview.getSettings().setBuiltInZoomControls(true);
		mWebview.getSettings().setDisplayZoomControls(false);
		
		mWebview.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				loadingDialog.dismiss();
			}
		});
		
		return v;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.showBannerAd();
		getActivity().setTitle(title);
	}
	
	@Override
	public void onDetach() {
		mActivity.increaseCounter();
		super.onDetach();
	}

}
