package com.elearning.client.view.kelas.editor;


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

public class SpinnerBarangAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<MataKuliah> barangs;
    private final int mResource;

    public SpinnerBarangAdapter(@NonNull Context context, @LayoutRes int resource,
                                @NonNull List objects) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        barangs = objects;
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
        for (MataKuliah barang : this.barangs) {
            if(barang.getId().toString().equals(barang_id.toString())){
                return this.barangs.indexOf(barang);
            }
        }
        return 0;
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView nama = (TextView) view.findViewById(R.id.nama);
        TextView harga = (TextView) view.findViewById(R.id.harga);

        MataKuliah barang = barangs.get(position);

        nama.setText(barang.getNama());

        return view;
    }
}
