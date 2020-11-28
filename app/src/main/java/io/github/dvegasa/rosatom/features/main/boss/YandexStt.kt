package io.github.dvegasa.rosatom.features.main.boss


import com.google.gson.annotations.SerializedName

data class YandexStt(
    @SerializedName("result")
    val result: String
)