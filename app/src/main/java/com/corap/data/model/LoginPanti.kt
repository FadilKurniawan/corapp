package com.corap.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class LoginPanti : RealmObject() {

    @SerializedName("token")
    var token: String? = null

    @SerializedName("user")
    var user: User? = null

}