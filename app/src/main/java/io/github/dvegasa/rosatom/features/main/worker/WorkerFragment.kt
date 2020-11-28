package io.github.dvegasa.rosatom.features.main.worker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.MainActivity
import io.github.dvegasa.rosatom.features.main.boss.AtomVoiceFragment
import io.github.dvegasa.rosatom.features.main.curtask.CurTaskFragment
import kotlinx.android.synthetic.main.fragment_boss.*
import kotlinx.android.synthetic.main.fragment_worker.*
import kotlinx.android.synthetic.main.fragment_worker.view.*

class WorkerFragment : Fragment() {

    var taskList: ArrayList<Task>? = arrayListOf(
        Task("Пройти плановый медосмотр", "Главный корпус", "9:00 — 10:30"),
        Task("Работа за станком ТАКТА", "Цех №3", "11:00 — 14:30"),
        Task("Проверка качества изделий №53", "Цех №3", "14:45 — 15:10"),
        Task("Мониторинг работы нагревательного котла", "Цех №3", "15:25 — 17:30"),
        Task("Сдача смены начальнику цеха", "Главный корпус", "17:50 — 18:00")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnGo.setOnClickListener {
            // Открыть первый таск
            val frag = CurTaskFragment.newInstance(Gson().toJson(taskList!![0]))
            frag.show(childFragmentManager, null)
        }

        listLoaded()
    }

    fun listLoaded() {
        btnGo.isEnabled = true
        rvShortTasks.adapter = RvTasksAdapter(taskList!!)
        rvShortTasks.layoutManager = LinearLayoutManager(requireContext())
        tvTasksNumber.text = "Сегодня будет ${taskList!!.size} задач"
    }

}