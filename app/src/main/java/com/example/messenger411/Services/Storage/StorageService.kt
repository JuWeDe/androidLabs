package com.example.messenger411.Services.Storage

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import io.ktor.utils.io.core.toByteArray
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.FileWriter

class StorageService {
    companion object {
        @RequiresApi(Build.VERSION_CODES.Q)
        fun saveToDownloads(context: Context, fileName: String, fileContent: String) {
            val contentResolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = contentResolver.insert(
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                values
            )

            uri?.let { fileUri ->
                contentResolver.openOutputStream(fileUri)?.use { outputStream ->
                    outputStream.write(fileContent.toByteArray())
                }
            }
        }

        fun saveToDownloads(fileName: String, content: String): Boolean {
            if (isExternalStorageWritable()) {
                val downloadsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileName)

                try {
                    FileWriter(file).use { writer ->
                        writer.write(content)
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        @SuppressLint("Range")
        @RequiresApi(Build.VERSION_CODES.Q)
        fun deleteFileFromDownloads(context: Context, fileName: String): Boolean {
            val contentResolver: ContentResolver = context.contentResolver
            val downloadsUri: Uri = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val selection = "${MediaStore.Downloads.DISPLAY_NAME} = ?"
            val selectionArgs = arrayOf(fileName)

            val cursor = contentResolver.query(downloadsUri, null, selection, selectionArgs, null)

            cursor?.use {
                if (it.moveToFirst()) {
                    val fileUri: Uri = Uri.withAppendedPath(
                        downloadsUri,
                        it.getString(it.getColumnIndex(MediaStore.Downloads._ID))
                    )

                    if (deleteFile(context, fileUri)) {
                        return true
                    }
                }
            }
            return false
        }

        private fun deleteFile(context: Context, fileUri: Uri): Boolean {
            val contentResolver: ContentResolver = context.contentResolver
            try {
                val rowsDeleted = contentResolver.delete(fileUri, null, null)
                return rowsDeleted > 0
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return false
        }

        fun deleteFileFromDownloads(fileName: String): Boolean {
            val downloadsDir: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val filePath = File(downloadsDir, fileName)

            if (filePath.exists()) {
                return filePath.delete()
            }
            return false
        }

        fun isFileInDownloads(fileName: String): Boolean {
            val downloadsDir: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val filePath = File(downloadsDir, fileName)

            return filePath.exists()
        }

        fun isFileInInternalStorage(context: Context, fileName: String): Boolean {
            val internalDir: File = context.filesDir
            val filePath = File(internalDir, fileName)

            return filePath.exists()
        }

        fun moveFileToInternalStorage(context: Context, fileName: String): Boolean {
            val downloadsDir: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val sourceFile = File(downloadsDir, fileName)

            val internalDir: File = context.filesDir
            Log.d("Save", context.filesDir.absolutePath)
            val destinationFile = File(internalDir, fileName)

            return moveFile(sourceFile, destinationFile)
        }

        fun moveFileToDownloads(context: Context, fileName: String): Boolean {
            val internalDir: File = context.filesDir
            val sourceFile = File(internalDir, fileName)

            val downloadsDir: File =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val destinationFile = File(downloadsDir, fileName)

            return moveFile(sourceFile, destinationFile)
        }

        private fun moveFile(sourceFile: File, destinationFile: File): Boolean {
            try {
                val inputStream = FileInputStream(sourceFile)
                val outputStream = FileOutputStream(destinationFile)
                val buffer = ByteArray(1024)
                var length: Int

                while (inputStream.read(buffer).also { length = it } > 0) {
                    outputStream.write(buffer, 0, length)
                }

                inputStream.close()
                outputStream.close()

                return sourceFile.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        private fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        private fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }
    }

}