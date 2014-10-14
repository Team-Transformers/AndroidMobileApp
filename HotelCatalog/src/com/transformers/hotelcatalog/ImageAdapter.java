package com.transformers.hotelcatalog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.transformers.hotelcatalog.PictureDownloader.PictureAsyncDownloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	private ImageView iv;
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
		if (convertView != null) {
			iv = (ImageView) convertView;
		} else {
			iv = new ImageView(context);
			iv.setScaleType(ScaleType.CENTER_CROP);
			iv.setPadding(10, -20, 10, -20);
		}
		

		//new PictureAsyncDownloader().execute("http://upload.wikimedia.org/wikipedia/en/d/d6/Preview_icon.png");
		
		//iv.setImageResource(images[position]);
		return iv;
	}
	public class PictureAsyncDownloader extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... pic_url) {
			int count;
			Bitmap img = null;
			try {
				URL url = new URL(pic_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				int lenghtOfFile = conection.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream(), 8192);

				byte data[] = new byte[1024];
				byte imgArray[] = new byte[lenghtOfFile];
				int total = 0;

				while ((count = input.read(data)) != -1) {
					for (int i = total; i < total + count; i++) {
						imgArray[i] = data[i - total];
					}
					total += count;
					publishProgress((int) ((total * 100) / lenghtOfFile));

				}

				input.close();
				img = BitmapFactory.decodeByteArray(imgArray, 0, imgArray.length);

			} catch (Exception e) {
				Log.d("Error: ", e.getMessage());
			}

			return img;
		}
		
		@Override
		protected void onPostExecute(Bitmap img) {
			//progressDialog.dismiss();
			iv.setImageBitmap(img);
		}

	}
}
