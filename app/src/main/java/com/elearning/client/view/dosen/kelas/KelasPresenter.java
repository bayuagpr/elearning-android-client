package com.elearning.client.view.dosen.kelas;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.KelasResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KelasPresenter {

    KelasView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public KelasPresenter(KelasView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getKelas(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllKelasDosen(token ,id,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KelasResponse>(){
                            @Override
                            public void onNext(KelasResponse kelasResponse) {
                                view.statusSuccess(kelasResponse);
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
