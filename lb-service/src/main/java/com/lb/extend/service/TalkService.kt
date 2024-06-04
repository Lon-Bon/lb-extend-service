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

    /**
     * 开关视频模式
     * */
    fun setVideoMode(open:Boolean)

    /**
     * 设置本地视频悬浮窗位置以及大小
     * */
    /**
     * 设置本地预览页面显示位置（单位px）
     * @param left 视频画面离屏幕左间距
     * @param top 视频画面离屏幕上间距
     * @param width 视频画面宽
     * @param height 视频画面高
     */
    fun setPreViewPosition(left: Int, top: Int, width: Int, height: Int)


    /**
     * 设置对方视频悬浮窗位置以及大小
     * */
    /**
     * 设置对方预览页面显示位置（单位px）
     * @param left 视频画面离屏幕左间距
     * @param top 视频画面离屏幕上间距
     * @param width 视频画面宽
     * @param height 视频画面高
     */
    fun setRemoteViewPosition(left: Int, top: Int, width: Int, height: Int)

    /**
     * 隐藏本地视频画面
     * */
    fun hideLocalView()


    /**
     * 隐藏对方视频画面
     * */
    fun hideRemoteView()

    /**
     * 设置本地视频画面全屏，true 全屏，false 取消全屏
     * */
    fun setLocalViewFullScreen(full:Boolean)

    /**
     * 设置对方视频画面全屏，true 全屏，false 取消全屏
     * */
    fun setRemoteViewFullScreen(full: Boolean)

}


