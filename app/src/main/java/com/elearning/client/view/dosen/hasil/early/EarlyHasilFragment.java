package com.elearning.client.view.dosen.hasil.early;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elearning.client.R;
import com.elearning.client.model.Hasil;
import com.elearning.client.network.response.HasilResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.dosen.hasil.editor.NilaiActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarlyHasilFragment extends Fragment implements EarlyHasilView {


    SessionManager session;
    EarlyHasilPresenter presenter;
    EarlyHasilAdapter adapter;
    List<Hasil> hasilList;
    Integer page,lastPage, initPage,totalPage;
    String idSoal;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recyclerjawaban)
    RecyclerView recyclerView;


    @BindView(R.id.swipe_refresh_jawaban)
    SwipeRefreshLayout swipe;

    public EarlyHasilFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_jawaban, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Kelas ");

        session = new SessionManager(getActivity());
        initDataIntent();
        initPage=0;
        page = 0;
        hasilList = new ArrayList<>();
        presenter = new EarlyHasilPresenter(this);
        adapter = new EarlyHasilAdapter(hasilList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
        swipe.setOnRefreshListener(() -> presenter.getHasil(session.getKeyToken(), idSoal, initPage));
        presenter.getHasil(session.getKeyToken(), idSoal,initPage);
        return x;

    }


    @Override
    public void showProgress() {
        swipe.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipe.setRefreshing(false);
    }

    @Override
    public void statusSuccess(HasilResponse materiResponse) {
        hasilList.removeAll(hasilList);
        hasilList.addAll(materiResponse.getHasilList());
        adapter.notifyDataSetChanged();
        lastPage = materiResponse.getTotalPages()-1;
        totalPage = materiResponse.getTotalPages();
    }

    @Override
    public void loadMore(HasilResponse materiResponse) {
        //kelasList.remove(kelasList.size()-1);
        List<Hasil> result = materiResponse.getHasilList();
        page= materiResponse.getNumber()+1;

        Log.d("load more", "result size"+result.size());
        if (result.size() > 0) {
            hasilList.addAll(result);
        } else {
            adapter.setMoreDataAvailable(false);
            Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataChanged();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            presenter.getHasil(session.getKeyToken(), idSoal,0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getHasil(session.getKeyToken(), idSoal,0);
        }
    }
    void onSetRecyclerView() {

        adapter.setLoadMoreListener(() -> recyclerView.post(() -> {
            if(totalPage.equals(1)){
                if(page<lastPage){
                    int index = hasilList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getHasilMore(session.getKeyToken(), idSoal,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(page<=lastPage){
                    int index = hasilList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getHasilMore(session.getKeyToken(), idSoal,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }


        }));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    void onClickRecylerView() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                (view, position) -> {
                    Hasil hasil = adapter.getHasil(position);
                    if(hasil.isTernilai()){
                        Intent intent = new Intent(getActivity(), NilaiActivity.class);
                        //Log.d("pilih materi", "isi: "+materi.getId()+" "+materi.getMataKuliah().getId()+" "+materi.getNama());
                        intent.putExtra("id", hasil.getId());
                        intent.putExtra("isi_jawaban", hasil.getIsiJawaban());
                        intent.putExtra("status_kumpul", hasil.getStatus());
                        intent.putExtra("nama_mahasiswa", hasil.getMahasiswa().getNama());
                        intent.putExtra("nim_mahasiswa", hasil.getMahasiswa().getNim());
                        intent.putExtra("tipesoal", hasil.getSoal().getTipe());
                        intent.putExtra("ternilai", hasil.isTernilai());
                        intent.putExtra("id_soal", idSoal);
                        intent.putExtra("attachment_materi", hasil.getAttachment());
                        intent.putExtra("nilai", hasil.getNilai());
                        intent.putExtra("komentar", hasil.getKomentar());
                        startActivityForResult(intent, REQUEST_UPDATE);
                    }else{
                        Intent intent = new Intent(getActivity(), NilaiActivity.class);
                        //Log.d("pilih materi", "isi: "+materi.getId()+" "+materi.getMataKuliah().getId()+" "+materi.getNama());
                        intent.putExtra("id", hasil.getId());
                        intent.putExtra("isi_jawaban", hasil.getIsiJawaban());
                        intent.putExtra("status_kumpul", hasil.getStatus());
                        intent.putExtra("nama_mahasiswa", hasil.getMahasiswa().getNama());
                        intent.putExtra("nim_mahasiswa", hasil.getMahasiswa().getNim());
                        intent.putExtra("tipesoal", hasil.getSoal().getTipe());
                        intent.putExtra("ternilai", hasil.isTernilai());
                        intent.putExtra("id_soal", idSoal);
                        intent.putExtra("attachment_materi", hasil.getAttachment());
                        startActivityForResult(intent, REQUEST_UPDATE);
                    }



                }));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
    private void initDataIntent() {
        Intent intent= getActivity().getIntent();
        idSoal = intent.getStringExtra("id");
    }

}
