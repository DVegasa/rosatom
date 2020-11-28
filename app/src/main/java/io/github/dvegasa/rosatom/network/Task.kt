package io.github.dvegasa.rosatom.network


import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("contractIds")
    var contractIds: List<Any>,
    @SerializedName("createdById")
    var createdById: Int,
    @SerializedName("deadline")
    var deadline: String,
    @SerializedName("desc")
    var desc: String,
    @SerializedName("linked")
    val linked: List<String>,
    @SerializedName("title")
    var title: String,
    @SerializedName("workerId")
    var workerId: List<Int>
)