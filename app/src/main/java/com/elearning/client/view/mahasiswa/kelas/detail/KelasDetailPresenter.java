package com.elearning.client.view.mahasiswa.kelas.detail;



import android.util.Log;

import com.elearning.client.model.Enrollment;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.EnrollmentResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class KelasDetailPresenter {

    KelasDetailView view;
    ApiInterface apiInterface;
    CompositeDisposable disposable;

    public KelasDetailPresenter(KelasDetailView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }

    void saveEnroll(String token, Enrollment kelas) {
        view.showProgress();
        disposable.add(
                apiInterface.saveEnrollment(token, kelas)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<EnrollmentResponse>() {
                            @Override
                            public void onNext(EnrollmentResponse kelasResponse) {
                                view.statusSuccess("Permintaan bergabung telah diajukan");
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
