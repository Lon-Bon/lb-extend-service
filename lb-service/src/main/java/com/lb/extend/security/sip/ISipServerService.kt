package com.lb.extend.security.sip

import com.lb.extend.security.intercom.LocalDeviceInfo
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
 * @Package:      com.lonbon.intercom_provider.sip
 * @ClassName: ISipServer
 * @Author：    neo
 * @Create:    2022/6/1
 * @Describe: sip 接口
 */
@BindImpl("com.lonbon.intercom_provider.sip.ISipServerServiceImpl")
interface ISipServerService {

    /**
     * sip 相关事件回调
     * @param callBack Result<SipEvent>
     */
    fun onSipEvent(callBack: Result<SipEvent>)

    /**
     * 获取sip注册账号
     * @return String
     */
    fun getSipUsername():String

    /**
     * 获取sip账号 显示名
     * @return String
     */
    fun getSipDisplayName():String

    /**
     *
     * @return Boolean -1 注销成功 0 注册失败 1 注册成功
     */
    fun isSipRegisterState():Int


    /**
     * sip 呼叫
     * @param sipNum String sip账号
     * @return String 异常的提示
     * @param dataType Int 设备 类型 2 默认是 sip 3 手机号码 4 固话
     */
    fun sipCall(sipNum:String,dataType:Int):String

    /**
     * sip接听
     * @param sipNum String sip账号
     * @param dataType Int 设备 类型 2 默认是 sip 3 手机号码 4 固话
     * @return String
     */
    fun sipAnswer(sipNum:String,dataType:Int):String

    /**
     * sip挂断
     * @param sipNum String sip账号
     * @param dataType Int 设备 类型 2 默认是 sip 3 手机号码 4 固话
     * @return String
     */
    fun sipHangup(sipNum:String,dataType:Int):String

    /**
     * sip 语音呼叫
     * @param sipNum String sip账号
     * @return String 异常的提示
     * @param dataType Int 设备 类型 2 默认是 sip 3 手机号码 4 固话
     */
    fun sipAudioCall(sipNum:String,dataType:Int):String


}

class SipEvent{
    var eventId = 0 //事件Id

    var accountId = -1 //自身的sipAccountId;


    var callId = -1 //自身的callIdId;


    var mParam2:String? = null //  sipAccount

    var account: String? = null //  账户

    var otherAccount: String? = null // 账户  account ，otherAccount为相同的字符串

    var isActive = false

    var roomDescribe = "" //房间的描述信息

    var initiator = "" //发起者信息

    var dataType = 2 //2 默认是 sip 3 手机号码 4 固话

    var isSupportVideo = true //是否支持视频

    override fun toString(): String {
        return "SipEventBean(eventId=$eventId, accountId=$accountId, mParam2='$mParam2', callId=$callId, account=$account, otherAccount=$otherAccount, isActive=$isActive, roomDescribe='$roomDescribe', initiator='$initiator')"
    }


}
