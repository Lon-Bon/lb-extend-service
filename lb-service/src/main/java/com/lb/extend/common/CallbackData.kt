package com.lb.extend.common

/**
 * Callback专用的数据
 * @param T
 * @property code Int 0成功，其它失败
 * @property msg String? 失败时的原因
 * @property data T? 具体的数据
 * @constructor
 */
data class CallbackData<T>(var code: Int, var msg: String? = null, var data: T? = null)

object Code {

    //成功
    const val SUCCESS = 0

}

object Msg {

    //成功
    const val SUCCESS = "SUCCESS"
}

