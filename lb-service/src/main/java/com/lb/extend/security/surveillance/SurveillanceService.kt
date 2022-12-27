package com.lb.extend.security.surveillance

import com.zclever.ipc.annotation.BindImpl

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 *
 * @ProjectName:  LBFloatProject
 * @Package:      com.lonbon.surveillance_provider
 * @ClassName: SurveillanceServiceImpl
 * @Author：    dlz
 * @Create:    2022/9/1
 * @Describe: 监控视频 接口
 */
@BindImpl("com.lonbon.surveillance_provider.SurveillanceServiceImpl")
interface SurveillanceService {


    /**
     * 设置onvif页面显示位置（单位px）
     * @param left 对讲页面离屏幕左间距
     * @param top 对讲页面离屏幕上间距
     * @param width 对讲页面宽
     * @param height 对讲页面高
     */
    fun setOnvifPosition(left: Int, top: Int, width: Int, height: Int)
    /**
     * 设置onvif 拉流路径  4418最多支持1个，905d3最多支持2个
     * @param param1 路径1
     * @param param2 路径2
     */
    fun setOnvifUrl(param1: String, param2: String)

    /**
     * 开始拉流操作，在setOnvifUrl 之后使用
     */
    fun startStream()

    /**
     * 结束拉流操作，startStream 之后使用
     */
    fun stopStream()

}


