package com.space.swagger.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.webkit.WebSettings
import com.space.swagger.databinding.ActivityWebviewBinding

class WebActivity : Activity() {

    private var url : String = ""
    private lateinit var binding : ActivityWebviewBinding

    companion object{
        const val ARTICLE_URL : String = "articleUrl"
        const val BUNDLE : String = "bundle"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val webSetting : WebSettings = binding.web.settings
        webSetting.javaScriptEnabled = true
        val bundle : Bundle? = intent.getBundleExtra(BUNDLE)
        url = bundle?.getString(ARTICLE_URL).toString()
        binding.web.loadUrl(url)
    }
}