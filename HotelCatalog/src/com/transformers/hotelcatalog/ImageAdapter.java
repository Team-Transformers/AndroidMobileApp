package com.transformers.hotelcatalog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.telerik.everlive.sdk.core.result.RequestResult;
import com.transformers.hotelcatalog.PictureDownloader.PictureAsyncDownloader;
import com.transformers.hotelcatalog.backend.DbRemote;
import com.transformers.hotelcatalog.backend.PictureDataItem;

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
	List<String> images;

	private Context context;

	public ImageAdapter(Context applicationContext, List<String> imgUrls) {
		context = applicationContext;
		this.images = imgUrls;
	}

	@Override
	public int getCount() {
		return images.size();
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

		new PictureAsyncDownloader(iv).execute(images.get(position));

		// iv.setImageResource(images[position]);
		return iv;
	}

	public class PictureAsyncDownloader extends
			AsyncTask<String, Integer, Bitmap> {

		private final WeakReference imageViewReference;

		public PictureAsyncDownloader(ImageView imageView) {
			imageViewReference = new WeakReference(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... pic_url) {

			String downloadLink = DbRemote.GetInstance().getDownloadLink(
					UUID.fromString(pic_url[0]));
			Log.d("d2", downloadLink);
			int count;
			Bitmap img = null;
			try {
				URL url = new URL(downloadLink);
				URLConnection conection = url.openConnection();
				conection.connect();

				int lenghtOfFile = conection.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream(),
						8192);

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
				img = BitmapFactory.decodeByteArray(imgArray, 0,
						imgArray.length);

			} catch (Exception e) {
				Log.d("Error: ", e.getMessage());
			}

			return img;
		}

		@Override
		protected void onPostExecute(Bitmap img) {
			// progressDialog.dismiss();
			if (isCancelled()) {
				img = null;
			}
			if (imageViewReference != null) {
				ImageView imageView = (ImageView) imageViewReference.get();
				if (imageView != null) {
					if (img != null) {
						imageView.setImageBitmap(img);
					} else {
						imageView.setImageDrawable(imageView.getContext()
								.getResources()
								.getDrawable(R.drawable.ic_launcher));
					}
				}
			}
		}

	}
}
