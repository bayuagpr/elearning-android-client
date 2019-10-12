package com.elearning.client.view.mahasiswa.enroll.requested;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class SetujuiDialog {
    public void deleteDialog(Context context,String nama){
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    if(listener!=null)
                        listener.onDeleteDialogResponse(true);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    if(listener!=null)
                        listener.onDeleteDialogResponse(false);
                    break;
            }
        };

        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setMessage("Setujui "+nama+" untuk bergabung?")
                .setPositiveButton("Setujui", dialogClickListener)
                .setNegativeButton("Batalkan", dialogClickListener)
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
