package xyz.argent.candidateassessment.tokenComponent

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import xyz.argent.candidateassessment.basePresentation.BaseViewModel
import xyz.argent.candidateassessment.basePresentation.ResourceState
import xyz.argent.candidateassessment.basePresentation.catchResource
import xyz.argent.candidateassessment.domain.token.model.Token
import xyz.argent.candidateassessment.usecase.GetTokenBalance


class TokenViewModel(
    private val token: Token,
    private val getTokenBalance: GetTokenBalance,
) : BaseViewModel() {
    private val retry = MutableSharedFlow<Unit>()

    val tokenBalance = retry
        .onStart { emit(Unit) }
        .transformLatest {
            emit(ResourceState.Loading)
            val resource = catchResource {
                getTokenBalance.execute(token)
            }
            emit(resource)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, ResourceState.Loading)

    fun retry() {
        viewModelScope.launch {
            retry.emit(Unit)
        }
    }
}
