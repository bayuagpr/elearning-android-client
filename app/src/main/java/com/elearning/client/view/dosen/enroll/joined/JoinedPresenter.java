package com.elearning.client.view.dosen.enroll.joined;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.HasilResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class JoinedPresenter {

    JoinedHasil view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public JoinedPresenter(JoinedHasil view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getEnroll(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllEnrollKelas(token ,id,true,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EnrollmentResponse>(){
                            @Override
                            public void onNext(EnrollmentResponse materiResponse) {
                                view.statusSuccess(materiResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.toString());
                                Log.d("kelaspresenter", "onError: "+e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    public void getEnrollMore(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllEnrollKelas(token ,id,true,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EnrollmentResponse>(){
                            @Override
                            public void onNext(EnrollmentResponse materiResponse) {
                                view.statusSuccess(materiResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.toString());
                                Log.d("kelaspresenter", "onError: "+e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    public void detachView() {
        disposable.dispose();
    }
}
