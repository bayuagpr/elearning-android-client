package com.elearning.client.view.dosen.soal.editor;


import com.elearning.client.model.Soal;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.SoalResponse;
import com.elearning.client.network.response.UploadFileResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class SoalPresenter {
    SoalVIew view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public SoalPresenter(SoalVIew view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }


    void saveSoal(String token, Soal materi) {
        view.showProgress();
        disposable.add(
                apiInterface.saveSoal(token, materi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SoalResponse>() {
                            @Override
                            public void onNext(SoalResponse kelasResponse) {
                                view.statusSuccess("Soal Disimpan");
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

    void updateSoal(String token, String id, Soal materi) {
        view.showProgress();
        disposable.add(
                apiInterface.updateSoal(token, id, materi)
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

    void deleteSoal(String token, String id) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteSoal(token, id)
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
    void uploadSoal(String token, MultipartBody.Part file) {
        view.showProgress();
        disposable.add(
                apiInterface.uploadSoal(token, file)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UploadFileResponse>() {
                            @Override
                            public void onNext(UploadFileResponse uploadFileResponse) {
                                view.handleFileUpload(uploadFileResponse);
                            }

                            @Override
                            public void onError(Throwable e) {
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
