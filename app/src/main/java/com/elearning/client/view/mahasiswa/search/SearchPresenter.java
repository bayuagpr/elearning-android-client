package com.elearning.client.view.mahasiswa.search;



import android.util.Log;

import com.elearning.client.model.Kelas;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.ExistEnrollResponse;
import com.elearning.client.network.response.KelasResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter {
    SearchView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SearchPresenter(SearchView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getSearch(String token, String nama, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getNamaKelas(token, nama, page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KelasResponse>(){
                            @Override
                            public void onNext(KelasResponse kelasResponse) {
                                //Log.d("isi get search", "onNext: "+kelasResponse.getKelasList().toString());
                                view.statusSuccess(kelasResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    public void getExistEnrollment(String token, String idMahasiswa, String idKelas) {
        view.showProgress();
        disposable.add(
                apiInterface.getExistEnroll(token, idMahasiswa, idKelas)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ExistEnrollResponse>(){
                            @Override
                            public void onNext(ExistEnrollResponse kelasResponse) {
                                //Log.d("isi get search", "onNext: "+kelasResponse.getKelasList().toString());
                                view.isExist(kelasResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    public void getSearchMore(String token, String nama, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getNamaKelas(token, nama, page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KelasResponse>(){
                            @Override
                            public void onNext(KelasResponse kelasResponse) {
                                //Log.d("isi get search", "onNext: "+kelasResponse.getKelasList().toString());
                                view.loadMore(kelasResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
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
