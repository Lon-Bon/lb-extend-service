package com.lb.extend.security.card

import com.lb.extend.common.CallbackData
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.card_provider.SwingCardServiceImpl")
interface SwingCardService {

    /**
     * 启动刷卡模块（同步方法），进入界面时可调用
     */
    fun start()

    /**
     * 停止刷卡模块（同步方法），离开界面时可调用
     */
    fun stop()

    /**
     * 刷卡回调
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取CardData，CardData的cardNum是卡号；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setCardDataCallBack(callBack: Result<CallbackData<CardData>>)

    /**
     * 获取清华国密卡信息，同步方法，结果在setQHCardDataCallBack回调
     */
    fun getQHCardInfo()

    /**
     * 获取清华国密卡信息回调
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取QHCardInfo
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setQHCardDataCallBack(callBack: Result<CallbackData<QHCardInfo>>)

}

data class CardData(val cardNum: String)


/**
 * 清华国密卡信息
 * QHCardInfo{
 *   ucCardSSN:0DEA0BD7A0EF0F25;
 *   cStudentID:2024a001;
 *   cChineseName:wei xiao li;
 *   cEnglishName:Ailx;
 *   cDeptCode:TSG002;
 *   cDeptName:The Second Library;
 *   cValidDate:2028-8-10;
 *   ulLogicCardNo:ab4130;
 *   ucTypeCode:2;
 *   cTypeName:employee;
 *   ucPSAMSN:123;
 *   ucDoorRights:536871532;
 *   cCardValidPeriod:2026-12-30;
 *   usMaxOpenTimes:10000;
 *   usRemainOpenTimes:8868;
 * }
 */
data class QHCardInfo(
    val ucCardSSN: String,          // 安全序列号
    val cStudentID: String,         // 学工号
    val cEnglishName: String,       // 英文姓名
    val cChineseName: String,       // 中文姓名
    val cDeptCode: String,          // 单位代码
    val cDeptName: String,          // 单位名称
    val cValidDate: String,         // 身份有效期
    val ulLogicCardNo: String,      // 逻辑卡号
    val ucTypeCode: String,         // 类型代码
    val cTypeName: String,          // 类型名称
    val ucPSAMSN: String,           // PSAM卡序列号
    val ucDoorRights: String,       // 门禁权限
    val cCardValidPeriod: String,   // 卡片有效期
    val usMaxOpenTimes: String,     // 可开门次数
    val usRemainOpenTimes: String   // 剩余开门次数
)
