package com.hxw.file.actions.page.upload

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.hxw.file.actions.R
import com.hxw.file.actions.base.activity.DefaultActivity
import com.hxw.file.actions.dao.FileActionDao
import com.hxw.file.actions.databinding.ActivityUploadBinding
import com.hxw.file.actions.entity.ActionEntity
import com.hxw.file.actions.page.adapter.ActionsAdapter
import com.hxw.file.actions.utils.FileUtil
import java.io.File

class UploadActivity : DefaultActivity() {

    private lateinit var mBinding: ActivityUploadBinding
    private val mUploadFiles = ArrayList<ActionEntity>()
    private var mAdapter: ActionsAdapter? = null

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(
                context,
                UploadActivity::class.java
            )
            context.startActivity(starter)
        }
    }

    override fun layoutId(): Int {
        return R.layout.activity_upload
    }

    override fun initViews() {
        super.initViews()
        mBinding = viewBinding()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        val uploadFile: File = FileUtil.getUploadFile(this)
        if (uploadFile.exists()) {
            val listFiles = uploadFile.listFiles()
            listFiles?.let {
                for (file in listFiles) {
                    if (file.exists() && file.isFile) {
                        mUploadFiles.add(ActionEntity.factory(file.absolutePath, file.name, false))
                    }
                }
            }
        }
        mAdapter = ActionsAdapter(mUploadFiles)
        mBinding.uploadRecycleView.adapter = mAdapter
    }

    override fun initListener() {
        super.initListener()
        mBinding.uploadView.setOnClickListener {
            val selectUploadFile = ArrayList<String>()
            for (upload in mUploadFiles) {
                if (upload.isChecked) {
                    selectUploadFile.add(upload.absolutePath)
                }
            }
            if (selectUploadFile.isEmpty()) {
                return@setOnClickListener
            }
            FileActionDao.upload(selectUploadFile).observe(this, Observer {
                if (it != null) {
                    Log.e("FileActionDao", it.toString())
                }
            })
        }

    }
}