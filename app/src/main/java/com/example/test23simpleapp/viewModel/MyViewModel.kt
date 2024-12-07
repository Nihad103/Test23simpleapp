package com.example.test23simpleapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test23simpleapp.service.ApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MyViewModel(private val apiService: ApiService) : ViewModel() {
    val quote = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        quote.postValue("Coroutine exception: ${throwable.localizedMessage}")
    }
    private var job: Job? = null

    init {
        fetchQuote()
        startAutoRefresh()
    }

    private fun fetchQuote() {
        viewModelScope.launch(exceptionHandler) {
            try {
                val response = apiService.getData()
                quote.postValue(response.quote)
                startAutoRefresh()
            } catch (e: HttpException) {
                quote.postValue("HTTP Error: ${e.code()} - ${e.message()}")
            } catch (e: IOException) {
                quote.postValue("Network Error: ${e.message}")
            } catch (e: Exception) {
                quote.postValue("Unknown Error: ${e.message}")
            }
        }
    }

    fun startAutoRefresh() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(10000)
            fetchQuote()
        }
    }

    fun refreshManually() {
        fetchQuote()
    }
}

