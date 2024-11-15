package com.lb.extend.security.fingerprint

import com.lb.extend.common.CallbackData
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.fingerprint_provider.FingerprintServiceImpl")
interface FingerprintService {

    /**
     * 初始化指纹模块，可以简单理解为给指纹模块上电，（同步方法），初始化后才能使用，一般是进入界面后调用
     */
    fun init()

    /**
     * 指纹采集（同步方法），指纹id和采集到的特征值通过setFingerprintFeatureCallBack设置的回调返回
     * @param id String 指纹id
     */
    fun fingerprintCollect(id: String)

    /**
     * 指纹特征值入库（同步方法），指纹id的入库的特征值通过setFingerprintFeatureCallBack设置的回调返回
     * @param id String 指纹id
     */
    fun fingerprintFeatureInput(id: String, feature: String)

    /**
     * 指纹识别（同步方法）
     */
    fun fingerprintRecognition()

    /**
     * 停止指纹录入或指纹识别，如果不调用指纹模块会一直在工作，一般是在结果回调或离开界面时调用
     */
    fun stop()

    /**
     * 释放资源，可以简单理解为停止给指纹模块供电（同步方法），离开界面后调用
     */
    fun destroy()

    /**
     * 指纹id和特征值的回调，指纹采集和指纹特征值入库的结果通过此CallBack返回
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取FingerprintFeatureResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setFingerprintFeatureCallBack(callBack: Result<CallbackData<FingerprintFeatureResult>>)

    /**
     * 指纹采集过程回调，每一枚指纹采集完成都会回调，leftCounts为0是则代表全部指纹采集完成。
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取GetFingerprintFeatureLeftNumResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。（失败用不到）
     */
    fun setFingerprintLeftNumCallBack(callBack: Result<CallbackData<FingerprintLeftNumResult>>)

    /**
     * 指纹比对回调
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取FingerprintCompareResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setFingerprintCompareCallBack(callBack: Result<CallbackData<FingerprintCompareResult>>)

    /**
     * 根据人员id清除本地指纹存储信息
     * @param id String
     */
    fun clearFingerprintById(id: String)

    /**
     * 根据指纹特征值清除本地指纹存储信息
     * @param feature String
     */
    fun clearFingerprintByFeature(feature: String)

    /**
     * 清空本地所有指纹存储信息
     */
    fun clearAllFingerprint()
    /**
     * 指纹特征值创建接口
     * @param img 指纹图片数据
     * @param feature 根据图片数据生成的指纹特征结果
     * @return 0成功，1设备未打开，2生成失败
     */
    fun fingerFeatureCreat(img: ByteArray, feature: ByteArray)

    /**
     * 回调指纹特征值创建
     */
    fun onFingerFeatureCreat(callBack: Result<ByteArray>)
    /**
     * 根据本地指纹图片注册指纹
     * 传入一张指纹图片路径，注册指纹
     * @param path 指纹路径
     * @param feature 指纹特征值提取返回结果，直接传new byte[512]
     * @return  0 成功，1 设备未打开，2 提取失败，3其他
     */
    fun autoRegisterFromFile(path: String, feature: ByteArray)
    /**
     * 回调图片注册指纹
     */
    fun onAutoRegisterFromFile(callBack: Result<ByteArray>)
    /**
     * 指纹1：1比对接口
     * 指纹识别
     * @param path1 指纹1路径
     * @param path2 指纹2路径
     * @param similarity 相似度
     * @return 0 成功，1 设备未打开，2 未注册，3识别失败
     */
    fun fringerCompare(path1: String, path2: String, similarity: FloatArray)

    /**
     * 回调指纹1：1比对接口
     */
    fun onFringerCompare(callBack: Result<Float>)

    /**
     *  指纹探测
     *  用于监测指纹是否按压
     *  @return 0未按压指纹，1设备未打开，2检测到指纹按压
     */
    fun  isFingerPress()
    /**
     * 回调指纹1：1比对接口
     */
    fun onIsFingerPress(callBack: Result<Boolean>)
}

/**
 * 指纹特征值入库和指纹特征值入库的回调结果
 * @property id String 指纹id
 * @property feature String 指纹特征值
 * @constructor
 */
data class FingerprintFeatureResult(val id: String, val feature: String, val fingerprintBase64Str: String?)

/**
 * 指纹录入剩余未采集指纹数量的回调结果
 * @property leftCounts Int 差几个指纹没录入（如果leftCounts为0，则会回调FingerprintFeatureResult）
 * @property feature String 指纹特征值
 * @property fingerprintBase64Str String? 当前录入指纹的图片的base64的字符串（暂未支持）
 * @constructor
 */
data class FingerprintLeftNumResult(
    val leftCounts: Int,
    val feature: String,
    val fingerprintBase64Str: String?
)

/**
 * 指纹比对结果
 * @property id String 指纹id
 * @property feature String 指纹特征值
 * @property fingerprintBase64Str String? 当前识别的指纹的图片的base64的字符串（暂未支持）
 * @constructor
 */
data class FingerprintCompareResult(
    val id: String,
    val feature: String,
    val fingerprintBase64Str: String?
)
