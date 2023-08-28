package xyz.argent.candidateassessment.tokensScreen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import xyz.argent.candidateassessment.basePresentation.BaseViewModel
import xyz.argent.candidateassessment.basePresentation.ResourceState
import xyz.argent.candidateassessment.basePresentation.catchResource
import xyz.argent.candidateassessment.usecase.GetTopTokens


class TokensViewModel(
    private val getTopTokens: GetTopTokens,
) : BaseViewModel() {
    private val queryInput = MutableStateFlow("")
    private val retry = MutableSharedFlow<Unit>()

    val tokens = retry
        .onStart { emit(Unit) }
        .flatMapLatest { queryInput }
        .debounce(400)
        .transformLatest { query ->
            if (query.length >= 2) {
                emit(ResourceState.Loading)
                val resource = catchResource {
                    getTopTokens.execute(query)
                }
                emit(resource)
            } else {
                emit(ResourceState.Initial)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ResourceState.Initial)

    val searchQueryState = queryInput.asStateFlow()

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            queryInput.emit(query)
        }
    }

    fun retry() {
        viewModelScope.launch {
            retry.emit(Unit)
        }
    }

}
