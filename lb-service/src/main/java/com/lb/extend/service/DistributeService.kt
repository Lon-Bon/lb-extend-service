package com.lb.extend.service

import com.zclever.ipc.annotation.BindImpl

/**
 * 信息发布双屏异显
 */
@BindImpl("com.provider.distribute.DistributeProviderManager")
interface DistributeService {

    //初始化信息发布
    fun init();


    // 启动双屏异显（初始化后才能启动）
    fun start();

}