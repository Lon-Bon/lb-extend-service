package com.lb.extend.security.videovisit

import com.zclever.ipc.annotation.BindImpl

/**
 * 视频会见主机接口
 */
@BindImpl("com.lonbon.video_visit_provider.VideoVisitMasterServiceImpl")
interface VideoVisitMasterService {

    /**
     * 获取指定类型的分机列表
     */
    fun getSlaveListByType(type: Int, subType: Int): List<VideoVisitDevice>

    /**
     * 获取通话中的会话列表
     */
    fun getTalkingSessionList(): List<TalkingSession>

    /**
     * 呼叫：呼叫指定的设备
     */
    fun call(type: Int, number: Int)

    /**
     * 接听：接听设备的呼叫
     */
    fun answer(type: Int, number: Int)

    /**
     * 转接：将通话的分机转接至其他分机上
     * @param fromDevice VideoVisitDevice 呼入对象
     * @param toDevice VideoVisitDevice 转接对象
     */
    fun transfer(
        fromDevice: VideoVisitDevice,
        toDevice: VideoVisitDevice,
    )

    /**
     * 挂断通话：结束当前通话进程
     * @param uuid String 通话的uuid
     */
    fun hangup(uuid: String)

    /**
     * 查看分机：监听监视单个分机
     * @param type Int
     * @param number Int
     */
    fun observer(type: Int, number: Int)

    /**
     * 监听监视：监听监视正在通话的两个分机
     * @param uuid String 通话uuid
     */
    fun monitor(uuid: String)

    /**
     * 切断通话：结束两个分机之间的通话进程
     * @param uuid String
     */
    fun cutVisit(uuid: String)

    /**
     * 暂停通话：暂停两个分机之间的通话进程
     * @param uuid String
     */
    fun pauseVisit(uuid: String)

    /**
     * 恢复通话:恢复之前两个分机的通话进程
     * @param uuid String
     */
    fun resumeVisit(uuid: String)

    /**
     * 插话通话:对正在通话的两个分机进行插话
     * @param uuid String
     */
    fun interposeVisit(uuid: String)

    /**
     * 结束插话:取消对正在通话的两个分机进行的插话
     * @param uuid String
     */
    fun cancelInterposeVisit(uuid: String)

    /**
     * 分页获取通话记录
     * @param pageIndex Int
     * @param pageSize Int
     * @return List<CallLog>
     */
    fun getCallLog(pageIndex: Int, pageSize: Int): List<CallLog>

    /**
     * 读取配置
     * @param key String
     * @return String
     */
    fun getConfig(key: String): String

    /**
     * 设置配置
     * @param key String
     * @param value String
     */
    fun saveConfig(key: String, value: String)

}

/**
 * 视频会见设备
 * @property type Int 设备类型  0:主机，1：副机，2探访机（subType->0:家属分机，1律师分机），3：会见分机，5远程会见分机（subType->0:Android版本远程会见分机，1：PC版远程会见分机）
 * @property subType Int 设备子类型
 * @property number Int 设备编号，不可重复
 * @property desc String 设备描述信息
 * @property model String 设备型号
 * @property state String 设备状态 Int 0：离线，1:空闲，2：呼叫中，3：呼入等待接听中，4：通话中
 * @property talkState String 设备通话附加状态 1：通话被暂停中，2：通话被插话中
 * @property limitVisitTime Int 会见限制时长（秒）
 * @property startTime Int 通话开始时间（时间戳）
 * @property talkDuration Int 已通话时间（秒）
 * @property visitInfo String 会见信息 VideoVisitInfo json
 * @constructor
 */
data class VideoVisitDevice(
    val type: Int,
    val subType: Int,
    val number: Int,
    val model: String,
    val desc: String? = "",
    var state: Int? = 0,
    var talkState: Int? = 0,
    var limitVisitTime: Int? = 0,
    var startTime: Int? = 0,
    var talkDuration: Int? = 0,
    var visitInfo: String? = "",
)

/**
 * 会见信息（待修改）
 * @property prisonerNum String 在押人员编号
 * @property prisonerName String 在押人员姓名
 * @property visitorNum String 探访人员编号
 * @property visitorName String 探访人员姓名
 * @constructor
 */
data class VideoVisitInfo(
    val prisonerNum: String,
    val prisonerName: String,
    val visitorNum: String,
    val visitorName: String,
)

/**
 * 通话中会话，用于监听等操作
 * @property uuid String 通话的唯一UUID
 * @property startTime Int 通话开始时间（时间戳）
 * @property talkDuration Int 已通话时间（秒）
 * @property visitInfo String String 会见信息 VideoVisitInfo json
 * @property prisonerDevice VideoVisitDevice 会见分机
 * @property visitorDevice VideoVisitDevice 探访分机
 * @constructor
 */
data class TalkingSession(
    val uuid: String,
    var startTime: Int,
    var talkDuration: Int,
    var visitInfo: String,
    val prisonerDevice: VideoVisitDevice,
    val visitorDevice: VideoVisitDevice,
)

/**
 * 通话记录
 * @property uuid String
 * @constructor
 */
data class CallLog(val id: Long, val uuid: String)

/**
 * 录音录像服务器配置
 * @property ip String
 * @property port Int
 * @constructor
 */
data class AVServerConfig(val ip: String, val port: Int)