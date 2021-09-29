package com.lb.extend.service

import com.zclever.ipc.annotation.BindImpl

/**
 * 信息发布双屏异显
 */
@BindImpl("com.provider.distribute.DistributeProviderManager")
interface DistributeService {

    /**
     * 开始双屏异显
     */
    fun start();



}