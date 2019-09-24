package com.elearning.client.view.kelas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import  com.elearning.client.R;
import com.elearning.client.model.Kelas;

import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    List<Kelas> penjualans;

    public KelasAdapter(List<Kelas> penjualans) {
        this.penjualans = penjualans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kelas,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Kelas penjualan = penjualans.get(i);
        viewHolder.nama.setText(penjualan.getNama());
        viewHolder.jumlah_barang.setText(penjualan.getDosen().getNama());
    }

    @Override
    public int getItemCount() {
        return penjualans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama, jumlah_barang;
        Button tanggal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            jumlah_barang = itemView.findViewById(R.id.jumlah_harga);
            tanggal = itemView.findViewById(R.id.tanggal);
    }





}public Kelas getPenjualan(int position) {
        return penjualans.get(position);
    }}
