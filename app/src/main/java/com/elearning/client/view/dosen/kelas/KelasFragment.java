package com.elearning.client.view.dosen.kelas;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

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
        presenter = new KelasPresenter(this);
        presenter.getKelas(session.getKeyToken(), 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe.setOnRefreshListener(() -> presenter.getKelas(session.getKeyToken(), 0));

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
        Log.d("fragment sukses", kelasResponse.getKelasList().toString());
        adapter = new KelasAdapter(kelasResponse.getKelasList());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                (view, position) -> {
                    Kelas kelas = adapter.getKelas(position);

                    Intent intent = new Intent(getActivity(), KelasActivity.class);
                    Log.d("pilih kelas", "isi: "+kelas.getId()+" "+kelas.getMataKuliah().getId()+" "+kelas.getNama());
                    intent.putExtra("id", kelas.getId());
                    intent.putExtra("matkul_id", kelas.getMataKuliah().getId());
                    intent.putExtra("nama_kelas", kelas.getNama());

                    startActivityForResult(intent, REQUEST_UPDATE);
                }));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            presenter.getKelas(session.getKeyToken(), 0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getKelas(session.getKeyToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
