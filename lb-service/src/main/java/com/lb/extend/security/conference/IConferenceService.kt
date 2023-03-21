package com.lb.extend.security.conference

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 *
 * @ProjectName:  LBFloatProject
 * @Package:      com.lonbon.intercom_provider.conference
 * @ClassName: IConferenceService
 * @Author：    dlz
 * @Create:    2022/7/1
 * @Describe: sip 接口
 */
@BindImpl("com.lonbon.intercom_provider.conference.IConferenceServiceImpl")
interface IConferenceService {

    /**
     * sip 相关事件回调
     * @param callBack Result<SipEvent>
     */
    fun onConferenceEvent(callBack: Result<ConferenceEvent>)

    /**
     * 获取sip注册账号
     * @return String
     */
    fun getConferenceUsername(): String

    /**
     * 获取sip账号 显示名
     * @return String
     */
    fun getConferenceDisplayName(): String

    /**
     *
     * @return Boolean -1 注销成功 0 注册失败 1 注册成功
     */
    fun isConferenceRegisterState(): Int

    /**
     * 开始一个视频会议
     * @param describe 设置会议的描述信息
     * @param isonlyaudio  设置会议类型    0 视频会议 1 语音会议
     * @param isdirectormode 设置会议模式   0 自由模式 1 主席模式
     * @param isrecord  设置记录开关        0 关闭      1 开启
     * @param videomixermode 设置初始布局  自适应9宫格           1  （ 默认）
     *                                    自适应10宫格          2
     *                                    河北定制              3
     *                                    固定单方布局          4
     *                                    固定4方布局           5
     *                                    固定6方布局           6
     *                                    固定8方布局           7
     *                                    固定9方布局           8
     *  @memberNames 会议的邀请成员 可以在开始会议后加入  或者主动邀请
     *  @return 会议房间号
     */
    fun createConference(
        describe: String,
        isonlyaudio: Int,
        isdirectormode: Int,
        isrecord: Int,
        videomixermode: Int,
        callBack: Result<ResultObject>
    )

    /**
     * sip 呼叫
     * @param sipNum String sip账号
     * @return String 异常的提示
     * @param dataType Int 设备 类型 2 默认是 sip 3 听众席 4 固话
     */
    fun conferenceCall(sipNum: String, dataType: Int): ResultObject

    /**
     * sip接听
     * @param sipNum String sip账号
     * @return String
     */
    fun conferenceAnswer(sipNum: String): ResultObject

    /**
     * sip挂断
     * @param sipNum String sip账号
     * @return String
     */
    fun conferenceHangup(sipNum: String): ResultObject

    /**
     * 设置uhiyi 页面显示位置（单位px）
     * @param left 对讲页面离屏幕左间距
     * @param top 对讲页面离屏幕上间距
     * @param width 对讲页面宽
     * @param height 对讲页面高
     */
    fun setConferenceViewPosition(left: Int, top: Int, width: Int, height: Int)


    /**
     * 会议服务器注册成员件回调接口
     * @return callBack 返回会议注册成员
     */
    fun getRegisterUserNames(callBack: Result<ResultRegisterInfo>)

    /**
     * 会议服务器邀请成员件回调接口
     * @param memberNames 受邀请成员列表
     * @return  返回会议邀请成员状态
     */
    fun inviteUsers(memberNames: List<String>, callBack: Result<ResultObject>)

    /**
     * 会议服务器邀请听众回调接口
     * 邀请听众成员
     * @param memberNames 成员列表
     * @param sipAccount 房间号
     * @param roomDescribe 房间描述信息
     */
    fun inviteLive(
        sipAccount: String,
        roomDescribe: String,
        memberNames: List<String>,
        callBack: Result<ResultObject>
    )

    /**
     * 本机服务器会议参会记录
     * @param index 首个索引
     * @param pageSize 返回记录条数
     * @param callBack 返回会议邀请成员状态
     */

    fun getConferenceRecordInfo(
        index: Int,
        pageSize: Int,
        callBack: Result<List<ConferenceRecordInfo>>
    )

    /**
     * 本机服务器会议参会记录总条数
     * @param callBack 返回会议邀请成员状态
     */
    fun getConferenceRecordCount(
        callBack: Result<Int>
    )

    /**
     * 删除所有的会议记录
     */
    fun deleteAll(callBack: Result<Int>)

