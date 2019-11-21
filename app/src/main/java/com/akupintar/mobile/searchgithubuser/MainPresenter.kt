package com.akupintar.mobile.searchgithubuser

import com.example.aryapk.footballscheduledb.api.GithubApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.FileNotFoundException

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {
    fun getUsers(keyword: String,page :String,loading : Boolean) {
        if (loading) view.showLoading()
        doAsync {
            try {
                val data = gson.fromJson(apiRepository
                    .doRequest(GithubApi.getUsers(keyword,page)),
                    UserSearchResponse::class.java
                )

                uiThread {
                    view.hideLoading()
                    if (data.items.isEmpty()) view.showMessage("No Results Matched")
                    else view.showUsers(data.items)
                }
            } catch (e : FileNotFoundException){
                uiThread {
                    view.showError()
                }
            }
        }
    }
}