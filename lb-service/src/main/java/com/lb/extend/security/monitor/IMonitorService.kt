package com.lb.extend.security.monitor

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 *
 * @ProjectName:  LBFloatProject
 * @Package:      com.lonbon.monitoring_provider
 * @ClassName: IMonitorService
 * @Author：    neo
 * @Create:    2023/3/20
 * @Describe: 视频监控右屏监护接口类
 */
@BindImpl("com.lonbon.monitoring_provider.MonitorServiceImpl")
interface IMonitorService {

    /**
     * 设置右屏监控IP
     * @param ip String
     */
    fun setMonitorIP(ip: String)

    /**
     * 初始化启监控模块
     */
    fun initMonitor()

    /**
     * 设置基础布局
     * @param rowColumn Int 行列数：（1，2，3）
     * @param callBack Result<BaseMonitorResponse>
     * @throws Exception
     */
    @Throws(Exception::class)
    fun initBaseLayout(rowColumn: Int, callBack: Result<BaseMonitorResponse>)

    /**
     * 设置监控数据
     * @param videoInfoList List<VideoInfo?>?
     * @param relayIP String?
     * @param relayPort Int
     * @param callBack Result<BaseMonitorResponse>
     * @throws Exception
     */
    @Throws(Exception::class)
    fun setVideoStreamData(
        videoInfoList: List<VideoInfo>,
        callBack: Result<BaseMonitorResponse>
    )

    /**
     *放大某一路监控视频
     * @param startX Int
     * @param startY Int
     * @param width Int
     * @param height Int
     * @param callBack Result<BaseMonitorResponse>
     */
    fun zoomVideo(
        startX: Int,
        startY: Int,
        width: Int,
        height: Int,
        callBack: Result<BaseMonitorResponse>
    )

    /**
     *还原监控视频
     * @param callBack Result<BaseMonitorResponse>
     */
    fun restoreVideo(callBack: Result<BaseMonitorResponse>)

    /**
     * 停止某一路监控视频
     * @param startX Int
     * @param startY Int
     * @param width Int
     * @param height Int
     * @param callBack Result<BaseMonitorResponse>
     */
    fun stopVideo(
        startX: Int,
        startY: Int,
        width: Int,
        height: Int,
        callBack: Result<BaseMonitorResponse>
    )

    /**
     * 确认监控数据，查询当前设置的监控数据，用于检查当前监控是否正常
     * @param row Int
     * @param column Int
     * @param videoInfoList List<VideoInfo?>?
     * @param relayIP String?
     * @param relayPort Int
     * @param callBack Result<BaseMonitorResponse>
     * @throws Exception
     */
    @Throws(Exception::class)
    fun confirmStreamData(
        row: Int,
        column: Int,
        videoInfoList: List<VideoInfo>,
        callBack: Result<BaseMonitorResponse>
    )

    /**
     * 中转服务器注册状态监听
     * @param callBack 回调门注册状态信息
     */
    fun onRelayServerListener(callBack: Result<RegisterEvent>)

    /**
     * 右屏串口Toast提示
     * @param callBack Result<SerialEvent>
     */
    fun onSerialEventListener(callBack: Result<SerialEvent>)



}

class RegisterEvent(val event: Int, val state: String)
class SerialEvent(val event: Int, val state: String)

class VideoInfo {


    var showTag :Int = 0

    //0是rtsp流，1是onvif
    @SerializedName("videoMode")
    var videoMode:Int = 0

    @SerializedName("startX")
    var startX:Int = 0

    @SerializedName("startY")
    var startY:Int = 0

    @SerializedName("width")
    var width:Int = 0

    @SerializedName("height")
    var height:Int = 0

    //视频参数，onvif模式按分号隔开，按冒号key:value(ip:192.168.1.141;port:8554;username:admin;password:admin)
    @SerializedName("videoParam")
    var videoParam:String = ""

    //是否显示描述信息（ 0 不显示； 1 显示）
    @SerializedName("isDescDisplay")
    var isDescDisplay:Int = 1


    @SerializedName("videoDescription")
    var videoDescription:String = ""

    @SerializedName("ipcPassword")
    var ipcPassword:String = ""

    @SerializedName("ipcAccount")
    var ipcAccount:String = ""

    //下标顺序
    var position:Int = -1

    //监控分组
    private var monitorId:Int  = -1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideoInfo

        if (videoMode != other.videoMode) return false
        if (videoParam != other.videoParam) return false
        if (ipcAccount != other.ipcAccount) return false
        if (monitorId != other.monitorId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = videoMode
        result = 31 * result + videoParam.hashCode()
        result = 31 * result + ipcAccount.hashCode()
        result = 31 * result + monitorId
        return result
    }

    override fun toString(): String {
        return "VideoInfo(showTag=$showTag, videoMode=$videoMode, startX=$startX, startY=$startY, width=$width, height=$height, videoParam='$videoParam', isDescDisplay=$isDescDisplay, videoDescription='$videoDescription', ipcPassword='$ipcPassword', ipcAccount='$ipcAccount', position=$position, monitorId=$monitorId)"
    }


}

class BaseMonitorResponse {
    @SerializedName("action")
    var action:String = ""

    @SerializedName("status")
    var status: String? = null

    @SerializedName("body")
    var body = JsonObject()

    val isSuccess: Boolean
        get() = "1" == status
}