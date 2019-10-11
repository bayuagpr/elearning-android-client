package com.elearning.client.view.dosen.soal;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.elearning.client.R;
import com.elearning.client.model.Materi;
import com.elearning.client.model.Soal;
import com.elearning.client.network.response.MateriResponse;
import com.elearning.client.network.response.SoalResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.dosen.materi.editor.MateriActivity;
import com.elearning.client.view.dosen.soal.detail.SoalDetailActivity;
import com.elearning.client.view.dosen.soal.editor.SoalActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SoalFragment extends Fragment implements SoalView {


    SessionManager session;
    SoalPresenter presenter;
    SoalAdapter adapter;
    List<Soal> soalList;
    Integer page,lastPage, initPage,totalPage;
    String idKelas;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recyclersoal)
    RecyclerView recyclerView;

    @BindView(R.id.fabsoal)
    Button fab;

    @BindView(R.id.swipe_refresh_soal)
    SwipeRefreshLayout swipe;

    public SoalFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_soal, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Kelas ");

        session = new SessionManager(getActivity());
        initDataIntent();
        initPage=0;
        page = 0;
        soalList = new ArrayList<>();
        presenter = new SoalPresenter(this);
        adapter = new SoalAdapter(soalList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
        swipe.setOnRefreshListener(() -> presenter.getSoal(session.getKeyToken(),idKelas, initPage));
        presenter.getSoal(session.getKeyToken(), idKelas,initPage);
        return x;

    }

    @OnClick(R.id.fabsoal) void editor() {
        Intent intent = new Intent(getActivity(), SoalActivity.class);
        intent.putExtra("id_kelas", idKelas);
        startActivityForResult(intent, REQUEST_ADD);
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
    public void statusSuccess(SoalResponse soalResponse) {
        soalList.removeAll(soalList);
        soalList.addAll(soalResponse.getSoalList());
        adapter.notifyDataSetChanged();
        lastPage = soalResponse.getTotalPages()-1;
        totalPage = soalResponse.getTotalPages();
    }

    @Override
    public void loadMore(SoalResponse soalResponse) {
        //kelasList.remove(kelasList.size()-1);
        List<Soal> result = soalResponse.getSoalList();
        page= soalResponse.getNumber()+1;

        Log.d("load more", "result size"+result.size());
        if (result.size() > 0) {
            soalList.addAll(result);
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
            presenter.getSoal(session.getKeyToken(), idKelas,0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getSoal(session.getKeyToken(), idKelas,0);
        }
    }
    void onSetRecyclerView() {

        adapter.setLoadMoreListener(() -> recyclerView.post(() -> {
            if(totalPage.equals(1)){
                if(page<lastPage){
                    int index = soalList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getSoalMore(session.getKeyToken(), idKelas,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(page<=lastPage){
                    int index = soalList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getSoalMore(session.getKeyToken(), idKelas,page);}
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
                    Soal soal = adapter.getSoal(position);
                    java.util.Date time=new java.util.Date((long)soal.getDueDate()*1000);
                    Intent intent = new Intent(getActivity(), SoalDetailActivity.class);
                    //Log.d("pilih materi", "isi: "+materi.getId()+" "+materi.getMataKuliah().getId()+" "+materi.getNama());
                    intent.putExtra("id", soal.getId());
                    intent.putExtra("judul_soal", soal.getJudul());
                    intent.putExtra("deskripsi_soal", soal.getDeskripsi());
                    intent.putExtra("id_kelas", idKelas);
                    intent.putExtra("attachment_soal", soal.getAttachment());
                    intent.putExtra("tipe_soal", soal.getTipe());
                    intent.putExtra("due_date", time);
                    startActivityForResult(intent, REQUEST_UPDATE);
                }));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
    private void initDataIntent() {
        Intent intent= getActivity().getIntent();
        idKelas = intent.getStringExtra("id");
    }

}
