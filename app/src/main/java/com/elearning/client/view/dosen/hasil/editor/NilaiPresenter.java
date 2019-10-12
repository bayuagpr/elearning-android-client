package com.elearning.client.view.dosen.hasil.editor;


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




    void simpanNilai(String token, String id, Hasil materi) {
        view.showProgress();
        disposable.add(
                apiInterface.beriNilai(token, id, materi)
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



    public void detachView() {
        disposable.dispose();
    }

}
