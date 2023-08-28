package xyz.argent.candidateassessment.domain.token.model

import java.math.BigDecimal
import java.math.RoundingMode

data class TokenBalance(
    val token: Token,
    val balance: Balance,
) {
    val balanceDecimal = run {
        val denominator = BigDecimal.TEN.pow(token.decimals.toInt())
        balance.value.toBigDecimal().divide(denominator, 4, RoundingMode.HALF_UP)
    }
}
