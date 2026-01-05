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
 *   ucCardSSN:0F4D42957A4368AC:
 *   ucCardASN:00018899609C0E3A:
 *   ucPsamSN:AA010001889960019804-
 *   cStudentID:3139393639393030323000000000000000000000
 *   cChineseName:'CDF5BDA8EABF0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
 *   cEnglishName:57616E67204A69616E78696E0000000000000000000000000000000000000000000000000000000000000000000000000000
 *   cDeptCode:00000000000015:
 *   ulLogicCardNo:0013418B;
 *   cCardValidPeriod:20430331:
 * }
 */
data class QHCardInfo(
    val ucCardSSN: String,          // 校园卡安全序列号
    val ucCardASN: String,         // 校园卡应用序列号
    val ucPsamSN: String,       // PASM卡应用序列号
    val cStudentID: String,       // 学工号  ASC11码
    val cChineseName: String,          // 中文姓名   GBK编码
    val cEnglishName: String,          // 英文姓名   ASC11码
    val cDeptCode: String,         // 单位代码  BCD编码
    val ulLogicCardNo: String,      // 逻辑卡号  四个字节转整形数字，范围1000000至2000000
    val cCardValidPeriod: String,         // 校园卡有效日期  BCD编码
)
