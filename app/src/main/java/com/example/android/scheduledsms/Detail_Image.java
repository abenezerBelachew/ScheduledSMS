/* package com.example.android.scheduledsms.;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

import com.example.android.scheduledsms.R;

public class Detail_Image extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__image);

        Intent in = getIntent();
        int index = in.getIntExtra("com.example.shixiong.index", -1);

        if(index >= 0){
            int pic = getImg(index);
            ImageView img = (ImageView) findViewById(R.id.imageView);
            scaleImg(img, pic);
        }
    }

    //shrink dowm image
    private int getImg(int index){
        switch (index){
            case 0: return R.drawable.peach;
            case 1: return R.drawable.tomato;
            case 2: return R.drawable.squash;
            default: return -1;
        }
    }

    private void scaleImg(ImageView img, int pic){
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //Turn On Boundaries. options in our bitmapfactory we need know the boundary that we talked
        //(can feel around edges and determine the width and height
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWith = screen.getWidth();


        if(imgWidth > screenWith){
            int ratio = Math.round((float) imgWidth / (float) screenWith);
            options.inSampleSize = ratio;
        }
        //turn off
        options.inJustDecodeBounds = false;
        Bitmap scaledImg = BitmapFactory.decodeResource(getResources(),pic,options);
        img.setImageBitmap(scaledImg);
    }
}
*/
