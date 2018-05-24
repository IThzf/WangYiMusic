package com.task.android.wangyiyun.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.bean.MusicMedia;

import java.util.ArrayList;

/**
 * Created by 袁赛 on 2018/5/21.
 */

public class PlayMusicActivity extends AppCompatActivity {
    private ImageButton backmain_play_btn,share_play_btn,last_paly_btn,play_play_btn,pause_play_btn,next_play_btn;
    private TextView musicname_play_text,musicauthor_play_text,time_play_text,alltime_play_text;
    private SeekBar mseekbar_play;
    private MediaPlayer mediaPlayer;
    private ArrayList<MusicMedia> myList;

    private int position;
    private int allNum;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);
        InitPlay();
        InitView();

        backmain_play_btn.setOnClickListener(imgbtnListener);
        share_play_btn.setOnClickListener(imgbtnListener);
        last_paly_btn.setOnClickListener(imgbtnListener);
        play_play_btn.setOnClickListener(imgbtnListener);
        pause_play_btn.setOnClickListener(imgbtnListener);
        next_play_btn.setOnClickListener(imgbtnListener);

        mseekbar_play.setOnSeekBarChangeListener(seekBarChangeListener);

    }


    public void InitView(){
        backmain_play_btn = (ImageButton)findViewById(R.id.backmain_play_ib);
        share_play_btn = (ImageButton)findViewById(R.id.share_play_ib);
        last_paly_btn = (ImageButton)findViewById(R.id.last_play_ib);
        play_play_btn = (ImageButton)findViewById(R.id.play_play_ib);
        pause_play_btn = (ImageButton)findViewById(R.id.pause_play_ib);
        next_play_btn = (ImageButton)findViewById(R.id.next_play_ib);

        musicname_play_text = (TextView) findViewById(R.id.musicname_play_tv);
        musicauthor_play_text = (TextView) findViewById(R.id.musicauthor_play_tv);
        time_play_text = (TextView) findViewById(R.id.time_play_tv);
        alltime_play_text = (TextView) findViewById(R.id.alltime_play_tv);

        mseekbar_play = (SeekBar)findViewById(R.id.mseekbar_play_sb);

        mediaPlayer = new MediaPlayer();

        position = 1;
        allNum = 0;

        handler = new Handler();

        musicname_play_text.setText(myList.get(position).getTitle());
        musicauthor_play_text.setText(myList.get(position).getArtist());
        alltime_play_text.setText(toTime(myList.get(position).getTime()));
        mseekbar_play.setMax((int) myList.get(position).getTime());

    }

    //音乐播放初始化
    public void InitPlay(){
        //判断是否获取了读取本地音乐的权限，若未获取，通过onRequestPermissionsResult方法动态获取
        if (ContextCompat.checkSelfPermission(PlayMusicActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(PlayMusicActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            myList = scanAllAudioFiles();
        }

    }

    //点击事件
    Button.OnClickListener imgbtnListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.backmain_play_ib:
                    backmainMethod();
                    return;
                case R.id.share_play_ib:
                    shareMethod();
                    return;
                case R.id.last_play_ib:
                    lastMethod();
                    return;
                case R.id.play_play_ib:
                    playMethod(position);
                    return;
                case R.id.pause_play_ib:
                    pauseMethod();
                    return;
                case R.id.next_play_ib:
                    nextMethod();
                    return;
            }
        }
    };

    //下一首
    private void nextMethod() {
        if (position == myList.size())
        {
            position = 1;
        }
        else
        {
            position = position + 1;
        }
        mediaPlayer.stop();
        playMethod(position);
    }

    //上一首
    private void lastMethod() {
        if (position == 1)
        {
            position = myList.size();
        }
        else
        {
            position = position - 1;
        }
        mediaPlayer.stop();
        playMethod(position);
    }

    //分享
    private void shareMethod() {
    }

    //返回上一页
    private void backmainMethod() {
    }

    //播放暂停
    private void pauseMethod(){
        play_play_btn.setVisibility(View.VISIBLE);
        pause_play_btn.setVisibility(View.GONE);

        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    //播放开始
    private void playMethod(int position)  {
        pause_play_btn.setVisibility(View.VISIBLE);
        play_play_btn.setVisibility(View.GONE);

        mediaPlayer.reset();

        if (!mediaPlayer.isPlaying()){
            try {

                mediaPlayer.setDataSource(myList.get(position).getUrl());
                Log.i("TAG", "scanAudioFiles: " + position);
                mediaPlayer.prepare();
                mediaPlayer.start();
                handler.post(updateThread);

            } catch (Exception e) {
                e.printStackTrace();
            }
            musicname_play_text.setText(myList.get(position).getTitle());
            musicauthor_play_text.setText(myList.get(position).getArtist());
            alltime_play_text.setText(toTime(myList.get(position).getTime()));
            mseekbar_play.setMax((int) myList.get(position).getTime());
        }
    }



    //设置歌曲时间格式
    public String toTime(long time){
        time /= 1000;
        long minute = time / 60;
        long hour = minute / 60;
        long second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d", minute, second);
    }

    Runnable updateThread = new Runnable() {
        public void run() {
            // 获得歌曲现在播放位置并设置成播放进度条的值
            if (mediaPlayer != null) {
                mseekbar_play.setProgress(mediaPlayer.getCurrentPosition());
                time_play_text.setText(toTime(mediaPlayer.getCurrentPosition()));
                // 每次延迟100毫秒再启动线程
                handler.postDelayed(updateThread, 1000);
            }
        }
    };

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
    {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            time_play_text.setText(toTime(seekBar.getProgress()));
            if (seekBar.getProgress() == myList.get(position).getTime())
            {
                nextMethod();
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            mediaPlayer.seekTo(progress);
        }
    };

    //动态获取权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    myList = scanAllAudioFiles();
                } else {
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    //获取本地音乐
    public ArrayList<MusicMedia> scanAllAudioFiles(){
        Log.i("TAG", "scanAllAudioFiles: " );
        ArrayList<MusicMedia> mylist = new ArrayList<MusicMedia>();

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        int i = 0;
        Log.i("TAG", "scanAllAudioFiles: " + cursor);
        if(cursor.moveToFirst()){
            Log.i("TAG", "scanAllAudioFiles: " + i);
            while(!cursor.isAfterLast()){
                i++;
                Log.i("TAG", "scanAllAudioFiles: " + i);
                //歌曲编号
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String title =  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                //歌曲歌手名
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长
                long duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                if(size > 1024*800){//大于800K
                    MusicMedia musicMedia = new MusicMedia();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(title);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbum(album);
                    musicMedia.setAlbumId(albumId);
                    mylist.add(musicMedia);
                    allNum = allNum + 1;
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return mylist;
    }


}
