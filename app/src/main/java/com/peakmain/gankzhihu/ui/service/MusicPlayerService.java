package com.peakmain.gankzhihu.ui.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.peakmain.gankzhihu.IMusicPlayerService;
import com.peakmain.gankzhihu.R;
import com.peakmain.gankzhihu.bean.video.MediaItem;
import com.peakmain.gankzhihu.ui.activity.MainActivity;
import com.peakmain.gankzhihu.ui.activity.MusicPlayerActivity;
import com.peakmain.gankzhihu.utils.CacheUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ：Peakmain
 * version ：1.0
 * createTime ：2018/11/29 0029 下午 1:26
 * mail : 2726449200@qq.com
 * describe ：
 */
public class MusicPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private static final String channelName = "PRIMARY_CHANNEL";
    private static final String channelID = "1";
    private ArrayList<MediaItem> mediaItems;
    private int position;

    /**
     * 播放音乐
     */
    private MediaPlayer mediaPlayer;
    /**
     * 当前播放的音频文件对象
     */
    private MediaItem mediaItem;
    /**
     * 顺序播放
     */
    public static final int REPEAT_NORMAL = 1;
    /**
     * 单曲循环
     */
    public static final int REPEAT_SINGLE = 2;
    /**
     * 全部循环
     */
    public static final int REPEAT_ALL = 3;

    /**
     * 播放模式
     */
    private int playMode = REPEAT_NORMAL;
    private Notification notification;

    @Override
    public void onCreate() {
        super.onCreate();
        playMode = CacheUtils.getPlaymode(this, "playMode");
        //加载音乐列表
        getDataFromLocal();
    }

    private void getDataFromLocal() {

        new Thread() {
            @Override
            public void run() {
                super.run();

                mediaItems = new ArrayList<>();
                ContentResolver resolver = getContentResolver();
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Audio.Media.DISPLAY_NAME,//视频文件在sdcard的名称
                        MediaStore.Audio.Media.DURATION,//视频总时长
                        MediaStore.Audio.Media.SIZE,//视频的文件大小
                        MediaStore.Audio.Media.DATA,//视频的绝对地址
                        MediaStore.Audio.Media.ARTIST,//歌曲的演唱者

                };
                Cursor cursor = resolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {

                        MediaItem mediaItem = new MediaItem();

                        mediaItems.add(mediaItem);//写在上面

                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//视频的文件大小
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);//视频的播放地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);
                    }
                    cursor.close();
                }

            }
        }.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private IMusicPlayerService.Stub stub = new IMusicPlayerService.Stub() {
        MusicPlayerService service = MusicPlayerService.this;


        @Override
        public void openAuido(int position) throws RemoteException {
            service.openAudio(position);
        }

        @Override
        public void playMusic() throws RemoteException {
            service.playMusic();
        }

        @Override
        public void pause() throws RemoteException {
            service.pause();
        }

        @Override
        public void stopMusic() throws RemoteException {
            service.stopMusic();
        }


        @Override
        public int getCurrentPosition() throws RemoteException {
            return service.getCurrentPosition();
        }

        @Override
        public int getDuration() throws RemoteException {
            return service.getDuration();
        }

        @Override
        public String getArtist() throws RemoteException {
            return service.getArtist();
        }

        @Override
        public String getName() throws RemoteException {
            return service.getName();
        }

        @Override
        public String getAudioPath() throws RemoteException {
            return service.getAudioPath();
        }

        @Override
        public void next() throws RemoteException {
            service.next();
        }

        @Override
        public void pre() throws RemoteException {
            service.pre();
        }

        @Override
        public void setPlayMode(int playmode) throws RemoteException {
            service.setPlayMode(playmode);
        }

        @Override
        public int getPlayMode() throws RemoteException {
            return service.getPlayMode();
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return service.isPlaying();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            mediaPlayer.seekTo(position);
        }

        @Override
        public int getAudioSessionId() throws RemoteException {
            return mediaPlayer.getAudioSessionId();
        }
    };

    /**
     * 播放上一个音频
     */
    private void pre() {
        //1.根据当前的播放模式，设置上一个的位置
        setPrePosition();
        //2.根据当前的播放模式和下标位置去播放音频
        openPreAudio();
    }

    private void openPreAudio() {
        int playMode = getPlayMode();
        if (playMode == MusicPlayerService.REPEAT_NORMAL) {
            if (position >= 0) {
                //正常范围
                openAudio(position);
            } else {
                position = 0;
            }
        } else if (playMode == MusicPlayerService.REPEAT_SINGLE) {
            openAudio(position);
        } else if (playMode == MusicPlayerService.REPEAT_ALL) {
            openAudio(position);
        } else {
            if (position >= 0) {
                //正常范围
                openAudio(position);
            } else {
                position = 0;
            }
        }
    }

    /**
     * 设置播放模式
     */
    private void setPlayMode(int playMode) {
        this.playMode = playMode;
        CacheUtils.putPlaymode(this, "playMode", playMode);
        if (playMode == MusicPlayerService.REPEAT_SINGLE) {
            //单曲循环播放-不会触发播放完成的回调
            mediaPlayer.setLooping(true);
        } else {
            //不循环播放
            mediaPlayer.setLooping(false);
        }
    }

    private void setPrePosition() {
        int playMode = getPlayMode();
        if (playMode == MusicPlayerService.REPEAT_NORMAL) {
            position--;
        } else if (playMode == MusicPlayerService.REPEAT_SINGLE) {
            position--;
            if (position < 0) {
                position = mediaItems.size() - 1;
            }
        } else if (playMode == MusicPlayerService.REPEAT_ALL) {
            position--;
            if (position < 0) {
                position = mediaItems.size() - 1;
            }
        } else {
            position--;
        }
    }

    /**
     * 播暂停音乐
     */
    private void pause() {
        mediaPlayer.pause();
        manager.cancel("MusicPlayerService".hashCode());
    }

    /**
     * 停止
     */
    private void stopMusic() {

    }

    /**
     * 得到当前的播放进度
     */
    private int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    /**
     * 得到当前音频的总时长
     *
     * @return
     */
    private int getDuration() {
        return mediaPlayer.getDuration();
    }

    /**
     * 得到艺术家
     *
     * @return
     */
    private String getArtist() {
        return mediaItem.getArtist();
    }

    /**
     * 得到歌曲播放的路径
     *
     * @return
     */
    private String getAudioPath() {
        return mediaItem.getData();
    }

    private NotificationManager manager;


    /**
     * 播放音乐
     */
    private void playMusic() {
        mediaPlayer.start();
        //当播放歌曲的时候，在状态显示正在播放，点击的时候，可以进入音乐播放页面


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //判断是否是8.0Android.O
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            Toast.makeText(this, channel.toString(), Toast.LENGTH_SHORT).show();
            channel.setLightColor(Color.GREEN);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            channel.setDescription("正在播放:" + getName());
            channel.setName("快报音乐");
            // 设置通知出现时的震动（如果 android 设备支持的话）
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            manager.createNotificationChannel(channel);

        } else {
            Intent intent = new Intent(this, MusicPlayerActivity.class);
            intent.putExtra("notification", true);//标识来自状态拦
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            notification = new Notification.Builder(this)
                    .setContentTitle("快报音乐")
                    .setContentText("正在播放:" + getName())
                    .setSmallIcon(R.drawable.notification_music_playing)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .build();
        }
        manager.notify("MusicPlayerService".hashCode(), notification);
    }

    /**
     * 得到歌曲名字
     *
     * @return
     */
    private String getName() {
        return mediaItem.getName();
    }

    /**
     * 根据位置打开对应的音频文件,并且播放
     *
     * @param position
     */
    private void openAudio(int position) {
        this.position = position;
        if (mediaItems != null && mediaItems.size() > 0) {
            mediaItem = mediaItems.get(position);
            if (mediaPlayer != null) {
                mediaPlayer.reset();
            }

            try {
                mediaPlayer = new MediaPlayer();
                //设置监听：播放出错，播放完成，准备好
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setOnErrorListener(this);
                mediaPlayer.setOnCompletionListener(this);
                mediaPlayer.setDataSource(mediaItem.getData());
                mediaPlayer.prepareAsync();
                if (playMode == MusicPlayerService.REPEAT_SINGLE) {
                    //单曲循环播放-不会触发播放完成的回调
                    mediaPlayer.setLooping(true);
                } else {
                    mediaPlayer.setLooping(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShort("亲，还没有数据");
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        EventBus.getDefault().post(mediaItem);
        playMusic();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        next();//播放下一首
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();//播放下一首
    }

    /**
     * 播放下一个音频
     */
    private void next() {
        //1.根据当前的播放模式，设置下一个的位置
        setNextPosition();
        //2.根据当前的播放模式和下标位置去播放音频
        openNextAudio();
    }


    private void setNextPosition() {
        int playMode = getPlayMode();
        if (playMode == MusicPlayerService.REPEAT_NORMAL) {
            position++;
        } else if (playMode == MusicPlayerService.REPEAT_SINGLE) {
            position++;
            if (position >= mediaItems.size()) {
                position = 0;
            }
        } else if (playMode == MusicPlayerService.REPEAT_ALL) {
            position++;
            if (position >= mediaItems.size()) {
                position = 0;
            }
        } else {
            position++;
        }
    }

    private void openNextAudio() {
        int playMode = getPlayMode();
        if (playMode == MusicPlayerService.REPEAT_NORMAL) {
            if (position < mediaItem.getSize()) {
                //正常范围
                openAudio(position);
            } else {
                position = mediaItems.size() - 1;
            }
        } else if (playMode == MusicPlayerService.REPEAT_SINGLE) {
            openAudio(position);
        } else if (playMode == MusicPlayerService.REPEAT_ALL) {
            openAudio(position);
        } else {
            if (position < mediaItems.size()) {
                //正常范围
                openAudio(position);
            } else {
                position = mediaItems.size() - 1;
            }
        }
    }

    /**
     * 得到播放模式
     *
     * @return
     */
    private int getPlayMode() {
        return playMode;
    }

    /**
     * 是否在播放音频
     *
     * @return
     */
    private boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}
