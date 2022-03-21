package com.kotlin.pixelsoftwaresdemo.Retrofit

import com.kotlin.getyoteamassignment.Model.Chapter
import com.kotlin.getyoteamassignment.Model.ChapterElement
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("chapterListWithProgress")
    fun getCourses(
        @Field("courseId") courseId: String,
        @Field("customerId") customerId: String
    ): Call<Chapter>

}