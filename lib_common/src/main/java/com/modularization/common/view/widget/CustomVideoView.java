package com.modularization.common.view.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.modularization.common.R;


/**
 * @author 左程耀
 * 自定义视频播放器控件
 */
public class CustomVideoView extends RelativeLayout
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener,
                   MediaPlayer.OnBufferingUpdateListener, TextureView.SurfaceTextureListener, View.OnClickListener
{
    private static final String TAG = "CustomVideoView";

    // region 常量
    private static final int TIME_MSG = 0x01;
    private static final int TIME_INTERVAL = 1000;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_PAUSING = 2;
    private static final int LOAD_TOTAL_COUNT = 3;
    private static final float VIDEO_HEIGHT_PERCENT = 9 / 16.0f;
    // endregion

    // region view
    private ViewGroup mParentContainer;
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;
    private Button mMiniPlayBtn;
    private ImageView mFullBtn;
    private ImageView mLoadingBar;
    private ImageView mFrameView;
    private Surface mVideoSurface;
    // endregion

    // region 播放器参数
    private String mUrl; // 播放源url
    private String mFrameURI; //
    private boolean isMute;
    private int mScreenWidth /* 屏幕宽 */, mDestationHeight /* 高度（16:9） */;
    // endregion

    // region Status状态保护
    private boolean isCanPlay = true;
    private boolean isRealPause;
    private boolean isComplete;
    private int mCurrentCount;
    private int mPlayerState = STATE_IDLE;
    // endregion

    // region 工具类
    private AudioManager mAudioManager; // 音量控制器
    private MediaPlayer mMediaPlayer;
    private ADVideoPlayerListener mListener; // 事件回调监听
    private ScreenEventReceiver mScreenReceiver; // 监听屏幕锁屏广播
    // endregion

    private Handler mHandler = new Handler(Looper.getMainLooper())
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case TIME_MSG:
                {
                    if (isPlaying())
                    {
                        mListener.onBufferUpdate(getCurrentPosition());
                        sendEmptyMessageDelayed(TIME_MSG, TIME_INTERVAL);
                    }
                    break;
                }
            }
        }
    };

    public CustomVideoView(Context context, ViewGroup parentContainer)
    {
        super(context);
        this.mParentContainer = parentContainer;
        this.mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        doInitData();
        doInitView();
        registerBroadcastReceiver();
    }

    private void doInitData()
    {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm != null)
        {
            wm.getDefaultDisplay().getMetrics(dm);
            this.mScreenWidth = dm.widthPixels;
            this.mDestationHeight = (int) (mScreenWidth * VIDEO_HEIGHT_PERCENT);
        }
    }

    private void doInitView()
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mPlayerView = (RelativeLayout) inflater.inflate(R.layout.video_player, this);
        mVideoView = mPlayerView.findViewById(R.id.video_player_texture);
        mVideoView.setOnClickListener(this);
        mVideoView.setKeepScreenOn(true);
        mVideoView.setSurfaceTextureListener(this);
        // 小模式状态
        LayoutParams params = new LayoutParams(mScreenWidth, mDestationHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mPlayerView.setLayoutParams(params);
        mMiniPlayBtn = mPlayerView.findViewById(R.id.video_player_small_play_btn);
        mMiniPlayBtn.setOnClickListener(this);
        mFullBtn = mPlayerView.findViewById(R.id.video_player_full);
        mFullBtn.setOnClickListener(this);
        mLoadingBar = mPlayerView.findViewById(R.id.video_player_loading_bar);
        mFrameView = mPlayerView.findViewById(R.id.video_player_framing);
    }

    private void registerBroadcastReceiver()
    {

    }

    public void setADVideoPlayerListener(ADVideoPlayerListener listener)
    {
        this.mListener = listener;
    }

    private synchronized void checkMediaPlayer()
    {
        if (mMediaPlayer == null) mMediaPlayer = createMediaPlayer();
    }

    private MediaPlayer createMediaPlayer()
    {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.reset();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnInfoListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (mVideoSurface != null && mVideoSurface.isValid())
            mMediaPlayer.setSurface(mVideoSurface);
        else
            stop();
        return mMediaPlayer;
    }

    /**
     * 加载视频url
     */
    public void load()
    {

    }

    /**
     * 暂停播放器
     */
    public void pause()
    {

    }

    /**
     * 恢复视频播放
     */
    public void resume()
    {

    }

    /**
     * 播放完成后回到初始状态
     */
    public void playBack()
    {}

    /**
     * 停止播放器播放
     */
    public void stop()
    {}

    /**
     * 销毁当前view
     */
    public void destroy()
    {}

    /**
     * 跳转到指定点播放
     */
    public void seekAndResume(int position)
    {

    }

    /**
     * 跳转到指定点暂停
     */
    public void seekAndPause(int position)
    {

    }

    private boolean isPlaying()
    {
        return false;
    }

    private void setCurrentPlayState(int state)
    {
        this.mPlayerState = state;
    }

    public int getDuration()
    {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    public int getCurrentPosition()
    {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    // region 回调方法
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility)
    {
        Log.i(TAG, "onVisibilityChanged visibility=" + visibility);
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // 防止与父容器事件冲突
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent)
    {

    }

    /**
     * 播放器播放完成后回调
     */
    @Override
    public void onCompletion(MediaPlayer mp)
    {

    }

    /**
     * 播放器遇到异常时回调
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        return false;
    }

    /**
     * 播放器处于就绪状态
     */
    @Override
    public void onPrepared(MediaPlayer mp)
    {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra)
    {
        return false;
    }

    /**
     * 标明TextureView处于就绪状态
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
    {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
    {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
    {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface)
    {

    }

    @Override
    public void onClick(View v)
    {

    }

    // endregion

    public class ScreenEventReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {

        }
    }

    public interface ADVideoPlayerListener
    {
        void onBufferUpdate(int time);

        void onClickFullScreenBtn();

        void onClickVideo();

        void onClickBackBtn();

        void onClickPlay();

        void onAdVideoLoadSuccess();

        void onAdVideoLoadFailed();

        void onAdVideoLoadComplete();
    }
}
