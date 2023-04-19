# lb-extend-service
这是一个为合作伙伴提供服务的仓库

## 引入库

1.**添加jitpack仓库到工程当中

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}
```

 **2.**添加依赖，始终用最新版本

```groovy
dependencies {
	implementation 'com.github.Lon-Bon:lb-extend-service:+'
}
```

## 如何使用

- ##### 来邦这边统一定义通信接口，接口用BindImpl注解修饰，注解的值为服务端实现这个接口的全类名：

```kotlin
@BindImpl("com.demo.ipc.InfoServiceManager")
interface InfoService {

    fun asyncGetUserInfo(callBack: Result<UserInfo>)

    fun syncGetUserInfo(): UserInfo

    fun sum(a: Int, b: Int, c: Int, result: Result<Int>)

    fun sendBigData(@BigData data: ByteArray)

    fun getEnum(code: Code): Code

    fun setEventCallBack(callBack: Result<Event>)

}

enum class Code {
    SUCCESS, FAILURE
}


data class Event(val id: Int)


data class UserInfo(val name: String, val age: Int)
```

上述接口定义支持回调的方式，定义回调的接口返回，必须使用**Result**类来承载

- ##### 来邦app：

来邦app实现上述接口，kotlin代码需要是object类：

```kotlin
object InfoServiceManager : InfoService {

    //获取userInfo，走的是回调的方式
    override fun asyncGetUserInfo(callBack: Result<UserInfo>) {
        thread {
            callBack.onData(UserInfo("asyncGetUserInfo", 20))
        }
    }


    override fun syncGetUserInfo(): UserInfo {
        return UserInfo("syncGetUserInfo", 18)
    }


    override fun sum(a: Int, b: Int, c: Int, result: Result<Int>) {
        result.onData(a + b + c)
    }

    override fun sendBigData(data: ByteArray) {
        Log.i(TAG, "sendBigData: ${data.contentToString()}")
    }

    override fun getEnum(code: Code): Code {
        Log.i(TAG, "getEnum: $code")
        return Code.SUCCESS
    }

    private var count=0

    private var mCallBack: Result<Event>? = null

    init {

        thread {//模拟回调事件回复客户端
            while (true) {
                mCallBack?.onData(Event(count++))

                Thread.sleep(2000)
            }
        }
    }

    override fun setEventCallBack(callBack: Result<Event>) {
        mCallBack = callBack
    }
}
```

java代码必须要写**getInstance**方法，返回自身，使用单例模式：

```java
public class InfoServiceManagerJava implements InfoService {
    private static final String TAG = "InfoServiceManagerJava";

    @Override
    public void sum(int a, int b, int c, @NotNull Result<Integer> result) {
        result.onData(a + b + c);
    }

    @Override
    public void sendBigData(@NotNull byte[] data) {
        Log.i(TAG, "sendBigData: " + Arrays.toString(data));
    }

    @NotNull
    @Override
    public Code getEnum(Code code) {
        return Code.SUCCESS;
    }

    @Override
    public void setEventCallBack(@NotNull Result<Event> callBack) {

    }

    private static final class Holder {
        private static final InfoServiceManagerJava instance = new InfoServiceManagerJava();
    }

    private InfoServiceManagerJava() {

    }

    public static InfoServiceManagerJava getInstance() {
        return Holder.instance;
    }

    @Override
    public void asyncGetUserInfo(@NotNull Result<UserInfo> callBack) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                callBack.onData(new UserInfo("asyncGetUserInfo", 24));
            }
        });

    }

    @NotNull
    @Override
    public UserInfo syncGetUserInfo() {
        return new UserInfo("syncGetUserInfo", 18);
    }
}


```

来邦app需要把服务注册到IpcManager当中，这里支持手动和自动注册：

- 手动注册，通过IpcManager注册，手动注册要提前，建议是在Application当中注册：

```kotlin
IpcManager.register(InfoService::class) //注册服务
```

- 自动注册，通过注解处理器来处理，在module中引入ipc-compiler模块：

```groovy
plugins {
    ...
    id 'kotlin-kapt'
}

或者

applay plugin:'kotlin-kapt'

dependencies {
	...
    kapt 'com.github.zhanggaoming.android-ipc:ipc-compiler:2.2'
}

```

引入ipc-compiler模块后，会自动找BindImpl注解修饰的接口并注册到IpcManager当中

- ##### 来邦合作方app(合作方主要看如何调用，这一段是重点)

来邦合作方app主要就是使用IpcManager这个类来寻找服务：

**1.**初始化并连接服务端进程,这步要提前，建议放到Application当中去：

```kotlin
 IpcManager.init(this)//传入上下文
 IpcManager.open("com.demo.ipcdemo")//连接服务端，这里是示例，实际应用要传入来邦app的包名，来邦的包名统一为com.lonbon.lonbon_app
