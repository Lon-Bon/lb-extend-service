package com.lb.extend.security.face

import android.graphics.Rect
import com.zclever.ipc.annotation.BindImpl

/**
 * *****************************************************************************
 * <p>
 * Copyright (C),2007-2016, LonBon Technologies Co. Ltd. All Rights Reserved.
 * <p>
 * *****************************************************************************
 * @ProjectName: lb-extend-service
 * @Package: com.lb.extend.security.face
 * @ClassName: FaceApiService
 * @Description: 广州高新兴人脸识别接口定制
 * @Author: ZHW
 * @Date: 2024/11/7 下午5:43
 */
@BindImpl("com.lonbon.face_provider.FaceApiServiceImpl")
interface FaceApiService {
    /**
     * 打开人脸识别
     * 启动人脸识别，用于初始化该软件的进程资源，建议在进程开始时使用。
     * @param licensePath 授权文件
     * @param result 初始化状态码
     * @return result 初始化
     */
    fun init(licensePath: String? = null): Int

    /**
     * 关闭人脸识别，用于释放该软件的进程资源， 建议在进程结束时使用
     * @return result 人脸引擎释放状态码
     */
    fun cleanUp(): Int

    /**
     * 创建人脸识别器实例函数
     *只有成功创建并获得实例 handle 后才进行人脸检测、特征提取、活体检测、库管理等功能
     * @param szModelPath 模型所在文件夹的路径
     * @return result 创建状态码
     */
    fun create(szModelPath: String? = null): Int

    /**
     * 销毁人脸识别器实例函数
     * 销毁人脸识别器实例函数
     * @return result 销毁状态码
     */
    fun release(): Int
    /**
     * 人脸检测
     * 用于检测指定图像中的人脸位置等信息
     * @param frame 人脸信息列表，用于装载人脸信息的返回,详见FaceTracked
     * @param frameWidth 图像的宽度
     * @param frameHeight 图像的高度
     * @param mode 运行该算法调用的线程数量（在合 理范围内， 数值越大则 cpu 占用越 高，速度越快）
     * @return 人脸检测状态码
     *
     */
    fun faceDetect(
        frame: ByteArray,
        frameWidth: Int,
        frameHeight: Int,
        mode: DetectMode
    ):FaceDetectInfo

    /**
     * 检测活体支持
     * 用于检验指定人脸是否为活体
     * @param frame nv21帧数据
     * @param width 宽
     * @param height 高
     * @param faceRect 帧图像里面的人脸区域
     */
    fun antiSpoofingProcess(
        frame: ByteArray,
        width: Int,
        height: Int,
        faceRect: Rect
    ): Float = 0f

    /**
     * 人脸特征提取
     *  用于提取指定人脸的表征特征
     *  @param featureArray 人脸特征向量,一般512
     * 传入帧图像，获取人脸特征值，有的人脸识别算法可能不支持
     *
     */
    fun getFaceFeature(
        frame: ByteArray,
        width: Int,
        height: Int
    ): List<FaceDetail> = mutableListOf()

    /**
     * 比较两个人脸特征值
     * @return 返回相似度，范围0-1
     */
    fun compareFaceFeature(faceFeature1: String, faceFeature2: String): Float = 0f

    /**
     * 人员注册库导入函数
     * 用于一次性加载整个注册库
     */
    fun importFace(faceFeature: String, uniqueCode: String): Boolean = false

    /**
     * 新增人脸
     * 新增人脸到库
     * @return  result 导入单个人脸状态码
     */
    fun addPerson(faceFeature: String, uniqueCode: String): Boolean = false

    /**
     * 更新指定人员特征
     * 用于更新指定人员特征。
     * @param person   根据 id 更新对应人员的特征
     * @return  result 人脸数据更新状态码
     */
    fun updatePersonFeature(faceFeature: String, uniqueCode: String): Boolean = false

    /**
     * 清空人员注册库
     * 用于清空人员注册库，一般重置时使用。
     */
    fun clearPersons()

    /**
     * 根据唯一编码清除人脸
     * @param uniqueCode 人脸唯一编号
     */
    fun deletePerson(uniqueCode: String): Boolean


    /**
     * 传入特征码，查找相似度超过阈值 threshold 的人脸
     */
    fun searchFace(searchFeature: String, threshold: Float): List<FaceSearchResult> =
        mutableListOf()
}


data class FaceDetail(val faceRect: Rect, val feature: String)

data class FaceSearchResult(val uniqueCode: String, val similarity: Float)
enum class DetectMode {

    DEFAULT,//默认检测
    ENROLL_DETECT,//录入检测
    RECOGNIZE_DETECT//识别检测

}

/**
 * 人脸节点信息，主要是检测人脸返回的信息
 */
data class FaceDetectInfo(
    val faceDetectDetails: MutableList<FaceDetectDetail>,
    val param: Any? = null
) {

}

/**
 * 人脸检测的详细信息
 */
data class FaceDetectDetail(val faceRect: Rect, val param: Any? = null) {


}