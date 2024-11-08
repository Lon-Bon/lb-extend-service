package com.lb.extend.security.fingerprint

import com.zclever.ipc.annotation.BindImpl

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 * @ProjectName: lb-extend-service
 * @Package: com.lb.extend.security.fingerprint
 * @ClassName: FingerprintApiService
 * @Description: 广州高新兴指纹识别接口定制
 * @Author: ZHW
 * @Date: 2024/11/7 下午5:57
 */
@BindImpl("com.lonbon.fingerprint_provider.FingerprintApiServiceImpl")
interface FingerprintApiService {

    /**
     * 根据本地指纹图片注册指纹
     * 传入一张指纹图片路径，注册指纹
     * @param path 指纹路径
     * @param feature 指纹特征值提取返回结果，直接传new byte[512]
     * @return  0 成功，1 设备未打开，2 提取失败，3其他
     */
    fun autoRegisterFromFile(path: String, feature: ByteArray): Int

    /**
     * 指纹1：1比对接口
     * 指纹识别
     * @param path1 指纹1路径
     * @param path2 指纹2路径
     * @param similarity 相似度
     * @return 0 成功，1 设备未打开，2 未注册，3识别失败
     */
    fun fringerCompare(path1: String, path2: String, similarity: IntArray): Int

    /**
     * 打开指纹模块
     * @return 0打开成功，-1打开失败
     */
    fun openFinger(): Int

    /**
     * 关闭指纹模块
     * @return 0成功，-1失败
     */
    fun closeFinger(): Int

    /**
     * 指纹特征采集
     * @param img 用于装载采集返回的图片数据
     * @return 0成功，1设备未打开，2采集失败
     */
    fun fingerImgCollect(img: ByteArray):Int

    /**
     *  指纹探测
     *  用于监测指纹是否按压
     *  @return 0未按压指纹，1设备未打开，2检测到指纹按压
     */
    fun  isFingerPress():Int


    /**
     * 指纹特征值创建接口
     * @param img 指纹图片数据
     * @param feature 根据图片数据生成的指纹特征结果
     * @return 0成功，1设备未打开，2生成失败
     */
    fun fingerFeatureCreat(img: ByteArray, feature: ByteArray): Int


    /**
     * 指纹注册， 指纹数据导入
     * @param feature 指纹特征数据
     * @param fingerId 返回录入成功后的指纹ID结果，传的时候new int[1]
     * @param score 相似度判断分数
     * @return 0成功，1设备未打开，2注册已达上限，3注册失败
     */
    fun addFinger(feature: ByteArray, fingerId: IntArray, score: Float): Int

    /**
     * 开始指纹识别
     * @param fingerId 返回录入成功后的指纹ID结果，传的时候new int[1]
     * @param score 置信度，作为识别结果的置信条件，0到1
     * @return 0成功，1设备未打开，2识别失败
     */
    fun starFingerRec(fingerId: IntArray, score: Float): Int

    /**
     * 清空指纹数据
     * @return 0成功，-1失败
     */
    fun clearFingerFeature(): Int

    /**
     * 指纹删除
     * @param fingerId 指纹id
     * @return 0成功，1设备未打开，2失败
     */
    fun deleteFingerFeature(fingerId: String): Int

}