package com.lb.extend.security.intercom

import com.lb.extend.common.CallbackData
import com.lb.extend.security.temperature.TemperatureData
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 * 高新兴对讲相关接口
 */
@BindImpl("com.lonbon.intercom_provider.IntercomServiceApiImpl")
interface IntercomApiService {


    /**
     * 文本语音播报接口
     * @param language 语言
     * @param rate 速率
     * @param text 文本内容，不能超过4000字符
     * @return 0 成功，1 失败
     */
    fun startTextToSpeech(language: String, rate: Int, text: String): Int


    /**
     * 门灯控制接口
     * 发送串口信息
     * @param color 门灯颜色 1 红闪，2 红亮，3 蓝闪，4 蓝亮，5 绿闪，6 绿亮，7 青闪，8 青亮，
     * 9 红蓝闪, 10 红绿闪，11 蓝绿闪，12 紫闪，13 紫亮，14 黄闪，15 黄亮， 16 白亮，
     * 17 白闪，18 黑亮，19 黑闪
     * @return 0 成功，1 失败，2其他
     */
    fun sendDoorLampCtrl(color: Int)


    /**
     * 设置门磁状态的回调
     */
    fun onDoorContactCallBack(callBack: Result<DoorContact>)


    /**
     * 启动测温
     */
    fun startGetTemperature()

    /**
     * 测温停止接口
     * @return 0关闭成功，1关闭失败
     */
    fun stopGetTemperature()

    /**
     * 设置测温接口回调
     */
    fun setTemperatureCallback(callBack: Result<CallbackData<TemperatureData>>)


    /**
     * 监测或响应 报警/呼叫物理按键
     */
    fun setOnIntercomIOCallBack(callBack: Result<Int>?)


    /**
     * 设置对讲事件的监听事件，比如呼出、呼入、报警、防拆报警等等
     */
    fun talkEventCallback(callBack: Result<TalkEvent>)


    /**
     * “alarm”:报警
     * “call”:呼叫
     */
    fun startCall(callType: String)


    /**
     * 挂断
     */
    fun hangup()


    /**
     * 请求通话视频流
     */
    fun requestVideo(request: Boolean)


    /**
     * 设置通话视频流回调
     */
    fun setNV21DataListener(callBack: Result<VideoStreamData>)


    /**
     * 请求摄像头流
     */
    fun requestCamera(callBack: Result<VideoStreamData>?, requestResult: Result<Int>)


}


data class VideoStreamData(
    val byteArray: ByteArray? = null,
    val width: Int = 0,
    val height: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoStreamData

        if (byteArray != null) {
            if (other.byteArray == null) return false
            if (!byteArray.contentEquals(other.byteArray)) return false
        } else if (other.byteArray != null) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = byteArray?.contentHashCode() ?: 0
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}
