package io.github.dvegasa.rosatom.features.main.bosscreate

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.network.DanielApi
import io.github.dvegasa.rosatom.network.Task
import kotlinx.android.synthetic.main.fragment_boss_create.*
import kotlinx.android.synthetic.main.fragment_file_picker.view.*
import kotlinx.android.synthetic.main.item_file.view.*
import kotlinx.android.synthetic.main.item_human.view.*
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*
import java.util.Collections.list
import kotlin.collections.ArrayList

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

    var hour: String? = null
    var min: String? = null
    var year: String? = null
    var month: String? = null
    var day: String? = null

    var filesList: ArrayList<String> = arrayListOf()
    var humanList: ArrayList<Human> = arrayListOf()
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tvChooseDateTime.setOnClickListener {
            MyDatePicker(DatePickerDialog.OnDateSetListener { datePicker: DatePicker, y: Int, m: Int, d: Int ->
                year = if (y < 10) "0$y" else "$y"
                month = if (m < 10) "0$m" else "$m"
                day = if (d < 10) "0$d" else "$d"

                MyTimePicker(TimePickerDialog.OnTimeSetListener { timepicker, h, mi ->
                    hour = if (h < 10) "0$h" else "$h"
                    min = if (mi < 10) "0$mi" else "$mi"

                    tvCaptionDeadline.visibility = View.VISIBLE
                    tvChooseDateTime.text = "$day.$month.$year $hour:$min (изменить)"
                }).show(childFragmentManager, null)
            }).show(childFragmentManager, null)
        }


        tvChooseFiles.setOnClickListener {
            FilePickerFragment(object : FilePickerFragment.Callback {
                override fun picked(s: String) {
                    filesList.add(s)
                    val x = LayoutInflater.from(requireContext()).inflate(R.layout.item_file, llFiles, false)
                    x.tv.text = s
                    llFiles.addView(x)
                }
            }).show(childFragmentManager, null)
        }

        tvChooseHuman.setOnClickListener {
            HumanPickerFragment(object : HumanPickerFragment.Callback {
                override fun picked(h: Human) {
                    humanList.add(h)
                    val x = LayoutInflater.from(requireContext()).inflate(R.layout.item_human, llHumans, false)
                    x.ivAva.setImageResource(h.avaId)
                    x.tvName.text = h.name
                    x.tvPosition.text = h.position
                    llHumans.addView(x)
                }
            }).show(childFragmentManager, null)
        }

        btnCreateTask.setOnClickListener {
            Log.d("ed__", "hour " + hour)
            Log.d("ed__", "min " + min)
            Log.d("ed__", "year " + year)
            Log.d("ed__", "month " + month)
            Log.d("ed__", "day " + day)
            val api = DanielApi.create()

            val workerIds = arrayListOf<Int>()
            for (h in humanList) {
                workerIds.add(h.id)
            }

            val linkeds = arrayListOf<String>()
            for (f in filesList) {
                linkeds.add(f)
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sdf.format(Date())
            val dline: String = "$year-$month-$day $hour:$min:00.000000"

            api.uploadTask(Task(
                title = tvTitle.text.toString(),
                desc = etDesc.text.toString(),
                workerId = workerIds,
                createdById = 10,
                contractIds = emptyList(),
                linked = linkeds,
                deadline = dline
            )).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("ed__", "failure")
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: retrofit2.Response<ResponseBody>
                ) {
                    Log.d("ed__", "ok")
                    if (response.code() in 200..299) {
                        Toast.makeText(requireContext(), "Отправлено", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            })
        }
    }
}