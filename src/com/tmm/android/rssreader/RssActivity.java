package com.tmm.android.rssreader;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.tmm.android.rssreader.reader.RssReader;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;


public class RssActivity extends ListActivity {

	private RssListAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<JSONObject> jobs = new ArrayList<JSONObject>();
		try {
			jobs = RssReader.getLatestRssFeed();
		} catch (Exception e) {
			Log.e("RSS ERROR", "Error loading RSS Feed Stream >> " + e.getMessage() + " //" + e.toString());
		}

		adapter = new RssListAdapter(this,jobs);
		setListAdapter(adapter);
	}

}