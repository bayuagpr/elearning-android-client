package com.elearning.client.view.mahasiswa.signup;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.elearning.client.R;
import com.elearning.client.model.Jurusan;
import com.elearning.client.model.MataKuliah;

import java.util.List;

public class SpinnerJurusanAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<Jurusan> mataKuliahList;
    private final int mResource;

    public SpinnerJurusanAdapter(@NonNull Context context, @LayoutRes int resource,
                                 @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        mataKuliahList = objects;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }


    public int getItemIndexById(String matkul_id) {
        for (Jurusan matkul : this.mataKuliahList) {
            if(matkul.getId().equals(matkul_id)){
                return this.mataKuliahList.indexOf(matkul);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);


        TextView nama = view.findViewById(R.id.nama_spinner);
        TextView matkul_id = view.findViewById(R.id.matkul_id_spinner);
        TextView fakultas = view.findViewById(R.id.fakultas_spinner);
        TextView fakultasId = view.findViewById(R.id.fakultas_spinner_id);

        Jurusan mataKuliah = mataKuliahList.get(position);

        matkul_id.setText(mataKuliah.getId());
        nama.setText(mataKuliah.getNama());
        fakultas.setText(mataKuliah.getFakultas().getNama());
        fakultasId.setText(mataKuliah.getFakultas().getId());

        return view;
    }
}
