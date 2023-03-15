package com.lb.extend.security.setting

import com.zclever.ipc.annotation.BindImpl

@BindImpl("com.lonbon.setting_provider.SystemSettingImpl")
interface SystemSettingService {

    /**
     * 设置系统时间
     * @time 从1970年算起的毫秒数
     */
    fun setSystemTime(time: Long)

    /**
     * 重启设备
     */
    fun rebootSystem()

}