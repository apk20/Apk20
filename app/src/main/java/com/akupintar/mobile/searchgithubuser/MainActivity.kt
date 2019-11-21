package com.akupintar.mobile.searchgithubuser

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class MainActivity : AppCompatActivity(),MainView {
    private var users: MutableList<User> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: UserAdapter
    var isLoading = false
    var pageCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = UserAdapter(this,users)
        rvUserList.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        etSearchKeyword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                users.clear()
                pageCount = 1
                presenter.getUsers(s.toString(),pageCount.toString(),true)
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        rvUserList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val llm = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && users.size % 100 ==0){
                    if (llm!= null && llm.findLastVisibleItemPosition() == users.size - 1){
                        isLoading = true
                        pageCount++
                        presenter.getUsers(etSearchKeyword.text.toString(),pageCount.toString(),false)
                    }
                }
            }
        })

    }

    override fun showLoading() {
        rvUserList.visibility = GONE
        pbUser.visibility = VISIBLE
        tvMessage.visibility = GONE
        rlShow.visibility = VISIBLE
    }

    override fun hideLoading() {
        rvUserList.visibility = VISIBLE
        pbUser.visibility = GONE
        tvMessage.visibility = GONE
        rlShow.visibility = GONE
    }

    override fun showUsers(data: List<User>) {
        users.addAll(data)
        adapter.notifyDataSetChanged()
        isLoading = false
    }

    override fun showMessage(message: String) {
        rvUserList.visibility = GONE
        pbUser.visibility = GONE
        tvMessage.visibility = VISIBLE
        rlShow.visibility = VISIBLE
        tvMessage.text = message
    }

    override fun showError() {
        hideLoading()
        Toast.makeText(this,"Search Limit reached, \n search is not allowed for approximately the next 1 minute",Toast.LENGTH_LONG).show()
    }
}
