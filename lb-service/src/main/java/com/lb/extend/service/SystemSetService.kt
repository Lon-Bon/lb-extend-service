package com.lb.extend.service

import com.lb.extend.command.LonbonEvent
import com.zclever.ipc.annotation.BindImpl

import com.zclever.ipc.core.Result

/**
 * 提供系统设置相关的服务
 */
//@BindImpl("com.lonbon.lonbon_app.xxx"),这里要使用BindImpl注解
@BindImpl("com.lb.extend.manager.SystemSetServiceManager")
interface SystemSetService {

    fun setEventCallBack(callBack :Result<LonbonEvent>)

}