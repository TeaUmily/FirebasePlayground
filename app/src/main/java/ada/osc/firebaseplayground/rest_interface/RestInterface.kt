package com.example.app_home.common.rest_interface

import ada.osc.firebaseplayground.AnswerModel.AnswersData
import io.reactivex.Observable
import retrofit2.http.*

interface RestInterface {

    @PUT("/year/2019/month/9/day/12/question_one/{type}.json")
    fun updateCastle(@Path("type") type: String, @Body value: Int): Observable<Unit>

    @GET("/year/2019/month/{month}/day/12/question_one/castle.json")
    fun getCastle(@Path("month") month: Int): Observable<Int>

    @GET("/year/{year}/month/{month}/day/{day}/{question}/{answer}.json")
    fun getDayData(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int,
        @Path("question") question: String,
        @Path("answer") answer: String
    ): Observable<Int>


    @PUT("/year/{year}/month/{month}/day/{day}/{question}/{answer}.json")
    fun writeDayData(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int,
        @Path("question") question: String,
        @Path("answer") answer: String,
        @Body value: Int
    ): Observable<Unit>


    @GET("/year/{year}/month/{month}/{question}/{answer}.json")
    fun getMonthData(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("question") question: String,
        @Path("answer") answer: String
    ): Observable<Int>


    @PUT("/year/{year}/month/{month}/{question}/{answer}.json")
    fun writeMonthData(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("question") question: String,
        @Path("answer") answer: String,
        @Body value: Int
    ): Observable<Unit>

    @GET("/year/{year}/{question}/{answer}.json")
    fun getYearData(
        @Path("year") year: Int,
        @Path("question") question: String,
        @Path("answer") answer: String
    ): Observable<Int>


    @PUT("/year/{year}/{question}/{answer}.json")
    fun writeYearData(
        @Path("year") year: Int,
        @Path("question") question: String,
        @Path("answer") answer: String,
        @Body value: Int
    ): Observable<Unit>

    @GET("/year/{year}/month/{month}/day.json")
    fun getVisitStatisticByDays(
        @Path("year") year: Int,
        @Path("month") month: Int
    ): Observable<HashMap<String, AnswersData>>


    @GET("/year/{year}/month.json")
    fun getVisitStatisticByMonths(
        @Path("year") year: Int
    ): Observable<HashMap<String, AnswersData>>


    @GET("/year.json")
    fun getVisitStatisticByYears(
    ): Observable<HashMap<String, AnswersData>>
}