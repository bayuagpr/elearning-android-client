package com.elearning.client.view.mahasiswa.materi;


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
import com.elearning.client.network.response.MateriResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.mahasiswa.materi.editor.MateriActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateriFragment extends Fragment implements MateriView {


    SessionManager session;
    MateriPresenter presenter;
    MateriAdapter adapter;
    List<Materi> materiList;
    Integer page,lastPage, initPage,totalPage;
    String idKelas;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recyclerMateri)
    RecyclerView recyclerView;

    @BindView(R.id.fabMateri)
    Button fab;

    @BindView(R.id.swipe_refresh_materi)
    SwipeRefreshLayout swipe;

    public MateriFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_materi, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Kelas ");

        session = new SessionManager(getActivity());
        initDataIntent();
        fab.setVisibility(View.GONE);
        initPage=0;
        page = 0;
        materiList = new ArrayList<>();
        presenter = new MateriPresenter(this);
        adapter = new MateriAdapter(materiList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
        swipe.setOnRefreshListener(() -> presenter.getMateri(session.getKeyToken(),idKelas, initPage));
        presenter.getMateri(session.getKeyToken(), idKelas,initPage);
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
    public void statusSuccess(MateriResponse materiResponse) {
        materiList.removeAll(materiList);
        materiList.addAll(materiResponse.getMateriList());
        adapter.notifyDataSetChanged();
        lastPage = materiResponse.getTotalPages()-1;
        totalPage = materiResponse.getTotalPages();
    }

    @Override
    public void loadMore(MateriResponse materiResponse) {
        //kelasList.remove(kelasList.size()-1);
        List<Materi> result = materiResponse.getMateriList();
        page= materiResponse.getNumber()+1;

        Log.d("load more", "result size"+result.size());
        if (result.size() > 0) {
            materiList.addAll(result);
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
            presenter.getMateri(session.getKeyToken(), idKelas,0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getMateri(session.getKeyToken(), idKelas,0);
        }
    }
    void onSetRecyclerView() {

        adapter.setLoadMoreListener(() -> recyclerView.post(() -> {
            if(totalPage.equals(1)){
                if(page<lastPage){
                    int index = materiList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getMateriMore(session.getKeyToken(), idKelas,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(page<=lastPage){
                    int index = materiList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getMateriMore(session.getKeyToken(), idKelas,page);}
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
                    Materi materi = adapter.getMateri(position);

                    Intent intent = new Intent(getActivity(), MateriActivity.class);
                    //Log.d("pilih materi", "isi: "+materi.getId()+" "+materi.getMataKuliah().getId()+" "+materi.getNama());
                    intent.putExtra("id", materi.getId());
                    intent.putExtra("judul_materi", materi.getJudul());
                    intent.putExtra("deskripsi_materi", materi.getDeskripsi());
                    intent.putExtra("id_kelas", idKelas);
                    intent.putExtra("attachment_materi", materi.getAttachment());
                    startActivity(intent);
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
