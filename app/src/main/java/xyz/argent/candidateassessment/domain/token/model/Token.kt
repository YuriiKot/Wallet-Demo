package xyz.argent.candidateassessment.domain.token.model

import java.math.BigInteger

data class Token(
    val address: String,
    val name: String?,
    val symbol: String?,
    val decimals: BigInteger,
    val image: String?,
)

