package com.elearning.client.view.mahasiswa.soal;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.SoalResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SoalPresenter {

    SoalView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SoalPresenter(SoalView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getSoal(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllKelasSoal(token ,id,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SoalResponse>(){
                            @Override
                            public void onNext(SoalResponse soalResponse) {
                                view.statusSuccess(soalResponse);
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

    public void getSoalMore(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllKelasSoal(token ,id,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SoalResponse>(){
                            @Override
                            public void onNext(SoalResponse soalResponse) {
                                view.statusSuccess(soalResponse);
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
