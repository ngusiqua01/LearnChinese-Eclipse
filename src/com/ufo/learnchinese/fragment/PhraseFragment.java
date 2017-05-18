package com.ufo.learnchinese.fragment;

import java.util.ArrayList;

import com.ufo.learnchinese.MainActivity;
import com.ufo.learnchinese.R;
import com.ufo.learnchinese.adapter.CategoryItem;
import com.ufo.learnchinese.adapter.GridPhrasesAdapter;
import com.ufo.learnchinese.adapter.PhraseItem;
import com.ufo.learnchinese.database.Database;
import com.ufo.learnchinese.utils.Utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class PhraseFragment extends Fragment {
	
	GridView mGridView;
	GridPhrasesAdapter mAdapter;
	
	String mTitle= "";
	
	MainActivity mActivity;
//	String [] listNameGrid;
//	int []   listResourceGrid;
	
	Database mDatabase;
	Cursor mCursor;
	ArrayList<PhraseItem> listPhraseItems;
	ArrayList<CategoryItem> listCategoryItems;
	
	//
	FragmentManager mFragmentManager;
	FragmentTransaction mTransaction;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mActivity = (MainActivity) getActivity();
//		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(false);
//		this.mTitle = getArguments().getString(Utils.TITLE);
		mDatabase = new Database(mActivity, Utils.PHRASE_DATABASE_NAME);
		mDatabase.open();
		
//		listNameGrid = mActivity.getResources().getStringArray(R.array.list_name_grid_phrases);
//		ArrayList<String> listCat = mDatabase.getListCategoryPhrase();
//		listNameGrid = new String[listCat.size()];
//		listNameGrid  =  listCat.toArray(listNameGrid);

//		TypedArray array = this.getResources().obtainTypedArray(R.array.list_resource_grid_phrases);
		/*listResourceGrid = new int[array.length()];
		for(int i = 0; i < array.length(); i++){
			listResourceGrid[i] = array.getResourceId(i, -1);
		}*/
		listCategoryItems  = mDatabase.getListCategoryItems();
		mAdapter = new GridPhrasesAdapter(mActivity, listCategoryItems, R.layout.item_grid_phrase);
		
		mFragmentManager = mActivity.getFragmentManager();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View mView = inflater.inflate(R.layout.phrase_fragment, container, false);
		mGridView = (GridView) mView.findViewById(R.id.grid_view_phrase);
		mGridView.setAdapter(mAdapter);
//		ListView  mListView = (ListView) mView.findViewById(R.id.list_view_phrases);
//		mListView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long viewId) {
				int catId = position;
				
//				mCursor = mDatabase.getListPhrases(catId);
				
				PhraseDetailFragment mFragment = new PhraseDetailFragment();
				Bundle mBundle = new Bundle();
				mBundle.putInt(Utils.CATEGORY_ID, position+1);
				mBundle.putString(Utils.TITLE, listCategoryItems.get(position).categoryName);
				mFragment.setArguments(mBundle);
				
				mTransaction = mFragmentManager.beginTransaction();
				mTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				mTransaction.replace(R.id.main_content, mFragment);
				mTransaction.addToBackStack(null);
				mTransaction.commit();
			}
			
		});
		
		return mView;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mActivity.getDrawerToogle().setDrawerIndicatorEnabled(true);
//		this.mActivity.setTitle(mTitle);
		this.mActivity.setTitle(getResources().getString(R.string.app_name));
//		mActivity.increaseCounter();
		mActivity.showBannerAd();
	}
	
	@Override
	public void onDetach() {
		mDatabase.close();
		super.onDetach();
	}
	
}
