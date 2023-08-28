package xyz.argent.candidateassessment.domain.token.repository

import xyz.argent.candidateassessment.domain.token.model.Balance
import xyz.argent.candidateassessment.domain.token.model.Token

interface TokenRepository {
    suspend fun getTopTokens(query: String? = null): List<Token>
    suspend fun getTokenBalance(address: String, contractAddress: String): Balance
}
