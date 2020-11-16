package com.corap.data.remote.services

import com.corap.data.model.LoginPanti
import com.corap.data.model.UserMembers
import com.corap.data.remote.wrapper.ResponseObject
import com.corap.data.remote.wrapper.Results
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by DODYDMW19 on 8/3/2017.
 */

interface APIService {

    @POST("user/login")
    fun postLogin(
            @Query("email") email:String,
            @Query("password") password:String
    ):Deferred<ResponseObject<LoginPanti>>

    @GET
    fun getMembers(
            @Url url: String,
            @Query("per_page") perPage: Int,
            @Query("page") page: Int): Single<Results<UserMembers>>

    @GET
    fun getMembersCoro(
            @Url url: String ,
            @Query("per_page") perPage: Int,
            @Query("page") page: Int): Deferred<Results<UserMembers>>

}
