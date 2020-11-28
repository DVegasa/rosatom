package io.github.dvegasa.rosatom.features.main.boss

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.network.YandexApi
import kotlinx.android.synthetic.main.fragment_atom_voice.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class AtomVoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atom_voice, container, false)
    }

    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }

    var toStartPlay = true
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mp = MediaPlayer.create(context, R.raw.welcome)

        btnPlay.setOnClickListener {
            if (toStartPlay) startPlaying()
            else stopPlaying()
            toStartPlay = !toStartPlay
        }

        btnRecord.setOnClickListener {
            startRecording()
        }

        btnStop.setOnClickListener {
            stopRecording()
        }

    }

    private var filename: String = ""
    private var recorder: MediaRecorder? = null
    private fun startRecording() {
        filename =
            "${requireActivity().externalCacheDir!!.absolutePath}/mic${System.currentTimeMillis()}.mp4"

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(filename)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e("ed__", "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        val cb = object : AudioConverter.Companion.Callback {
            override fun onSuccess(file: File) {
                Log.d("ed__", "converted to WAV: " + file.path)
                Toast.makeText(
                    context,
                    "converted for Yandex Speech Kit: " + file.path,
                    Toast.LENGTH_SHORT
                ).show()
                filename = file.path
                sendToYandex()
            }

            override fun onFailure(e: Exception) {
                Log.d("ed__", "failure: ")
                e.printStackTrace()
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        }

        AudioConverter.toYandexType(requireContext(), File(filename), cb)

    }


    private var player: MediaPlayer? = null
    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(filename)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e("ed__", "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    /////////////////
    private fun sendToYandex() {
        val api = YandexApi.create()
        val file = File(filename)
        val ctx = requireContext()
        val contentResolver = ctx.contentResolver
        val uri = Uri.fromFile(file)
        // val type: String = contentResolver.getType(uri)
        Log.d("ed__", "MIME type: ${MediaType.get(getMimeType(uri.toString()))}")

        val body = RequestBody.create(
            MediaType.get(getMimeType(uri.toString())),
            file
        )

        api.stt(
            MultipartBody.Part.createFormData("file", file.name, body),
            format = "lpcm",
            hz = 16000
        ).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                Log.d("ed__", response.message())
            }
        })
    }

    fun getMimeType(url: String?): String {
        var type: String? = null
        val extension: String = MimeTypeMap.getFileExtensionFromUrl(url)
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        return type!!
    }
}