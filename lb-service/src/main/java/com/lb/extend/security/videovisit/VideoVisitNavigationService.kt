package com.lb.extend.security.videovisit

import com.zclever.ipc.annotation.BindImpl

/*
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 */ /**
 * 界面跳转服务
 */
@BindImpl("com.lonbon.video_visit_provider.VideoVisitNavigationServiceImpl")
interface VideoVisitNavigationService {

    /**
     * 跳转到指定类型的界面
     * @param type Int 0：通话记录
     */
    fun navigation2Activity(type: Int)
}