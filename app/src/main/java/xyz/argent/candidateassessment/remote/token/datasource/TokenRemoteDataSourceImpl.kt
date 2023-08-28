package xyz.argent.candidateassessment.remote.token.datasource

import xyz.argent.candidateassessment.balanceRetriever.EtherscanApi
import xyz.argent.candidateassessment.data.token.datasource.TokenRemoteDataSource
import xyz.argent.candidateassessment.domain.token.model.Balance
import xyz.argent.candidateassessment.domain.token.model.Token
import xyz.argent.candidateassessment.domain.token.repository.TokenRepository
import xyz.argent.candidateassessment.tokenRegistry.EthExplorerApi
import java.math.BigInteger

class TokenRemoteDataSourceImpl(
    private val etherscanApi: EtherscanApi,
    private val ethExplorerApi: EthExplorerApi,
) : TokenRemoteDataSource {
    override suspend fun getTopTokens(): List<Token> {
        return ethExplorerApi.getTopTokens(10, "freekey")
            .tokens
            .map {
                Token(
                    it.address,
                    it.name,
                    it.symbol,
                    BigInteger(it.decimals ?: "0"),
                    it.image,
                )
            }
    }

    override suspend fun getTokenBalance(contractAddress: String, address: String): Balance {
        val balanceString = etherscanApi.getTokenBalance(
            contractAddress,
            address,
            "E5QFXD7ZYRH7THQM5PIXB8JD4GY38SEJZ4",
        ).result
        return Balance(BigInteger(balanceString))
    }
}
