package xyz.argent.candidateassessment.memory.token.datasource

import xyz.argent.candidateassessment.data.token.datasource.TopTokensMemoryDataSource
import xyz.argent.candidateassessment.domain.token.model.Token

class TopTokensMemoryDataSourceImpl : TopTokensMemoryDataSource {
    private var value: TopTokensMemoryDataSource.TokenTimeStamped? = null

    override suspend fun saveTokens(tokens: List<Token>) {
        value = TopTokensMemoryDataSource.TokenTimeStamped(tokens, System.currentTimeMillis())
    }

    override suspend fun getTopTokens(freshnessMillis: Long): List<Token>? {
        return value
            ?.takeIf { System.currentTimeMillis() - it.timestamp < freshnessMillis }
            ?.tokens
    }
}