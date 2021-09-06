package com.hxw.file.actions.page

import android.os.Bundle
import com.hxw.file.actions.R
import com.hxw.file.actions.base.activity.DefaultActivity
import com.hxw.file.actions.databinding.ActivityMainBinding
import com.hxw.file.actions.page.download.DownloadActivity
import com.hxw.file.actions.page.upload.UploadActivity
import com.hxw.file.actions.view.chart.ChartData
import com.hxw.file.actions.view.stepview.dashed.DashedData

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
        val list = ArrayList<ChartData>()
        list.add(ChartData().apply {
            color = "#6200EE"
            count = 9f
            name = "我要存款"
        })
        list.add(ChartData().apply {
            color = "#EC407A"
            count = 20f
            name = "我要取钱"
        })
        list.add(ChartData().apply {
            color = "#03DAC5"
            count = 30f
            name = "信用卡申请"
        })
        list.add(ChartData().apply {
            color = "#ADB657"
            count = 20f
            name = "现金业务"
        })
        list.add(ChartData().apply {
            color = "#E67451"
            count = 100f
            name = "鑫易存单"
        })

        mBinding.pieView.postDelayed({
            mBinding.pieView.setPieDataList(list)
        }, 200)

        mBinding.barView.postDelayed({
            mBinding.barView.setBarDataList(list)
        }, 200)

        mBinding.dashedStepView.postDelayed({
            val dashedList = ArrayList<DashedData>()
            dashedList.add(DashedData().apply {
                leftText = "繁忙窗口"
                rightText = "繁忙队列"
                isTitle = true
            })
            dashedList.add(DashedData().apply {
                leftText = "窗口1"
                rightText = "队列1"
            })
            dashedList.add(DashedData().apply {
                leftText = "窗口2"
                rightText = "队列2"
            })
            dashedList.add(DashedData().apply {
                leftText = "窗口3"
                rightText = "队列3"
            })
            dashedList.add(DashedData().apply {
                leftText = "窗口3"
                rightText = "队列3"
            })
            dashedList.add(DashedData().apply {
                leftText = "窗口4"
                rightText = "队列4"
            })
            mBinding.dashedStepView.setDashedDataList(dashedList)
        }, 200)
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