
package com.ufo.learnchinese;

import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.ufo.learnchinese.adapter.ListLeftAdapter;
import com.ufo.learnchinese.database.Database;
import com.ufo.learnchinese.fragment.FavoritesFragment;
import com.ufo.learnchinese.fragment.GrammarFragment;
import com.ufo.learnchinese.fragment.PhraseFragment;
import com.ufo.learnchinese.fragment.SearchPhragment;
import com.ufo.learnchinese.fragment.SeemoreFragment;
import com.ufo.learnchinese.fragment.TestFragment;
import com.ufo.learnchinese.utils.Utils;

import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.Toolbar;

public class MainActivity extends Activity {

	ActionBar mActionBar = null;
	Database mDatabase = null;
	Cursor mCursor = null;
	ListView mListView = null;
	ListLeftAdapter mAdapter = null;
	Fragment fragment_1 = null;
	FragmentManager mFragmentManager = null;
	FragmentTransaction mTransaction = null;
	DrawerLayout mDrawerLayout;
	ActionBarDrawerToggle mDrawerToggle;
	Toolbar mToolbar = null;

	SearchView mSearchView;
	MenuItem searchMenuItem;

	String[] listNameNav;
	int[] listImgResource;

	private boolean isShowBanner;

	// Ads
	private InterstitialAd mInterstitialAd;
	private AdView mAdView;
	private AdRequest mAdRequest;
	private int popupAdCounter = 0;
	private int popupTimer = 9;
	
//	ImageView closeBannerAds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// System.out.println("MainActivity.onCreate()");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawerlayout);
		setTitle(getResources().getString(R.string.app_name));

		// determine country
		Utils.DEVICE_LANGUAGE = Locale.getDefault().getDisplayLanguage();

		// Ads popup
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId(getResources().getString(R.string.popup_ad_unit_id));
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				requestNewInterstitialAd();
			}

			@Override
			public void onAdLoaded() {
				// System.out.println("popup ad is loaded");
			}
			
			@Override
			public void onAdFailedToLoad(int errorCode) {
				// TODO Auto-generated method stub
				super.onAdFailedToLoad(errorCode);
			}
		});
		requestNewInterstitialAd();

		// Ads Banner
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {

				super.onAdLoaded();
				isShowBanner = true;
				showBannerAd();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {

				super.onAdFailedToLoad(errorCode);

			}

		});
		requestNewBannerAd();
		
		
		// closeAd
		/*closeBannerAds = (ImageView) findViewById(R.id.closeAdview);
		closeBannerAds.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				hideBannerAd();
			}
		});*/

		// set actionbar mode
		mActionBar = getActionBar();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mListView = (ListView) findViewById(R.id.left_navigation);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// set toolge for drawer layout
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.menu_navigation_left, R.string.app_name) {

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);

				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// init adapter
		listNameNav = this.getResources().getStringArray(R.array.list_string_navigation);
		
		TypedArray array = this.getResources().obtainTypedArray(R.array.list_drawble_navigation);
		listImgResource = new int[array.length()];
		for(int i = 0; i < array.length(); i++){
			listImgResource[i] = array.getResourceId(i, -1);
		}
		
		mAdapter = new ListLeftAdapter(this, listNameNav, listImgResource, R.layout.item_list_left);
		mListView.setAdapter(mAdapter);

