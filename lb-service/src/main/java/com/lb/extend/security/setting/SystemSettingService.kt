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
     * 设置定时开关机，格式 HH:mm，24 小时制的时与分如 20:30，空字符串表示无关机时间设置
     * 目前定制开关机的需求在瀚正悬浮窗有过，lonbon开关机设置暂时放在悬浮窗项目中实现
     * 开机时间 enable 是否打开定时开关机
     * @param onTime 开机时间  eg ： 05:18、25:30
     * @param onTime 关机时间  eg ： 05:18、25:30
     */
    fun powerOnOrOff(enable: Boolean, onTime: String, offTime: String)


    /**
     * 进入系统设置界面（翰正需要提供）
     */
    fun goSystemSettingPage()


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
     * 设置应用"安装未知应用"权限
     * 对应：设置 > 应用和通知(应用管理) > 特殊应用权限 > 安装未知应用
     *
     * 【注意：禁止时目标应用会被系统杀掉，属预期行为，不是崩溃】
     * enable = false 且权限状态由"允许"翻转为"禁止"时，Android 系统（AppOpsService）会主动
     * SIGKILL 杀掉 packageName 对应的应用进程，随后自动重新拉起。Logcat 表现为：
     *   I/ActivityManager: Killing <pid>:<packageName> (adj 0): REQUEST_INSTALL_PACKAGES changed.
     *   I/libprocessgroup: Successfully killed process cgroup uid <uid> pid <pid>
     *   I/Zygote: Process <pid> exited due to signal 9 (Killed)
     * 该行为由 AOSP 实现（app-op 变化会改变应用的 OBB 挂载视图/安装权限状态，系统靠杀进程保证
     * 新状态生效），本接口不会抛异常，也不会有 FATAL EXCEPTION / AndroidRuntime 堆栈，
     * `logcat -b crash` 为空 —— 看到目标应用消失/重启时请勿误判为代码崩溃。
     *
     * 只有状态真正翻转（允许 -> 禁止）才触发；重复设置到同一状态不会杀进程。
     * 注意：若 packageName 传的是调用方自己，调用方进程会一并被杀并重启。
     *
     * @param packageName 目标应用包名
     * @param enable true 允许安装未知来源应用，false 禁止
     */
    fun setInstallUnknownApps(packageName: String, enable: Boolean)

    /**
     * 获取应用"安装未知应用"权限状态
     * @param packageName 目标应用包名
     * @return true 已允许，false 未允许
     */
    fun isInstallUnknownAppsAllowed(packageName: String): Boolean
}


/**
 * enable	开机开关 false:未打开；true:已打开
 * onTime	开机时间，例如：“18:00”、“09:05”
 * offTime	关机时间，例如：“18:00”、“09:05”
 */
data class PowerOnOrOffConfig(
    val enable: Boolean,
    val onTime: String,
    val offTime: String,
)