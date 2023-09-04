package com.lb.extend.security.intercom

import android.util.SparseArray
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result
import java.io.File

@BindImpl("com.lonbon.intercom_provider.IntercomServiceImpl")
interface IntercomService {

    /**
     * 设置对讲页面显示位置（单位px）
     * @param left 对讲页面离屏幕左间距
     * @param top 对讲页面离屏幕上间距
     * @param width 对讲页面宽
     * @param height 对讲页面高
     */
    fun setTalkViewPosition(left: Int, top: Int, width: Int, height: Int)

    /**
     * 门灯控制
     * @param color 门灯颜色 1 红闪，2 红亮，3 蓝闪，4 蓝亮，5 绿闪，6 绿亮，7 青闪，8 青亮，
     *              9 红蓝闪,  10 红绿闪，11 蓝绿闪，12 紫闪，13 紫亮，14 黄闪，15 黄亮，
     *              16 白亮， 17 白闪，18 黑亮，19 黑闪
     * @param open 门灯开关 1 打开 0关闭
     */
    fun extDoorLampCtrl(color: Int, open: Int)

    /**
     * 门磁开关回调
     * @param callBack 回调门磁状态信息
     */
    fun onDoorContactValue(callBack: Result<DoorContact>)

    /**
     * 查询设备列表接口（带描述信息）
     *
     * 主机用：用于查询设备在线列表进行UI显示
     *
     * 仅传区号，其他参数传0，则为查询区号下的主机列表
     * 传区号、主机号、注册类型，分机号传0，则为查询该区该主机下某类型的分机列表
     *
     * @param areaId 区号
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param devRegType 注册类型
     * @param callBack 返回该areaId下的在线设备列表
     *
     */
    fun asyncGetDeviceListInfo(
        areaId: Int,
        masterNum: Int,
        slaveNum: Int,
        devRegType: Int,
        callBack: Result<ArrayList<DeviceInfo>>
    )

    /**
     * 设备对讲状态回调接口
     *
     * 监听某个设备的状态改变（包括正常空闲，对讲中，监听中等等）
     * 主机根据callback里返回的设备状态改变列表UI显示
     * 终端根据callback里返回的设备状态改变UI显示
     *
     * @param callBack 返回状态变化的设备
     */
    fun updateDeviceTalkState(callBack: Result<DeviceInfo>)

    /**
     * 对讲点击方法，主机使用
     * 主机点击列表中某个设备调用，交由悬浮窗判断是呼叫还是接听
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun masterClickItem(masterNum: Int, slaveNum: Int, areaID: Int, devRegType: Int)

    /**
     * 对讲呼叫方法
     * 已有对讲状态时调用呼叫
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun call(masterNum: Int, slaveNum: Int, areaID: Int, devRegType: Int)

    /**
     * 对讲接听方法
     * 已有对讲状态时调用接听
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun answer(masterNum: Int, slaveNum: Int, areaID: Int, devRegType: Int)

    /**
     * 对讲挂断方法
     * 已有对讲状态时调用挂断
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun hangup(masterNum: Int, slaveNum: Int, areaID: Int, devRegType: Int)

    /**
     * 开关电控锁
     *
     * @param num 电控锁序号
     * @param open 开关 0关 1开
     */
    fun openLockCtrl(num: Int, open: Int)

    /**
     * 获取当前设备信息（包含设备编号）
     *
     * @param callBack 设备信息
     */
    fun getCurrentDeviceInfo(callBack: Result<LocalDeviceInfo>)

    /**
     * 设备对讲事件回调接口
     *
     * 回调当前设备对讲事件
     * @param callBack 返回状态变化的设备
     */
    fun talkEventCallback(callBack: Result<TalkEvent>)

    /**
     * 设备在线回调接口
     *
     * @param callBack 返回状态变为在线的设备
     */
    fun onDeviceOnLine(callBack: Result<DeviceInfo>)

    /**
     * 设备离线回调接口
     *
     * @param callBack 返回态变为离线的设备
     */
    fun onDeviceOffLine(callBack: Result<DeviceInfo>)

    /**
     * 监听转对讲
     *
     * @param callBack 返回态变为离线的设备
     */
    fun listenToTalk()

    /**
     * 设置视频隐藏
     *
     * @param hide 隐藏视频 true隐藏 false显示
     */
    fun hideTalkView(hide: Boolean)

