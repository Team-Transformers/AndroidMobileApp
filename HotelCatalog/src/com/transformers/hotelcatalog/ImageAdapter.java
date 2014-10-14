package com.transformers.hotelcatalog;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	int[] images = { R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
//			R.drawable.camera, R.drawable.camera, R.drawable.camera,
			R.drawable.camera, R.drawable.camera, R.drawable.camera };

	private Context context;

	public ImageAdapter(Context applicationContext) {
		context = applicationContext;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView != null) {
			iv = (ImageView) convertView;
		} else {
			iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setPadding(10, -20, 10, -20);
		}
		

		
		//iv.setImageResource(images[position]);
		return iv;
	}

}
