package com.example.athletics.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.agrawalsuneet.dotsloader.loaders.RotatingCircularDotsLoader;
import com.example.Athletics.R;


@SuppressWarnings("ALL")
public class Functions {

    public static Dialog progressDialog;
    public static RotatingCircularDotsLoader rotatingCircularDotsLoader;

    public static void animNext(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public static void animBack(Context context) {
        //((Activity) context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        ((Activity) context).overridePendingTransition(0, R.anim.fade_out);

    }


    public static void hideSoftKeyboard(Activity activity, EditText editText) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    public static void dialogShow(Context context) {


        progressDialog = new Dialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        progressDialog.setContentView(R.layout.contain_progress);
        progressDialog.show();
        progressDialog.getWindow().setLayout((int) (screenWidth / 1.1), LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    public static void dialogShowNotCancellable(Context context) {
        progressDialog = new Dialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        progressDialog.setContentView(R.layout.contain_progress);
        progressDialog.show();
        progressDialog.getWindow().setLayout((int) (screenWidth / 1.1), LinearLayout.LayoutParams.WRAP_CONTENT);


    }

    public static void dialogHide() {
        progressDialog.dismiss();
    }


}
