package com.elearning.client.view.mahasiswa.materi.editor;


import com.elearning.client.model.Materi;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.MateriResponse;
import com.elearning.client.network.response.UploadFileResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class MateriPresenter {
    MateriView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public MateriPresenter(MateriView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }


    void saveMateri(String token, Materi materi) {
        view.showProgress();
        disposable.add(
                apiInterface.saveMateri(token, materi)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MateriResponse>() {
                            @Override
                            public void onNext(MateriResponse kelasResponse) {
                                view.statusSuccess("Materi Disimpan");
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

    void updateMateri(String token, String id, Materi materi) {
        view.showProgress();
        disposable.add(
                apiInterface.updateMateri(token, id, materi)
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

    void deleteMateri(String token, String id) {
        view.showProgress();
        disposable.add(
                apiInterface.deleteMateri(token, id)
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
    void uploadMateri(String token, MultipartBody.Part file) {
        view.showProgress();
        disposable.add(
                apiInterface.uploadMateri(token, file)
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
