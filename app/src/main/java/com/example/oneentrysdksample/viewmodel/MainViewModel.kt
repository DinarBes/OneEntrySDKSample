package com.example.oneentrysdksample.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oneentry.model.blocks.OneEntryBlock
import com.example.oneentry.model.common.OneEntryResult
import com.example.oneentry.model.pages.OneEntryPage
import com.example.oneentry.model.project.OneEntryLocale
import com.example.oneentrysdksample.network.BlocksProvider
import com.example.oneentrysdksample.network.PagesProvider
import com.example.oneentrysdksample.network.ProjectProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _locales = MutableStateFlow<List<OneEntryLocale>?>(null)
    val locales = _locales.asStateFlow()

    val dataExist: Boolean
        get() = _blocks.value != null && _pages.value != null

    private val _blocks = MutableStateFlow<OneEntryResult<OneEntryBlock>?>(null)
    val blocks = _blocks.asStateFlow()

    private val _pages = MutableStateFlow<List<OneEntryPage>?>(null)
    val pages = _pages.asStateFlow()

    init {
        getLocales()
    }

    private fun getLocales() {
        viewModelScope.launch {
            try {
                _locales.value = ProjectProvider.getActiveLocale()
            } catch (error: Exception) {
                Log.e("Get locales, vm error", error.toString())
            }
        }
    }

    fun getBlocks() {
        viewModelScope.launch {
            try {
                _blocks.value = BlocksProvider.getBlocks()
            } catch (error: Exception) {
                Log.e("Get blocks, vm error", error.toString())
            }
        }
    }

    fun getPages() {
        viewModelScope.launch {
            try {
                _pages.value = PagesProvider.getPages()
            } catch (error: Exception) {
                Log.e("Get pages, vm error", error.toString())
            }
        }
    }
}