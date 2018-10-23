package com.suleyman.vkclient.module.fragment.profile;

import com.suleyman.vkclient.api.object.user.*;
import org.xutils.view.annotation.*;

import android.os.Bundle;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.base.BaseFragment;
import de.hdodenhof.circleimageview.CircleImageView;

@ContentView(R.layout.fragment_profile)
public class ProfileFragment extends BaseFragment<ProfileView, ProfilePresenter> implements ProfileView {

	@ViewInject(R.id.profilePhoto)
	private CircleImageView profilePhoto;

	@ViewInject(R.id.profileTitle)
	private TextView profileTitle;

	@Override
	public ProfilePresenter createPresenter() {
		return new ProfilePresenter();
	}

	public static ProfileFragment newInstance() {
		return new ProfileFragment();
	}

	@Override
	public void onCreateFragment(Bundle savedInstanceState) {

	}

	@Override
	public void onStart() {
		super.onStart();

		presenter.loadProfile();
	}

	@Override
	public void onStop() {
		super.onStop();

		presenter.dispose();
	}

	@Override
	public void showProfile(ObjectUser objectUser) {

		ItemUser user = objectUser.getResponse().get(0);

		String title = user.getTitle();
		String photoUrl = user.getPhoto();

		profileTitle.setText(title);

		Glide.with(this).
			load(photoUrl).
			into(profilePhoto);

	}

	@Override
	public void showError(Throwable error) {
		error.printStackTrace();
	}
}
