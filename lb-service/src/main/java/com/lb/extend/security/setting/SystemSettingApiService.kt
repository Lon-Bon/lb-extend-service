package com.lb.extend.security.setting

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 * 广州高新兴基础设置接口
 */
@BindImpl("com.lonbon.setting_provider.SystemSettingApiImpl")
interface SystemSettingApiService {

    /**
     * 查询监室内屏（分机）及主机设备信息
     * @return  返回系统基本信息
     */
    fun getDevInfo(callBack: Result<GxxSysDevInfo>)

    /**
     * 查询监室内屏运行状态
     * @return 硬件运行信息
     */
    fun getHardware(callBack: Result<GxxHardwareInfo>)

    /**
     * 修改设备IP/配置以太网
     * @return 返回设置状态 0:成功，1失败
     */
    fun setSysNetConfig(
        staticEnable: Boolean,    //是否使用静态IP 当为true,以上参数必填
        ethip: String,  //设备IP
        ethNetmask: String,//掩码
        ethGateWay: String, //网关
        dnsservers: Array<String>//DSN服务器
    ): Int


    /**
     * 重启终端操作系统
     * 系统断电重启
     * @param time 当time字段为空，则立即重启；当time为一个“00:00”格式时间戳，则指定每天某个时间定时重启
     * @return 返回设置状态 0:成功，1失败
     */
    fun sysReboot(time: String?): Int

    /**
     * 修改设备ADB端口
     * 开启关闭网络adb
     * @param isOpen True:开启；false关闭
     * @param port adb 端口号
     * @return  返回设置状态 0:成功，1失败
     */
    fun adbModify(isOpen: Boolean, port: Int): Int

    /**
     * 重启app
     * @param packageName App 包名
     * @return 返回设置状态 0:成功，1失败
     *
     */
    fun appRestart(packageName: String): Boolean

    /**
     * 升级APP
     * 静默安装app
     * @param appPath  安装包路径
     * @return 安装包路径 0:成功，1失败
     */
    fun install(appPath: String): Int


    /**
     * 设置音量
     * @param volume 音量值
     * @param type 类型，0-电话，1-系统，2-铃声，3-媒体，4-警告，5-通知
     * @return 0 成功，1 失败
     */
    fun setVolume(volume: Int, type: Int): Int

    /**
     * 获取音量
     * @param type 类型，0-电话，1-系统，2-铃声，3-媒体，4-警告，5-通知
     * @return 返回当前类型音量值
     */
    fun getVolume(type: Int): Int

    /**
     * 设置mic增益
     * @param number 增益值，数字越大增益越大
     * @return 0 成功，1 失败
     */
    fun setAdcBoost(number: Int): Int


}


data class GxxHardwareInfo(
    var cpuModel: String,//Cpu型号
    var cpuRate: String, //cpu 使用率/总频率
    var cpuNum: String, //Cpu核数
    var memory: String, //运行内存 已使用/总内存
    var storage: String, //存储空间 已使用/总内存
)


/**
 * 广州高新兴 数据实体类包装
 */

data class GxxSysDevInfo(
    var ethip: String,//设备IP
    var ethNetmask: String, //掩码
    var ethGateWay: String, //网关
    var dnsservers: Array<String>, //DSN服务器
    var staticEnable: Boolean, //是否使用静态IP
    var name: String = "",//设备名
    var serial: String = "" //序列号，唯一
)