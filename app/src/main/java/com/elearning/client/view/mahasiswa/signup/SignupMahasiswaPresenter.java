package com.elearning.client.view.mahasiswa.signup;


import android.util.Log;

import com.elearning.client.model.Mahasiswa;
import com.elearning.client.model.User;
import com.elearning.client.network.ApiClient;
import com.elearning.client.network.ApiInterface;
import com.elearning.client.network.response.JurusanResponse;
import com.elearning.client.network.response.MahasiswaResponse;
import com.elearning.client.network.response.UserResponse;
import com.elearning.client.view.dosen.signup.SignupView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SignupMahasiswaPresenter {
    private SignupMahasiswaView view;
    private ApiInterface apiInterface;
    private CompositeDisposable disposable;

    public SignupMahasiswaPresenter(SignupMahasiswaView view) {
        this.view = view;
        apiInterface = ApiClient.getClient(view.getContext()).create(ApiInterface.class);
        disposable = new CompositeDisposable();
    }


    public void regist(User user) {
        view.showProgress();
        disposable.add(
          apiInterface.postRegistration(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<User>(){
                        @Override
                        public void onNext(User authResponse) {
                            view.hideProgress();
//                            if (authResponse.getStatus().equals("404")) {
//                                Log.d("Login attempt", "gagal");
//                                view.statusError(authResponse.getStatus());
//                            } else {
//                                Log.d("Login attempt", "berhasil");
//                                view.statusSuccess(authResponse.getUser().getToken());
//                            }
                            view.statusSuccess(authResponse.getToken());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Login attempt", "error");
                            Log.d("Error disposable", "onError " + e.getMessage());
                            view.hideProgress();
                            view.statusError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d("Login attempt", "komplit");
                            view.hideProgress();
                        }
                    })
        );
    }

    void getListJurusan(String token) {
        view.showProgress();
        disposable.add(
                apiInterface.getAllJurusan(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<JurusanResponse>(){
                            @Override
                            public void onNext(JurusanResponse mataKuliahResponse) {
                                view.setListJurusan(mataKuliahResponse);
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

    public void submitMahasiswa(String token, Mahasiswa user) {
        view.showProgress();
        disposable.add(
                apiInterface.saveMahasiswa(token,user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MahasiswaResponse>(){
                            @Override
                            public void onNext(MahasiswaResponse authResponse) {
                                view.hideProgress();
//                            if (authResponse.getStatus().equals("404")) {
//                                Log.d("Login attempt", "gagal");
//                                view.statusError(authResponse.getStatus());
//                            } else {
//                                Log.d("Login attempt", "berhasil");
//                                view.statusSuccess(authResponse.getUser().getToken());
//                            }
                                view.afterSubmitMahasiswa();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Login attempt", "error");
                                Log.d("Error disposable", "onError " + e.getMessage());
                                view.hideProgress();
                                view.statusError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("Login attempt", "komplit");
                                view.hideProgress();
                            }
                        })
        );
    }

    public void loginAuth(User user) {
        view.showProgress();
        disposable.add(
                apiInterface.postAuthMahasiswa(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<User>(){
                            @Override
                            public void onNext(User authResponse) {
                                view.hideProgress();
//                            if (authResponse.getStatus().equals("404")) {
//                                Log.d("Login attempt", "gagal");
//                                view.statusError(authResponse.getStatus());
//                            } else {
//                                Log.d("Login attempt", "berhasil");
//                                view.statusSuccess(authResponse.getUser().getToken());
//                            }
                                view.statusSuccess(authResponse.getToken());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Login attempt", "error");
                                Log.d("Error disposable", "onError " + e.getMessage());
                                view.hideProgress();
                                view.statusError(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.d("Login attempt", "komplit");
                                view.hideProgress();
                            }
                        })
        );
    }

    public void getUser(String token) {
        view.showProgress();
        disposable.add(
                apiInterface.getUser(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserResponse>(){
                            @Override
                            public void onNext(UserResponse userResponse) {
                                view.statusSuccess(userResponse.getUser().getEmail());
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
