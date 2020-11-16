package com.corap.data.model


import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User: RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("birthdate")
    var birthdate: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("email_verified")
    var emailVerified: Int? = null

    @SerializedName("last_visit")
    var lastVisit: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null

    @SerializedName("phone_verified")
    var phoneVerified: Int? = null

    @SerializedName("picture")
    var picture: String? = null

    @SerializedName("picture_medium_banner")
    var pictureMediumBanner: String? = null

    @SerializedName("picture_medium_cover")
    var pictureMediumCover: String? = null

    @SerializedName("picture_medium_square")
    var pictureMediumSquare: String? = null

    @SerializedName("picture_normal_cover")
    var pictureNormalCover: String? = null

    @SerializedName("picture_small_banner")
    var pictureSmallBanner: String? = null

    @SerializedName("picture_small_cover")
    var pictureSmallCover: String? = null

    @SerializedName("picture_small_square")
    var pictureSmallSquare: String? = null

    @SerializedName("picture_thumbnail")
    var pictZZZureThumbnail: String? = null

    @SerializedName("registration_date")
    var registrationDate: String? = null

    @SerializedName("request_change_role")
    var requestChangeRole: String? = null

    @SerializedName("role")
    var role: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("timezone")
    var timezone: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("username")
    var username: String? = null
}
