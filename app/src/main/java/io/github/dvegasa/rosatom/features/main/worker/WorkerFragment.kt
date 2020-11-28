package io.github.dvegasa.rosatom.features.main.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.MainActivity
import io.github.dvegasa.rosatom.features.main.boss.AtomVoiceFragment
import kotlinx.android.synthetic.main.fragment_boss.*
import kotlinx.android.synthetic.main.fragment_worker.*
import kotlinx.android.synthetic.main.fragment_worker.view.*

class WorkerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val list = (requireActivity() as MainActivity).taskList!!

        rvShortTasks.adapter = RvTasksAdapter(list)
        rvShortTasks.layoutManager = LinearLayoutManager(requireContext())
        tvTasksNumber.text = "${list.size} заданий"

        btnGo.setOnClickListener {
            // Открыть первый таск
        }
    }

    fun listLoaded() {
        btnGo.isEnabled = true
    }
}