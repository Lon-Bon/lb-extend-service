package com.lb.extend.security.intercom

import android.util.SparseArray
import com.zclever.ipc.annotation.BindImpl

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
     * 设备终端管理接口
     *
     * 主机用：用于查询设备在线列表进行UI显示
     *
     * @param areaId 区号
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param devRegType 注册类型
     * @param callBack 返回该areaId下的在线设备列表
     *
     */
    fun asyncGetDeviceListInfo(areaId: Int, masterNum: Int, slaveNum: Int, devRegType: Int, callBack: Result<ArrayList<DeviceInfo>>)

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
    fun masterClickItem(masterNum: Int, slaveNum: Int, areaID : Int, devRegType : Int)

    /**
     * 对讲呼叫方法
     * 已有对讲状态时调用呼叫
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun call(masterNum: Int, slaveNum: Int, areaID : Int, devRegType : Int)

    /**
     * 对讲接听方法
     * 已有对讲状态时调用接听
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun answer(masterNum: Int, slaveNum: Int, areaID : Int, devRegType : Int)

    /**
     * 对讲挂断方法
     * 已有对讲状态时调用挂断
     *
     * @param masterNum 主机号
     * @param slaveNum 分机号
     * @param areaID 区号
     * @param devRegType 注册类型
     */
    fun hangup(masterNum: Int, slaveNum: Int, areaID : Int, devRegType : Int)

}

/**
 * 门磁状态信息
 *
 * num：门磁序号: 1 门磁一   2 门磁二
 * open：门磁开关, 1 打开 0关闭
 */
data class DoorContact(
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

    constructor()

    constructor(areaID: Int, masterNum: Int, slaveNum: Int, childNum: Int, devRegType: Int)

    constructor(ip: String, description: String, areaID: Int, masterNum: Int, slaveNum: Int, childNum: Int, devRegType: Int, talkState: Int, doorState: SparseArray<Int>)
}