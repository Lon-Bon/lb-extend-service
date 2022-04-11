package com.lb.extend.security.setting

import com.zclever.ipc.annotation.BindImpl

@BindImpl("com.lonbon.setting_provider.SystemSettingImpl")
interface SystemSettingService {

    /**
     * 设置系统时间
     * @time 从1970年算起的毫秒数
     */
    fun setSystemTime(time: Long)

    /**
     * Sets the volume index for a particular stream.
     * @param streamType The stream whose volume index should be set.
     * @param index The volume index to set. See
     *            {@link #getStreamMaxVolume(int)} for the largest valid value.
     * @see #getStreamMaxVolume(int)
     * @see #getStreamVolume(int)
     * @throws SecurityException if the volume change triggers a Do Not Disturb change
     *   and the caller is not granted notification policy access.
     */
    fun setStreamVolume(streamType: Int, index: Int)

    /**
     * Returns the current volume index for a particular stream.
     *
     * @param streamType The stream type whose volume index is returned.
     * @return The current volume index for the stream.
     * @see #getStreamMaxVolume(int)
     * @see #setStreamVolume(int, int, int)
     */
    fun getStreamVolume(streamType: Int, index: Int)

    /**
     * Returns the maximum volume index for a particular stream.
     *
     * @param streamType The stream type whose maximum volume index is returned.
     * @return The maximum valid volume index for the stream.
     * @see #getStreamVolume(int)
     */
    fun getStreamMaxVolume(streamType: Int)

    /**
     * Returns the minimum volume index for a particular stream.
     * @param streamType The stream type whose minimum volume index is returned. Must be one of
     *     {@link #STREAM_VOICE_CALL}, {@link #STREAM_SYSTEM},
     *     {@link #STREAM_RING}, {@link #STREAM_MUSIC}, {@link #STREAM_ALARM},
     *     {@link #STREAM_NOTIFICATION}, {@link #STREAM_DTMF} or {@link #STREAM_ACCESSIBILITY}.
     * @return The minimum valid volume index for the stream.
     * @see #getStreamVolume(int)
     */
    fun getStreamMinVolume(streamType: Int)


}