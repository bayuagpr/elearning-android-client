package com.elearning.client.view.mahasiswa.kelas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.elearning.client.R;
import com.elearning.client.model.Kelas;

import java.util.List;

public class KelasAdapter extends RecyclerView.Adapter<KelasAdapter.ViewHolder> {

    List<Kelas> kelasList;

    public KelasAdapter(List<Kelas> kelasList) {
        this.kelasList = kelasList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama, dosen;
        Button lihatBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama_kelas);
            dosen = itemView.findViewById(R.id.nama_dosen);
            lihatBtn = itemView.findViewById(R.id.button_lihat);
        }
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
        Kelas kelas = kelasList.get(i);
        viewHolder.nama.setText(kelas.getNama());
        viewHolder.dosen.setText(kelas.getDosen().getNama());
    }

    public Kelas getKelas(int position) {
        return kelasList.get(position);
    }

    @Override
    public int getItemCount() {
        return kelasList.size();
    }
}
