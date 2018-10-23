package com.suleyman.vkclient.module.fragment.photo;

import android.view.*;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.suleyman.vkclient.R;
import java.util.ArrayList;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class PhotoFragment extends DialogFragment {

	@ViewInject(R.id.photosPager)
	private ViewPager photosPager;

	@ViewInject(R.id.toolbar)
	private Toolbar toolbar;

	private PhotosAdapter adapter;
	private ArrayList<String> photosUrl;

	public static PhotoFragment newInstanse(Bundle args) {
		PhotoFragment photoFragment = new PhotoFragment();
		photoFragment.setArguments(args);
		return photoFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_photo, container, false);
		x.view().inject(this, view);

		Bundle arguments = getArguments();
		photosUrl = arguments.getStringArrayList("urls");
		int index = arguments.getInt("index");
		
		Toast.makeText(getContext(), ""+index, Toast.LENGTH_SHORT).show();
		
		adapter = new PhotosAdapter(getContext(), photosUrl, index);
		photosPager.setAdapter(adapter);
		
		photosPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
				@Override
				public void onPageScrolled(int pos, float p2, int p3) {
				}

				@Override
				public void onPageSelected(int pos) {
					adapter.setIndex(pos);
					setTitle(pos);
				}

				@Override
				public void onPageScrollStateChanged(int pos) {
				}
			});

		toolbar.setBackgroundResource(R.color.primary_black);
		toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);
		toolbar.setNavigationOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					dismiss();
				}
			});

		return view;
	}

	public void setTitle(int pos) {
		toolbar.setTitle(pos + 1 + " из " + photosUrl.size());
	}

	@Override
	public void onStart() {
		super.onStart();

		Dialog dialog = getDialog();
		if (dialog != null) {
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setLayout(width, height);
		}

	}

}
