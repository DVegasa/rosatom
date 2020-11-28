package io.github.dvegasa.rosatom.features.main.boss

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.fragment_atom_voice.*

class AtomVoiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_atom_voice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mp = MediaPlayer.create(context, R.raw.welcome)
        btnPlay.setOnClickListener {
            mp.start()
        }
        mp.setOnCompletionListener {
            mp.release()
        }
    }
}