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