package com.suleyman.vkclient.module.activity.login;

import android.widget.*;
import com.suleyman.vkclient.api.object.account.*;
import io.reactivex.functions.*;
import org.xutils.view.annotation.*;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import com.suleyman.vkclient.R;
import com.suleyman.vkclient.module.activity.main.MainActivity;
import com.suleyman.vkclient.module.base.BaseActivity;
import com.suleyman.vkclient.util.DisposableManager;
import io.reactivex.Observable;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

	@ViewInject(R.id.editTextEmail)
	private EditText editTextEmail;

	@ViewInject(R.id.editTextPassword)
	private EditText editTextPassword;

	@ViewInject(R.id.btnLogin)
	private Button buttonLogin;

	@Override
	public LoginPresenter createPresenter() {
		return new LoginPresenter();
	}

	@Override
	public void onCreateActivity(Bundle savedInstanceState) {

		// checking for login
		if (VKAccountManager.isLogined()) {
			toMain();
		}

		buttonLogin.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					final Observable<String> observableTextEmail = Observable.just(editTextEmail.getText().toString());
					final Observable<String> observableTextPassword = Observable.just(editTextPassword.getText().toString());

					Observable.combineLatest(observableTextEmail, observableTextPassword, 
						new BiFunction<String, String, Boolean>(){
							@Override
							public Boolean apply(String email, String password) throws Exception {
								return !email.isEmpty() && !password.isEmpty();
							}
						}).subscribe(new Consumer<Boolean>(){
							@Override
							public void accept(Boolean notEmptyFields) throws Exception {
								if (notEmptyFields) {
									/** start presenter for login */
									presenter.login(observableTextEmail.blockingFirst(), observableTextPassword.blockingFirst());
								}
							}
						});
				}
			});
	}

	@Override
	public void showDialogLoading(boolean loading) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Загрузка...");
		progressDialog.setIndeterminate(true);
		progressDialog.show();
	}

	@Override
	public void setAccount(VKAccount account) {
		VKAccountManager.save(account);
	}

	@Override
	public void toMain() {
		// to MainActivity
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);

		// finish LoginActivity
		finish();
	}

	@Override
	public void showDialogError(String message) {
		AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
		errorDialog.setTitle(R.string.error);
		errorDialog.setMessage(message);
		errorDialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		/** Dispose login task */
		DisposableManager.dispose("login");
	}
}
