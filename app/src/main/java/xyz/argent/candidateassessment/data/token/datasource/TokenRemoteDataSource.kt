package xyz.argent.candidateassessment.data.token.datasource

import xyz.argent.candidateassessment.domain.token.model.Balance
import xyz.argent.candidateassessment.domain.token.model.Token

interface TokenRemoteDataSource {
    suspend fun getTopTokens(): List<Token>
    suspend fun getTokenBalance(contractAddress: String, address: String): Balance
}