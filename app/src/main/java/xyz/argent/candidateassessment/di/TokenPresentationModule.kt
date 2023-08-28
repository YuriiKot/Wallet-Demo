package xyz.argent.candidateassessment.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import xyz.argent.candidateassessment.tokenComponent.TokenViewModel
import xyz.argent.candidateassessment.tokensScreen.TokensViewModel

val tokenPresentationModule = module {
    viewModelOf(::TokensViewModel)
    viewModel { params -> TokenViewModel(params.get(), get()) }
}