package com.space.swagger.ui

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import com.space.swagger.model.Article
import com.space.swagger.repository.ArticleRepository
import com.space.swagger.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ArticleViewModel
@Inject
constructor(
    private val articleRepository: ArticleRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mutableDataState: MutableLiveData<DataState<List<Article>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Article>>>
        get() = mutableDataState


    fun loadArticles() {
        viewModelScope.launch {

            articleRepository.getArticles()
                .onEach { dataState ->
                    mutableDataState.value = dataState
                }
                .launchIn(viewModelScope)

        }
    }
}
