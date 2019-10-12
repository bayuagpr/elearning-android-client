package com.elearning.client.view.mahasiswa.kelas.tertunda;



import android.util.Log;

import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.EnrollmentResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TertundaKelasPresenter {

    TertundaKelasView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public TertundaKelasPresenter(TertundaKelasView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    public void getKelas(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllEnrollMahasiswa(token ,id,false,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EnrollmentResponse>(){
                            @Override
                            public void onNext(EnrollmentResponse kelasResponse) {
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

    public void getKelasMore(String token,String id, Integer page) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllEnrollMahasiswa(token ,id,false,page, 10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EnrollmentResponse>(){
                            @Override
                            public void onNext(EnrollmentResponse kelasResponse) {
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
