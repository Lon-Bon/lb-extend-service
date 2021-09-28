package com.lb.extend.command

object EventCommand {

    /**
     * 呼叫按钮按下 呼叫按钮弹起
     */
    val LB_BTN_CALL_DOWN = "btn_call_down"
    val LB_BTN_CALL_UP = "btn_call_up"

    /**
     * 喊话按钮按下 呼叫按钮弹起
     */
    val LB_BTN_TALK_DOWN = "btn_talk_down"
    val LB_BTN_TALK_UP = "btn_talk_up"

}

/**
 * NNV-A10设备命令
 */
object NNV_A10Command {
    /**
     * 手持设备 呼叫按钮按下 呼叫按钮弹起
     */
    const val LB_H_BTN_CALL_DOWN = "h_btn_call_down"
    const val LB_H_BTN_CALL_UP = "h_btn_call_up"

    /**
     * 手持设备 频道+按钮按下 呼叫按钮弹起
     */
    const val LB_H_BTN_ADD_CHANNEL_DOWN = "h_btn_add_channel_down"
    const val LB_H_BTN_ADD_CHANNEL_UP = "h_btn_add_channel_up"

    /**
     * 手持设备 频道-按钮按下 呼叫按钮弹起
     */
    const val LB_H_BTN_SUB_CHANNEL_DOWN = "h_btn_sub_channel_down"
    const val LB_H_BTN_SUB_CHANNEL_UP = "h_btn_sub_channel_up"

    /**
     * 手持设备 音量+按钮按下 呼叫按钮弹起
     */
    const val LB_H_BTN_ADD_VOLUME_DOWN = "h_btn_add_volume_down"
    const val LB_H_BTN_ADD_VOLUME_UP = "h_btn_add_volume_up"

    /**
     * 手持设备 音量-按钮按下 呼叫按钮弹起
     */
    const val LB_H_BTN_SUB_VOLUME_DOWN = "h_btn_sub_volume_down"
    const val LB_H_BTN_SUB_VOLUME_UP = "h_btn_sub_volume_up"

    /**
     * 手持设备 呼叫护工 呼叫按钮弹起
     */
    const val LB_H_BTN_CALL_CARER_DOWN = "h_btn_call_carer_down"
    const val LB_H_BTN_CALL_CARER_UP = "h_btn_call_carer_up"

    /**
     * 手持设备 换药提醒 呼叫按钮弹起
     */
    const val LB_H_BTN_MEDICINE_REMIND_DOWN = "h_btn_medicine_remind_down"
    const val LB_H_BTN_MEDICINE_REMIND_UP = "h_btn_medicine_remind_up"

    /**
     * 手持设备 确定 呼叫按钮弹起
     */
    const val LB_H_BTN_SURE_DOWN = "h_btn_sure_down"
    const val LB_H_BTN_SURE_UP = "h_btn_sure_up"
}