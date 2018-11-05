package com.suleyman.vkclient.api;

import io.reactivex.*;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Callable;

public class ObservableProvider {

	public static <T> Observable<T> observableFromCallable(Callable<T> callable) {
		return Observable.fromCallable(callable).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread());
	}

	public static <T> Flowable<T> flowableFromCallable(Callable<T> callable) {
		return Flowable.fromCallable(callable).
			subscribeOn(Schedulers.io()).
			observeOn(AndroidSchedulers.mainThread());
	}

}
