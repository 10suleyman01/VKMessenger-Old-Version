package com.suleyman.vkclient.api.http.callable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.concurrent.Callable;

public class GetDrawableCallable implements Callable<Drawable> {
	
	private Context context;
	private String url;
	private int width, height;

	public GetDrawableCallable(Context context, String url, int width, int height) {
		this.context = context;
		this.url = url;
		this.width = width;
		this.height = height;
	}

	@Override
	public Drawable call() throws Exception {
		return Glide.with(context).
			asDrawable().
			apply(new RequestOptions().circleCropTransform()).
			load(url).
			into(width, height).
			get();
	}

}