    /**
     * 通知会议本设备的状态改变
     * @param state 状态信息 -->ConferenceCommand 中字段
     * @param sipAccount 房间号
     *
     */
    fun localDeviceConferenceNotice(state: String,sipAccount: String)

    /**
     *切换自由模式  一般只有发起者调用
     * @param sipAccount 房间号
     * @param callBack 结果返回
     */

    fun freeMode(sipAccount: String, callBack: Result<ResultObject>)

    /**
     * 切换主席模式  一般只有发起者调用
     * @param sipAccount 房间号
     * @param memberName 开启声音设备名 为空 则默认本机设备开启声音
     * @param callBack 结果返回
     */
    fun directorMode(sipAccount: String, memberName: String, callBack: Result<ResultObject>)

    /**
     * 删除成员 一般只有发起者调用
     * @param sipAccount 房间号
     * @param memberName 操作设备名称
     */
    fun deleteMember(sipAccount: String, memberName: String, callBack: Result<ResultObject>)

    /**
     * 全体静音
     * @param sipAccount 房间号
     * @param callBack 结果返回
     */
    fun closeAllVoice(sipAccount: String, callBack: Result<ResultObject>)

    /**
     * 设置发言者
     * @param sipAccount 房间号
     * @param memberName 成员名称
     * @param callBack 结果返回
     */

    fun setSpeaker(sipAccount: String, memberName: String, callBack: Result<ResultObject>)

    /**
     * 结束会议
     * @param sipAccount 房间号
     * @param callBack 结果返回
     */
    fun closeConference(sipAccount: String, callBack: Result<ResultObject>)

    /**
     * 切换声音
     * @param sipAccount 房间号
     * @param memberName 成员名称
     * @param isOpen   true 打开声音 false 关闭声音
     * @param callBack 结果返回
     */
    fun switchVoice(
        sipAccount: String,
        memberName: String,
        isOpen: Boolean,
        callBack: Result<ResultObject>
    )

    /**
     * @return 返回设备序列号 会议的唯一标识
     */
    fun getDeviceSn(): String

    /**
     * @return 返回本设备注册名称
     */
    fun getSipRegisterName(): String

    /**
     * 会议的消息相关回调
     * 主席操作会议 如 切换模式，开关声音等通知其他主机更新成员的状态信息
     * @param callBack Result<ConferenceMessage>
     */
    fun onConferenceMessage(callBack: Result<ConferenceMessage>)

    /**
     * 获取当前会议信息
     * @param sipAccount 房间号
     * @param callBack 房间所有信息
     */
    fun getConferenceInfo(sipAccount: String, callBack: Result<ConferenceRoomInfo?>)
}

/**
 * 请求回调
 */
class ResultObject {
    var code = -1  //  0 成功  非0 失败

    var message = ""// 描述性

    var sipAccount = "" //房间号

    override fun toString(): String {
        return "ResultObject(code=$code, message=$message)"
    }

}

/**
 * 请求回调注册信息
 */
class ResultRegisterInfo {
    var code = -1  //  0 成功  非0 失败
    var message = ""// 描述性
    var maxmember = 32  //服务器可以邀请的最大成员数量   默认32
    var isAllowLive = false// 是否支持听众邀请
    var list: List<String>? = null
    override fun toString(): String {
        return "ResultObject(code=$code, message=$message)"
    }

}

/**
 * @param isallowlive  是否允许听众  1 允许  0 允许
 * @param  rooms  会议列表  当前的会议为第一个成员
 */
