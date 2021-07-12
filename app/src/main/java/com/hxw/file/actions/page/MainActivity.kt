package com.hxw.file.actions.page

import android.os.Bundle
import com.hxw.file.actions.R
import com.hxw.file.actions.base.activity.DefaultActivity
import com.hxw.file.actions.databinding.ActivityMainBinding
import com.hxw.file.actions.page.download.DownloadActivity
import com.hxw.file.actions.page.upload.UploadActivity
import com.hxw.file.actions.view.draw.DrawData

class MainActivity : DefaultActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        super.initViews()
        mBinding = viewBinding()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        val list = ArrayList<DrawData>()
        list.add(DrawData().apply {
            color = "#6200EE"
            count = 9
            name = "我要存款"
        })
        list.add(DrawData().apply {
            color = "#EC407A"
            count = 20
            name = "我要取钱"
        })
        list.add(DrawData().apply {
            color = "#03DAC5"
            count = 30
            name = "信用卡申请"
        })
        list.add(DrawData().apply {
            color = "#ADB657"
            count = 20
            name = "现金业务"
        })
        list.add(DrawData().apply {
            color = "#E67451"
            count = 100
            name = "鑫易存单"
        })

        mBinding.pieView.postDelayed({
            mBinding.pieView.setPieDataList(list)
        }, 200)

        mBinding.barView.postDelayed({
            mBinding.barView.setBarDataList(list)
        }, 200);
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