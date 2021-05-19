package com.hxw.file.actions.page

import com.hxw.file.actions.R
import com.hxw.file.actions.base.activity.DefaultActivity
import com.hxw.file.actions.databinding.ActivityMainBinding
import com.hxw.file.actions.page.download.DownloadActivity
import com.hxw.file.actions.page.upload.UploadActivity

class MainActivity : DefaultActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        super.initViews()
        mBinding = viewBinding()
    }

    override fun initListener() {
        super.initListener()
        mBinding.fileUploadView.setOnClickListener {
            UploadActivity.start(this)
        }
        mBinding.fileDownloadView.setOnClickListener {
            DownloadActivity.start(this)
        }

        val list = listOf(1, 2, 3, 4, 5)
        val result = list.sum {
            println(it)
        }
        println("计算结果：${result}")

    }

    fun List<Int>.sum(callback: (Int) -> Unit): Int {
        var result = 0
        for (v in this) {
            result += v
            callback(v)
        }
        return result;
    }

}