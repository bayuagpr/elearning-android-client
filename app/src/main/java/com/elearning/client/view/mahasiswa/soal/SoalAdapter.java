package com.elearning.client.view.mahasiswa.soal;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elearning.client.R;
import com.elearning.client.model.Soal;

import java.text.SimpleDateFormat;
import java.util.List;

public class SoalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_LIST = 0 ;
    public final int TYPE_LOAD = 1 ;
    List<Soal> soalList;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false;
    boolean isMoreDataAvailable = true;


    public SoalAdapter(List<Soal> soalList) {
        this.soalList = soalList;
    }
    static class ListHolder extends RecyclerView.ViewHolder {
        TextView nama, tipe;
        Button lihatBtn;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.judulTugas);
            tipe = itemView.findViewById(R.id.tipeTugas);
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
            return new ListHolder(inflater.inflate(R.layout.list_tugas, viewGroup, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.list_loading, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Soal soal = soalList.get(i);

        if (i >= getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener !=
                null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(i) == TYPE_LIST){
            Log.d("pos", "onBindViewHolder: "+i);
            Log.d("nama", "onBindViewHolder: "+soal.getJudul());
            //Log.d("bongkar", "onBindViewHolder: "+materi.getDosen().getNidn());
            //java.util.Date time=new java.util.Date((long)soal.getDueDate()*1000);
            SimpleDateFormat mFormatter = new SimpleDateFormat(" dd MMMM yyyy hh:mm aa");
            ListHolder listHolder = (ListHolder) viewHolder;
            listHolder.nama.setText(soal.getJudul());
            listHolder.tipe.setText("Tipe: "+soal.getTipe()+ "\n" +"Dikumpulkan paling lambat pada: "+ "\n" +mFormatter.format(soal.getDueDate()));


        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == soalList.size()) ? TYPE_LOAD : TYPE_LIST;
    }

    public Soal getSoal(int position) {
        return soalList.get(position);
    }

    @Override
    public int getItemCount() {

        return null!= soalList ? soalList.size():0;
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
