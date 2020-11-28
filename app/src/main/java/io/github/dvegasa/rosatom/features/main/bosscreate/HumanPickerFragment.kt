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
import kotlinx.android.synthetic.main.item_human.view.*

class HumanPickerFragment(val cb: Callback) : DialogFragment() {

    interface Callback {
        fun picked(h: Human)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_human_picker, container, false)
        for (h in humans) {
            val x = inflater.inflate(R.layout.item_human, v.ll, false)
            x.ivAva.setImageResource(h.avaId)
            x.tvName.text = h.name
            x.tvPosition.text = h.position
            x.setOnClickListener {
                cb.picked(h)
                dismiss()
            }
            v.ll.addView(x)
        }
        return v
    }

    val humans = listOf(
        Human("Халтурин Эдуард", "Старший наладчик оборудования", R.drawable.ava0),
        Human("Штанько Никита", "Наладчик оборудования", R.drawable.ava1),
        Human("Помидоркин Олег", "Сварщик 2 разряда", R.drawable.ava2),
        Human("Ищенко Александр", "Сварщик 4 разряда", R.drawable.ava3),
        Human("Лисицын Дмитрий", "Младший наладчик оборудования", R.drawable.ava4),
        Human("Новеллин Анатолий", "Сварщик стажёр", R.drawable.ava5)
    )

}