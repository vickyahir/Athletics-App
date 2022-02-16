package com.example.athletics.Activity;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.util.Rational;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;

import com.example.Athletics.R;


public class FullScreenMediaController extends MediaController {

    private ImageButton fullScreen, PictureInPicture;
    private String isFullScreen;

    public FullScreenMediaController(Context context) {
        super(context);
    }


    @Override
    public void setAnchorView(View view) {

        super.setAnchorView(view);

        //image button for full screen to be added to media controller
        fullScreen = new ImageButton(super.getContext());
        PictureInPicture = new ImageButton(super.getContext());

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 100;
        addView(fullScreen, params);

        PictureInPicture.setImageResource(R.drawable.ic_picture_in_picture);
        LayoutParams param = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        param.gravity = Gravity.LEFT;
        param.leftMargin = 100;
        addView(PictureInPicture, param);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            PictureInPicture.setVisibility(View.VISIBLE);
        } else {
            PictureInPicture.setVisibility(View.GONE);
        }

        //fullscreen indicator from intent
        isFullScreen = ((Activity) getContext()).getIntent().
                getStringExtra("fullScreenInd");

        if ("y".equals(isFullScreen)) {
            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
        } else {
            fullScreen.setImageResource(R.drawable.ic_fullscreen);
        }

        //add listener to image button to handle full screen and exit full screen events
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Activity) getContext()).finish();

//                Intent intent = new Intent(getContext(), HomeActivity.class);
//
//                if ("y".equals(isFullScreen)) {
//                    intent.putExtra("fullScreenInd", "");
//                } else {
//                    intent.putExtra("fullScreenInd", "y");
//                }
//                ((Activity) getContext()).startActivity(intent);
            }
        });


        PictureInPicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    PictureInPictureParams pips = null;
                    pips = new PictureInPictureParams.Builder()
                            .setAspectRatio(new Rational(1, 1))
                            .build();
                    ((Activity) getContext()).enterPictureInPictureMode(pips);
                }
            }
        });
    }


}
