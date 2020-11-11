package com.corap.data.remote.services

import com.corap.data.model.User
import com.corap.data.remote.wrapper.Results
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by DODYDMW19 on 8/3/2017.
 */

interface APIService {

    @GET("users")
    fun getMembers(
            @Query("per_page") perPage: Int,
            @Query("page") page: Int): Single<Results<User>>

    @GET("users")
    fun getMembersCoro(
            @Query("per_page") perPage: Int,
            @Query("page") page: Int): Deferred<Results<User>>

}