    /**
     * 一键呼叫
     */
    fun oneKeyCall()

    /**
     * 开关本地摄像头
     *
     * @param open 开关本地摄像头 true打开 false关闭
     */
    fun openLocalCamera(open: Boolean)

    /**
     * 切断分机通话
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun cutCallExt(masterNum: Int, slaveNum: Int, areaID: Int, devRegType: Int)

    /**
     * 设置IPC页面显示位置（单位px）
     * @param left 对讲页面离屏幕左间距
     * @param top 对讲页面离屏幕上间距
     * @param width 对讲页面宽
     * @param height 对讲页面高
     */
    fun setIpcViewPosition(left: Int, top: Int, width: Int, height: Int)

    /**
     * 设置视频隐藏
     *
     * @param hide 隐藏视频 true隐藏 false显示
     */
    fun hideIpcView(hide: Boolean)

    /**
     * 设置呼叫对象
     *
     * @param masterNum 主机号
     * @param areaID 区号
     */
    fun setCallDevice(masterNum: Int, areaID: Int)

    /**
     * 设置报警对象
     *
     * @param masterNum 主机号
     * @param areaID 区号
     */
    fun setAlarmDevice(masterNum: Int, areaID: Int)

    /**
     * 一键呼叫
     *
     */
    fun extCall()

    /**
     * 一键报警
     *
     */
    fun extAlarm()

    fun setTalkViewFullScreen(needFullScreen: Boolean)

    /**
     * 设置本地预览页面显示位置（单位px）
     * @param left 对讲页面离屏幕左间距
     * @param top 对讲页面离屏幕上间距
     * @param width 对讲页面宽
     * @param height 对讲页面高
     */
    fun setPreViewPosition(left: Int, top: Int, width: Int, height: Int)

    /**
     * 设置本地预览视频隐藏
     *
     * @param hide 隐藏视频 true隐藏 false显示
     */
    fun hidePreView(hide: Boolean)

    /**
     * 设置咪头使能
     *
     * @param enable
     */
    fun setMicEna(enable: Boolean)


    /**
     * 本机摄像头拍照
     */
    fun initFrame()

    /**
     * 开启启动发送数据
     */
    fun startTakeFrame()

    /**
     * 设置采集图像宽高
     * @param width Int
     * @param height Int
     */
    fun setViewWidthHeight(width:Int,height:Int)

    /**
     * 对讲终端人员信息回调接口
     *
     * @param callBack 返回人员信息
     */
    fun onTalkPeopleInfoCallback(callBack: Result<GeneralUDPBean>)

    /**
     * 设置通话记录保存路径
     * @param path String
     * @param callBack Result<String>
     */
    fun setRecordPath(path:String,callBack: Result<String>)

    /**
     * 获取路径下的文件
     * @param path String
     * @param callBack Result<ArrayList<File>>
     */
    fun getFileList(path:String,callBack: Result<ArrayList<File>>)

    /**
     * 删除文件
     * @param path String
     * @param callBack Result<Boolean>
     */
    fun deleteFile(path:String,callBack: Result<Boolean>)

    /**
     * 隐藏和显示对讲视频框
     *
     * @param hide 隐藏视频 true隐藏 false显示
     */
    fun hideRemoteVideoView(hide: Boolean)

    /**
     * 主机设置分机通话音量
     * @param volume 范围 0-5
     */
    fun setSlaveTalkVolume(volume: Int)

    /**
     * 主机获取分机通话音量
     */
    fun getSlaveTalkVolume(): Int


    /**
     * 获取下级主机描述信息
     */
    fun getSubMasterList(callBack: Result<ArrayList<MasterDeviceInfo>>): Int
}


/**
 * 门磁状态信息
 *
 * num：门磁序号: 1 门磁一   2 门磁二
 * open：门磁开关, 1 打开 0关闭
 */
data class DoorContact(
    val deviceInfo: DeviceInfo,
    val num: Int,
    val open: Int
)

/**
 * 设备信息
 *
 * ip: 设备ip地址
 * description：设备描述信息
 * areaID: 设备区号
 * masterNum: 设备主机号
 * slaveNum: 设备分机号
 * childNum:
 * devRegType: 设备类型
 * talkState：设备对讲状态（参考BaseDevice里的状态）
 * doorState：门磁状态
 */
