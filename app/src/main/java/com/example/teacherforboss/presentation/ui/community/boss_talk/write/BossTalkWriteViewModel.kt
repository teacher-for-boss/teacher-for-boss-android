package com.example.teacherforboss.presentation.ui.community.boss_talk.write

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BossTalkWriteViewModel : ViewModel() {
    val hasTagList:ArrayList<String> = arrayListOf()
    val imageList: ArrayList<String> = arrayListOf()

    private val _textTitleLength = MutableLiveData<Int>()
    val textTitleLength: LiveData<Int> get()=_textTitleLength

    private val _textBodyLength = MutableLiveData<Int>()
    val textBodyLength: LiveData<Int> get()=_textBodyLength

    private val _textTagLength = MutableLiveData<Int>()
    val textTagLength: LiveData<Int> get()=_textTagLength

    fun addHashTag(tag: String) {
        hasTagList.add(tag)
    }
    fun deleteHashTag(position: Int) {
        hasTagList.removeAt(position)
    }

    fun addImage(imageUri: Uri?) {
        imageList.add(imageUri.toString())
    }
    fun deleteImage(position: Int) {
        imageList.removeAt(position)
    }

    fun setTitleLength(length: Int) {
        _textTitleLength.value = length
    }
    fun setBodyLength(length: Int) {
        _textBodyLength.value = length
    }
    fun setTagLength(length: Int) {
        _textTagLength.value = length
    }

}