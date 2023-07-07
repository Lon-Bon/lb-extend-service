package com.lb.extend.security.education

import com.zclever.ipc.annotation.BindImpl


@BindImpl("com.lonbon.education_provider.EducationServiceImpl")
interface EducationService {

    /**
     * 启动前必须先进行初始化
     */
    fun init()

    /**
     * 进入电教点播直播首页
     */
    fun showEducation()

    /**
     * 退出点播直播页面
     */
    fun exitEducation()

    /**
     * 获取电教任务
     */
    fun getEducationTask(): List<EducationTaskBean>

    /**
     * 退出电教任务
     */
    fun exitEducationTask()

    /**
     * 进入电教页面
     */
    fun showEducationTask()

    /**
     * 获取电教任务状态
     */
    fun getEducationTaskState(callBack: Result<EducationTaskStateBean>)

    /**
     * HDMI配置
     * @param outputConfigure 1:一直有信号输出，2：仅在设备接收到信息发布或点播直播任务时有信号输出
     */
    fun setHDMIConfigure(outputConfigure: Int)

}


data class EducationTaskStateBean(
    val taskId: String, //任务id
    val taskName: String, //任务名称
    val execStart: String,//播放时间开始
    val execEnd: String,//播放时间结束
    val taskState: Int
)

data class EducationTaskBean(
    val taskId: String, //任务id
    val taskName: String, //任务名称
    val effectStart: String, //有效期开始
    val effectEnd: String,//有效期结束
    val execStart: String,//播放时间开始
    val execEnd: String,//播放时间结束
    val cycleType: String,//循环方式 week/month/count
    val cycleDetail: String //周:用,分开的星期数, 月: 1-1,1-28,3-10，count:按次,目前只有1
)