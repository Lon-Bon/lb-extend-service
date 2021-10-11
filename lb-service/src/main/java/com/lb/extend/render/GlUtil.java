package com.lb.extend.render;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * created by zhanggaoming at 2017/8/14
 * OpenglES 相关操作工具类
 */
public class GlUtil {

    private final static String TAG = "GlUtil";
    /**
     * Identity matrix for general use.  Don't modify or life will get weird.
     */
    public static final float[] IDENTITY_MATRIX;

    static {
        IDENTITY_MATRIX = new float[16];
        Matrix.setIdentityM(IDENTITY_MATRIX, 0);
    }

    private static final int SIZEOF_FLOAT = 4;

    private GlUtil() {
    }     // do not instantiate

    // 创建shader程序的方法
    public static int createProgram(String vertexSource, String fragmentSource) {
        // 加载顶点着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }

        // 加载片元着色器
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        // 创建程序
        int program = GLES20.glCreateProgram();
        // 若程序创建成功则向程序中加入顶点着色器与片元着色器
        if (program != 0) {
            // 向程序中加入顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            // 向程序中加入片元着色器
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            // 链接程序
            GLES20.glLinkProgram(program);
            // 存放链接成功program数量的数组
            int[] linkStatus = new int[1];
            // 获取program的链接情况
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            // 若链接失败则报错并删除程序
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e("ES20_ERROR", "Could not link program: ");
                Log.e("ES20_ERROR", GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    // 加载制定shader的方法
    public static int loadShader(int shaderType, // shader的类型
                                 // GLES20.GL_VERTEX_SHADER(顶点)
                                 // GLES20.GL_FRAGMENT_SHADER(片元)
                                 String source // shader的脚本字符串
    ) {
        // 创建一个新shader
        int shader = GLES20.glCreateShader(shaderType);
        // 若创建成功则加载shader
        if (shader != 0) {
            // 加载shader的源代码
            GLES20.glShaderSource(shader, source);
            // 编译shader
            GLES20.glCompileShader(shader);
            // 存放编译成功shader数量的数组
            int[] compiled = new int[1];
            // 获取Shader的编译情况
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {// 若编译失败则显示错误日志并删除此shader
                Log.e("ES20_ERROR", "Could not compile shader " + shaderType
                        + ":");
                Log.e("ES20_ERROR", GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    // 从sh脚本中加载shader内容的方法
    public static String loadFromAssetsFile(String fileName, Resources r) {
        String result = null;
        try {
            InputStream in = r.getAssets().open(fileName);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, "UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    // 检查每一步操作是否有错误的方法
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("ES20_ERROR", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }


    //创建一个纹理ID
    public static int createTextureID() {
        int[] texture = new int[1];

        GLES20.glGenTextures(1, texture, 0);

        // Bind the texture handle to the 2D texture target.
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
        // Configure min/mag filtering, i.e. what scaling method do we use if what we're rendering
        // is smaller or larger than the source image.
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

        return texture[0];
    }


    /**
     * Allocates a direct float buffer, and populates it with the float array data.
     * 创建direct Buffer，用来操作顶底和颜色
     */
    public static FloatBuffer createFloatBuffer(float[] arrayData) {
        // Allocate a direct ByteBuffer, using 4 bytes per float, and copy arrayData into it.
        ByteBuffer bb = ByteBuffer.allocateDirect(arrayData.length * SIZEOF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arrayData);
        fb.position(0);
        return fb;
    }


    ///可生成旋转矩阵
    public static void mat4f_LoadOrtho(float left, float right, float bottom, float top, float near, float far, float[] mout) {
        float r_l = right - left;
        float t_b = top - bottom;
        float f_n = far - near;
        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        mout[0] = 2.0f / r_l;
        mout[1] = 0.0f;
        mout[2] = 0.0f;
        mout[3] = 0.0f;

        mout[4] = 0.0f;
        mout[5] = 2.0f / t_b;
        mout[6] = 0.0f;
        mout[7] = 0.0f;

        mout[8] = 0.0f;
        mout[9] = 0.0f;
        mout[10] = -2.0f / f_n;
        mout[11] = 0.0f;

        mout[12] = tx;
        mout[13] = ty;
        mout[14] = tz;
        mout[15] = 1.0f;
    }


    /**
     * Creates a texture from raw data.
     *
     * @param data   Image data, in a "direct" ByteBuffer.
     * @param width  Texture width, in pixels (not bytes).
     * @param height Texture height, in pixels.
     * @param format Image data format (use constant appropriate for glTexImage2D(), e.g. GL_RGBA).
     * @return Handle to texture.
     */
    public static int createImageTexture(ByteBuffer data, int width, int height, int format) {
        int[] textureHandles = new int[1];
        int textureHandle;

        GLES20.glGenTextures(1, textureHandles, 0);
        textureHandle = textureHandles[0];
        checkGlError("glGenTextures");

        // Bind the texture handle to the 2D texture target.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);

        // Configure min/mag filtering, i.e. what scaling method do we use if what we're rendering
        // is smaller or larger than the source image.
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        checkGlError("loadImageTexture");

        // Load the data from the buffer into the texture handle.
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, format, width, height, 0, format,
                GLES20.GL_UNSIGNED_BYTE, data);
        checkGlError("loadImageTexture");

        return textureHandle;
    }


    /**
     * Checks to see if the location we obtained is valid.  GLES returns -1 if a label
     * could not be found, but does not set the GL error.
     * <p>
     * Throws a RuntimeException if the location is invalid.
     */
    public static void checkLocation(int location, String label) {
        if (location < 0) {
            throw new RuntimeException("Unable to locate '" + label + "' in program");
        }
    }


    /**
     * Writes GL version info to the log.
     */
    public static void logVersionInfo() {
        Log.e(TAG, "vendor  : " + GLES20.glGetString(GLES20.GL_VENDOR));
        Log.e(TAG, "renderer: " + GLES20.glGetString(GLES20.GL_RENDERER));
        Log.e(TAG, "version : " + GLES20.glGetString(GLES20.GL_VERSION));

    }

    /**
     * yvu to bitmap
     */
    public static Bitmap nv21ToBitmap(byte[] data, int w, int h) {
        final YuvImage image = new YuvImage(data, ImageFormat.NV21, w, h, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
        if (image.compressToJpeg(new Rect(0, 0, w, h), 100, os)) {
            byte[] tmp = os.toByteArray();
            return BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
        }
        return null;
    }
}
