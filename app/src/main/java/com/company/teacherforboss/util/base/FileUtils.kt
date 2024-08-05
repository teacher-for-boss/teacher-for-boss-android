package com.company.teacherforboss.util.base

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import java.io.File

object FileUtils {
    fun getFileFromUri(context: Context,uri:Uri):File{
        val filePath= getPathFromURI(context,uri)
        return File(filePath)
    }
    fun getPathFromURI(context: Context, uri: Uri):String{
        var path=""
        val cursor:Cursor?=context.contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA),null,null,null)
        cursor?.use {
            if(it.moveToFirst()){
                val index=it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path=it.getString(index)
            }
        }
        return path
    }
}