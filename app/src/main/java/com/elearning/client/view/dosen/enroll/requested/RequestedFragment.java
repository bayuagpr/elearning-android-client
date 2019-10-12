package com.elearning.client.view.dosen.enroll.requested;


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
import com.elearning.client.model.Enrollment;
import com.elearning.client.model.Hasil;
import com.elearning.client.network.response.EnrollmentResponse;
import com.elearning.client.network.response.HasilResponse;
import com.elearning.client.utils.RecyclerItemClickListener;
import com.elearning.client.utils.SessionManager;
import com.elearning.client.utils.SimpleDividerItemDecoration;
import com.elearning.client.view.dosen.hasil.editor.NilaiActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestedFragment extends Fragment implements RequestedView {


    SessionManager session;
    RequestedPresenter presenter;
    RequestedAdapter adapter;
    List<Enrollment> enrollmentList;
    Integer page,lastPage, initPage,totalPage;
    String idKelas;

    private static final int REQUEST_ADD = 1;
    private static final int REQUEST_UPDATE = 2;

    @BindView(R.id.recyclerjawaban)
    RecyclerView recyclerView;


    @BindView(R.id.swipe_refresh_jawaban)
    SwipeRefreshLayout swipe;

    public RequestedFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_joined, container, false);
        ButterKnife.bind(this, x );
        getActivity().setTitle("Kelas ");

        session = new SessionManager(getActivity());
        initDataIntent();
        initPage=0;
        page = 0;
        enrollmentList = new ArrayList<>();
        presenter = new RequestedPresenter(this);
        adapter = new RequestedAdapter(enrollmentList);
        adapter.setMoreDataAvailable(true);
        onSetRecyclerView();
        onClickRecylerView();
        swipe.setOnRefreshListener(() -> presenter.getEnroll(session.getKeyToken(), idKelas, initPage));
        presenter.getEnroll(session.getKeyToken(), idKelas,initPage);
        return x;

    }

    private void showLongToast(final String msg) {
        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
    public void statusSuccess(EnrollmentResponse materiResponse) {
        enrollmentList.removeAll(enrollmentList);
        enrollmentList.addAll(materiResponse.getEnrollmentList());
        adapter.notifyDataSetChanged();
        lastPage = materiResponse.getTotalPages()-1;
        totalPage = materiResponse.getTotalPages();
    }

    @Override
    public void loadMore(EnrollmentResponse materiResponse) {
        //kelasList.remove(kelasList.size()-1);
        List<Enrollment> result = materiResponse.getEnrollmentList();
        page= materiResponse.getNumber()+1;

        Log.d("load more", "result size"+result.size());
        if (result.size() > 0) {
            enrollmentList.addAll(result);
        } else {
            adapter.setMoreDataAvailable(false);
            Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataChanged();
    }

    @Override
    public void refresh() {
        presenter.getEnroll(session.getKeyToken(), idKelas,0);
    }

    @Override
    public void statusError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            presenter.getEnroll(session.getKeyToken(), idKelas,0);
        } else if (requestCode == REQUEST_UPDATE && resultCode == RESULT_OK) {
            presenter.getEnroll(session.getKeyToken(), idKelas,0);
        }
    }
    void onSetRecyclerView() {

        adapter.setLoadMoreListener(() -> recyclerView.post(() -> {
            if(totalPage.equals(1)){
                if(page<lastPage){
                    int index = enrollmentList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getEnrollMore(session.getKeyToken(), idKelas,page);}
                else {
                    adapter.setMoreDataAvailable(false);
                    Toast.makeText(getActivity(), "No more data", Toast.LENGTH_SHORT).show();
                }
            }else{
                if(page<=lastPage){
                    int index = enrollmentList.size();
                    //kelasList.add(new Kelas("load"));
                    //adapter.notifyItemInserted(kelasList.size()-1);
                    if(page.equals(0)){
                        page =index/10;
                    }
                    Log.d("load more", "page: "+page);
                    presenter.getEnrollMore(session.getKeyToken(), idKelas,page);}
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
    private void showDialog(String id,String nama){
        SetujuiDialog dialog=new SetujuiDialog();
        dialog.setListener(respononse -> {
            if(respononse){
                Enrollment enroll = new Enrollment();
                enroll.setDisetujui(true);
                presenter.updateEnroll(session.getKeyToken(),id,enroll);
                //DO SOMETHING IF YES
            }
        });

        dialog.deleteDialog(getActivity(),nama);
    }

        void onClickRecylerView() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                (view, position) -> {
                    Enrollment materi = adapter.getHasil(position);
                    showDialog(materi.getId(),materi.getMahasiswa().getNama());



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
