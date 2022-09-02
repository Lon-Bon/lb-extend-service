package com.lb.extend.service

import com.zclever.ipc.annotation.BindImpl

/**
 * 提供一些系统配置祥光服务器
 * @BindImpl("com.lonbon.lonbon_app.xxx"),这里要使用BindImpl注解
 */
@BindImpl("com.lonbon.lonbon_app.LonbonServiceImpl")
interface ILonbonService {

    /**
     * 是否开启第三方守护广播：com.lonbon.intent.action.app_start
     * 第三方设备监听该广播可以调起应用
     * @param open 开关：true 开启， false 关闭
     */
    fun openGuard(open:Boolean)

}