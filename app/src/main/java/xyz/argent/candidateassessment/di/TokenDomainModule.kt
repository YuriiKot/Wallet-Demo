package xyz.argent.candidateassessment.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import xyz.argent.candidateassessment.data.token.datasource.BalanceMemoryDataSource
import xyz.argent.candidateassessment.data.token.datasource.TokenRemoteDataSource
import xyz.argent.candidateassessment.data.token.datasource.TopTokensMemoryDataSource
import xyz.argent.candidateassessment.data.token.repository.TokenRepositoryImpl
import xyz.argent.candidateassessment.domain.token.repository.TokenRepository
import xyz.argent.candidateassessment.memory.token.datasource.BalanceMemoryDataSourceImpl
import xyz.argent.candidateassessment.memory.token.datasource.TopTokensMemoryDataSourceImpl
import xyz.argent.candidateassessment.remote.token.datasource.TokenRemoteDataSourceImpl
import xyz.argent.candidateassessment.usecase.GetTokenBalance
import xyz.argent.candidateassessment.usecase.GetTopTokens

val tokenDomainModule = module {
    singleOf(::GetTokenBalance)
    singleOf(::GetTopTokens)
    singleOf(::TokenRepositoryImpl) { bind<TokenRepository>() }
    singleOf(::TokenRemoteDataSourceImpl) { bind<TokenRemoteDataSource>() }
    singleOf(::BalanceMemoryDataSourceImpl) { bind<BalanceMemoryDataSource>() }
    singleOf(::TopTokensMemoryDataSourceImpl) { bind<TopTokensMemoryDataSource>() }
}
