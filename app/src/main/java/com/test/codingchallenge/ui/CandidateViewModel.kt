package com.test.codingchallenge.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.codingchallenge.data.CandidateService
import com.test.codingchallenge.data.Candidate
import kotlinx.coroutines.launch

class CandidateViewModel: ViewModel() {

    var candidateList = mutableStateListOf<Candidate>()
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)

    private val _candidateDetail: MutableState<Candidate?> = mutableStateOf(null)
    var candidateDetail: State<Candidate?> = _candidateDetail
        private set

    fun setCandidateDetail(candidate: Candidate) {
        _candidateDetail.value = candidate
    }

    fun retrieveCandidates () {
        viewModelScope.launch {
            isLoading = true
            errorMessage = "";
            val apiService = CandidateService.getInstance()
            try {
                candidateList.clear()
                candidateList.addAll(apiService.getCandidates().data)
                isLoading = false

            } catch (e: Exception) {
                isLoading = false
                errorMessage = e.message.toString()
                println("ERROR: $errorMessage")
            }
        }
    }
}