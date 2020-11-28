package io.github.dvegasa.rosatom.features.main.boss

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import cafe.adriel.androidaudioconverter.AndroidAudioConverter
import cafe.adriel.androidaudioconverter.callback.IConvertCallback
import cafe.adriel.androidaudioconverter.model.AudioFormat
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.network.YandexApi
import kotlinx.android.synthetic.main.fragment_atom_voice.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.lang.Exception

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
            "${requireActivity().externalCacheDir!!.absolutePath}/mic${System.currentTimeMillis()}.3gp"

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(filename)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

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
        sendToYandex()

//        val cb = object : IConvertCallback {
//            override fun onSuccess(convertedFile: File?) {
//                Log.d("ed__", "converted to WAV: " + convertedFile!!.path)
//                Toast.makeText(
//                    context,
//                    "converted to WAV: " + convertedFile.path,
//                    Toast.LENGTH_SHORT
//                ).show()
//                filename = convertedFile.path
//            }
//
//            override fun onFailure(error: Exception?) {
//                Log.d("ed__", "failure: ")
//                error!!.printStackTrace()
//                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        AudioConverter.oggToWav(requireContext(), File(filename), cb)

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
        val body = RequestBody.create(
            MediaType.parse(requireContext().contentResolver.getType(file.toUri())!!),
            file
        )

        api.stt(body).enqueue(object : Callback<ResponseBody> {
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
}