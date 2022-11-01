package it.hembik.stargazers.ui.input

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import it.hembik.stargazers.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor() : ViewModel() {

    private val _navigateToStargazers = SingleLiveEvent<Unit>()
    val navigateToStargazers: LiveData<Unit> = _navigateToStargazers

    private val _ownerError = MutableLiveData<Boolean>()
    val ownerError: LiveData<Boolean> = _ownerError

    private val _repoNameError = MutableLiveData<Boolean>()
    val repoNameError: LiveData<Boolean> = _repoNameError

    var owner: String = ""
        set(value) {
            field = value
            _ownerError.value = value.isEmpty() || value.length < 3
        }

    var repoName: String = ""
        set(value) {
            field = value
            _repoNameError.value = value.isEmpty() || value.length < 3
        }

    fun checkInputFields(owner: String, repoName: String) {
        this.owner = owner
        this.repoName = repoName
        if (ownerError.value == true || repoNameError.value == true) {
            return
        }
        _navigateToStargazers.call()
    }
}