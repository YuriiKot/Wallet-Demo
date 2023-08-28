package xyz.argent.candidateassessment.usecase

import xyz.argent.candidateassessment.domain.token.model.Token
import xyz.argent.candidateassessment.domain.token.repository.TokenRepository

class GetTopTokens(
    private val tokenRepository: TokenRepository,
) {
    suspend fun execute(query: String): List<Token> {
        return tokenRepository.getTopTokens(query)
    }
}