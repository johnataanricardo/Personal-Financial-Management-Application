package info.seufinanceiro.utils;

import android.view.LayoutInflater;
import android.widget.Toast;

public class ToastMessageUtil {

    public static void toastMessage(final LayoutInflater inflater, String message) {
        Toast.makeText(inflater.getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
