package com.ufo.learnchinese.utils;

import java.util.Locale;

public class Utils {
	
	public static String DEVICE_LANGUAGE = "en";
	public static final String TIENGVIET = "Tiếng Việt";
	
	public static String TABLE_LESSONS = "BaiHoc";
	public static String TABLE_IDIOMS = "ThanhNgu";
	public static String TABLE_READING = "LuyenDoc";
	public static String TABLE_VOCABULARY = "TuVung";
	public static String TABLE_GRAMMAR = "NguPhap";
	public static String TABLE_vIDEO = "Videos";
	
	public static String GRAMMAR_DATABASE_NAME = "enlesslove.dat";
	public static String PHRASE_DATABASE_NAME = "enlesslove.dat";
	
	public static final int PHRASE_DATABASE_VERSION = 12;
	public static final int GRAMMAR_DATABASE_VERSION = 1;
	
	public static String PHRASE_TABLE = "temp";
	public static String GRAMMAR_TABLE = "npt";
	
	public static String LANGUAGE_COLLUNM = "chinese";
	
	public static String GRAMMAR_COLUMN_ID = "_id";
	public static String GRAMMAR_COLUMN_NAME = "title";
	public static String GRAMMAR_COLUMN_CONTENT = "content";
	public static String CATEGORY_ID = "category_id";
	public static String LESSON_TYPE = "lesson";
	
	public static String TITLE = "title";
	
	public static String KEY_SEARCH_FRAGMENT = "search_fragment";
	
	public static String validSQL(String query ){
		return query.trim().toLowerCase().replace("'", "''");
	}

}
