package com.islam.youtubeplayercore.player.options

import org.json.JSONException
import org.json.JSONObject

/**
 * Options used to configure the IFrame Player. All the options are listed here:
 * [IFrame player parameters](https://developers.google.com/youtube/player_parameters#Parameters)
 */
class IFramePlayerOptions private constructor(private val playerOptions: JSONObject) {

    companion object {
        val default = Builder().build()
    }

    override fun toString(): String {
        return playerOptions.toString()
    }

    class Builder {
        companion object {
            private const val AUTO_PLAY = "autoplay"
            private const val CONTROLS = "controls"
            private const val ENABLE_JS_API = "enablejsapi"
            private const val FS = "fs"
            private const val ORIGIN = "origin"
            private const val REL = "rel"
            private const val SHOW_INFO = "showinfo"
            private const val IV_LOAD_POLICY = "iv_load_policy"
            private const val MODEST_BRANDING = "modestbranding"
        }

        private val builderOptions = JSONObject()

        init {
            addInt(AUTO_PLAY, 0)
            addInt(CONTROLS, 0)
            addInt(ENABLE_JS_API, 1)
            addInt(FS, 0)
            addString(ORIGIN, "https://www.youtube.com")
            addInt(REL, 0)
            addInt(SHOW_INFO, 0)
            addInt(IV_LOAD_POLICY, 3)
            addInt(MODEST_BRANDING, 1)
        }

        fun build(): IFramePlayerOptions {
            return IFramePlayerOptions(builderOptions)
        }

        /**
         * Controls whether the web-based UI of the IFrame player is used or not.
         * @param controls If set to 0: web UI is not used. If set to 1: web UI is used.
         */
        fun controls(controls: Int): Builder {
            addInt(CONTROLS, controls)
            return this
        }

        /**
         * Controls the related videos shown at the end of a video.
         * @param rel If set to 0, related videos will come from the same channel as the video that was just played. If set to 1, related videos will come from multiple channels.
         */
        fun rel(rel: Int): Builder {
            addInt(REL, rel)
            return this
        }

        /**
         * Controls video annotations.
         * @param ivLoadPolicy if set to 1: causes video annotations to be shown by default. If set to 3 causes video annotations to not be shown by default.
         */
        fun ivLoadPolicy(ivLoadPolicy: Int): Builder {
            addInt(IV_LOAD_POLICY, ivLoadPolicy)
            return this
        }

        private fun addString(key: String, value: String) {
            try {
                builderOptions.put(key, value)
            } catch (e: JSONException) {
                throw RuntimeException("Illegal JSON value $key: $value")
            }

        }

        private fun addInt(key: String, value: Int) {
            try {
                builderOptions.put(key, value)
            } catch (e: JSONException) {
                throw RuntimeException("Illegal JSON value $key: $value")
            }

        }
    }
}
