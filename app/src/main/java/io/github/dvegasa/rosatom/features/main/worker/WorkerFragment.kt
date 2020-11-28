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
import io.github.dvegasa.rosatom.features.main.boss.AtomVoiceFragment
import kotlinx.android.synthetic.main.fragment_boss.*
import kotlinx.android.synthetic.main.fragment_worker.view.*

class WorkerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_worker, container, false)
        val list = arrayListOf(
            Task("Пройти плановый медосмотр", "Главный корпус", "9:00 — 10:30"),
            Task("Работа за станком ТАКТА", "Цех №3", "11:00 — 14:30"),
            Task("Проверка качества изделий №53", "Цех №3", "14:45 — 15:10"),
            Task("Мониторинг работы нагревательного котла", "Цех №3", "15:25 — 17:30"),
            Task("Сдача смены начальнику цеха", "Главный корпус", "17:50 — 18:00")
        )
        v.apply {
            rvShortTasks.adapter = RvTasksAdapter(list)
            rvShortTasks.layoutManager = LinearLayoutManager(requireContext())

            tvTasksNumber.text = "${list.size} заданий"
        }
        return v
    }



}