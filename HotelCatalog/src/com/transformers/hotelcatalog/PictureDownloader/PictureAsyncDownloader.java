package com.transformers.hotelcatalog.PictureDownloader;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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

}