```

**2.**发现服务：

*kotlin*

```kotlin
IpcManager.getService(InfoService::class)
IpcManager.getService<InfoService>()
```

*java*

```java
IpcManager.INSTANCE.getService(InfoService.class);
```

以下是客户端demo的示例代码：

```kotlin
class CommonActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "CommonActivity"
    }

    val instance by lazy { IpcManager.getService<InfoService>() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        IpcManager.config(Config.builder().configDebug(true).build())
        IpcManager.init(this)
        IpcManager.open("com.demo.ipcdemo")
    }

    fun syncGetUserInfo(view: View) {

        Toast.makeText(this, instance.syncGetUserInfo().toString(), Toast.LENGTH_LONG).show()

        Log.i(TAG, "syncGetUserInfo: ->${instance.getEnum(Code.FAILURE)}")

    }


    fun asyncGetUserInfo(view: View) {

        instance.asyncGetUserInfo(object : Result<UserInfo>() {

            override fun onData(data: UserInfo) {
                runOnUiThread {

                    Toast.makeText(this@CommonActivity, data.toString(), Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    fun sum(view: View) {

        instance.sum(1, 2, 3, object : Result<Int>() {
            override fun onData(data: Int) {
                runOnUiThread {
                    Toast.makeText(this@CommonActivity, "the sum is $data", Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    fun sendBigData(view: View) {

        instance.sendBigData(byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    }



    fun setEventCallBack(view: View) {

        instance.setEventCallBack(object : Result<Event>() {
            override fun onData(data: Event) {
                Log.i(TAG, "onData: ${data.id}")
            }
        })

    }


}
```


**3.**服务端异常重连

- 服务端崩溃异常，客户端会自动重连，同时会反馈给客户端，通过IpcManager设置一个服务死亡回调：

  ```kotlin
  IpcManager.serverDeath={
              Log.i(TAG, "server dead!!")
  }
  ```

  

### 以下是取摄像头数据流的方案，有取摄像头数据流需求的话往下看

根据lb提供的接口，获取相应的服务，这里还要额外说明一下，目前我们有对外提供取帧数据和取流数据服务，可以参考如下方式获取：

```kotlin
 		//首先配置开启媒体服务
        IpcManager.config(Config.builder().configOpenMedia(true).build())
        IpcManager.init(this)
        IpcManager.open("com.demo.ipcdemo")
```

媒体服务支持以下能力：

```kotlin
interface IMediaManager {
	//取视频帧
    fun takeFrame(frameType: FrameType)
	//停止取视频帧
    fun stopTakeFrame()
	//获取一张照片
    fun takePicture(pictureFormat: PictureFormat)
	//设置视频帧回调
    fun setPreviewCallBack(callBack: IPreviewCallBack)
	//设置照片回调
    fun setPictureCallBack(callBack: IPictureCallBack)
}

enum class FrameType(val type: Int) {
    NV21(1), H264(2)
}

enum class PictureFormat(val format: Int) {
    JPEG(1), PNG(2)
}

interface IPreviewCallBack {
    fun onPreviewFrame(data: ByteArray?, width: Int, height: Int, frameType: FrameType)
}

interface IPictureCallBack {
    fun onPictureTaken(data: ByteArray?, width: Int, height: Int, pictureFormat: PictureFormat)
}
```

获取媒体数据调用示例代码如下：

```kotlin
class VideoActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "VideoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        //配置开启媒体服务
        IpcManager.config(Config.builder().configOpenMedia(true).build())
        IpcManager.init(this)
        IpcManager.open("com.demo.ipcdemo")
    }


    fun takePicture(view: View) {

        IpcManager.getMediaService().setPictureCallBack(object : IPictureCallBack {
            override fun onPictureTaken(
                data: ByteArray?, width: Int, height: Int, pictureFormat: PictureFormat
            ) {
                Log.i(TAG, "onPictureTaken: ${data.contentToString()},format->$pictureFormat")
            }
        })

        IpcManager.getMediaService().takePicture(PictureFormat.JPEG)

    }


    fun takeFrame(view: View) {

        IpcManager.getMediaService().setPreviewCallBack(object : IPreviewCallBack {
            override fun onPreviewFrame(
                data: ByteArray?, width: Int, height: Int, frameType: FrameType
            ) {
                Log.i(TAG, "onPreviewFrame: ${data.contentToString()},format->$frameType")
            }
        })


        IpcManager.getMediaService().takeFrame(FrameType.NV21)
    }


    fun stopTakeFrame(view: View) {

        IpcManager.getMediaService().stopTakeFrame()

    }
}
```

服务端需要继承VideoService，并将实例放置到IpcManager当中去

```kotlin
/**
 * 这个类是模拟发送数据
 */
object VideoManager : VideoService() {

    private const val TAG = "VideoManager"

    @Volatile
    var sendFrame = false

    override fun takePicture(pictureFormat: Int) {

        jpegPictureData?.let {
            onTakePicture(it, 1410, 882, it.size, PictureFormat.JPEG.format)
        }
    }

    override fun takeFrame(type: Int) {
        sendFrame = true
    }

    override fun stopTakeFrame() {
        sendFrame = false
    }

    val handler = Handler(Looper.getMainLooper())

    val frameData = ByteArray(10) { i ->
        i.toByte()
    }

    private val runnable = object : Runnable {
        override fun run() {

            if (sendFrame) {
                frameData[0]++

                onTakeFrame(
                    frameData, 640, 480, 10, FrameType.NV21.type
                )

            }

            handler.postDelayed(this, 150)
        }

    }

    init {
        handler.post(runnable)
    }

    var jpegPictureData: ByteArray? = null

}
```

```kotlin
class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        IpcManager.config(Config.builder().configDebug(true).build())
        IpcManager.initVideoService(VideoManager) //初始化视频服务,用于提供视频数据,要提前设置好

        assets.open("kotlin.jpeg").use {
            VideoManager.jpegPictureData=it.readBytes()
        }

    }

    fun commonJump(view: View) {
        startActivity(Intent(this, CommonActivity::class.java))
    }

    fun videoJump(view: View) {
        startActivity(Intent(this, VideoActivity::class.java))
    }


}
```

