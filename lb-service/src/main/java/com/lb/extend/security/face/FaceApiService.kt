package com.lb.extend.security.face

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
     * @param faceArr 人脸信息列表，用于装载人脸信息的返回,详见FaceTracked
     * @param szImgBuf nv21 格式的图像
     * @param nWidth 图像的宽度
     * @param nHeight 图像的高度
     * @param nNumThreads 运行该算法调用的线程数量（在合 理范围内， 数值越大则 cpu 占用越 高，速度越快）
     * @return 人脸检测状态码
     *
     */
    fun faceDetect(
        faceArr: List<FaceTrackedRect>,
        szImgBuf: ByteArray,
        nWidth: Int,
        nHeight: Int,
        nNumThreads: Int
    ): Int

    /**
     * 活体检测
     * 用于检验指定人脸是否为活体
     * @param asResult 活体检测结果，数组。用于装载活体检测结果。判定结果， 0 代表活体，1 代表假体
     * @param szImgBuf bgr 格式的图像
     * @param nWidth 图像的宽度
     * @param nHeight 图像的高度
     * @param FacesRC 需进行活体判定的人脸对应的检测 信息
     * @param nNumThreads 运行该算法调用的线程数量（在合 理范围内， 数值越大则 cpu 占用越 高，速度越快）
     * @return result 活体检测状态码
     */
    fun antiSpoofingProcess(
        asResult: IntArray,
        szImgBuf: ByteArray,
        nWidth: Int,
        nHeight: Int,
        FacesRC: FaceTrackedRect,
        nNumThreads: Int
    ): Int

    /**
     * 人脸特征提取
     * 用于提取指定人脸的表征特征
     * @param featureArray 人脸特征向量,一般512
     * @param featureLen 对应上面数组长度
     * @param szImgBuf nv21格式的图像
     * @param nWidth 图像的宽度
     * @param nHeight 图像的高度
     * @param pFaceRc 需进行特征提取的人脸对应的检测 信息
     * @param nNumThreads 运行该算法调用的线程数量（在合 理范围内， 数值越大则 cpu 占用越 高，速度越快）
     * @return result 人脸特征提取状态码
     */
    fun getFaceFeature(
        featureArray: ByteArray,
        featureLen: Int,
        szImgBuf: ByteArray, nWidth: Int, nHeight: Int, pFaceRc: FaceTrackedRect, nNumThreads: Int
    ): Int

    /**
     * 人脸比对
     * 用于对 2 个人脸特征进行比对，得到相似度。
     * @param fpSimilarity 相似度,float数组,相似度分数
     * @param szFeature1 人脸特征向量 1
     * @param npFeatureLen1 人脸特征向量 1 的长度
     * @param szFeature2 人脸特征向量 2
     * @param npFeatureLen2 人脸特征向量 2 的长度
     * @return result 人脸比对状态码
     */
    fun compareFeature(
        fpSimilarity: FloatArray,
        szFeature1: ByteArray,
        npFeatureLen1: Int,
        szFeature2: ByteArray,
        npFeatureLen2: Int
    ): Int


    /**
     * 人员注册库导入函数
     * 用于一次性加载整个注册库
     * @param personsArray 人员信息库（数组的形态），单个 人信息见 PersonInfo，主要包含 id 和特征。
     * @param personNum 人员数。
     * @return result 人脸库导入状态码
     */
    fun importPersons(personsArray: Array<PersonInfo>, personNum: Int): Int

    /**
     * 清空人员注册库
     * 用于清空人员注册库，一般重置时使用。
     */
    fun clearPersons()

    /**
     * 新增人脸
     * 新增人脸到库
     * @param person   入库的人员信息，单个人信息见 PersonInfo，主要包含 id 和特征
     * @param repThresold 重复注册判断阈值， 当注册库中存 在与输入人员特征相似都超过判断 阈值的，则视为重复注册，将不入 库
     * @return  result 导入单个人脸状态码
     */
    fun addPerson(person: PersonInfo, repThresold: Float): Int

    /**删除指定 id 的人员
     * 用于删除指定 id 的人员
     * @param personId 要求删除的人员 id
     * @return 删除单个人脸状态码
     */
    fun deletePerson(personId: Int): Int

    /**
     * 更新指定人员特征
     * 用于更新指定人员特征。
     * @param person   根据 id 更新对应人员的特征
     * @return  result 人脸数据更新状态码
     */
    fun updatePersonFeature(person: PersonInfo)


    /**
     * 1:N 人脸检索
     * 用于与输入人脸特征相似度超过阈值的 topN。
     * @param featureArray   用于检索的特征向量
     * @param featureLen 用于检索的特征向量长度
     * @param threshold  阈值， 大于该阈值的人员才可能被 输出
     * @param maxNum topN，最多输出大于设定阈值的人 员数
     * @param searchedResultArray  检索结果， 数组，每个成员定义见  PersonSearchInfo（主要是 id 和 相似度）,按照相似度从高到低排序
     * @param resultNum 检索出的人数
     * @return  result 人脸检测状态码
     */
    fun searchPerson(
        featureArray: ByteArray,
        featureLen: Int,
        threshold: Float,
        maxNum: Int,
        searchedResultArray: Array<PersonSearchInfo>,
        resultNum: IntArray
    ): Int
}

data class FaceTrackedRect(
    var nX: Int = 0,  //人脸检测框左上角横坐标
    var nY: Int = 0,  //人脸检测框左上角纵坐标
    var nWidth: Int = 0, //人脸检测框宽度
    var nHeight: Int = 0,//人脸检测框高度
    var nID: Int = 0, //人脸跟踪 ID，仅在流类型为视频 流时生效
    var facePoints: Int = 0 //人脸关键点集， FR_POINT_NUM 为 5
)

data class PersonInfo(
    var personId: Int = 0, //人员 ID（唯一）
    var faceFeaturer: ByteArray //人脸特征值
)

data class PersonSearchInfo(
    var personId: Int = 0, //人员 ID（唯一）
    var similarity: Float = 0f //相似度
)