package com.elearning.client.view.mahasiswa.soal.detail;



import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.ExistHasilResponse;

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

    public void getExistHasil(String token, String idMahasiswa, String idSoal) {
        view.showProgress();
        disposable.add(
                apiInterface.getExistHasil(token, idMahasiswa, idSoal)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ExistHasilResponse>(){
                            @Override
                            public void onNext(ExistHasilResponse kelasResponse) {
                                //Log.d("isi get search", "onNext: "+kelasResponse.getKelasList().toString());
                                view.showProgress();
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

    public void detachView() {
        disposable.dispose();
    }
}
