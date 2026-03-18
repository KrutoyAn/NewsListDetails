package ru.mygames.newslist.presentation.viewModel

import androidx.lifecycle.SavedStateHandle
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
import ru.mygames.newslist.domain.usecase.GetNewsDetailUseCase
import ru.mygames.newslist.presentation.newsList.NewsDetailUiState
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsDetailUseCase: GetNewsDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val newsId: String = checkNotNull(savedStateHandle["newsId"])

    private val _uiState = MutableStateFlow(NewsDetailUiState(isLoading = true))
    val uiState: StateFlow<NewsDetailUiState> = _uiState.asStateFlow()

    init {
        loadNewsDetail()
    }

    private fun loadNewsDetail() {
        getNewsDetailUseCase(newsId)
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
}