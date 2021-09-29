package com.lb.extend.service

import com.lb.extend.command.LonbonEvent
import com.zclever.ipc.annotation.BindImpl

import com.zclever.ipc.core.Result

/**
 * 提供系统设置相关的服务
 */
//@BindImpl("com.lonbon.lonbon_app.xxx"),这里要使用BindImpl注解
@BindImpl("com.lonbon.lonbonprovider.manager.EventProviderManager")
interface SystemSetService {

    /**
     * 监听各种按钮事件
     */
    fun setEventCallBack(callBack :Result<LonbonEvent>)

    /**
     * 门灯控制
     * 门灯对应的颜色必须一开一关。连着开不同颜色就会有不同颜色闪烁
     * 关闭必须把所有颜色关闭才能不亮
     * color参数：
     * 1 红闪，2 红亮，3 蓝闪，4 蓝亮，5 绿闪，6 绿亮，7 青闪，8 青亮，9 红蓝闪
     * 10 红绿闪，11 蓝绿闪，12 紫闪，13 紫亮，14 黄闪，15 黄亮，16 白亮，17 白闪，18 黑亮，19 黑闪
     */
    fun extDoorLampCtrl(color: Int, bOn: Boolean)

}