class ConferenceRoomInfo(
    val isallowlive: Int,
    val rooms: List<RoomsBean>
) {
    class RoomsBean(
        var confID: String,                  //房间标识         
        val roomNumber: String?,            //房间名-->sipAccount
        var roomDescribe: String?,          //房间描述
        var recordeName: String?,          //会议的播放地址
        var Initiator: String?,            //发起者描述
        var isRecord: Int,                 //是否开启录像  1 开启 0关闭
        var isCrcStartlb: Int,             //是否轮询      1 开启  0关闭
        var isdirectormode: Int,           // 1 主席模式   0 自由模式
        var maxMemberCount: Int,           //最大成员个数  已加入+未加入
        var visibleMemberCount: Int,        //已加入成员
        var StartTime: String?,              //会议创建时间
        var duration: String?,              //会议时长
        var isonlyaudio: Int,              // 会议类型  1 音频会议， 0 视频会议
        var roomModeSet: Int,             //布局设置
        var speakMemberCount: Int,         //可说话成员个数
        var rtmpname: String?,              //听众观看流地址
        var presentername: String?,          //会议主讲人
        var members: List<MembersBean>?
    ) {

        class MembersBean {
         
            var isSelect = false  //自定义设置，页面显示是否被选中
            var online = 0       //0 不在线  1 在线
            var id = 0
            var name: String = ""     //会议显示名称
            var muteMask = 0         //0 可以说话也可以听到  1 不能说话  2 不能听到
            var vMPState = 0           // 0 不在屏幕 1 在屏幕
            var connectstatuslb = 0

            /**
             *   0 不在线   180 呼叫中  10 呼叫成功  11 主席挂断  12 主机主动挂断
             *   408 超时挂断 603 主机拒绝接听  487 无应答  其他  暂无
             *   1003 听众退出 1002 听众拒绝接听  1001 听众加入  1000  听众无反应
             */
            var islive = 0           //0是参与者 1为听众
            fun isOnline(): Boolean {
                return online == 1
            }

            fun hasVoice(): Boolean {
                return muteMask == 0
            }

            val isOnScreen: Boolean
                get() = vMPState == 1

        }
    }

}


class ConferenceEvent {
    var eventId = 0 //事件Id

    var accountId = -1 //自身的sipAccountId;

    var callId = -1 //自身的callIdId;

    var account: String? = null //  账户

    var isActive = false

    var roomDescribe = "" //房间的描述信息

    var initiator = "" //发起者信息

    var dataType = 2 //2 默认是 sip 3 听众 4 固话

    var isSupportVideo = true //是否支持视频，根据sdk流区分

    var videoUrl = "" //听众视频路径，可以加载主讲人视频或音频（直播）

    var isAudio = false //听众成员收到消息时 ，显示音频或者视频   true 音频  false 视频

    var conferenceMode = false  //听众成员收到消息时,显示的会议模式  true 主席  false 自由

    override fun toString(): String {
        return "SipEventBean(eventId=$eventId, accountId=$accountId', callId=$callId, account=$account, isActive=$isActive, roomDescribe='$roomDescribe', initiator='$initiator')"
    }
}

class ConferenceRecordInfo {
    /**
     * private Long id;
     * conferenceName;        //会议的名称
     * psourceName;            //发起者
     * attendTime;            //加入会议开始时间
     * leaveTime;               //离开会议时间
     *  rtsp;                //rstp视频流
     * displayNum;         //设备号
     * conferenceType   //1 语音会议  其他 视频会议
     */
    var id: Long = 0
    var conferenceName: String = ""
    var sourceName: String = ""
    var attendTime: String = ""
    var leaveTime: String = ""
    var rtsp: String = ""
    var displayNum: Int = 0
    var conferenceType = 0

    constructor(
        id: Long,
        conferenceName: String,
        sourceName: String,
        attendTime: String,
        leaveTime: String,
        rtsp: String,
        displayNum: Int,
        conferenceType: Int
    ) {
        this.id = id
        this.conferenceName = conferenceName
        this.sourceName = sourceName
        this.attendTime = attendTime
        this.leaveTime = leaveTime
        this.rtsp = rtsp
        this.displayNum = displayNum
        this.conferenceType = conferenceType
    }

}
/**
 * Created by a dlz on 2022/7/2.
 *
 * 发送服务器消息类
 */
