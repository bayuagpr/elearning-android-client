package com.elearning.client.view.dosen.soal.editor;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class DateValidatorDialog {
    public void deleteDialog(Context context){
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    if(listener!=null)
                        listener.onDeleteDialogResponse(true);
                    break;


            }
        };

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setMessage("Tolong isi tenggat waktu")
                .setPositiveButton("Ok", dialogClickListener)
                .show();


    }

    /** my listner */
    public interface SetujuiDialogListener{
        public void onDeleteDialogResponse(boolean respononse);
    }
    private SetujuiDialogListener listener;

    public void setListener(SetujuiDialogListener listener) {
        this.listener = listener;
    }


}
