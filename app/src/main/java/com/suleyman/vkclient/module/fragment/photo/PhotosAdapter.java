package com.suleyman.vkclient.module.fragment.photo;

import android.view.*;
import android.widget.*;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import com.bumptech.glide.Glide;
import com.suleyman.vkclient.R;
import java.util.ArrayList;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;

public class PhotosAdapter extends PagerAdapter {

	private Context context;
	private ArrayList<String> photoUrls;
	private int index;

	private LayoutInflater inflater;

	public PhotosAdapter(Context context, ArrayList<String> photoUrls, int index) {
		this.context = context;
		this.photoUrls = photoUrls;
		this.index = index;

		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return photoUrls.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		LinearLayout photolayout = (LinearLayout) inflater.inflate(R.layout.photo_view, container, false);

		ImageView photo = (ImageView) photolayout.findViewById(R.id.photoView);
		ImageMatrixTouchHandler zoomHandler = new ImageMatrixTouchHandler(context);
		photo.setOnTouchListener(zoomHandler);

		String url = null;

		if (index == 0) {
			url = photoUrls.get(position);
		}
		
		Glide.with(photo).
			load(url).
			into(photo);

		container.addView(photolayout, 0);

		return photolayout;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view.equals(object);
	}

}
