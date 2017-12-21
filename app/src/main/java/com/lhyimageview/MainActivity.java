package com.lhyimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.lhy.view.LhyProgressBar;
import com.lhy.view.LhyRoundImageView;
import com.lhy.view.LhySeekProgress;

public class MainActivity extends AppCompatActivity {
    LhyRoundImageView lhyRoundImageView;
    LhyProgressBar progress_bar;
    LhySeekProgress seek_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lhyRoundImageView = findViewById(R.id.lhyRoundImageView);
        progress_bar = findViewById(R.id.progress_bar);
        seek_progress = findViewById(R.id.seek_progress);
        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512732699329&di=ecbe520425294ceb0bd87377f82e20cc&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Dcfb53f93c3177f3e0439f44e18a651b2%2F6609c93d70cf3bc7814060c9db00baa1cd112a56.jpg").into(lhyRoundImageView);
        progress_bar.setProgress(40);
        seek_progress.setProgress(60);
    }
}
