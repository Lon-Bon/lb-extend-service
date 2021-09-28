package com.lb.extend.manager

import com.lb.extend.command.LonbonEvent
import com.lb.extend.service.SystemSetService
import com.zclever.ipc.core.Result

object SystemSetServiceManager : SystemSetService {

    private var mCallBack: Result<LonbonEvent>? = null

    override fun setEventCallBack(callBack: Result<LonbonEvent>){
        mCallBack = callBack
    }

    fun setEvent(event: String){
        mCallBack?.onData(LonbonEvent(event))
    }

}