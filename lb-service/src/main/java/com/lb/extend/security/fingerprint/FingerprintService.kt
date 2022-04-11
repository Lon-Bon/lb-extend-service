package com.lb.extend.security.fingerprint

import com.lb.extend.common.CallbackData
import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.fingerprint_provider.FingerprintServiceImpl")
interface FingerprintService {

    /**
     * 启动指纹模块（同步方法），进入界面时可调用
     * @return Int Int 0：成功，其它值失败
     */
    fun start(): Int

    /**
     * 停止指纹模块（同步方法），离开界面时可调用
     * @return Int 0：成功，其它值失败
     */
    fun stop(): Int

    /**
     * 指纹采集（同步方法）
     * @param id Int 指纹id
     * @return Int 0：调用成功，其它值失败。指纹id和采集到的特征值通过setGetFingerprintFeatureCallBack设置的回调返回
     */
    fun fingerprintCollect(id: Int): Int

    /**
     * 指纹特征值入库（同步方法）
     * @param id Int 指纹id
     * @return Int 0：调用成功，其它值失败。指纹id的入库的特征值通过setGetFingerprintFeatureCallBack设置的回调返回
     */
    fun fingerprintFeatureInput(id: Int, feature: String): Int

    /**
     * 指纹id和特征值的回调，指纹采集和指纹特征值入库的结果通过此CallBack返回
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取GetFingerprintFeatureResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setGetFingerprintFeatureCallBack(callBack: Result<CallbackData<GetFingerprintFeatureResult>>)

    /**
     * 指纹采集过程回调，每一枚指纹采集完成都会回调，leftCounts为0是则代表全部指纹采集完成。
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取GetFingerprintFeatureLeftNumResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。（失败用不到）
     */
    fun setGetFingerprintFeatureLeftNumCallBack(callBack: Result<GetFingerprintFeatureLeftNumResult>)


    /**
     * 指纹比对回调
     * @param callBack 根据返回的CallbackData的code来判断是否成功（0成功，其他值都是失败），
     * 如果成功，则通过CallbackData的data获取GetGetCompareFingerResult；
     * 如果失败，则通过CallbackData的code来判断失败类型，CallbackData的msg会写明失败原因。
     */
    fun setGetCompareFingerprintCallBack(callBack: Result<GetGetCompareFingerResult>)


}

/**
 * 指纹特征值入库和指纹特征值入库的回调结果
 * @property id int 指纹id
 * @property feature String 指纹特征值
 * @constructor
 */
data class GetFingerprintFeatureResult(val id: Int, val feature: String)

/**
 * 指纹录入剩余未采集指纹数量的回调结果
 * @property leftCounts Int 差几个指纹没录入（如果leftCounts为0，则会回调GetFingerprintFeatureResult）
 * @property fingerprintBase64Str String? 当前录入指纹的图片的base64的字符串（暂未支持）
 * @constructor
 */
data class GetFingerprintFeatureLeftNumResult(
    val leftCounts: Int,
    val fingerprintBase64Str: String?
)

/**
 * 指纹比对结果
 * @property id Int 指纹id
 * @property feature String 指纹特征值
 * @property fingerprintBase64Str String? 当前识别的指纹的图片的base64的字符串（暂未支持）
 * @constructor
 */
data class GetGetCompareFingerResult(
    val id: Int,
    val feature: String,
    val fingerprintBase64Str: String?
)
