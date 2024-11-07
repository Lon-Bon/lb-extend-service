package com.lb.extend.security.gxgxx

/**
 * 广州高新兴 数据实体类包装
 */

data class GSysDevInfo(
    var ethip: String,//设备IP
    var ethNetmask: String, //掩码
    var ethGateWay: String, //网关
    var dnsservers: Array<String>, //DSN服务器
    var staticEnable: Boolean, //是否使用静态IP
    var name: String = "",//设备名
    var serial: String = "" //序列号，唯一
)

data class GHardwareInfo(
    var cpuModel: String,//Cpu型号
    var cpuRate: String, //cpu 使用率/总频率
    var cpuNum: String, //Cpu核数
    var memory: String, //运行内存 已使用/总内存
    var storage: Boolean, //存储空间 已使用/总内存
)

data class GNetWorkInfo(
    var ethip: String,//设备IP
    var ethNetmask: String, //掩码
    var ethGateWay: String, //网关
    var dnsservers: Array<String>, //DSN服务器
    var staticEnable: Boolean, //是否使用静态IP 当为true,以上参数必填
)