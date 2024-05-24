package com.lb.extend.command



/**
 *@author: baohekang
 *@time: 2024-05-24 11:32
 *@function: 医院对外提供的对讲事件
 **/
data class TalkServiceEvent(val event:String,val eventId:Int)

enum class CallState{
    CALL_PROCESSING,
    CALL_RING_BACK,
    CALL_TALKING_START,
    CALL_FAILED,
    CALL_TALKING_END,

}
