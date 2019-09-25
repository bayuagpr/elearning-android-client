package com.elearning.client.view.dosen.kelas.editor;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.elearning.client.R;
import com.elearning.client.model.MataKuliah;

import java.util.List;

public class SpinnerMatkulAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<MataKuliah> mataKuliahList;
    private final int mResource;

    public SpinnerMatkulAdapter(@NonNull Context context, @LayoutRes int resource,
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


    public int getItemIndexById(String barang_id) {
        for (MataKuliah matkul : this.mataKuliahList) {
            if(matkul.getId().toString().equals(barang_id.toString())){
                return this.mataKuliahList.indexOf(matkul);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);


        TextView nama = (TextView) view.findViewById(R.id.nama);

        MataKuliah mataKuliah = mataKuliahList.get(position);


        nama.setText(mataKuliah.getNama());

        return view;
    }
}
