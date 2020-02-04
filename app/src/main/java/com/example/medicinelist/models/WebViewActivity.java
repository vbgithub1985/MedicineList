package com.example.medicinelist.models;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.medicinelist.R;

import java.io.FileDescriptor;
import java.io.IOException;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ImageView imageView;
    private String fileName;
    int touchState;
    final int IDLE = 0;
    final int TOUCH = 1;
    final int PINCH = 2;
    float dist0, distCurrent;
    Bitmap bitmap;
    int bmpWidth, bmpHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        //webView = (WebView)findViewById(R.id.webView);
        imageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        fileName = intent.getExtras().getString("fileName");
        imageView.setImageDrawable(Drawable.createFromPath(fileName));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        imageView.setLayoutParams(layoutParams);
        distCurrent = 1;
        dist0 = 1;
        bitmap = BitmapFactory.decodeResource(getResources(), R.id.imageView);
        bmpWidth = 0;
        bmpHeight = 0;
        imageView.setOnTouchListener(myTouchListener);
        touchState = IDLE;
        //webView.loadUrl("file:///" +fileName);
    }

    View.OnTouchListener myTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            /*float distx, disty;

            switch(event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    touchState = TOUCH;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:

                    touchState = PINCH;

                    distx = event.getX(0) - event.getX(1);
                    disty = event.getY(0) - event.getY(1);
                    dist0 = (float) Math.sqrt(distx * distx + disty * disty);

                    break;
                case MotionEvent.ACTION_MOVE:

                    if(touchState == PINCH){
                        distx = event.getX(0) - event.getX(1);
                        disty = event.getY(0) - event.getY(1);
                        distCurrent = (float) Math.sqrt(distx * distx + disty * disty);
                        drawMatrix();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    touchState = IDLE;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    touchState = TOUCH;
                    break;
            }
            return true;*/
            Log.d("MyLOG","onTouch - event.getAction()" + event.getAction());
            switch(event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    v.getLayoutParams().width = 100;
                    v.getLayoutParams().height = 100;
                    break;
                case MotionEvent.ACTION_DOWN:
                    v.getLayoutParams().width = 50;
                    break;

            }
            if (event.getAction() == MotionEvent.ACTION_MOVE){
                Log.d("MyLOG","onTouch - ACTION_MOVE");
                v.getLayoutParams().width = 100;
                return true;
            } else{
                return false;
            }
        }
    };

    private void drawMatrix(){
        float curScale = distCurrent/dist0;
        if (curScale < 0.1){
            curScale = 0.1f;
        }

        Bitmap resizedBitmap;
        int newHeight = (int) (bmpHeight * curScale);
        int newWidth = (int) (bmpWidth * curScale);
        resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);
        imageView.setImageBitmap(resizedBitmap);
    }

}
