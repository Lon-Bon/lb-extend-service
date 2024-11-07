package com.lb.extend.security.gxgxx

import com.zclever.ipc.annotation.BindImpl

/**
 * 广州高新兴接口定制
 */
@BindImpl("com.lonbon.setting_provider.SystemApiForGxx")
interface SystemApiForGxx {

    /**
     * 查询监室内屏（分机）及主机设备信息
     * @return  返回系统基本信息
     */
    fun getDevInfo(): GSysDevInfo?

    /**
     * 查询监室内屏运行状态
     * @return 硬件运行信息
     */
    fun getHardware(): GHardwareInfo?

    /**
     * 修改设备IP/配置以太网
     * @return 返回设置状态 0:成功，1失败
     */
    fun setSysNetConfig(info: GNetWorkInfo): Int


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
    fun appRestart(packageName: String): Int

    /**
     * 升级APP
     * 静默安装app
     * @param appPath  安装包路径
     * @return 安装包路径 0:成功，1失败
     */
    fun install(appPath: String): Int

    /**
     * 文本语音播报接口
     * @param language 语言
     * @param rate 速率
     * @param text 文本内容，不能超过4000字符
     * @return 0 成功，1 失败
     */
    fun startTextToSpeech(language: String, rate: Int, text: String): Int

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