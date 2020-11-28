package io.github.dvegasa.rosatom.features.main.boss

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.fragment_atom_voice.*
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
        filename = "${requireActivity().externalCacheDir!!.absolutePath}/mic${System.currentTimeMillis()}.3gp"

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
}