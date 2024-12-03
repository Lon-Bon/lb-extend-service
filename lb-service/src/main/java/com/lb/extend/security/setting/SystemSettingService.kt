package com.lb.extend.security.setting

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.setting_provider.SystemSettingImpl")
interface SystemSettingService {

    /**
     * 设置系统时间
     * @time 从1970年算起的毫秒数
     */
    fun setSystemTime(time: Long)

    /**
     * 重启设备
     */
    fun rebootSystem()


    /**
     * 获取屏幕像素密度
     */
    fun getScreenDensityDpi(): Int


    /**
     * 设置屏幕像素密度,实现对屏幕的缩放效果
     */
    fun setScreenDensityDpi(densityDpi: Int)


    /**
     * 导航栏虚拟按键的显示、隐藏
     * isVisible true:显示   false：隐藏
     */
    fun setNavigationVisible(isVisible: Boolean)

    /**
     * 设置媒体音量
     */
    fun setDeviceMediaVolume(volume: Int)


    /**
     * 设置是否亮屏
     * true:亮屏   false： 息屏
     */
    fun setIsBrightScreen(isBrightScreen: Boolean)


    /**
     * 静默安装apk
     * apkPath ： apk文件的绝对路径
     * isReboot： 安装结束是否需要重启设备
     */
    fun installApkSilently(apkPath: String, isReboot: Boolean)


    /**
     * 静默卸载apk
     * packageName ： apk文件的包名
     */
    fun uninstallApkSilently(packageName: String)


    /**
     * 获取默认网卡以太网IP
     */
    fun getDefaultIFaceEthernetIp(): String


    /**
     * 获取子网掩码
     */
    fun getDefaultEthernetNetMask(): String


    /**
     * 获取网关
     */
    fun getDefaultEthernetGateWay(): String


    /**
     * 设置以太网信息
     * ethernetIP: 设备ip
     * ethernetNetMask：子网掩码
     * ethernetGateWay： 网关
     */
    fun setEthernetInfo(ethernetIP: String?, ethernetNetMask: String?, ethernetGateWay: String?)


    /**
     * 定时开关机
     * @param mode   0/1   关机/开机
     * @param ena   0/1    否/是 开启该功能
     * @param time  18:30  时间
     */
    fun powerOnOrOff(mode: Int, ena: Int, time: String)


    /**
     * Get power on or off config 获取设备开关机信息
     *
     * @param dataCallBack
     */
    fun getPowerOnOrOffConfig(dataCallBack: Result<PowerOnOrOffConfig>)


    /**
     *水印控制开关
     * @param oSDEnable  true:打开水印  false:关闭水印
     */
    fun setMainCameraOSDEnable(oSDEnable: Boolean)


    /**
     * 升级第三方厂商的lonbon文件
     * @param lbFilePath  LB升级包文件
     * @param extras  后续可扩展一些配置，不需要就传 null
     */
    fun upgradeThirdPartyLBPackage(lbFilePath: String, vararg extras: String?)


    /**
     * 判断是否是前台应用
     */
    fun isAppForeground(packageName: String)
}

/**
 * Power on or off config  定时开关机数据
 *
 * @property powerOnSwitch  是否设置开机时间
 * @property powerOnTime   开机时间
 * @property powerOffSwitch  是否设置关机时间
 * @property powerOffTime   关机时间
 * @constructor Create empty Power on or off config
 */
data class PowerOnOrOffConfig(
    val powerOnSwitch: Boolean,
    val powerOnTime: String,
    val powerOffSwitch: Boolean,
    val powerOffTime: String,
)