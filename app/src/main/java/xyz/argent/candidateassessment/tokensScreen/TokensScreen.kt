package xyz.argent.candidateassessment.tokensScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import xyz.argent.candidateassessment.basePresentation.ResourceState
import xyz.argent.candidateassessment.tokenComponent.TokenComponent

@Composable
fun TokensScreen(viewModel: TokensViewModel) {
    val query by viewModel.searchQueryState.collectAsState()
    remember { mutableStateOf("Enter Text") }
    val focusRequester = remember { FocusRequester() }
    val tokens by viewModel.tokens.collectAsState()
    Column {
        TextField(
            value = query,
            onValueChange = { viewModel.onQueryChanged(it) },
            placeholder = { Text("Search for you tokens here...") },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        when (val tokensLocal = tokens) {

            is ResourceState.Error -> {
                Column {
                    Text("Error loading")
                    Button(onClick = { viewModel.retry() }) {
                        Text("Retry")
                    }
                }
            }

            ResourceState.Loading -> {
                Surface {
                    Text("Loading")
                }
            }

            is ResourceState.Success -> {
                if(tokensLocal.value.isEmpty()){
                    Surface {
                        Text("No tokens")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(tokensLocal.value) { token ->
                            TokenComponent(token)
                        }
                    }
                }
            }

            ResourceState.Initial -> Unit
        }
    }
}