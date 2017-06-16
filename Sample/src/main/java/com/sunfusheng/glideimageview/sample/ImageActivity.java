package com.sunfusheng.glideimageview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestOptions;
import com.sunfusheng.glideimageview.GlideImageView;
import com.sunfusheng.glideimageview.progress.CircleProgressView;
import com.sunfusheng.glideimageview.progress.OnGlideImageViewListener;

import java.util.Random;

import static com.sunfusheng.glideimageview.sample.MainActivity.girl;
import static com.sunfusheng.glideimageview.sample.MainActivity.girl_thumbnail;
import static com.sunfusheng.glideimageview.sample.MainActivity.isWiFiAvailable;

/**
 * Created by sunfusheng on 2017/6/15.
 */
public class ImageActivity extends AppCompatActivity {

    GlideImageView glideImageView;
    CircleProgressView progressView;

    CircleProgressView progressView1;
    CircleProgressView progressView2;
    CircleProgressView progressView3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        glideImageView = (GlideImageView) findViewById(R.id.glideImageView);
        progressView1 = (CircleProgressView) findViewById(R.id.progressView1);
        progressView2 = (CircleProgressView) findViewById(R.id.progressView2);
        progressView3 = (CircleProgressView) findViewById(R.id.progressView3);

        initProgressView();
        loadImage();
    }

    private void initProgressView() {
        int randomNum = new Random().nextInt(3);
        switch (randomNum) {
            case 1:
                progressView = progressView2;
                break;
            case 2:
                progressView = progressView3;
                break;
            case 0:
            default:
                progressView = progressView1;
                break;
        }
        progressView1.setVisibility(View.GONE);
        progressView2.setVisibility(View.GONE);
        progressView3.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    private void loadImage() {
        glideImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(ImageActivity.this);
            }
        });

        String imageUrl = isWiFiAvailable(this) ? girl : girl_thumbnail;
        RequestOptions requestOptions = glideImageView.requestOptions(R.color.placeholder_color)
                .centerCrop();
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true);

        glideImageView.load(imageUrl, requestOptions).listener(new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {
                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
                progressView.setProgress(percent);
                progressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });
    }
}