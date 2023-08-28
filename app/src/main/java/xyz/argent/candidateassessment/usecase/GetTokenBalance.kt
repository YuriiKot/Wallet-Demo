package xyz.argent.candidateassessment.usecase

import xyz.argent.candidateassessment.app.Constants
import xyz.argent.candidateassessment.domain.token.model.Token
import xyz.argent.candidateassessment.domain.token.model.TokenBalance
import xyz.argent.candidateassessment.domain.token.repository.TokenRepository

class GetTokenBalance(
    private val tokenRepository: TokenRepository,
) {
    suspend fun execute(token: Token): TokenBalance {
        val balance = tokenRepository.getTokenBalance(
            Constants.walletAddress,
            token.address,
        )
        return TokenBalance(
            token,
            balance,
        )
    }
}
