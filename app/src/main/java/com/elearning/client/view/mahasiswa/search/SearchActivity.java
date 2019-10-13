package com.elearning.client.view.mahasiswa.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.elearning.client.R;
import com.elearning.client.model.Kelas;
import com.elearning.client.network.response.ExistEnrollResponse;
import com.elearning.client.network.response.KelasResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.BaseActivity;
import com.elearning.client.view.mahasiswa.kelas.tergabung.TergabungKelasAdapter;
import com.elearning.client.view.mahasiswa.kelas.detail.KelasDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class SearchActivity extends BaseActivity implements SearchView {

    SearchPresenter presenter;
    SessionManager session;
    KelasAdapter adapter;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.searchTextId)
    EditText et_searchText;
    @BindView(R.id.clear)
    ImageButton clear;
    @BindView(R.id.progress)
    ProgressBar progress;
    List<Kelas> kelasList;
    Integer page,lastPage, initPage,totalPage;
    String dicari,id,matkul_id,namaDosen,nama_kelas;
    private static final int REQUEST_UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initPage=0;
        page = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        session = new SessionManager(this);
        presenter = new SearchPresenter(this);
        kelasList = new ArrayList<>();
        adapter = new KelasAdapter(kelasList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
    }


    @OnClick(R.id.clear) void clear(View view) {
        if (view.getId() == R.id.clear) {
            et_searchText.setText("");
        }
    }

    @OnClick(R.id.search) void search() {
        dicari = et_searchText.getText().toString();
        presenter.getSearch(
                session.getKeyToken(),
                dicari,
                0
        );
    }

    @OnTextChanged(value = R.id.searchTextId, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void searchTextChanged(Editable s) {
        String text = s.toString();
        if (text.length() == 0) {
            clear.setVisibility(View.INVISIBLE);
        } else {
            clear.setVisibility(View.VISIBLE);
        }
    }

    @OnEditorAction(R.id.searchTextId) boolean onSearch() {
        search();
        return true;
    }


    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.INVISIBLE);
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
            Toast.makeText(this, "No more data", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataChanged();
    }

    @Override
    public void isExist(ExistEnrollResponse supplierResponse) {

                    if(supplierResponse.getStatus()){
                        Intent intent = new Intent(SearchActivity.this, KelasDetailActivity.class);
                        //Log.d("pilih kelas", "isi: "+kelas.getId()+" "+kelas.getMataKuliah().getId()+" "+kelas.getNama());
                        intent.putExtra("id", id);
                        intent.putExtra("matkul_id", matkul_id);
                        intent.putExtra("nama_kelas", nama_kelas);
                        intent.putExtra("nama_dosen", namaDosen);
                        intent.putExtra("status_enroll", supplierResponse.getEnrollmentList().get(0).getDisetujui());
                        intent.putExtra("status_exist", supplierResponse.getStatus());
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(SearchActivity.this, KelasDetailActivity.class);
                        //Log.d("pilih kelas", "isi: "+kelas.getId()+" "+kelas.getMataKuliah().getId()+" "+kelas.getNama());
                        intent.putExtra("id", id);
                        intent.putExtra("matkul_id", matkul_id);
                        intent.putExtra("nama_kelas", nama_kelas);
                        intent.putExtra("nama_dosen", namaDosen);
                        intent.putExtra("status_exist", supplierResponse.getStatus());
                        startActivity(intent);
                    }



    }

    @Override
    public void statusError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
                    presenter.getSearchMore(session.getKeyToken(), dicari,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(this, "No more data", Toast.LENGTH_SHORT).show();
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
                    presenter.getSearchMore(session.getKeyToken(), dicari,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(this, "No more data", Toast.LENGTH_SHORT).show();
                }
            }


        }));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(SearchActivity.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    void onClickRecylerView() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(SearchActivity.this,
                (view, position) -> {
                    Kelas kelas = adapter.getKelas(position);
                    id = kelas.getId();
                    matkul_id = kelas.getMataKuliah().getId();
                    nama_kelas = kelas.getNama();
                    namaDosen = kelas.getDosen().getNama();
                    presenter.getExistEnrollment(session.getKeyToken(),session.getKeyId(),id);
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
