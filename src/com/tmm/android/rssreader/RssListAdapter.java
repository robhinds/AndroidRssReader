package com.tmm.android.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<JSONObject> {

	public RssListAdapter(Activity activity, List<JSONObject> imageAndTexts) {
		super(activity, 0, imageAndTexts);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Activity activity = (Activity) getContext();
		LayoutInflater inflater = activity.getLayoutInflater();

		// Inflate the views from XML
		View rowView = inflater.inflate(R.layout.image_text_layout, null);
		JSONObject jsonImageText = getItem(position);
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		//The next section we update at runtime the text - as provided by the JSON from our REST call
		////////////////////////////////////////////////////////////////////////////////////////////////////
		TextView textView = (TextView) rowView.findViewById(R.id.job_text);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.feed_image);

		
        try {
        	
        	if (jsonImageText.get("imageLink") != null){
        		
        		System.out.println("XXXX Link found!");
        		String url = (String) jsonImageText.get("imageLink");
                URL feedImage= new URL(url);
            	
            	HttpURLConnection conn= (HttpURLConnection)feedImage.openConnection();
                InputStream is = conn.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);
                imageView.setImageBitmap(img);
        	}
        	
        	Spanned text = (Spanned)jsonImageText.get("text");
        	textView.setText(text);

        } catch (MalformedURLException e) {
        	//handle exception here - in case of invalid URL being parsed
        	//from the RSS feed item
        }
        catch (IOException e) {
        	//handle exception here - maybe no access to web
        }
        catch (JSONException e) {
        	textView.setText("JSON Exception");
        }

		return rowView;

	} 

}