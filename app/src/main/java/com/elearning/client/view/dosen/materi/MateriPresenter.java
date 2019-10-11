package com.elearning.client.view.dosen.materi;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.network.response.MateriResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MateriPresenter {

    MateriView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public MateriPresenter(MateriView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getMateri(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllKelasMateri(token ,id,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MateriResponse>(){
                            @Override
                            public void onNext(MateriResponse materiResponse) {
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

    public void getMateriMore(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllKelasMateri(token ,id,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MateriResponse>(){
                            @Override
                            public void onNext(MateriResponse materiResponse) {
                                view.loadMore(materiResponse);
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
