package com.elearning.client.view.dosen.kelas.editor;


import com.elearning.client.model.Kelas;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.network.response.MataKuliahResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
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

    void getListMatkul(String token, Integer page) {
        view.showProgress();
        disposable.add(
            apiInterface.getAllMataKuliah(token, page, 10)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<MataKuliahResponse>(){
                        @Override
                        public void onNext(MataKuliahResponse mataKuliahResponse) {
                            view.setListMatkul(mataKuliahResponse);
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

    void saveKelas(String token, Kelas kelas) {
        view.showProgress();
        disposable.add(
                apiInterface.saveKelas(token, kelas)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<KelasResponse>() {
                            @Override
                            public void onNext(KelasResponse kelasResponse) {
                                view.statusSuccess("Kelas Disimpan");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                view.hideProgress();
                            }
                        })
        );
    }

    void updateKelas(String token, String id, Kelas kelas) {
        view.showProgress();
        disposable.add(
                apiInterface.updateKelas(token, id, kelas)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){
                            @Override
                            public void onComplete() {
                                view.hideProgress();
                                view.statusSuccess("berhasil update");
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.hideProgress();
                                view.statusError(e.getLocalizedMessage());
                            }
                        })
        );
    }

    void deleteKelas(String token, String id) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteKelas(token, id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver(){
                            @Override
                            public void onComplete() {
                                view.hideProgress();
                                view.statusSuccess("berhasil delete");
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
