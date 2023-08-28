package xyz.argent.candidateassessment.tokensScreen

import android.os.Bundle
import android.view.View
import com.example.shortbooks.ui.theme.AppTheme
import org.koin.android.ext.android.inject
import xyz.argent.candidateassessment.basePresentation.BaseFragment
import xyz.argent.candidateassessment.databinding.FragmentTokensBinding


class TokensFragment : BaseFragment() {
    private val views by viewBinding(FragmentTokensBinding::inflate)

    private val viewModel: TokensViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        views.root.setContent {
            AppTheme {
                TokensScreen(viewModel)
            }
        }
    }
}
