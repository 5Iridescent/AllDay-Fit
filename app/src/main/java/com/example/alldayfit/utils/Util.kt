package com.example.alldayfit.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileInputStream

object Util {
    const val realtime_database="https://alldayfit-d9c92-default-rtdb.asia-southeast1.firebasedatabase.app/"

    fun getBitmapFromFile(filePath: String): Bitmap? {
        val options = BitmapFactory.Options()
        return BitmapFactory.decodeFile(filePath, options)
    }

    fun readContentIntoByteArray(file: File): ByteArray {
        val fileInputStream: FileInputStream?
        val bFile = ByteArray(file.length().toInt())
        try {
            //convert file into array of bytes
            fileInputStream = FileInputStream(file)
            fileInputStream.read(bFile)
            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bFile
    }
}