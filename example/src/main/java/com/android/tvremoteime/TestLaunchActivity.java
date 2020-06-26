package com.android.tvremoteime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;

import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.Nullable;

public class TestLaunchActivity extends Activity {

    private StandardGSYVideoPlayer videoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_launch);

        PlayerFactory.setPlayManager(IjkPlayerManager.class);

        videoPlayer = findViewById(R.id.player);

        Uri data = getIntent().getData();

        String name = data.getQueryParameter("name");
        Log.i("TestLaunch", name);

        String path = data.getQueryParameter("uri");
        Log.i("TestLaunch", path);

        videoPlayer.setUp(new String(Base64.decode(path, Base64.DEFAULT)), true, name);
        videoPlayer.startPlayLogic();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("TestLaunch", "onNewIntent");

        Uri data =intent.getData();

        String name = data.getQueryParameter("name");
        Log.i("TestLaunch", name);

        String path = data.getQueryParameter("uri");
        Log.i("TestLaunch", path);

        videoPlayer.setUp(new String(Base64.decode(path, Base64.DEFAULT)), true, name);
        videoPlayer.startPlayLogic();
    }

}
