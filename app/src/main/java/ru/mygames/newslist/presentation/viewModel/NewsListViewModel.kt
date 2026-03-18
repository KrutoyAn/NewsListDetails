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
import ru.mygames.newslist.domain.repository.NewsRepository
import ru.mygames.newslist.domain.usecase.GetNewsListUseCase
import ru.mygames.newslist.presentation.newsList.NewsListUiState
import ru.mygames.newslist.presentation.newsList.NewsTab
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase,
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsListUiState(isLoading = true))
    val uiState: StateFlow<NewsListUiState> = _uiState.asStateFlow()

    init {
        loadNews()
        refresh()
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
                    it.copy(isLoading = false, error = error?.message ?: "Unknown Error")
                }
            }
            .launchIn(viewModelScope)
    }

    fun onTabSelected(tab: NewsTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.refreshNews()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}