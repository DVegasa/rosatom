package io.github.dvegasa.rosatom.features.main.bosscreate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import io.github.dvegasa.rosatom.R
import kotlinx.android.synthetic.main.fragment_file_picker.view.*
import kotlinx.android.synthetic.main.item_file.view.*

class FilePickerFragment(val cb: Callback) : DialogFragment() {

    interface Callback {
        fun picked(s: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_file_picker, container, false)
        for (s in files) {
            val x = inflater.inflate(R.layout.item_file, v.ll, false)
            x.tv.text = s
            x.setOnClickListener {
                cb.picked(s)
            }
            v.ll.addView(x)
        }
        return v
    }

    val files = listOf(
        "ГОСТ 4.161-85\n" +
                "Система показателей качества продукции. Изделия маникюрные и педикюрные. Номенклатура показателей",
        "ГОСТ 4.164-85\n" +
                "Система показателей качества продукции. Анализаторы радиоспектрометрические. Номенклатура показателей",
        "ГОСТ 8.019-85\n" +
                "Государственная система обеспечения единства измерений. Государственный первичный эталон и государственная поверочная схема для средств измерений тангенса угла потерь",
        "ГОСТ 8.023-86\n" +
                "Государственная поверочная схема для средств измерений световых величин непрерывного и импульсного излучения",
        "ПНСТ 473-2020\n" +
                "Изделия, полученные методом аддитивных технологических процессов. Требования к образцам для испытаний"
    )
}