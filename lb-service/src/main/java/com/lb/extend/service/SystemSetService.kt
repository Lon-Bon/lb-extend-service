package com.lb.extend.service

import com.lb.extend.command.LonbonEvent
import com.lb.extend.command.DeviceParams
import com.zclever.ipc.annotation.BindImpl

import com.zclever.ipc.core.Result

/**
 * 提供系统设置相关的服务
 */
//@BindImpl("com.lonbon.lonbon_app.xxx"),这里要使用BindImpl注解
@BindImpl("com.lonbon.lonbonprovider.manager.EventProviderManager")
interface SystemSetService {

    /**
     * 监听各种按钮事件
     */
    fun setEventCallBack(callBack :Result<LonbonEvent>)

    /**
     * 门灯控制
     * @color color 参数：
     * 1 红闪，2 红亮，3 蓝闪，4 蓝亮，5 绿闪，6 绿亮，7 青闪，8 青亮，9 红蓝闪
     * 10 红绿闪，11 蓝绿闪，12 紫闪，13 紫亮，14 黄闪，15 黄亮，16 白亮，17 白闪，18 黑亮，19 黑闪
     * @param bOn 开关：
     *  true为开，false为关
     *
     *  门灯对应的颜色必须一开一关。
     *  如果打开了1 在打开了3 就会是3现象。关闭3 就会是1现象。在关闭1 门灯才会熄灭
     */
    fun extDoorLampCtrl(color: Int, bOn: Boolean)

    /**
     * NFC刷卡器开关
     * @param bOn 开关：true 为打开， false为关闭
     */
    fun nfcControl(bOn:Boolean)

    /**
     * 摄像头开关
     * @param open 开关：true 为打开， false为关闭
     */
    fun cameraControl(open:Boolean)

    /**
     * 开关屏
     * @param open 开关：true 为开屏， false为关屏
     */
    fun panelPowerControl(open:Boolean)

    /**
     * 重启
     */
    fun rebootControl()

    /**
     * 截图
     * */
    fun screenshotControl()

    /**
     * 升级
     * */
    fun upgradeApp(path: String,packageName: String)

    /**
     * 同步系统时间
     * */
    fun updateSystemTime(newTime: Long)

    /**
     * 开启 wifi true 为开，false为关
     * **/
    fun openWifi(key:Boolean)

    /**
     * 进入设置
     * **/
    fun intoSetting()

    /**
     * 退出设置
     * **/
    fun backSetting()

    /**
     * 设置音量
     * */
    fun setVolume(streamType: Int,volume: Int)

    /**
     * 设置亮度
     * */
    fun setBrightness(brightness: Int)

    /**
     * 设置静态网络(ip,网关,子网掩码,DNS)
     * */
    fun setEthernetConfig(ip: String,gateway: String,netmask: String,dns: String)

    /**
     * 获取静态网络配置
     * */
    fun getEthernetConfig()

    /**
     * 获取设备mac
     * */
    fun getMac()

    /**
     * 获取存储/内存/CPU使用率参数
     * */
    fun getDeviceParams()

    /**
     * 获取设备信息参数
     */
    fun setDeviceParamsCallBack(callBack : Result<DeviceParams>)

    /**
     * 设置设备dpi参数
     */
    fun setDeviceDensity(density: Int)

    //2025/5/8
    /**
     * 报警
     * */
    fun alarm(type: Int)

    /**
     * 报警事件回调
     * */
    fun setAlarmCallBack(callBack : Result<List<AlarmEvent>>)
}

