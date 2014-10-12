package com.transformers.hotelcatalog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	int[] images = { R.drawable.one, R.drawable.two, R.drawable.three,
			R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven,
			R.drawable.eight, R.drawable.nine, R.drawable.ten,
			R.drawable.eleven, R.drawable.twelve };

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
			iv.setScaleType(ScaleType.FIT_CENTER);
			iv.setPadding(0, -70, 0, -70);
		}
		iv.setImageResource(images[position]);
		return iv;
	}

}
