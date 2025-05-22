package com.saffat.examconductor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;
import android.widget.Toast;

public class NetworkUtil {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }

        return false;
    }

    public static void showNoInternetDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_internet_dialog);
        dialog.setCancelable(false);

        Button btnTryAgain = dialog.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(v -> {
            if (isNetworkAvailable(context)) {
                dialog.dismiss();
            } else {
                Toast.makeText(context, "Still no internet", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}
