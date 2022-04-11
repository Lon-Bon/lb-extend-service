package com.lb.extend.security.temperature

import com.lb.extend.common.CallbackData
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.temperature_provider.TemperatureMeasurementServiceImpl")
interface TemperatureMeasurementService {

    /**
     * 启动测温模块（同步方法），进入界面时可调用
     * @return Int Int 0：成功，其它值失败
     */
    fun syncStart(): Int

    /**
     * 停止测温模块（同步方法），离开界面时可调用
     * @return Int 0：成功，其它值失败
     */
    fun syncStop(): Int

    /**
     * 测温回调
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取TemperatureData，TemperatureData的temperature是浮点型的温度值；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setTemperatureDataCallBack(callBack: Result<CallbackData<TemperatureData>>)
}

data class TemperatureData(val temperature: Float)
