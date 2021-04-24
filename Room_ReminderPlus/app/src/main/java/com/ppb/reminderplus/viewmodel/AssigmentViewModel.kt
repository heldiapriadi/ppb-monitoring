package com.ppb.reminderplus

import androidx.lifecycle.*
import com.ppb.reminderplus.database.Assigment
import com.ppb.reminderplus.database.AssigmentRepository
import kotlinx.coroutines.launch

class AssigmentViewModel(private val repository: AssigmentRepository) : ViewModel() {

    // Using LiveData and caching what allAssigments returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allAssigments: LiveData<List<Assigment>> = repository.allAssigments.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(assigment: Assigment) = viewModelScope.launch {
        repository.insert(assigment)
    }
}

class AssigmentViewModelFactory(private val repository: AssigmentRepository)    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssigmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssigmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}