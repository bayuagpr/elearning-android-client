package com.elearning.client.view.dosen.kelas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elearning.client.R;
import com.elearning.client.model.Kelas;

import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;
    List<Kelas> kelasList;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;


    public KelasAdapter(List<Kelas> kelasList) {
        this.kelasList = kelasList;
    }
    static class ListHolder extends RecyclerView.ViewHolder {
        TextView nama, dosen;
        Button lihatBtn;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_kelas);
            dosen = itemView.findViewById(R.id.nama_dosen);
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
            return new ListHolder(inflater.inflate(R.layout.list_kelas, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Kelas kelas = kelasList.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            Log.d("pos", "onBindViewHolder: "+i);
            Log.d("nama", "onBindViewHolder: "+kelas.getNama());
            Log.d("bongkar", "onBindViewHolder: "+kelas.getDosen().getNidn());
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.nama.setText(kelas.getNama());
            listHolder.dosen.setText(kelas.getDosen().getNama());


        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == kelasList.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    public Kelas getKelas(int position) {
        return kelasList.get(position);
    }

    @Override
    public int getItemCount() {

        return null!=kelasList?kelasList.size():0;
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
