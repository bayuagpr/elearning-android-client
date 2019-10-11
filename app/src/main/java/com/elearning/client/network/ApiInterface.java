package com.elearning.client.network;

import com.elearning.client.model.Dosen;
import com.elearning.client.model.Enrollment;
import com.elearning.client.model.Fakultas;
import com.elearning.client.model.Hasil;
import com.elearning.client.model.Jurusan;
import com.elearning.client.model.Kelas;
import com.elearning.client.model.MataKuliah;
import com.elearning.client.model.Materi;
import com.elearning.client.model.Soal;
import com.elearning.client.model.User;
import com.elearning.client.model.UserLogin;
import com.elearning.client.network.response.DosenResponse;
import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.FakultasResponse;
import com.elearning.client.network.response.HasilResponse;
import com.elearning.client.network.response.JurusanResponse;
import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.network.response.MataKuliahResponse;
import com.elearning.client.network.response.MateriResponse;
import com.elearning.client.network.response.SoalResponse;
import com.elearning.client.network.response.UploadFileResponse;
import com.elearning.client.network.response.UserResponse;


import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    //Auth
    @POST("credential/auth/mahasiswa")
    Observable<User> postAuthMahasiswa(@Body User user);

    @POST("credential/auth/dosen")
    Observable<User> postAuthDosen(@Body User user);

    @GET("credential/refresh")
    Observable<UserResponse> postRefresh();

    @POST("credential/registration")
    Observable<User> postRegistration(@Body User user);

    @GET("user/current")
    Observable<UserResponse> getUser(@Header("Authorization") String token);

    //Dosen CRUD
    @GET("dosen/tampilkan")
    Observable<DosenResponse> getAllDosen(@Header("Authorization") String token,
                                          @Query("page") Integer page,
                                          @Query("size") Integer size);

    @GET("dosen/tampilkan")
    Observable<DosenResponse> getOneDosen(@Header("Authorization") String token,
                                              @Query("id") String id);



    @POST("dosen")
    Observable<DosenResponse> saveDosen(@Header("Authorization") String token,
                                              @Body Dosen dosen);

    @PUT("dosen/renewal")
    Completable updateDosen(@Header("Authorization") String token,
                               @Query("id") String id,
                               @Body Dosen dosen);

    @DELETE("dosen/hapus")
    Completable deleteDosen(@Header("Authorization") String token,
                               @Query("id") String id);

    //Enrollment CRUD
    @GET("enrollment/tampilkan")
    Observable<EnrollmentResponse> getAllEnrollment(@Header("Authorization") String token,
                                                    @Query("page") Integer page,
                                                    @Query("size") Integer size);
    @GET("enrollment/tampilkanSemuaKelas")
    Observable<EnrollmentResponse> getAllEnrollKelas(@Header("Authorization") String token,
                                              @Query("id") String id,
                                                @Query("disetujui") Boolean disetujui,
                                              @Query("page") Integer page,
                                              @Query("size") Integer size);
    @GET("enrollment/tampilkanSemuaMahasiswa")
    Observable<EnrollmentResponse> getAllEnrollMahasiswa(@Header("Authorization") String token,
                                                   @Query("id") String id,
                                                   @Query("page") Integer page,
                                                   @Query("size") Integer size);
    @GET("enrollment/tampilkan")
    Observable<EnrollmentResponse> getOneEnrollment(@Header("Authorization") String token,
                                          @Query("id") String id);



    @POST("enrollment")
    Observable<EnrollmentResponse> saveEnrollment(@Header("Authorization") String token,
                                        @Body Enrollment enrollment);


    @PUT("enrollment/renewal")
    Completable updateEnrollment(@Header("Authorization") String token,
                            @Query("id") String id,
                            @Body Enrollment enrollment);

    @DELETE("enrollment/hapus")
    Completable deleteEnrollment(@Header("Authorization") String token,
                            @Query("id") String id);

    //Hasil CRUD
    @GET("hasil/tampilkan")
    Observable<HasilResponse> getAllHasil(@Header("Authorization") String token,
                                          @Query("page") Integer page,
                                          @Query("size") Integer size);
    @GET("hasil/tampilkanSemuaSoal")
    Observable<HasilResponse> getAllHasilSoal(@Header("Authorization") String token,
                                             @Query("id") String id,
                                             @Query("status") String status,
                                             @Query("page") Integer page,
                                             @Query("size") Integer size);
    @GET("hasil/tampilkanSemuaMahasiswa")
    Observable<HasilResponse> getAllHasilMahasiswa(@Header("Authorization") String token,
                                             @Query("id") String id,
                                             @Query("page") Integer page,
                                             @Query("size") Integer size);
    @GET("hasil/tampilkan")
    Observable<HasilResponse> getOneHasil(@Header("Authorization") String token,
                                          @Query("id") String id);



    @POST("hasil")
    Observable<HasilResponse> saveHasil(@Header("Authorization") String token,
                                        @Body Hasil hasil);


    @PUT("hasil/renewal")
    Completable updateHasil(@Header("Authorization") String token,
                            @Query("id") String id,
                            @Body Hasil hasil);

    @DELETE("hasil/hapus")
    Completable deleteHasil(@Header("Authorization") String token,
                            @Query("id") String id);

    //Fakultas CRUD
    @GET("fakultas/tampilkan")
    Observable<FakultasResponse> getAllFakultas(@Header("Authorization") String token,
                                                @Query("page") Integer page,
                                                @Query("size") Integer size);

    @GET("fakultas/tampilkan")
    Observable<FakultasResponse> getOneFakultas(@Header("Authorization") String token,
                                                @Query("id") String id);



    @POST("fakultas")
    Observable<FakultasResponse> saveFakultas(@Header("Authorization") String token,
                                              @Body Fakultas fakultas);


    @PUT("fakultas/renewal")
    Completable updateFakultas(@Header("Authorization") String token,
                               @Query("id") String id,
                               @Body Fakultas fakultas);

    @DELETE("fakultas/hapus")
    Completable deleteFakultas(@Header("Authorization") String token,
                               @Query("id") String id);

    //Jurusan CRUD
    @GET("jurusan/tampilkan")
    Observable<JurusanResponse> getAllJurusan(@Header("Authorization") String token,
                                              @Query("page") Integer page,
                                              @Query("size") Integer size);

    @GET("jurusan/tampilkan")
    Observable<JurusanResponse> getOneJurusan(@Header("Authorization") String token,
                                                @Query("id") String id);



    @POST("jurusan")
    Observable<JurusanResponse> saveJurusan(@Header("Authorization") String token,
                                              @Body Jurusan jurusan);


    @PUT("jurusan/renewal")
    Completable updateJurusan(@Header("Authorization") String token,
                               @Query("id") String id,
                               @Body Jurusan jurusan);

    @DELETE("jurusan/hapus")
    Completable deleteJurusan(@Header("Authorization") String token,
                               @Query("id") String id);

    //Kelas CRUD
    @GET("kelas/tampilkan")
    Observable<KelasResponse> getAllKelas(@Header("Authorization") String token,
                                          @Query("page") Integer page,
                                          @Query("size") Integer size);

    @GET("kelas/tampilkanSemua")
    Observable<KelasResponse> getAllKelasDosen(@Header("Authorization") String token,
                                               @Query("id") String id,
                                          @Query("page") Integer page,
                                          @Query("size") Integer size);

    @GET("kelas/tampilkan")
    Observable<KelasResponse> getOneKelas(@Header("Authorization") String token,
                                              @Query("id") String id);

    @GET("kelas/tampilkan/{nama}")
    Observable<KelasResponse> getNamaKelas(@Header("Authorization") String token,
                                          @Path("nama") String nama,
                                           @Query("page") Integer page,
                                           @Query("size") Integer size);



    @POST("kelas")
    Observable<KelasResponse> saveKelas(@Header("Authorization") String token,
                                            @Body Kelas kelas);


    @PUT("kelas/renewal")
    Completable updateKelas(@Header("Authorization") String token,
                              @Query("id") String id,
                            @Body Kelas kelas);

    @DELETE("kelas/hapus")
    Completable deleteKelas(@Header("Authorization") String token,
                              @Query("id") String id);

    //Mata Kuliah CRUD
    @GET("matakuliah/tampilkan")
    Observable<MataKuliahResponse> getAllMataKuliah(@Header("Authorization") String token,
                                                    @Query("page") Integer page,
                                                    @Query("size") Integer size);

    @GET("matakuliah/tampilkan")
    Observable<MataKuliahResponse> getOneMataKuliah(@Header("Authorization") String token,
                                          @Query("id") String id);



    @POST("matakuliah")
    Observable<MataKuliahResponse> saveMataKuliah(@Header("Authorization") String token,
                                        @Body MataKuliah mataKuliah);


    @PUT("matakuliah/renewal")
    Completable updateMataKuliah(@Header("Authorization") String token,
                            @Query("id") String id,
                                 @Body MataKuliah mataKuliah);

    @DELETE("matakuliah/hapus")
    Completable deleteMataKuliah(@Header("Authorization") String token,
                            @Query("id") String id);

    //Materi CRUD
    @GET("materi/tampilkan")
    Observable<MateriResponse> getAllMateri(@Header("Authorization") String token,
                                            @Query("page") Integer page,
                                            @Query("size") Integer size);

    @GET("materi/tampilkanSemua")
    Observable<MateriResponse> getAllKelasMateri(@Header("Authorization") String token,
                                               @Query("id") String id,
                                               @Query("page") Integer page,
                                               @Query("size") Integer size);

    @GET("materi/tampilkan")
    Observable<MateriResponse> getOneMateri(@Header("Authorization") String token,
                                                    @Query("id") String id);



    @POST("materi")
    Observable<MateriResponse> saveMateri(@Header("Authorization") String token,
                                                  @Body Materi materi);


    @PUT("materi/renewal")
    Completable updateMateri(@Header("Authorization") String token,
                                 @Query("id") String id,
                                 @Body Materi materi);

    @DELETE("materi/hapus")
    Completable deleteMateri(@Header("Authorization") String token,
                                 @Query("id") String id);

    //Soal CRUD
    @GET("soal/tampilkan")
    Observable<SoalResponse> getAllSoal(@Header("Authorization") String token,
                                        @Query("page") Integer page,
                                        @Query("size") Integer size);

    @GET("soal/tampilkanSemua")
    Observable<SoalResponse> getAllKelasSoal(@Header("Authorization") String token,
                                                 @Query("id") String id,
                                                 @Query("page") Integer page,
                                                 @Query("size") Integer size);

    @GET("soal/tampilkan")
    Observable<SoalResponse> getOneSoal(@Header("Authorization") String token,
                                                    @Query("id") String id);



    @POST("soal")
    Observable<SoalResponse> saveSoal(@Header("Authorization") String token,
                                                  @Body Soal soal);


    @PUT("soal/renewal")
    Completable updateSoal(@Header("Authorization") String token,
                                 @Query("id") String id,
                                 @Body Soal soal);

    @DELETE("soal/hapus")
    Completable deleteSoal(@Header("Authorization") String token,
                                 @Query("id") String id);

    @Multipart
    @POST("soal/uploadSoal")
    Observable<UploadFileResponse> uploadSoal(@Header("Authorization") String token,
                                              @Part MultipartBody.Part file);

    @Multipart
    @POST("hasil/uploadHasil")
    Observable<UploadFileResponse> uploadHasil(@Header("Authorization") String token,
                           @Part MultipartBody.Part file);

    @Multipart
    @POST("materi/uploadMateri")
    Observable<UploadFileResponse> uploadMateri(@Header("Authorization") String token,
                           @Part MultipartBody.Part file);


}

