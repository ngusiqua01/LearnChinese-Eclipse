package com.ufo.learnchinese.fragment;

import java.util.ArrayList;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.ufo.learnchinese.MainActivity;
import com.ufo.learnchinese.R;
import com.ufo.learnchinese.adapter.PhraseDetailAdapter;
import com.ufo.learnchinese.adapter.PhraseItem;
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
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PhraseDetailFragment extends ExpandableFragment{
	
	/*ListView mListView;
	
//	PhraseDetailAdapter mDetailAdapter;
	PhraseListAdapter mPhraseListAdapter;
	MainActivity mActivity;
	
	
	Database mDatabase;
	Cursor mCursor;
	
	MediaPlayer mPlayer;
	
	
	
	int currentPosition = -1;
	View previousView = null;
	
	ArrayList<PhraseItem> liPhraseItems;*/
	
	String mTitle = "";
	int categoryId;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*mActivity = (MainActivity) getActivity();
		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
		//db
		mDatabase = new Database(mActivity, Utils.PHRASE_DATABASE_NAME);
		mDatabase.open();*/
		
		// rieng
		this.categoryId = getArguments().getInt(Utils.CATEGORY_ID);
		this.mTitle  = getArguments().getString(Utils.TITLE);
//		mCursor = mDatabase.getListPhrases(this.categoryId);
//		
//		mDetailAdapter = new PhraseDetailAdapter(mActivity, mCursor, 0, R.layout.phrase_detail_item_2, mDatabase);
		
		liPhraseItems = mDatabase.getListPhraseItem(categoryId);
		mPhraseListAdapter = new PhraseListAdapter(mActivity, liPhraseItems, R.layout.phrase_detail_item_2, mDatabase);
		//mActivity.showBannerAd();
		mActivity.increaseCounter();
	}
	
	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View mView = inflater.inflate(R.layout.phrase_detail_fragment, container, false);
		mListView = (ListView) mView.findViewById(R.id.list_phrase_detail);
//		mListView.setAdapter(mDetailAdapter);
		mListView.setAdapter(mPhraseListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View mView, int position,
					long viewId) {
				mListView.setItemChecked(position, true);
				// play sound
//				mCursor.moveToPosition(position);
//				String voice = mCursor.getString(mCursor.getColumnIndex("voice"))+"_f";
				String voice = liPhraseItems.get(position).getVoice()+"_f";
				releasePlayer();
				
				int rawId = mActivity.getResources().getIdentifier(voice, "raw", mActivity.getPackageName());
				if(rawId != 0){
					mPlayer = MediaPlayer.create(mActivity,rawId);
					mPlayer.start();
				}
				
				// show hidden part
				if(currentPosition != position){
					if(previousView != null){
//						LinearLayout previousLayout = (LinearLayout) previousView.findViewById(R.id.item_hide);
						collapseView(previousView);
					}
					expandView(mView);
					//showBannerAd
					currentPosition = position;
//					mDetailAdapter.setPositionSelected(position);
					mPhraseListAdapter.setPositionSelected(position);
					previousView = mView;
				}
				
			}
			
		});
		
		return mView;
	}
	
	private void expandView(final View view)
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
	
	
	
	 * release mPlayer when replay or exit fragment
	 * 
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
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.hideBannerAd();
		mActivity.setTitle(mTitle);
	}
	
	@Override
	public void onDetach() {
//		mDatabase.close();
//		releasePlayer();
		super.onDetach();
		mActivity.increaseCounter();
		
	}
	
	
}
