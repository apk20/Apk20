package com.example.aryapk.footballscheduledb.api

import com.akupintar.mobile.searchgithubuser.BuildConfig

object GithubApi {
    fun getUsers(keyword : String, page : String): String {
        return BuildConfig.BASE_URL + "users?q=" + keyword + "&page=" + page + "&per_page=100"
    }
}