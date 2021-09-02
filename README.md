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

## 合作小伙伴如何使用

参考：https://github.com/zhanggaoming/android-ipc#readme

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

