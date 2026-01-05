package com.lb.extend.security.face

import com.zclever.ipc.annotation.BindImpl
import com.zclever.ipc.core.Result

@BindImpl("com.lonbon.face_provider.FaceServiceImpl")
interface FaceService {

    /**
     * 开始录入，跳转录入页面
     */
    fun startEnroll(code: String?)

    /**
     * 关闭录入，关闭录入页面
     */
    fun closeEnroll()

    /**
     * 开始识别，跳转识别页面
     */
    fun startCompare()

    /**
     * 关闭识别，关闭识别页面
     */
    fun closeCompare()

    /**
     * 通过图片录入人脸
     */
    fun enrollFaceByImg(imgPath: String?, code: String?)

    /**
     * 识别一次
     */
    fun compareFace()

    /**
     * 删除人脸，为空则清空人脸数据
     */
    fun deleteFace(code: String?)

    /**
     * 配置识别框位置大小
     */
    fun configureScanRectF(
        leftPercent: Float,
        topPercent: Float,
        widthPercent: Float,
        heightPercent: Float
    )

    /**
     * 开始无界面识别
     */
    fun startCompareWithParams(openPreview: Boolean)

    /**
     * 配置无界面人预览框
     */
    fun configFacePreview(left: Int, top: Int, width: Int, height: Int, hide: Boolean)

    /**
     * 获取已录入人脸编号列表
     */
    fun getFaceCodesList();

    /**
     * 批量录入人脸图片
     */
    fun enrollFaceByImgBatch(enrollMap: Map<String, String>);

    /**
     * 开关人脸活体检测
     */
    fun switchFaceLive(open: Int)

    /**
     * 通过图片识别人脸
     */
    fun faceVerifyByImg(imgPath: String?)

    /**
     * 开启人脸识别页面回调
     */
    fun onStartCompare(callBack: Result<Int>)

    /**
     * 关闭人脸识别回调
     */
    fun onCloseCompare(callBack: Result<Int>)

    /**
     * 配置识别框大小回调
     */
    fun onConfigureFaceScanRectF(callBack: Result<Int>)

    /**
     * 识别一次回调
     */
    fun onFaceCompare(callBack: Result<CompareResult>)

    /**
     * 人脸录入页面结果回调
     */
    fun onFaceEnrollByCamera(callBack: Result<EnrollResult>)

    /**
     * 图片录入结果回调
     */
    fun onFaceEnrollByImg(callBack: Result<CommonFaceResult>)

    /**
     * 删除人脸回调
     */
    fun onFaceDelete(callBack: Result<CommonFaceResult>)

    /**
     * 获取已录入人脸列表回调
     */
    fun onGetFaceCodesListCallBack(callBack: Result<List<String>>)

    /**
     * 批量录入人脸图片回调
     */
    fun onFaceEnrollByImgBatch(callBack: Result<EnrollImgBatchResult>)

    /**
     * 实时回调人脸捕捉矩阵
     */
    fun onFaceDetectRect(callBack: Result<FaceDetectResult>)

    /**
     * 图片识别结果回调
     */
    fun onFaceVerifyByImg(callBack: Result<CommonFaceResult>)

    /**
     * 开关人脸识别提示词
     */
    fun switchFaceVerifyHint(open: Int)
}

const val FACE_SDK_FREE = 0 //人脸SDK空闲可用

const val FACE_SDK_IMAGING = 1 //人脸SDK正在图片录入

const val FACE_SDK_VERIFYING = 2 //人脸SDK正在识别

const val FACE_SDK_INPUTING = 3 //人脸SDK正在拍照录入

const val FACE_SDK_DELETING = 4 //人脸SDK正在删除

const val MSG_CODE_OTHER = -1 //其他错误

const val MSG_CODE_SUCCESS = 0 //成功

const val MSG_CODE_PARAM_INVALID = 1 //入参为空或非法

const val MSG_CODE_SDK_BUSY = 2 //人脸SDK正在进行其他操作，图片录入、人脸录入、识别、删除均不可同时执行，一次只能做一件事


data class CommonFaceResult(val code: String, val msgCode: Int)
data class CompareResult(
    val code: String,
    val verifyState: Int,
    val msgCode: Int,
    val jpegByte: ByteArray? = null
)

data class EnrollResult(val code: String, val msgCode: Int, val imgPathList: String, val jpegByte: ByteArray? = null)
data class EnrollImgBatchResult(val SuccessCodes: List<String>, val msgCode: Int)
data class RectBean(val left: Int, val top: Int, val right: Int, val bottom: Int)
data class FaceDetectResult(val width: Int, val height: Int, val rectList: List<RectBean>)

