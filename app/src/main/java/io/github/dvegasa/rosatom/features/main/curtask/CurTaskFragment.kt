package io.github.dvegasa.rosatom.features.main.curtask

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.worker.Task
import kotlinx.android.synthetic.main.fragment_cur_task.*


private const val ARG_PARAM1 = "param1"

class CurTaskFragment : DialogFragment() {
    private var taskJsoned: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskJsoned = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cur_task, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            dialog.window
                ?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val task = Gson().fromJson(taskJsoned, Task::class.java)
        tv.text = task.title
    }

    companion object {
        @JvmStatic
        fun newInstance(taskJsoned: String) =
            CurTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, taskJsoned)
                }
            }
    }
}