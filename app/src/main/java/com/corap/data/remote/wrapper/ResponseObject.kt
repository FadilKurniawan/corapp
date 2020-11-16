package com.corap.data.remote.wrapper

import com.google.gson.annotations.SerializedName


class ResponseObject<T> {

    @SerializedName("status")
    var status: Int? = 0

    @SerializedName("message")
    var message: String? = ""

    @SerializedName("result")
    var result: T? = null
}
