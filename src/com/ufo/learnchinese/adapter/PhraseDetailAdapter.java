package com.ufo.learnchinese.adapter;

import com.ufo.learnchinese.R;
import com.ufo.learnchinese.database.Database;
import com.ufo.learnchinese.utils.DecodeUtil;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhraseDetailAdapter extends CursorAdapter{
	
	Context mContext;
	TextView txtKorean;
	TextView txtPinpyn;
	TextView txtVietnam;
	
	View hiddenLayout;
//	ImageView imgfavorite;
	
	Cursor mCursor;
	
	MediaPlayer mPlayer;
	
	int itemResId;
	
	String phraseColumnName = "";
	Database mDatabase;
	
	int currentPosition = -1;
	
	/**
	 * 
	 * @param context
	 * @param c
	 * @param flag should be set to 0
	 */
	public PhraseDetailAdapter(Context context, Cursor c, int flag, int resId, Database pDatabase) {
		super(context, c,flag);
		this.mContext = context;
		this.mCursor = c;
		this.itemResId = resId;
		this.phraseColumnName = mContext.getResources().getString(R.string.phrase_name_column);
		this.mDatabase = pDatabase;
	}


	@Override
	public void bindView(View view, Context context, final Cursor cursor) {
//		System.out.println("PhraseDetailAdapter.bindView()");
		ViewHolder holder = (ViewHolder) view.getTag();
		int position = cursor.getPosition();
		if(position != currentPosition){
			// hide
			holder.hiddenLayout.setVisibility(View.GONE);
		}else{
			holder.hiddenLayout.setVisibility(View.VISIBLE);
		}
		txtKorean = holder.txtKorean;
		txtPinpyn = holder.txtPinyin;
		txtVietnam = holder.txtVietnamese;
		final ImageView imgfavorite = holder.imgStar;
		
		String pinyin = cursor.getString(cursor.getColumnIndex("pinyin"));
        String korean = cursor.getString(cursor.getColumnIndex("chinese"));
        String vietnamese = cursor.getString(cursor.getColumnIndex(this.phraseColumnName));
        
        String print = cursor.getString(cursor.getColumnIndex("vietnamese"));
        
        DecodeUtil mDecodeUtil = new DecodeUtil(DecodeUtil.keycode_phrase);
        pinyin  = mDecodeUtil.decode(pinyin);
        korean = mDecodeUtil.decode(korean);
        
        System.out.println("-"+print+" -- "+korean+" -- "+pinyin);
//        System.out.println("PhraseDetailAdapter.bindView() korean = "+korean);
        
        txtKorean.setText(korean);
        txtPinpyn.setText(pinyin);
        int stt = position +1;
        txtVietnam.setText(vietnamese);
        
        final String voice = cursor.getString(cursor.getColumnIndex("voice"))+"_f";
        final int favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
        final int phraseId = cursor.getInt(cursor.getColumnIndex("_id"));
//        System.out.println("bindview phraseId = "+phraseId+" && favorite = "+favorite);
        if(favorite == 1){
        	imgfavorite.setSelected(true);
        }else{
        	imgfavorite.setSelected(false);
        }
        
        imgfavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				/*if(mPlayer != null && mPlayer.isPlaying()){
					try{
						mPlayer.stop();
						mPlayer.release();
					}catch (Exception e) {
						
					}
				}
				
				int rawId = mContext.getResources().getIdentifier(voice, "raw", mContext.getPackageName());
				if(rawId != 0){
					mPlayer = MediaPlayer.create(mContext,rawId);
					mPlayer.start();
				}*/
				if(!imgfavorite.isSelected()){
					mDatabase.updateFavorite(phraseId, 1);
					imgfavorite.setSelected(true);
				}else {
					mDatabase.updateFavorite(phraseId, 0);
					imgfavorite.setSelected(false);
				}
				
				
			}
		});
	}
	
	public void setPositionSelected(int position)
	{
		this.currentPosition =position;
	}

	@Override
	public View newView(Context context, final Cursor cursor, ViewGroup parent) {
//		System.out.println("PhraseDetailAdapter.newView()");
		View view = LayoutInflater.from(mContext).inflate(itemResId,parent, false);
		ViewHolder holder = new ViewHolder();
		holder.txtKorean = (TextView) view.findViewById(R.id.txt_korea);
		holder.txtPinyin = (TextView) view.findViewById(R.id.txt_pinpyn);;
		holder.txtVietnamese = (TextView) view.findViewById(R.id.txt_vietnam);
		holder.imgStar  =  (ImageView)view.findViewById(R.id.imgVoice);
		holder.hiddenLayout = view.findViewById(R.id.item_hide);
		
		view.setTag(holder);
		return view;
	}
	
	 static class ViewHolder{
		TextView txtKorean;
		TextView txtPinyin;
		TextView txtVietnamese;
		ImageView imgStar;
		
		View hiddenLayout;
	}
	
	
}
