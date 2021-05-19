package com.hxw.file.actions.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * <p>文件描述：</p>
 * <p>作者：hanxw</p>
 * <p>创建时间：2021/5/19</p>
 * <p>更改时间：2021/5/19</p>
 * <p>版本号：1</p>
 */
public class FileUtil {

    private static final String TAG = "FileUtil";
    private static final String UPLOAD_ROOT_FILE_PATH = "file_action_upload";
    private static final String DOWNLOAD_ROOT_FILE_PATH = "file_action_download";

    public static File getUploadFile(@NonNull Context context) {
        return getFileAction(context, UPLOAD_ROOT_FILE_PATH);
    }

    public static File getFileAction(@NonNull Context context, @NonNull String type) {
        return context.getApplicationContext().getExternalFilesDir(type);
    }

    /**
     * 将下载文件，写入到磁盘
     *
     * @param body
     * @param context
     * @param fileName 下载文件名字
     * @return
     */
    public static boolean writeResponseBodyToDisk(@NonNull ResponseBody body, @NonNull Context context, @NonNull String fileName) {
        try {
            File futureStudioIconFile = new File(
                    getFileAction(context, DOWNLOAD_ROOT_FILE_PATH).getAbsolutePath()
                            + File.separator + fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
