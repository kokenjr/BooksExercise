package com.possible.booksexercise.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;

import com.possible.booksexercise.R;

/**
 * Created by korji on 7/29/17.
 */

public class LoadingDialog extends ProgressDialog {

    ProgressBar pbLoading;
    Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading_dialog);

        pbLoading = findViewById(R.id.pbLoading);
    }

    @Override
    public void show() {
        super.show();
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        pbLoading.setVisibility(View.INVISIBLE);
    }

}
