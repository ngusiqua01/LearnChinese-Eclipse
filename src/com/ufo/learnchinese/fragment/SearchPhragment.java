package com.ufo.learnchinese.fragment;

import com.ufo.learnchinese.MainActivity;
import com.ufo.learnchinese.R;
import com.ufo.learnchinese.adapter.PhraseDetailAdapter;
import com.ufo.learnchinese.adapter.PhraseListAdapter;
import com.ufo.learnchinese.database.Database;
import com.ufo.learnchinese.utils.Utils;

import android.app.Fragment;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchPhragment extends ExpandableFragment{
	
//	ListView mListView;
	TextView txtNoResult;
	
//	PhraseDetailAdapter mDetailAdapter;
//	MainActivity mActivity;
	
//	Cursor mCursor;
	
	String mTitle = "";
	
	String mKeyword;
	
//	MediaPlayer mPlayer;
	
	boolean isFound = false;
//	private Database mDatabase;
//	protected int currentPosition = -1;
//	View previousView;
	
	public SearchPhragment(String query) {
		mKeyword = query;
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//db
		super.onCreate(savedInstanceState);
		/*mActivity  = (MainActivity) getActivity();
		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		
		// init database
		mDatabase = new Database(mActivity, Utils.PHRASE_DATABASE_NAME);
		mDatabase.open();*/
		
		liPhraseItems = mDatabase.searchPhrase2(mKeyword);
		this.mCursor =  mDatabase.searchPhrase(mKeyword);
		if(mCursor != null && mCursor.getCount() > 0)
			isFound = true;
		
		if(isFound)
//			mPhraseListAdapter = new PhraseDetailAdapter(mActivity, mCursor, 0, R.layout.phrase_detail_item_2, mDatabase);
			mPhraseListAdapter  = new PhraseListAdapter(mActivity, liPhraseItems, R.layout.phrase_detail_item_2, mDatabase);
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View mView = inflater.inflate(R.layout.phrase_detail_fragment, container, false);
		mListView = (ListView) mView.findViewById(R.id.list_phrase_detail);
		txtNoResult = (TextView) mView.findViewById(R.id.txt_no_result);
		if(isFound){
			mListView.setAdapter(mDetailAdapter);
			mListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View mView, int position,
						long viewId) {
					
					// play sound
					mCursor.moveToPosition(position);
					String voice = mCursor.getString(mCursor.getColumnIndex("voice"))+"_f";
//					System.out.println("voice = "+voice);
					
					releasePlayer();
					
					int rawId = mActivity.getResources().getIdentifier(voice, "raw", mActivity.getPackageName());
					if(rawId != 0){
						mPlayer = MediaPlayer.create(mActivity,rawId);
						mPlayer.start();
					}
					
					// show hidden part
					if(currentPosition != position){
						if(previousView != null){
//							LinearLayout previousLayout = (LinearLayout) previousView.findViewById(R.id.item_hide);
							collapseView(previousView);
						}
						expandView(mView);
						currentPosition = position;
						mDetailAdapter.setPositionSelected(position);
						previousView = mView;
					}
				}
				
			});
			mListView.setVisibility(View.VISIBLE);
			txtNoResult.setVisibility(View.GONE);
		}
		else{
			txtNoResult.setVisibility(View.VISIBLE);
			mListView.setVisibility(View.GONE);
		}
			
		return mView;
		
	}*/
	
	public void updateResult(String query){
//		System.out.println("SearchPhragment.updateResult()");
//		this.mCursor =  mDatabase.searchPhrase(query);
		this.liPhraseItems = mDatabase.searchPhrase2(query);
//		this.mPhraseListAdapter.changeCursor(mCursor);
		this.mPhraseListAdapter.updateListPhraseItems(liPhraseItems);
		mListView.setItemChecked(currentPosition,false);
//		this.mListView.setAdapter(mPhraseListAdapter);
		mPhraseListAdapter.setPositionSelected(-1);
		currentPosition = -1;
		previousView = null;
		mActivity.setTitle("Search for: "+query);
	}
	
	/*private void expandView(final View view)
	{
		final LinearLayout hiddenLayout = (LinearLayout) view.findViewById(R.id.item_hide);
		Animation expandAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.slide_down);
		expandAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				hiddenLayout.setVisibility(View.VISIBLE);
				
			}
		});
		
		hiddenLayout.startAnimation(expandAnimation);
		
	}
	
	public void collapseView(final View view){
		
		final LinearLayout hiddenLayout = (LinearLayout) view.findViewById(R.id.item_hide);
		
		Animation collapseAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.slide_up);
		collapseAnimation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				hiddenLayout.setVisibility(View.GONE);
				
			}
		});
		hiddenLayout.startAnimation(collapseAnimation);
		
	}
	
	public void releasePlayer(){
		if(mPlayer != null && mPlayer.isPlaying()){
			try{
				mPlayer.stop();
				mPlayer.release();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}*/
	
	@Override
	public void onResume() {
		mActivity.hideBannerAd();
		super.onResume();
		mActivity.setTitle("Search for: "+mKeyword);
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
//		releasePlayer();
//		mDatabase.close();
		mActivity.clearSearchView();
		mActivity.increaseCounter();
		
	}

}
