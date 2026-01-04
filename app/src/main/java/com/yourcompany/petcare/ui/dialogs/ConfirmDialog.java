package com.yourcompany.petcare.ui.dialogs;

import android.app.AlertDialog;
import android.content.Context;

public class ConfirmDialog {

    public interface Callback {
        void onYes();
    }

    public static void show(Context ctx, String title, String msg, Callback yes) {
        new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", (d, w) -> yes.onYes())
                .setNegativeButton("Cancel", null)
                .show();
    }
}
