package io.github.dvegasa.rosatom.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import io.github.dvegasa.rosatom.R
import io.github.dvegasa.rosatom.features.main.boss.BossFragment
import io.github.dvegasa.rosatom.features.main.worker.Task
import io.github.dvegasa.rosatom.features.main.worker.WorkerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var taskList: ArrayList<Task>? = arrayListOf(
        Task("Пройти плановый медосмотр", "Главный корпус", "9:00 — 10:30"),
        Task("Работа за станком ТАКТА", "Цех №3", "11:00 — 14:30"),
        Task("Проверка качества изделий №53", "Цех №3", "14:45 — 15:10"),
        Task("Мониторинг работы нагревательного котла", "Цех №3", "15:25 — 17:30"),
        Task("Сдача смены начальнику цеха", "Главный корпус", "17:50 — 18:00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username")

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            if (username == "dvegasa") {
                add<BossFragment>(flRoot.id)
            } else {
                add<WorkerFragment>(flRoot.id)
            }
        }
    }
}