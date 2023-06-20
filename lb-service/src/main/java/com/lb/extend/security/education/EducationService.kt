package com.lb.extend.security.education

import com.zclever.ipc.annotation.BindImpl


@BindImpl("com.lonbon.education_provider.EducationServiceImpl")
interface EducationService {

    /**
     * 启动前必须先进行初始化
     */
    fun init()


    /**
     * 跳转到电教点播直播首页Activity
     */
    fun startActivity()


    /**
     * 退出电教页面
     */
    fun exit()

    /**
     * 是否执行电教任务
     * @param isExecute 是否执行 true:执行，false：不执行
     */
    fun setEduTaskExecuteState(isExecute: Boolean)

    /**
     * 退出电教任务
     */
    fun exitEduTask()

    /**
     * 电教任务状态
     */
    fun hasEduTask(): Boolean

    /**
     * HDMI配置
     * @param outputConfigure 1:一直有信号输出，2：仅在设备接收到信息发布或点播直播任务时有信号输出
     */
    fun setHDMIConfigure(outputConfigure: Int)

    /**
     * 电教任务监听器
     * @param isExistTask：是否存在电教任务
     */
    fun setEduTaskListener(isExistTask: ((Boolean) -> Unit))

}