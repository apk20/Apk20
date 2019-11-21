package com.akupintar.mobile.searchgithubuser

import User

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun showMessage(message : String)
    fun showUsers(data: List<User>)
}