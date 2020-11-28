package io.github.dvegasa.rosatom.features.main.bosscreate

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.fragment_boss_create.*

class BossCreateFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_boss_create, container, false)
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

    var hour: Int? = null
    var min: Int? = null
    var year: Int? = null
    var month: Int? = null
    var day: Int? = null
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvChooseDateTime.setOnClickListener {
            MyDatePicker(DatePickerDialog.OnDateSetListener { datePicker: DatePicker, y: Int, m: Int, d: Int ->
                year = y
                month = m
                day = d

                MyTimePicker(TimePickerDialog.OnTimeSetListener { timepicker, h, mi ->
                    hour = h
                    min = mi

                    tvCaptionDeadline.visibility = View.VISIBLE
                    tvChooseDateTime.text = "$day.$month.$year $hour:$min (изменить)"
                }).show(childFragmentManager, null)
            }).show(childFragmentManager, null)

        }
    }
}