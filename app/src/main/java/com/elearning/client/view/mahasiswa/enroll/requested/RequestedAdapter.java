package com.elearning.client.view.mahasiswa.enroll.requested;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elearning.client.R;
import com.elearning.client.model.Enrollment;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class RequestedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;
    List<Enrollment> enrollmentList;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;


    public RequestedAdapter(List<Enrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }
    static class ListHolder extends RecyclerView.ViewHolder {
        TextView nama, dosen;
        MaterialButton lihatBtn;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_mahasiswa);
            dosen = itemView.findViewById(R.id.nim_mahasiswa);
            //lihatBtn = itemView.findViewById(R.id.saveBtnSetujui);
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == TYPE_LIST) {
            return new ListHolder(inflater.inflate(R.layout.list_enroll_joined, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Enrollment materi = enrollmentList.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            Log.d("pos", "onBindViewHolder: "+i);
            Log.d("nama", "onBindViewHolder: "+materi.getMahasiswa().getNama());
            //Log.d("bongkar", "onBindViewHolder: "+materi.getDosen().getNidn());
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.nama.setText(materi.getMahasiswa().getNama());
            listHolder.dosen.setText(materi.getMahasiswa().getNim());


        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == enrollmentList.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    public Enrollment getHasil(int position) {
        return enrollmentList.get(position);
    }

    @Override
    public int getItemCount() {

        return null!=enrollmentList?enrollmentList.size():0;
    }


    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading =  false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
