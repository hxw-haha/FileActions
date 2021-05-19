package com.hxw.file.actions.page.download

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.hxw.file.actions.APP
import com.hxw.file.actions.R
import com.hxw.file.actions.base.activity.DefaultActivity
import com.hxw.file.actions.dao.FileActionDao
import com.hxw.file.actions.databinding.ActivityDownloadBinding
import com.hxw.file.actions.entity.ActionEntity
import com.hxw.file.actions.entity.AllFilesResponse
import com.hxw.file.actions.page.adapter.ActionsAdapter
import java.io.File

class DownloadActivity : DefaultActivity() {
    private lateinit var mBinding: ActivityDownloadBinding

    private val mUploadFiles = ArrayList<ActionEntity>()
    private var mAdapter: ActionsAdapter? = null
    override fun layoutId(): Int {
        return R.layout.activity_download
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, DownloadActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun initViews() {
        super.initViews()
        mBinding = viewBinding()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        FileActionDao.getAllFiles().observe(this, Observer {
            if (it != null
                && it.code == AllFilesResponse.SUCCEED
                && it.childFileNames != null
                && it.childFileNames.size > 0
            ) {
                for (childFileName in it.childFileNames) {
                    mUploadFiles.add(
                        ActionEntity.factory(
                            it.rootFilePath + File.separator + childFileName,
                            childFileName,
                            false
                        )
                    )
                }
                mAdapter = ActionsAdapter(mUploadFiles)
                mBinding.downloadRecycleView.adapter = mAdapter
            }
        })
    }

    override fun initListener() {
        super.initListener()
        mBinding.downloadView.setOnClickListener {
            val selectUploadFile = ArrayList<String>()
            for (upload in mUploadFiles) {
                if (upload.isChecked) {
                    selectUploadFile.add(APP.BASE_URL + upload.name)
                }
            }
            FileActionDao.download(this, selectUploadFile)
        }
    }
}