class ConferenceMessage {
    /**
     * 0:服务器断网断电状态
     * 1:设备断网断电状态
     * 2:服务器主动下发的信息,一般有轮询退出和某成员超时挂断
     * 3:声音开关申请关闭，开启轮询，关闭轮询，准备关闭，准备挂断等
     * 4:发言席加入，退出等
     * 5:观众席加入，退出等
     * 6:邀请观众席信息
     */
    /**
     * {
     * "messagetype": 1,   用于查询失败原因
     * "roomname": "video_10000",
     * "roomid": 1111,
     * "membername": "深圳岗厦来邦产业基地 [sip:1000sip2M01A4400031$NLV-120@192.168.1.52:5060;transport=udp]",
     * "message": "closebefore"   对应 消息体
     * }
     */
    var messagetype = 0
    var roomname: String=""
    var roomid = 0
    var membername: String=""
    var message: String=""
    var isAudio: String="0"
    var conferenceMode: String="1"
    var rtmpUrl: String=""
    var roomDescribe: String=""
    var initiator: String=""
    var serverIp: String=""
    var signInNum: String? = null
    override fun toString(): String {
        return "SendMessage{" +
                "messagetype=" + messagetype +
                ", roomname='" + roomname + '\'' +
                ", roomid=" + roomid +
                ", membername='" + membername + '\'' +
                ", message='" + message + '\'' +
                ", isAudio='" + isAudio + '\'' +
                ", conferenceMode='" + conferenceMode + '\'' +
                ", rtmpUrl='" + rtmpUrl + '\'' +
                ", roomDescribe='" + roomDescribe + '\'' +
                ", Initiator='" + initiator + '\'' +
                ", serverIp='" + serverIp + '\'' +
                '}'
    }
}
object ConferenceCommand {

    ///-------------对应SipMessage body-------------------///
    const val FREEMODE = "freemode"                                          //自由模式
    const val DIRECTORMODE = "directormode"                                //导演模式
    const val ATTEND = "attend"                                            //成员加入会议
    const val REFUSE = "refuse"                                            //成员拒绝会议
    const val DISCONNECT = "disconnect"                                     //成员断开会议
    const val QUIT = "quit"                                                //主动退出会议
    const val MESSAGE_CLOSE_BEFORE = "closebefore"                        //关闭会议前告诉所有成员会议将关闭
    const val APPLY_SPEAK_ACCEPT = "applyspeakaccept"                     //成员申请发言同意
    const val APPLY_SPEAK_REFUSE = "applyspeakrefuse"                     //听众申请发言拒绝
    const val APPLY_FOR_SPEAK = "applyspeak"                              //成员申请发言
    const val END_SPEAK = "endspeak"                                      //结束发言
    const val VIDEO_TRUE = "openvideo"                                    //打开视频
    const val VIDEO_FALSE = "closevideo"                                  //关闭视频
    const val MESSAGE_ALL_MEMBER_MUTE = "allmembermute"                  //所有成员禁音消息
    const val MESSAGE_MUTE = "mute"                                      //关闭声音
    const val MESSAGE_UNMUTE = "unmute"                                 //打开声音
    const val MESSAGE_DROP = "drop"                                     //主动挂断
    const val MESSAGE_DROP_BEFORE = "dropbefore"                       //挂断之前告诉挂断的设备，当前的挂断是被主席挂断
    const val SERVER_OUT_DISCONNECT = "lonbon_conferenc_disconnect"    //服务器未知原因断开
    const val TIME_OUT_DISCONNECT = "timeoutdisconnect"               //服务器超时挂断
    const val OPEN_CYCLE = "opencycle"                                //打开轮巡
    const val CLOSE_CYCLE = "closecycle"                               //关闭轮巡
    const val UPDATE_VDIEO_CYCLE = "updatevideocycle"                  //跟新视频轮询
    const val INVITE_MEMBER = "invitmember"                            //邀请成员，发送服务器IP，点对点发送

    const val INVITE_AUDIENCE = "inviteaudience"                       //邀请听众，点对点发送
    const val INVITE_AUDIENCE_ATTEND = "attendlive"                     //邀请听众接受
    const val INVITE_AUDIENCE_REFUSE = "attendliverefuse"               //邀请听众拒绝
    const val INVITE_AUDIENCE_QUIT = "quitlive"                         //听众退出
    const val ADUIENCE_APPLY_FOR_SPEAK = "aduienceapplyspeak"            //听众席申请发言
    const val AUDIENCE_APPLY_SPEAK_ACCEPT = "audienceapplyspeakaccept"   //听众席申请同意

    const val OPEN_VIDEO_CAMERA = "openvideocamera"                       //打开视频
    const val LIVE_FORCE_QUIT = "liveforcequit"                         //房间原因，服务器发送听众退出
    const val SIGN_IN = "signin"                                       //签到人数
    const val OVER_8_HOUR_DISCONNECT = "over8hourdisconnect"           //超过8小时所有设备断开
    const val MESSAGE_SPOKESMAN = "spokesman"                         //设置主讲人
    const val DIRECTORMODE_IN_START_FILE = "directormodeinstartfile" //会议分享中设置会议模式


}
