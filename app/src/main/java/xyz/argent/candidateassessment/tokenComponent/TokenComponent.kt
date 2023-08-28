package xyz.argent.candidateassessment.tokenComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import xyz.argent.candidateassessment.basePresentation.ResourceState
import xyz.argent.candidateassessment.composeUtils.CardItem
import xyz.argent.candidateassessment.domain.token.model.Token


@Composable
fun TokenComponent(token: Token) {
    val tokenViewModel: TokenViewModel = koinViewModel(key = token.address, parameters = { parametersOf(token) })
    val tokenBalanceState by tokenViewModel.tokenBalance.collectAsState()

    CardItem(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (tokenBalanceState is ResourceState.Error) {
                tokenViewModel.retry()
            }
        }
    ) {
        Column {
            Text("${token.name ?: "N/A"} balance:")
            Spacer(Modifier.height(8.dp))

            when (val tokenBalance = tokenBalanceState) {
                is ResourceState.Error -> {
                    Text("Error loading, click to retry")
                }

                ResourceState.Initial -> Unit
                ResourceState.Loading -> Text("↺")
                is ResourceState.Success -> {
                    val symbol = tokenBalance.value.token.symbol
                    val symbolSuffix = if (symbol == null) "" else " $symbol"
                    Text(
                        tokenBalance.value.balanceDecimal.toString() + symbolSuffix
                    )
                }
            }
        }
    }
}
