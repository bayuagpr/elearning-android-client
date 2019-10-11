package com.elearning.client.view.dosen.kelas;


import android.content.Intent;
import android.os.Bundle;

import com.elearning.client.view.dosen.kelas.detail.KelasDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.elearning.client.R;
import com.elearning.client.model.Kelas;
import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.dosen.kelas.editor.KelasActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class KelasFragment extends Fragment implements KelasView {


    SessionManager session;
    KelasPresenter presenter;
    KelasAdapter adapter;
    List<Kelas> kelasList;
    Integer page,lastPage, initPage,totalPage;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;

    public KelasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_kelas, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Kelas ");

        session = new SessionManager(getActivity());
        initPage=0;
        page = 0;
        kelasList = new ArrayList<>();
        presenter = new KelasPresenter(this);
        adapter = new KelasAdapter(kelasList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
        swipe.setOnRefreshListener(() -> presenter.getKelas(session.getKeyToken(),session.getKeyId(), initPage));
        presenter.getKelas(session.getKeyToken(), session.getKeyId(),initPage);
        return x;

    }

    @OnClick(R.id.fab) void editor() {
        Intent intent = new Intent(getActivity(), KelasActivity.class);
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
    public void statusSuccess(KelasResponse kelasResponse) {
        kelasList.removeAll(kelasList);
        kelasList.addAll(kelasResponse.getKelasList());
        adapter.notifyDataSetChanged();
        lastPage = kelasResponse.getTotalPages()-1;
       totalPage = kelasResponse.getTotalPages();
    }

    @Override
    public void loadMore(KelasResponse kelasResponse) {
        //kelasList.remove(kelasList.size()-1);
        List<Kelas> result = kelasResponse.getKelasList();
        page= kelasResponse.getNumber()+1;

        Log.d("load more", "result size"+result.size());
        if (result.size() > 0) {
            kelasList.addAll(result);
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
            presenter.getKelas(session.getKeyToken(), session.getKeyId(),0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getKelas(session.getKeyToken(), session.getKeyId(),0);
        }
    }
    void onSetRecyclerView() {

        adapter.setLoadMoreListener(() -> recyclerView.post(() -> {
            if(totalPage.equals(1)){
                if(page<lastPage){
                    int index = kelasList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getKelasMore(session.getKeyToken(), session.getKeyId(),page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(page<=lastPage){
                    int index = kelasList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getKelasMore(session.getKeyToken(), session.getKeyId(),page);}
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
                    Kelas kelas = adapter.getKelas(position);

                    Intent intent = new Intent(getActivity(), KelasDetailActivity.class);
                    Log.d("pilih kelas", "isi: "+kelas.getId()+" "+kelas.getMataKuliah().getId()+" "+kelas.getNama());
                    intent.putExtra("id", kelas.getId());
                    intent.putExtra("matkul_id", kelas.getMataKuliah().getId());
                    intent.putExtra("nama_kelas", kelas.getNama());
                    startActivityForResult(intent,REQUEST_UPDATE);
                }));
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
