package xyz.argent.candidateassessment.data.token.datasource

import xyz.argent.candidateassessment.domain.token.model.Token

interface TopTokensMemoryDataSource {
    class TokenTimeStamped(
        val tokens: List<Token>,
        val timestamp: Long,
    )

    suspend fun saveTokens(
        tokens: List<Token>,
    )

    suspend fun getTopTokens(
        freshnessMillis: Long = 0L,
    ): List<Token>?
}