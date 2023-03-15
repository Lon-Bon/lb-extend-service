package com.lb.extend.security.videovisit

import com.zclever.ipc.annotation.BindImpl

/**
 * 视频会见配置读写接口
 */
@BindImpl("com.lonbon.video_visit_provider.VideoVisitConfigServiceImpl")
interface VideoVisitConfigService {

    /**
     * 读取配置
     * @param key String
     * @return String
     */
    fun getConfig(key: String): String

    /**
     * 写入配置
     * @param key String
     * @param value String
     */
    fun saveConfig(key: String, value: String)
}

//录音录像配置
const val CONFIG_KEY_AV_SERVER = "CONFIG_KEY_AV_SERVER"

/**
 * 录音录像服务器配置
 * @property ip String
 * @property port Int
 * @constructor
 */
data class AVServerConfig(val ip: String, val port: Int)