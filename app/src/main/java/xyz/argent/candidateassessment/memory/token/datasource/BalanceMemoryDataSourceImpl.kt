package xyz.argent.candidateassessment.memory.token.datasource

import xyz.argent.candidateassessment.data.token.datasource.BalanceMemoryDataSource
import xyz.argent.candidateassessment.domain.token.model.Balance

class BalanceMemoryDataSourceImpl : BalanceMemoryDataSource {
    private val map = mutableMapOf<Pair<String, String>, BalanceMemoryDataSource.BalanceTimeStamped>()
    override suspend fun saveTokenBalance(address: String, contractAddress: String, tokenBalance: Balance) {
        map[address to contractAddress] =
            BalanceMemoryDataSource.BalanceTimeStamped(tokenBalance, System.currentTimeMillis())
    }

    override suspend fun getTokenBalance(
        address: String,
        contractAddress: String,
        freshnessMillis: Long,
    ): Balance? {
        return map[address to contractAddress]
            ?.takeIf { System.currentTimeMillis() - it.timestamp < freshnessMillis }
            ?.balance
    }
}