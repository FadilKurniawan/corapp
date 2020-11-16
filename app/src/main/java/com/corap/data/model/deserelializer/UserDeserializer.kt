package com.corap.data.model.deserelializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.corap.data.model.UserMembers
import java.lang.reflect.Type

/**
 * Created by DODYDMW19 on 1/30/2018.
 */
class UserDeserializer : SuitCoreJsonDeserializer<UserMembers>() {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): UserMembers {
        val jsonObject = json?.asJsonObject

        val userMembers: UserMembers? = UserMembers()
        if (jsonObject?.has("id")!!) {
            userMembers?.id = getIntOrZeroFromJson(jsonObject.get("id"))
        }

        if (jsonObject.has("first_name")) {
            userMembers?.firstName = getStringOrNullFromJson(jsonObject.get("first_name"))
        }

        if (jsonObject.has("last_name")) {
            userMembers?.lastName = getStringOrNullFromJson(jsonObject.get("last_name"))
        }

        if (jsonObject.has("avatar")) {
            userMembers?.avatar = getStringOrNullFromJson(jsonObject.get("avatar"))
        }

        return userMembers!!

    }
}