package com.test.codingchallenge.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.codingchallenge.data.CandidateService
import com.test.codingchallenge.data.Candidate
import kotlinx.coroutines.launch

class CandidateViewModel: ViewModel() {

    private var allCandiates = mutableListOf<Candidate>()
    var candidateList: MutableState<List<Candidate>> = mutableStateOf(value = emptyList())
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)
    var searchQuery: MutableState<String> = mutableStateOf("")
    var statusList: MutableState<List<String>> = mutableStateOf(value = emptyList())

    val isApproved: MutableState<Boolean> = mutableStateOf(false)
    val isReject: MutableState<Boolean> = mutableStateOf(false)
    val isWaiting: MutableState<Boolean> = mutableStateOf(false)

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
                candidateList.value = emptyList()
                statusList.value = emptyList()
                val updatedList = candidateList.value.toMutableList()
                val data = apiService.getCandidates().data
                allCandiates.addAll(data)
                updatedList.addAll(data)
                candidateList.value = updatedList
                isLoading = false

            } catch (e: Exception) {
                isLoading = false
                errorMessage = e.message.toString()
                println("ERROR: $errorMessage")
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        updateFilteredItems()
    }

    private fun updateFilteredItems() {
        candidateList.value = if (searchQuery.value.isEmpty()) {
            candidateList.value
        } else {
            // Kpa, used contains but can also change equals.
            allCandiates.filter { it.name.contains(searchQuery.value, ignoreCase = true) }
        }
    }

    fun onApply() {
        candidateList.value = if (statusList.value.isEmpty()) {
            candidateList.value
        } else {
            // Kpa, used contains but can also change equals.
            allCandiates.filter { statusList.value.contains(it.status) }
        }
    }

    // Methods to toggle button selections
    fun toggleApproved() {
        isApproved.value = !isApproved.value
        val list = statusList.value.toMutableList();
        if (isApproved.value) {
            list.add("approved")
        } else {
            list.remove("approved")
        }
        statusList.value = list
    }

    fun toggleReject() {
        isReject.value = !isReject.value
        val list = statusList.value.toMutableList();
        if (isReject.value) {
            list.add("rejected")
        } else {
            list.remove("rejected")
        }
        statusList.value = list
    }

    fun toggleWaiting() {
        isWaiting.value = !isWaiting.value
        val list = statusList.value.toMutableList();
        if (isWaiting.value) {
            list.add("waiting")
        } else {
            list.remove("waiting")
        }
        statusList.value = list
    }

    fun nameSort(by: String) {
        val list = candidateList.value.toMutableList()
        if (by == "ASC") {
            candidateList.value = list.sortedBy { it.name }
        } else {
            candidateList.value = list.sortedByDescending { it.name }
        }
    }

    fun positionSort(by: String) {
        val list = candidateList.value.toMutableList()
        if (by == "ASC") {
            candidateList.value = list.sortedBy { it.positionApplied }
        } else {
            candidateList.value = list.sortedByDescending { it.positionApplied }
        }
    }
    fun yoeSort(by: String) {
        val list = candidateList.value.toMutableList()
        if (by == "ASC") {
            candidateList.value = list.sortedBy { it.yearsOfExperience }
        } else {
            candidateList.value = list.sortedByDescending { it.yearsOfExperience }
        }
    }
    fun dateSort(by: String) {
        val list = candidateList.value.toMutableList()
        if (by == "ASC") {
            candidateList.value = list.sortedBy { it.applicationDate }
        } else {
            candidateList.value = list.sortedByDescending { it.applicationDate }
        }
    }
}