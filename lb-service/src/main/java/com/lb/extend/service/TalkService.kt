package com.lb.extend.service

import com.lb.extend.command.LonbonEvent
import com.lb.extend.command.TalkServiceEvent

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 *@author: baohekang
 *@time: 2024-05-24 10:21
 *@function: 对讲服务对外接口的定义
 **/


@BindImpl("com.lonbon.lonbon_app.service.TalkServiceImpl")
interface TalkService {
    /**
    初始化服务
    **/
    fun init()

    /**
     * 发起呼叫
     * **/
    fun talk2Master()
    /**
     *挂断
     * **/
    fun hangup()

    /**
     * 设置事件回调
     * **/
    fun setEventCallBack(callBack : Result<TalkServiceEvent>)

    /**
     * 接听
     * **/
    fun  answer()
}


