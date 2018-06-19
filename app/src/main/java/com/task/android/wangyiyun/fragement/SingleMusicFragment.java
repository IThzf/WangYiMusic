package com.task.android.wangyiyun.fragement;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.activity.PlayMusicActivity;
import com.task.android.wangyiyun.adapter.MusicItemsAdapter;
import com.task.android.wangyiyun.bean.MusicMedia;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/23.
 */

public class SingleMusicFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<MusicMedia> musicList;
    private MusicItemsAdapter mAdapter;
    private int allNum;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.single_music,container,false);
        mRecyclerView = view.findViewById(R.id.id_recyclerview);
        mContext = getActivity();
        initPlay();

        mAdapter = new MusicItemsAdapter(getActivity(),musicList );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListener(new MusicItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent_PlayMusic=new Intent(getActivity(), PlayMusicActivity.class);
                if (PlayMusicActivity.mediaPlayer != null){
                    Log.i("TAG", "onItemClick: " + PlayMusicActivity.mediaPlayer.getCurrentPosition());
                    PlayMusicActivity.curTime = PlayMusicActivity.mediaPlayer.getCurrentPosition();
                }

                intent_PlayMusic.putExtra("position",position);
                startActivity(intent_PlayMusic);
            }
        });
        return view ;


    }

    private void initPlay() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            Log.i("SingleMusic", "initPlay: request");
            ActivityCompat.requestPermissions((Activity) mContext,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else {
            Log.i("SingleMusic", "initPlay: scan");
            musicList = scanAllAudioFiles();
        }
    }

    //动态获取权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    musicList = scanAllAudioFiles();
                } else {
                    Toast.makeText(mContext,"拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    //获取本地音乐
    public ArrayList<MusicMedia> scanAllAudioFiles(){
        Log.i("SingleMusic", "scanAllAudioFiles: ");
        Log.i("TAG", "scanAllAudioFiles: " );
        ArrayList<MusicMedia> musicList = new ArrayList<MusicMedia>();

        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        int i = 0;
        //Log.i("TAG", "scanAllAudioFiles: " + cursor);
        if(cursor.moveToFirst()){
          //  Log.i("TAG", "scanAllAudioFiles: " + i);
            while(!cursor.isAfterLast()){
                i++;
             //   Log.i("TAG", "scanAllAudioFiles: " + i);
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

                if(size > 1024*4000 && size < 1024*10000){//大于800K
                    MusicMedia musicMedia = new MusicMedia();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(title);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbum(album);
                    musicMedia.setAlbumId(albumId);
                    musicList.add(musicMedia);
                    allNum = allNum + 1;
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return musicList;
    }
}