//		System.out.println("MainActivity.onCreate() Utils.DEVICE_LANGUAGE = " + Utils.DEVICE_LANGUAGE);
		
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				mDrawerLayout.closeDrawer(mListView);
				Fragment mFragment = null;
				// mCursor.moveToPosition(position);
				switch (position) {
				case 0:
					mFragment = new PhraseFragment();
					break;
				case 1: // grammar
					if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
						mFragment = new GrammarFragment();
					else
						mFragment = new FavoritesFragment();
					break;
				/*case 2:
					if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
						mFragment = new TestFragment();
					else
						mFragment = new FavoritesFragment();
					break;*/
				case 2:
					if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
						mFragment = new FavoritesFragment();
					else
						rateApp();// rate
					break;
				case 3:
					if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
						rateApp();// rate app
					else
						seeMoreApps();
					break;
				case 4:
					if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
						seeMoreApps();
				default:
					break;
				}
				
				// check same fragment
				Fragment currentFragment = mFragmentManager.findFragmentById(R.id.main_content);

				if (mFragment != null && ! mFragment.getClass().equals(currentFragment.getClass())) {
					// remove list fragment in stack before add fragment
					for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); i++) {
						mFragmentManager.popBackStack();
					}

					Bundle mBundle = new Bundle();
					mBundle.putString(Utils.TITLE, listNameNav[position]);
					mFragment.setArguments(mBundle);
					mTransaction = mFragmentManager.beginTransaction();
					mTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
					mTransaction.replace(R.id.main_content, mFragment);
					if (!(mFragment instanceof PhraseFragment)) {
						mTransaction.addToBackStack(null);
					}
					mTransaction.commit();
					
					mListView.setItemChecked(position, true);
					
				}

			}

		});

		// add main_fragment first
		fragment_1 = new PhraseFragment();
		mFragmentManager = getFragmentManager();
		mTransaction = mFragmentManager.beginTransaction();
		mTransaction.add(R.id.main_content, fragment_1).commit();

	}
	
	public void rateApp(){
		Uri storeUri = Uri.parse("market://details?id="+ this.getPackageName());
		Intent rateIntent = new Intent(Intent.ACTION_VIEW, storeUri);
		rateIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
							|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		try{
			startActivity(rateIntent);
		}catch(ActivityNotFoundException e){
			startActivity(new Intent(Intent.ACTION_VIEW,
	                Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
		}
		
	}
	
	public void seeMoreApps(){
		Uri storeUri = Uri.parse("market://search?q=pub:ufostudio");
		Intent seemoreIntent = new Intent(Intent.ACTION_VIEW, storeUri);
		seemoreIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
							|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		try{
			startActivity(seemoreIntent);
		}catch(ActivityNotFoundException e){
			startActivity(new Intent(Intent.ACTION_VIEW,
	                Uri.parse("http://play.google.com/store/search?q=pub:ufostudio")));
		}
		
	}
	

	public InterstitialAd getPopupAd() {
		return mInterstitialAd;
	}

	public void requestNewInterstitialAd() {
		AdRequest popupAdRequest = new AdRequest.Builder()
//		 .addTestDevice("DEC4C6AF2AA95724D456FF356F6D825B")
				.build();
		mInterstitialAd.loadAd(popupAdRequest);
	}

	public void requestNewBannerAd() {

		mAdRequest = new AdRequest.Builder()
//		 .addTestDevice("DEC4C6AF2AA95724D456FF356F6D825B")
				.build();
		mAdView.loadAd(mAdRequest);
	}

	private void showInterstitialAs() {
		if (mInterstitialAd != null)
			mInterstitialAd.show();
	}

	public void hideBannerAd() {
		// System.out.println("MainActivity.hideBannerAd()");
		if (mAdView != null)
			mAdView.setVisibility(View.GONE);
//		if(closeBannerAds != null){
//			closeBannerAds.setVisibility(View.GONE);
//		}
	}

	public void showBannerAd() {
		if (mAdView != null && isShowBanner)
			mAdView.setVisibility(View.VISIBLE);
//		if(closeBannerAds != null){
//			closeBannerAds.setVisibility(View.VISIBLE);
//		}
	}

	public void increaseCounter() {
		if ((popupAdCounter - 1) % popupTimer == 0) {
			showInterstitialAs();
		}
		popupAdCounter++;
	}

	public ActionBarDrawerToggle getDrawerToogle() {
		return mDrawerToggle;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// System.out.println("MainActivity.onConfigurationChanged()");
		super.onConfigurationChanged(newConfig);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// System.out.println("MainActivity.onPostCreate()");
		super.onPostCreate(savedInstanceState);
		// change icon toogle when open/close drawerlayout
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		searchMenuItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchMenuItem.getActionView();
		mSearchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
//		mSuggestAdapter = new SuggestCursorAdapter(this, null);
//		mSearchView.setSuggestionsAdapter(mSuggestAdapter);
		
		mSearchView.setOnSearchClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mDrawerLayout.isDrawerOpen(mListView))
					mDrawerLayout.closeDrawer(mListView);
			}
		});
		
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String key) {
				String query =mSearchView.getQuery().toString().trim().toLowerCase();
				if(query.length() > 0)
				suggesṭ̣(mSearchView.getQuery().toString().toLowerCase().trim());
				mSearchView.clearFocus();
				mSearchView.onActionViewCollapsed();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String key) {
				System.out.println("MainActivity.OnQueryTextListener() {...}.onQueryTextChange()");
				String query =mSearchView.getQuery().toString().trim().toLowerCase();
				if(query.length() > 0)
					suggesṭ̣(query);
				return false;
			}
		});
		
		return true;
	}
	
	public void suggesṭ̣( String keyword){
		// check current fragment
		Fragment currentFragment = mFragmentManager.findFragmentById(R.id.main_content);
		 if (currentFragment instanceof SearchPhragment) {
//			 System.out.println("MainActivity.suggesṭ̣()");
			((SearchPhragment) currentFragment).updateResult(keyword);
			
		}else{
			searchPhrase(keyword);
		}
		
	}

	public void searchPhrase(String query) {

		SearchPhragment searchPhragment = new SearchPhragment(query);
		mTransaction = mFragmentManager.beginTransaction();
//		mTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		mTransaction.replace(R.id.main_content, searchPhragment, Utils.KEY_SEARCH_FRAGMENT);
		if ((mFragmentManager.findFragmentByTag(Utils.KEY_SEARCH_FRAGMENT) instanceof SearchPhragment)) {
			mFragmentManager.popBackStack();
		}
		mTransaction.addToBackStack(null);
		mTransaction.commit();
	}
	
	public void clearSearchView(){
		mSearchView.setQuery("", false);
		mSearchView.clearFocus();
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// click toogle to close/open drawer layout
		// if (mDrawerToggle.onOptionsItemSelected(item))
		// return true;
		if (item.getItemId() == android.R.id.home) {
			// System.out
			// .println("MainActivity.onOptionsItemSelected() getFragmentManager().getBackStackEntryCount() "
			// + getFragmentManager().getBackStackEntryCount());
			if (mSearchView != null & !mSearchView.isIconified()) {
				mSearchView.onActionViewCollapsed();
//				mSearchView.clearFocus();
			}
			if (getFragmentManager().getBackStackEntryCount() > 0) { // sub
																		// fragment
																		// System.out
				// .println("MainActivity.onOptionsItemSelected() pop stack");
				onBackPressed();

			} else {
				// System.out
				// .println("MainActivity.onOptionsItemSelected() toogle");
				mDrawerToggle.onOptionsItemSelected(item);
			}
			return true;
		}

		/*
		 * if (item.getItemId() == R.id.action_home) { // remove current
		 * fragments in stack to back mainfragment for (int i = 0; i <
		 * mFragmentManager.getBackStackEntryCount(); i++) {
		 * mFragmentManager.popBackStack(); }
		 * 
		 * return true; }
		 */

		return super.onOptionsItemSelected(item);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (mSearchView != null & !mSearchView.isIconified()) {
			mSearchView.onActionViewCollapsed();
		}
		if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
			mDrawerLayout.closeDrawer(Gravity.START);
			return;
		}
		if (mFragmentManager.getBackStackEntryCount() > 0) { // sub fragment
				mFragmentManager.popBackStack();
				if(mFragmentManager.getBackStackEntryCount() == 1){
					mListView.setItemChecked(0, true);
				}
				return;
			}
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		// System.out.println("MainActivity.onPause()");
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// System.out.println("MainActivity.onResume()");
//		showBannerAd();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// System.out.println("MainActivity.onDestroy()");

		super.onDestroy();
	}

}
