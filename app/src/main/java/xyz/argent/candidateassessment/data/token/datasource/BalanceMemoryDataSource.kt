package xyz.argent.candidateassessment.data.token.datasource

import xyz.argent.candidateassessment.domain.token.model.Balance

interface BalanceMemoryDataSource {
    class BalanceTimeStamped(
        val balance: Balance,
        val timestamp: Long,
    )

    suspend fun saveTokenBalance(
        address: String,
        contractAddress: String,
        tokenBalance: Balance,
    )

    suspend fun getTokenBalance(
        address: String,
        contractAddress: String,
        freshnessMillis: Long = 0L,
    ): Balance?
}