class DeviceInfo {
    var ip: String = ""
    var description: String = ""
    var areaID: Int = 0
    var masterNum: Int = 0
    var slaveNum: Int = 0
    var childNum: Int = 0
    var devRegType: Int = 0
    var talkState: Int = 0
    var doorState: SparseArray<Int> = SparseArray()

    constructor(areaID: Int, masterNum: Int, slaveNum: Int, childNum: Int, devRegType: Int) {
        this.areaID = areaID
        this.masterNum = masterNum
        this.slaveNum = slaveNum
        this.childNum = childNum
        this.devRegType = devRegType
    }

    constructor(
        description: String,
        areaID: Int,
        masterNum: Int,
        slaveNum: Int,
        childNum: Int,
        devRegType: Int,
        talkState: Int,
        doorState: SparseArray<Int>
    ) {
        this.description = description
        this.areaID = areaID
        this.masterNum = masterNum
        this.slaveNum = slaveNum
        this.childNum = childNum
        this.devRegType = devRegType
        this.talkState = talkState
        this.doorState = doorState
    }

    override fun toString(): String {
        return "DeviceInfo(ip='$ip', description='$description', areaID=$areaID, masterNum=$masterNum, slaveNum=$slaveNum, childNum=$childNum, devRegType=$devRegType, talkState=$talkState, doorState=$doorState)"
    }
}

class LocalDeviceInfo {
    var deviceName = ""
    var deviceModel = ""
    var customizedModel = ""
    var hardwareVersion = ""
    var NKVersion = ""
    var modelCode = 0
    var platform: String? = null
    var account = ""
    var password = ""
    var encPassword = ""
    var sipPort = 0
    var sn = ""
    var mac = ""
    var ip = ""
    var gateway = ""
    var netmask = ""
    var isAllowSDRecording = false
    var manufactoryType = 0
    var paymentTermCode = ""
    var produceTime = ""
    var displayNum = 0
    var masterNum = 0
    var slaveNum = 0

    override fun toString(): String {
        return "LocalDeviceInfo(deviceName='$deviceName', deviceModel='$deviceModel', customizedModel='$customizedModel', hardwareVersion='$hardwareVersion', NKVersion='$NKVersion', modelCode=$modelCode, platform=$platform, account='$account', password='$password', encPassword='$encPassword', sipPort=$sipPort, sn='$sn', mac='$mac', ip='$ip', gateway='$gateway', netmask='$netmask', isAllowSDRecording=$isAllowSDRecording, manufactoryType=$manufactoryType, paymentTermCode='$paymentTermCode', produceTime='$produceTime', displayNum=$displayNum, masterNum=$masterNum, slaveNum=$slaveNum)"
    }

}

class TalkEvent {
    var deviceInfo: DeviceInfo? = null
    var eventID: Int = 0

    constructor(deviceInfo: DeviceInfo?, eventID: Int) {
        this.deviceInfo = deviceInfo
        this.eventID = eventID
    }

    override fun toString(): String {
        return "TalkEvent(deviceInfo=$deviceInfo, eventID=$eventID)"
    }

}
class GeneralUDPBean {
    var action: String? = null
    var interCmd: String? = null
    var slaveCode: String? = null
    var name: String? = null
    var code: String? = null
    var event: String? = null
    var displayNum: String? = null
    var areaID: String? = null
    var BranchCode: String? = null
    var PersonType :String? = null
    var RoomNum: String? = null

    override fun toString(): String {
        return "GeneralUDPBean(action=$action, interCmd=$interCmd, slaveCode=$slaveCode, name=$name, code=$code, event=$event, displayNum=$displayNum, areaID=$areaID, BranchCode=$BranchCode, PersonType=$PersonType, RoomNum=$RoomNum)"
    }
}
/**
 * 主机设备信息
 *
 * areaID: 设备区号
 * displayNum：设备号
 * devRegType：注册类型
 * description：设备描述信息
 */
class MasterDeviceInfo {
    var areaID: Int = 0
    var displayNum: Int=0
    var devRegType: Int = 0
    var description: String? = null
    override fun toString(): String {
        return "MasterDeviceInfo(description=$description, displayNum=$displayNum, areaID=$areaID)"
    }


}