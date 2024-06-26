package com.lb.extend.command



/**
 *@author: baohekang
 *@time: 2024-05-24 11:32
 *@function: 医院对外提供的对讲事件
 **/
data class TalkServiceEvent(val event:String,val eventId:Int)

enum class CallState{
    CALL_PROCESSING,//1
    CALL_RING_BACK,//2
    CALL_MONITOR_TALKING_START,//3
    CALL_TALKING_START,//4
    CALL_TALKING_END,//5
    CALL_REFUSE,//6
    CALL_BUSY,//7
    CALL_FAILED,//8
    CALL_TIME_OUT,//9
    CALL_IN,//10
    CALL_OFFLINE,
    CALL_ENTER_NURSE_STATE,//240
    CALL_EXIT_NURSE_STATE,//241
    CALL_BROADCAST_START,//27
    CALL_BROADCAST_END,//28

}
