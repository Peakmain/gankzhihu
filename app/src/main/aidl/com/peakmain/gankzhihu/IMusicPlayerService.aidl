// IMusicPlayerService.aidl
package com.peakmain.gankzhihu;

// Declare any non-default types here with import statements

interface IMusicPlayerService {
             /**
              * 根据位置打开对应的音频文件
              *
              * @param position
              */
             void openAuido(int position);

             /**
              * 播放音乐
              */
             void playMusic();

             /**
              * 暂停音乐
              */
             void pause();

             /**
              * 停止音乐
              */
             void stopMusic();

             /**
              * 得到当前播放的位置
              */
             int getCurrentPosition();

             /**
              * 得到当前音频的总时长
              */
             int getDuration();

             /**
              * 得到艺术家
              */
             String getArtist();

             /**
              * 得到歌曲名字
              */
             String getName();
             /**
              * 得到歌曲播放的路径
              */
             String getAudioPath();
             /**
              * 播放下一个音频
              */
             void next();
             /**
              * 播放上一个音频
              */
             void pre();
             /**
              * 设置播放模式
              */
             void setPlayMode(int playMode);
             /**
              * 得到播放模式
              */
             int getPlayMode();
             /**
              * 是否正在播放
              */
             boolean isPlaying();

             /**
              * 拖动音频
              */
             /**
              拖动音频
              */
             void seekTo(int position);

             int getAudioSessionId();
}
