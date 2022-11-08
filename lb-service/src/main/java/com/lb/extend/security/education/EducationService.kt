package com.lb.extend.security.education

import android.content.Context
import com.zclever.ipc.annotation.BindImpl


@BindImpl("com.lonbon.education_provider.EducationServiceImpl")
interface EducationService {

    /**
     * 初始化电教相关配置，启动前必须先进行初始化
     *
     * sn：设备序列号
     * ip：设备ip
     * mac：设备mac地址
     * model：设备型号
     * clientType：设备类型
     * clientVersion：设备软件版本号
     * spaceTotal：总可用空间
     * spaceFree：剩余可用空间
     * description：描述信息
     */
    fun init(
        sn: String,
        ip: String,
        mac: String,
        model: String,
        clientType: String,
        clientVersion: String,
        spaceTotal: String,
        spaceFree: String,
        description: String,
    )

    /**
     * 跳转到电教点播直播首页Activity
     */
    fun startEducationMainActivity(context: Context)


    /**
     * 退出电教库，点播直播，双屏异显（其实本质就是finish电教Activity，销毁跟电教相关的任何事情）
     */
    fun exitEducation()

}