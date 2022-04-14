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

}

data class CardData(val cardNum: String)

