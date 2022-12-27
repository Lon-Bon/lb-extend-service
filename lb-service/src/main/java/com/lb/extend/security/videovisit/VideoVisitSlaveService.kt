package com.lb.extend.security.videovisit

import com.zclever.ipc.annotation.BindImpl

/**
 * 视频会见分机接口
 */
@BindImpl("com.lonbon.video_visit_provider.VideoVisitSlaveServiceImpl")
interface VideoVisitSlaveService {

    /**
     * 呼叫：默认呼叫到主机和副机，如果主机设置了本地一对一或者分配了会见信息，则优先处理
     */
    fun call()

    /**
     * 接听：接听当前呼入的设备
     */
    fun answer()

    /**
     * 挂断：挂断当前呼入的设备，或者正在进行的通话
     */
    fun hangup()

    /**
     * 获取当前设备状态
     * @return Int 0：离线，1:空闲，2：呼叫中，3：呼入等待接听中，4：通话中
     */
    fun getState(): Int

    /**
     * 获取当前设备通话附加状态
     * @return Int 1：通话被暂停中，2：通话被插话中
     */
    fun getTalkState(): Int

    /**
     * 是否繁忙
     * @return Int
     */
    fun isBusy(): Int

    /**
     * 是否空闲
     * @return Int
     */
    fun isIdle(): Int
}
