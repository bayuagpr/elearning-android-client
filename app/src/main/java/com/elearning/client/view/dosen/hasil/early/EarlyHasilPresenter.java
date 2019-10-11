package com.elearning.client.view.dosen.hasil.early;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.HasilResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class EarlyHasilPresenter {

    EarlyHasilView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public EarlyHasilPresenter(EarlyHasilView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getHasil(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllHasilSoal(token ,id,"early",page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<HasilResponse>(){
                            @Override
                            public void onNext(HasilResponse materiResponse) {
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

    public void getHasilMore(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllHasilSoal(token ,id,"early",page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<HasilResponse>(){
                            @Override
                            public void onNext(HasilResponse materiResponse) {
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
