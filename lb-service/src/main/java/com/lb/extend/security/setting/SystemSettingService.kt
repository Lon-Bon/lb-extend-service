package com.lb.extend.security.setting

import com.zclever.ipc.annotation.BindImpl

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
     */
    fun installApkSilently(apkPath: String)


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


}