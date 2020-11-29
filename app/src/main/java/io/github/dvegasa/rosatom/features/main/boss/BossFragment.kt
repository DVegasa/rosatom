package io.github.dvegasa.rosatom.features.main.boss

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.bosscreate.BossCreateFragment
import kotlinx.android.synthetic.main.fragment_boss.*

class BossFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boss, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<AtomVoiceFragment>(flVoiceContainer.id)
        }

        btnCreateTask.setOnClickListener {
            BossCreateFragment().show(childFragmentManager, null)
        }
    }
}