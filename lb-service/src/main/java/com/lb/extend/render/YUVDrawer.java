package com.lb.extend.render;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.Buffer;
import java.nio.FloatBuffer;

/**
 * created by zhanggaoming at 2017/8/14
 * step to use:<br/>
 * 1. new YUVDrawer()<br/>
 * 2. buildProgram()<br/>
 * 3. buildTextures()<br/>
 * 4. drawFrame()<br/>
 * YUV程序
 */
public class YUVDrawer {

    private final String TAG = "YUVDrawer";
    // program id
    private int program;
    // texture id
    private int textureI;
    private int textureII;
    // texture index in gles
    private int textureIindex;
    private int textureIIindex;
    // vertices on screen
    private float[] vertices;
    // handles
    private int positionHandle = -1, coordinateHandle = -1;
    private int yHandle = -1, uvHandle = -1;
    private int yTextureId = -1, uvTextureId = -1;
    // vertices buffer
    private FloatBuffer positionBuffer;
    private FloatBuffer coordinateBuffer;
    // video width and height
    private int videoWidth = -1;
    private int videoHeight = -1;
    // flow control
    private boolean isProgramBuilt = false;


    public YUVDrawer() {
        vertices = squareVertices;//全屏
        textureI = GLES20.GL_TEXTURE0;
        textureII = GLES20.GL_TEXTURE1;

        textureIindex = 0;
        textureIIindex = 1;

        createBuffers();
    }


    public boolean isProgramBuilt() {
        return isProgramBuilt;
    }

    public void buildProgram() {
        if (program <= 0) {
            program = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        }

        /*
         * get handle for "vPosition" and "a_texCoord"
         */
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        checkGlError("glGetAttribLocation vPosition");
        if (positionHandle == -1) {
            throw new RuntimeException("Could not get attribute location for vPosition");
        }
        coordinateHandle = GLES20.glGetAttribLocation(program, "a_texCoord");
        checkGlError("glGetAttribLocation a_texCoord");
        if (coordinateHandle == -1) {
            throw new RuntimeException("Could not get attribute location for a_texCoord");
        }
        /*
         * get uniform location for y/u/v, we pass data through these uniforms
         */
        yHandle = GLES20.glGetUniformLocation(program, "tex_y");
        checkGlError("glGetUniformLocation tex_y");
        if (yHandle == -1) {
            throw new RuntimeException("Could not get uniform location for tex_y");
        }
        uvHandle = GLES20.glGetUniformLocation(program, "tex_uv");
        checkGlError("glGetUniformLocation tex_u");
        if (uvHandle == -1) {
            throw new RuntimeException("Could not get uniform location for tex_u");
        }

        isProgramBuilt = true;
    }

    /**
     * build a set of textures, for RGB
     */
    public void buildTextures(Buffer y, Buffer uv, int width, int height) {
        boolean videoSizeChanged = (width != videoWidth || height != videoHeight);
        if (videoSizeChanged) {
            videoWidth = width;
            videoHeight = height;
            Log.d(TAG, "buildTextures videoSizeChanged: w=" + videoWidth + " h=" + videoHeight);
        }

        // building texture for Y data
        if (yTextureId < 0 || videoSizeChanged) {
            if (yTextureId >= 0) {
                GLES20.glDeleteTextures(1, new int[]{yTextureId}, 0);
                checkGlError("glDeleteTextures");
            }
            // GLES20.glPixelStorei(GLES20.GL_UNPACK_ALIGNMENT, 1);
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            checkGlError("glGenTextures");
            yTextureId = textures[0];
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, yTextureId);
        checkGlError("glBindTexture");
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, videoWidth, videoHeight, 0,
                GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, y);
        checkGlError("glTexImage2D");
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        // building texture for UV data
        if (uvTextureId < 0 || videoSizeChanged) {
            if (uvTextureId >= 0) {
                GLES20.glDeleteTextures(1, new int[]{uvTextureId}, 0);
                checkGlError("glDeleteTextures");
            }
            int[] textures = new int[1];
            GLES20.glGenTextures(1, textures, 0);
            checkGlError("glGenTextures");
            uvTextureId = textures[0];
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, uvTextureId);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE_ALPHA, videoWidth / 2, videoHeight / 2, 0,
                GLES20.GL_LUMINANCE_ALPHA, GLES20.GL_UNSIGNED_BYTE, uv);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

    }

    /**
     * render the frame
     * the YUV data will be converted to RGB by shader.
     */
    public void drawFrame() {
        GLES20.glUseProgram(program);
        checkGlError("glUseProgram");

        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 8, positionBuffer);
        checkGlError("glVertexAttribPointer mPositionHandle");
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(coordinateHandle, 2, GLES20.GL_FLOAT, false, 8, coordinateBuffer);
        checkGlError("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(coordinateHandle);

        // bind textures
        GLES20.glActiveTexture(textureI);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, yTextureId);
        GLES20.glUniform1i(yHandle, textureIindex);

        GLES20.glActiveTexture(textureII);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, uvTextureId);
        GLES20.glUniform1i(uvHandle, textureIIindex);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glFinish();

        GLES20.glDisableVertexAttribArray(positionHandle);
        GLES20.glDisableVertexAttribArray(coordinateHandle);
    }


    /**
     * these two buffers are used for holding positionVertices, screen positionVertices and texture positionVertices.
     */
    protected void createBuffers() {

        positionBuffer = GlUtil.createFloatBuffer(squareVertices);

        coordinateBuffer = GlUtil.createFloatBuffer(coordinateVertices);

    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.d(TAG, "***** " + op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    static float[] squareVertices = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f,}; // fullscreen

    static float[] squareVertices1 = {-1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f,}; // left-top

    static float[] squareVertices2 = {0.0f, -1.0f, 1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f,}; // right-bottom

    static float[] squareVertices3 = {-1.0f, -1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,}; // left-bottom

    static float[] squareVertices4 = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,}; // right-top

    static float[] coordinateVertices = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,};// whole-texture

    private static final String VERTEX_SHADER =
            "attribute vec4 vPosition;\n"
                    + "attribute vec2 a_texCoord;\n"
                    + "varying vec2 tc;\n"
                    + "void main() {\n"
                    + "gl_Position = vPosition;\n"
                    + "tc = a_texCoord;\n"
                    + "}\n";

    private static final String FRAGMENT_SHADER =
            "precision mediump float;\n"
                    + "uniform sampler2D tex_y;\n"
                    + "uniform sampler2D tex_uv;\n"
                    + "varying vec2 tc;\n"
                    + "void main() {\n"
                    + "vec4 c = vec4((texture2D(tex_y, tc).r - 16./255.) * 1.164);\n"
                    + "vec4 U = vec4(texture2D(tex_uv, tc).a - 128./255.);\n"
                    + "vec4 V = vec4(texture2D(tex_uv, tc).r - 128./255.);\n"
                    + "c += V * vec4(1.596, -0.813, 0, 0);\n"
                    + "c += U * vec4(0, -0.392, 2.017, 0);\n"
                    + "c.a = 1.0;\n"
                    + "gl_FragColor = c;\n" + "}\n";

}