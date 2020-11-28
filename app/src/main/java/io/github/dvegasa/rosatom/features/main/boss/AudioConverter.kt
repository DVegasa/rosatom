package io.github.dvegasa.rosatom.features.main.boss

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_CANCEL
import com.arthenica.mobileffmpeg.Config.RETURN_CODE_SUCCESS
import com.arthenica.mobileffmpeg.ExecuteCallback
import com.arthenica.mobileffmpeg.FFmpeg
import java.io.File

/**
 * Created by Ed Khalturin @DVegasa
 */
class AudioConverter {
    companion object {
        interface Callback {
            fun onSuccess(file: File)
            fun onFailure(e: Exception)
        }

        fun toYandexType(context: Context, file: File, callback: Callback) {
            val newFile = getNewFile(file)
            val command =
                // "-y -i ${file.path} -acodec pcm_s16le -f s16le -ac 1 -ar 16000 ${newFile.path}" // без ошибок, но выход без звука
                "-i ${file.path} -ac 2 -f wav ${newFile.path}"

                // "-i ${file.path} ${newFile.path}"

            Log.d("ed__", "command: $command")
            val executionId: Long = FFmpeg.executeAsync(
                command
            ) { _, returnCode ->
                when (returnCode) {
                    RETURN_CODE_SUCCESS -> {
                        Log.i("ed__", "Async command execution completed successfully.")
                        Toast.makeText(context, "FFMPEG: OK", Toast.LENGTH_SHORT).show()
                        callback.onSuccess(newFile)
                    }
                    RETURN_CODE_CANCEL -> {
                        Log.i("ed__", "Async command execution cancelled by user.")
                        Toast.makeText(context, "FFMPEG: failure :(", Toast.LENGTH_SHORT).show()
                        callback.onFailure(Exception(returnCode.toString()))
                    }
                    else -> {
                        Log.i("ed__", "Async command execution failed with returnCode=$returnCode")
                        Toast.makeText(context, "FFMPEG: failure :(", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        private fun getNewFile(
            originalFile: File
        ): File {
            val f =
                originalFile.path.split("\\.".toRegex()).toTypedArray()
            val filePath =
                originalFile.path.replace(f[f.size - 1], "wav")
            return File(filePath)
        }
    }
}