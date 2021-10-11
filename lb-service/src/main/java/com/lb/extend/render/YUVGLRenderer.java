package com.lb.extend.render;

import android.annotation.TargetApi;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;

import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * created by zhanggaoming at 2017/8/14
 * YUV渲染器
 */

public class YUVGLRenderer implements GLSurfaceView.Renderer {


    private final String TAG = "YUVGLRenderer";
    private GLSurfaceView mTargetSurface;
    private YUVDrawer yuvDrawer = new YUVDrawer();
    private int mVideoWidth, mVideoHeight;
    private ByteBuffer y;
    private ByteBuffer uv;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public YUVGLRenderer(GLSurfaceView surface, int videoWidth,int videoHeight) {
        mTargetSurface = surface;
        // 设置OpenGl ES的版本为2.0
        mTargetSurface.setEGLContextClientVersion(2);
        // 设置与当前GLSurfaceView绑定的Renderer
        mTargetSurface.setRenderer(this);
        // 设置渲染的模式
        mTargetSurface.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        int yArraySize = mVideoWidth * mVideoHeight;
        int uvArraySize = yArraySize / 2;

        y = ByteBuffer.allocate(yArraySize);

        byte[] uvBytes = new byte[uvArraySize];
        Arrays.fill(uvBytes, (byte) 128);
        uv = ByteBuffer.wrap(uvBytes);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated: ");
        if (!yuvDrawer.isProgramBuilt()) {
            yuvDrawer.createBuffers();
            yuvDrawer.buildProgram();
            Log.d(TAG, "YUVGLRenderer :: buildProgram done");
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "YUVGLRenderer :: onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        synchronized (this) {
            if (y != null) {
                // reset position, have to be done
                yuvDrawer.buildTextures(y, uv, mVideoWidth, mVideoHeight);
                yuvDrawer.drawFrame();
            }
        }
    }


    /**
     * 更新视频宽高
     */
    public void updateWH(int w, int h) {
        if (w > 0 && h > 0) {
            // 初始化容器
            if (w != mVideoWidth && h != mVideoHeight) {
                this.mVideoWidth = w;
                this.mVideoHeight = h;
                int yArraySize = w * h;
                int uvArraySize = yArraySize / 2;

                y = ByteBuffer.allocate(yArraySize);
                uv = ByteBuffer.allocate(uvArraySize);
            }
        }

    }

    /**
     * 更新yuv数据
     */
    public void updateYUVData(byte[] yuvData, int pixLength) {
        synchronized (this) {
            y.clear();
            uv.clear();

            y.put(yuvData, 0, pixLength).position(0);
            uv.put(yuvData, pixLength, pixLength >> 1).position(0);
            // request to render
            mTargetSurface.requestRender();
        }
    }


}
