package com.elearning.client.view.mahasiswa.enroll.requested;



import android.util.Log;

import com.elearning.client.model.Enrollment;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.EnrollmentResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RequestedPresenter {

    RequestedView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public RequestedPresenter(RequestedView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getEnroll(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllEnrollKelas(token ,id,false,page, 10)
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
                apiInterface.getAllEnrollKelas(token ,id,false,page, 10)
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

    void updateEnroll(String token, String id, Enrollment materi) {
        view.showProgress();
        disposable.add(
                apiInterface.updateEnrollment(token, id, materi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){
                            @Override
                            public void onComplete() {
                                view.hideProgress();
                                view.refresh();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
                            }
                        })
        );
    }

    public void detachView() {
        disposable.dispose();
    }
}
