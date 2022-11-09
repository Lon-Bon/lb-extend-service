package com.lb.extend.security.education

import android.content.Context
import com.zclever.ipc.annotation.BindImpl


@BindImpl("com.lonbon.education_provider.EducationServiceImpl")
interface EducationService {

    /**
     * 启动前必须先进行初始化
     */
    fun initEducation()


    /**
     * 跳转到电教点播直播首页Activity
     */
    fun startHomeActivity(context: Context)


    /**
     * 退出电教页面
     */
    fun exitEducation()

}