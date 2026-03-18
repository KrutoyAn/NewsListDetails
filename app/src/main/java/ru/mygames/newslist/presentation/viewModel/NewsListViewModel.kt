package ru.mygames.newslist.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mygames.newslist.domain.usecase.GetNewsListUseCase
import ru.mygames.newslist.presentation.newsList.NewsListUiState
import ru.mygames.newslist.presentation.newsList.NewsTab
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState(isLoading = true))
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        getNewsListUseCase()
            .onEach { news ->
                _uiState.update {
                    it.copy(news = news, isLoading = false, error = null)
                }
            }
            .catch { error ->
                _uiState.update {
                    it.copy(isLoading = false, error = error.message)
                }
            }
            .launchIn(viewModelScope)
    }

    fun onTabSelected(tab: NewsTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun refresh() {
        viewModelScope.launch {
        }
    }
}