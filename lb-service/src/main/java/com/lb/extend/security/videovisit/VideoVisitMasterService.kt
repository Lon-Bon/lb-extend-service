package com.lb.extend.security.videovisit

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 * 视频会见管理主机接口
 */
@BindImpl("com.lonbon.video_visit_provider.VideoVisitMasterServiceImpl")
interface VideoVisitMasterService {

    /**
     * 获取指定类型的客户端列表
     */
    fun getVideoVisitClientListByType(type: Int, subType: Int? = 0): List<VideoVisitClient>

    /**
     * 获取通话中的会话列表
     */
    fun getTalkingSessionList(): List<VideoVisitSession>

    /**
     * 呼叫：呼叫指定的客户端
     * @param client VideoVisitClient
     */
    fun call(client: VideoVisitClient)

    /**
     * 接听：接听指定客户端的呼叫
     * @param client VideoVisitClient
     */
    fun answer(client: VideoVisitClient)

    /**
     * 设置回铃回调，一般呼入或者呼出时会回调
     * @param callBack Result<VideoVisitClient>
     */
    fun setRingBackCallBack(callBack: Result<VideoVisitClient>)

    /**
     * 转接：将通话的客户端转接至其他客户端上
     * @param fromClient VideoVisitClient 呼入客户端
     * @param toClient VideoVisitClient 转接客户端
     */
    fun transfer(fromClient: VideoVisitClient, toClient: VideoVisitClient)

    /**
     * 挂断通话：结束当前通话进程，主机没有接听对话框，因此不存在呼入的分机被挂断的情况，一般只有在通话界面通过挂断按钮挂断
     * @param sessionId  String 通话的sessionId
     */
    fun hangup(sessionId: String)

    /**
     * 查看分机：监听监视单个客户端
     * @param client VideoVisitClient
     */
    fun observer(client: VideoVisitClient)

    /**
     * 监听监视：监听监视正在通话的两个分机
     * @param sessionId  String 通话sessionId
     */
    fun monitorSession(sessionId: String)

    /**
     * 切断通话：结束两个分机之间的通话进程
     * @param sessionId  String
     */
    fun cutOffSession(sessionId: String)

    /**
     * 暂停通话：暂停两个分机之间的通话进程
     * @param sessionId  String
     */
    fun pauseSession(sessionId: String)

    /**
     * 恢复通话:恢复之前两个分机的通话进程
     * @param sessionId  String
     */
    fun resumeSession(sessionId: String)

    /**
     * 插话通话:对正在通话的两个分机进行插话
     * @param sessionId  String
     */
    fun interposeSession(sessionId: String)

    /**
     * 结束插话:取消对正在通话的两个分机进行的插话
     * @param sessionId  String
     */
    fun cancelInterposeSession(sessionId: String)

    /**
     * 分页获取通话记录
     * @param pageIndex Int
     * @param pageSize Int
     * @return List<CallLog>
     */
    fun getCallLog(pageIndex: Int, pageSize: Int): List<CallLog>

}

/**
 * 视频会见设备(固定不可变)
 * @property type Int 设备类型  0:主机，1：副机，2探访机（subType->0:家属分机，1律师分机），3：会见分机，5远程会见分机（subType->0:Android版本远程会见分机，1：PC版远程会见分机）
 * @property number Int 设备编号，不可重复
 * @property subType Int 设备子类型
 * @constructor
 */
data class VideoVisitDevice(
    val type: Int,
    val number: Int,
    val subType: Int
)

/**
 * 视频会见客户端，保存相关信息和状态（状态机）
 * @property device VideoVisitDevice 设备
 * @property state String 设备状态 Int 0：离线，1:空闲，2：呼叫中，3：呼入等待接听中，4：通话中
 * @property talkState String 设备通话附加状态 1：通话被暂停中，2：通话被插话中
 * @property desc String 设备描述信息
 * @property remark String 设备的备注信息
 * @constructor
 */
data class VideoVisitClient(
    val device: VideoVisitDevice,
    var state: Int = 0,
    var talkState: Int = 0,
    val desc: String = "",
    val remark: String = ""
)

/**
 * 通话中会话，用于监听等操作
 * @property sessionId  String 通话的唯一sessionId
 * @property limitVisitTime Int 会见限制时长（秒）
 * @property startTime Int 通话开始时间（时间戳）
 * @property talkDuration Int 已通话时间（秒）
 * @property visitInfo String String 会见信息 VideoVisitInfo json
 * @property callerClient VideoVisitClient 呼叫方客户端，方便业务处理
 * @property prisonerClient VideoVisitClient 会见分机
 * @property visitorClient VideoVisitClient 探访分机
 * @property callLog CallLog 通话记录
 * @constructor
 */
data class VideoVisitSession(
    val sessionId: String,
    var limitVisitTime: Int = 0,
    var startTime: Int = 0,
    var talkDuration: Int = 0,
    var visitInfo: String = "",
    var callerClient: VideoVisitClient,
    val prisonerClient: VideoVisitClient,
    val visitorClient: VideoVisitClient,
    val callLog: CallLog
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
 * 通话记录
 * @property id 自增长ID
 * @property sessionId  通话唯一ID
 * @property caller String 呼叫方 VideoVisitDevice json
 * @property callee String 被呼叫方 VideoVisitDevice json
 * @property startTime Int 通话开始时间戳
 * @property talkDuration Int 通话时长（秒）
 * @property event Int 事件类型：0->呼入未接,1->呼入已接,2->呼出未接，3->呼出已接
 * @property transfer String 转接情况
 * @property videoList String 录音录像文件列表 VideoInfo json数组
 * @constructor
 */
data class CallLog(
    val id: Long,
    val sessionId: String,
    val caller: String,
    val callee: String,
    val startTime: Int,
    val talkDuration: Int,
    val event: Int,
    val transfer: String,
    val videoList: String,
)

/**
 * 录音录像文件
 * @property sessionId  String 通话唯一ID
 * @property path 录音录像文件路径
 * @constructor
 */
data class VideoInfo(val sessionId: String, val path: Int)
