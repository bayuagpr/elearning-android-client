package com.elearning.client.view.mahasiswa.jawaban;


import com.elearning.client.model.Hasil;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.HasilResponse;
import com.elearning.client.network.response.UploadFileResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class NilaiPresenter {
    NilaiView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public NilaiPresenter(NilaiView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    void uploadHasil(String token, MultipartBody.Part file) {
        view.showProgress();
        disposable.add(
                apiInterface.uploadHasil(token, file)
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


    void updateJawaban(String token, String id, Hasil materi) {
        view.showProgress();
        disposable.add(
                apiInterface.updateHasil(token, id, materi)
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


    void simpanJawaban(String token, Hasil materi) {
        view.showProgress();
        disposable.add(
                apiInterface.saveHasil(token, materi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<HasilResponse>() {
                            @Override
                            public void onNext(HasilResponse kelasResponse) {
                                view.statusSuccess("Jawaban Disimpan");
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
    public void detachView() {
        disposable.dispose();
    }

}
