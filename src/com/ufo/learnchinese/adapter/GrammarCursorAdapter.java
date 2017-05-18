package com.ufo.learnchinese.adapter;

import com.ufo.learnchinese.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GrammarCursorAdapter extends CursorAdapter {
	


	public GrammarCursorAdapter(Context context, Cursor c) {
		super(context, c);
		
	}

	@Override
	public void bindView(View view, Context pContext, Cursor pCursor) {
		TextView mTextView = (TextView)view.findViewById(R.id.item_text);
		mTextView.setText(pCursor.getString(pCursor.getColumnIndex("title")));
		
		TextView imgTextView = (TextView)view.findViewById(R.id.item_image);
		imgTextView.setText(String.valueOf(pCursor.getPosition() +1));
		
	}

	@Override
	public View newView(Context pContext, Cursor pCursor, ViewGroup parent) {
		return LayoutInflater.from(pContext).inflate(R.layout.item_list_view, parent,false);
	}

}
