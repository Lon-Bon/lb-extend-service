package com.lb.extend.security.broadcast

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result
import java.util.ArrayList

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 *
 * @ProjectName:  LBFloatProject
 * @Package:      com.lonbon.provider_center.broadcast
 * @ClassName: IBroadcastService
 * @Author：    neo
 * @Create:    2023/3/21
 * @Describe: 广播交互接口类
 */
@BindImpl("com.lonbon.broadcast_provider.BroadcastServiceImpl")
interface IBroadcastService {

    /**
     * 初始化喊话广播
     * @param list ArrayList<AreaDivision>
     */
    fun initSpeakBroadcast()

    /**
     * 设置喊话广播设备
     * @param list ArrayList<AreaDivision>
     */
    fun setSpeakBroadcastDevice(list: ArrayList<AreaDivision>)

    /**
     * 开始启动喊话广播
     * @param paramId Int
     */
    fun startSpeakBroadcast(paramId: Int)

    /**
     * 停止喊话广播
     * @param paramId Int
     */
    fun stopSpeakBroadcast(paramId: Int)

    /**
     * 右屏监控Toast提示
     * @param callBack Result<SerialEvent>
     */
    fun onToastListener(callBack: Result<String>)

    /**
     * 喊话广播相关状态回调
     * @param callBack Result<SpeakBroadcastState>
     */
    fun onSpeakBroadcastListener(callBack: Result<SpeakBroadcastState>)

    /**
     * 设备IO事件回调
     * @param callBack Result<Int>
     */
    fun onIONotifyListener(callBack: Result<Int>)



}

class AreaDivision {
    /**
     * 广播中的区号
     */
    var broadAreaNum = 0

    /**
     * 地址盒区域Id
     */
    var areaId = 0

    /**
     * 主机编号
     */
    var masterNum = 0

    /**
     * 设备6位displayNum
     */
    var displayNum = 0

    /**
     * 设备注册类型
     */
    var devRegType = 0

    fun isMaster(): Boolean {
        return displayNum % 1000 == 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AreaDivision

        if (areaId != other.areaId) return false
        if (masterNum != other.masterNum) return false
        if (displayNum != other.displayNum) return false
        if (devRegType != other.devRegType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = areaId
        result = 31 * result + masterNum
        result = 31 * result + displayNum
        result = 31 * result + devRegType
        return result
    }

    override fun toString(): String {
        return "AreaDivision(broadAreaNum=$broadAreaNum, areaId=$areaId, masterNum=$masterNum, displayNum=$displayNum, devRegType=$devRegType)"
    }


}

class SpeakBroadcastState(val event: Int){
    companion object{
        val onStart = 1
        val onFailed = 2
        val onPause = 3
        val onStop = 4
        val onProcessing = 5
        val onNextFile = 6
    }

}