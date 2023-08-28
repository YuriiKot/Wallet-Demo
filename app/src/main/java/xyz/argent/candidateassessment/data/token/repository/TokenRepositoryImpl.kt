package xyz.argent.candidateassessment.data.token.repository

import xyz.argent.candidateassessment.data.token.datasource.BalanceMemoryDataSource
import xyz.argent.candidateassessment.data.token.datasource.TokenRemoteDataSource
import xyz.argent.candidateassessment.data.token.datasource.TopTokensMemoryDataSource
import xyz.argent.candidateassessment.domain.token.model.Balance
import xyz.argent.candidateassessment.domain.token.model.Token
import xyz.argent.candidateassessment.domain.token.repository.TokenRepository
import java.util.concurrent.TimeUnit

class TokenRepositoryImpl(
    private val tokenRemoteDataSource: TokenRemoteDataSource,
    private val tokenBalanceLocalDataSource: BalanceMemoryDataSource,
    private val topTokensMemoryDataSource: TopTokensMemoryDataSource,
) : TokenRepository {
    companion object {
        val BALANCE_FRESHNESS = TimeUnit.MILLISECONDS.convert(5, TimeUnit.MINUTES)
        val TOP_TOKENS_FRESHNESS = TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES)
    }

    override suspend fun getTopTokens(query: String?): List<Token> {
        val tokens = topTokensMemoryDataSource.getTopTokens(TOP_TOKENS_FRESHNESS)
            ?: tokenRemoteDataSource.getTopTokens().also {
                topTokensMemoryDataSource.saveTokens(it)
            }
        if (query == null) return tokens
        return tokens
            .filter { token ->
                token.name?.contains(query, true) == true
                        || token.symbol?.contains(query, true) == true
            }
    }

    override suspend fun getTokenBalance(address: String, contractAddress: String): Balance {
        return tokenBalanceLocalDataSource.getTokenBalance(address, contractAddress, BALANCE_FRESHNESS)
            ?: tokenRemoteDataSource.getTokenBalance(contractAddress, address).also {
                tokenBalanceLocalDataSource.saveTokenBalance(address, contractAddress, it)
            }
    }
}
