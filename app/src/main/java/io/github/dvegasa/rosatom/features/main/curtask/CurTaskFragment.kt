package io.github.dvegasa.rosatom.features.main.curtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.MainActivity
import kotlinx.android.synthetic.main.fragment_cur_task.*

private const val ARG_PARAM1 = "param1"

class CurTaskFragment : Fragment() {
    private var pos: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pos = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cur_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val task = (requireActivity() as MainActivity).taskList!![this.pos!!]
        tv.text = task.title
    }

    companion object {
        @JvmStatic
        fun newInstance(pos: Int) =
            CurTaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, pos)
                }
            }
    }
}