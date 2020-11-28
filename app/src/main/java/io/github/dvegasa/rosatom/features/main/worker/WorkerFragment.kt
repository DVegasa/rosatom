package io.github.dvegasa.rosatom.features.main.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.boss.AtomVoiceFragment
import kotlinx.android.synthetic.main.fragment_boss.*

class WorkerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }


}