package io.github.dvegasa.rosatom.features.main.boss

import android.content.Context
import cafe.adriel.androidaudioconverter.callback.IConvertCallback
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler
import java.io.File
import java.io.IOException

/**
 * Created by Ed Khalturin @DVegasa
 */
class AudioConverter {
    companion object {
        fun oggToWav(context: Context, file: File, callback: IConvertCallback) {
            val newFile = getNewFile(file)
            val command =
                "ffmpeg -y -i ${file.path} -acodec pcm_s16le -f s16le -ac 1 -ar 16000 ${newFile.path}"

            FFmpeg.getInstance(context)
                .execute(arrayOf(command), object : FFmpegExecuteResponseHandler {
                    override fun onFinish() {
                    }

                    override fun onSuccess(message: String?) {
                        callback.onSuccess(newFile)
                    }

                    override fun onFailure(message: String?) {
                        callback.onFailure(IOException(message))
                    }

                    override fun onProgress(message: String?) {
                    }

                    override fun onStart() {
                    }

                })
        }

        private fun getNewFile(
            originalFile: File
        ): File {
            val f =
                originalFile.path.split("\\.".toRegex()).toTypedArray()
            val filePath =
                originalFile.path.replace(f[f.size - 1], "pcm")
            return File(filePath)
        }
    }
}