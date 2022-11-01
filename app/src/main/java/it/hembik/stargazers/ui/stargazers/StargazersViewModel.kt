package it.hembik.stargazers.ui.stargazers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import it.hembik.domain.model.Stargazer
import it.hembik.domain.usecase.GetStargazersUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StargazersViewModel @Inject constructor(
    private val getStargazersUseCase: GetStargazersUseCase
) : ViewModel() {

    private val _pagingData = MutableLiveData<PagingData<Stargazer>>()
    val pagingData: LiveData<PagingData<Stargazer>> = _pagingData

    fun getStargazers(owner: String, repoName: String) {
        viewModelScope.launch {
            getStargazersUseCase.invoke(owner, repoName).cachedIn(viewModelScope).collect {
                _pagingData.value = it
            }
        }
    }
}