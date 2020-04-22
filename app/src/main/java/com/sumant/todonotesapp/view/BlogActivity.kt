package com.sumant.todonotesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.sumant.todonotesapp.R
import com.sumant.todonotesapp.adapter.BlogAdapter
import com.sumant.todonotesapp.model.Response
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        getBlog()
    }

    private fun getBlog() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Response::class.java, object: ParsedRequestListener<Response> {
                    override fun onResponse(response: Response?) {
                        if (response != null) {
                            setupRecyclerView(response)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("TAG", anError?.localizedMessage)
                    }

                })
    }

    private fun setupRecyclerView(response: Response){
        val blogAdapter = BlogAdapter(response.data)
        blogRv.adapter = blogAdapter
    }
}
