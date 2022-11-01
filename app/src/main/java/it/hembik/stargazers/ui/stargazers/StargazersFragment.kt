package it.hembik.stargazers.ui.stargazers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import it.hembik.domain.exceptions.StargazersNotFoundException
import it.hembik.stargazers.R
import it.hembik.stargazers.databinding.FragmentStargazersBinding

@AndroidEntryPoint
class StargazersFragment : Fragment(R.layout.fragment_stargazers) {
    private var _binding: FragmentStargazersBinding? = null
    private val binding get() = _binding!!

    private val viewModel by navGraphViewModels<StargazersViewModel>(R.id.nav_graph) { defaultViewModelProviderFactory }
    private val args: StargazersFragmentArgs by navArgs()

    private val adapter = StargazersPagerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStargazersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = adapter


        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                binding.progressDialog.isVisible = true
            } else {
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                binding.rv.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                binding.progressDialog.isVisible = false
                binding.message.isVisible = true
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                if (isListEmpty) {
                    // show empty list
                    binding.message.text = getString(R.string.empty_list, args.owner, args.repoName)
                    binding.emptyList.isVisible = true
                } else {
                    binding.message.text =
                        getString(R.string.stargazers_for, args.repoName, args.owner)
                }

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    when (it.error) {
                        is StargazersNotFoundException -> {
                            binding.message.text = getString(R.string.repo_not_found)
                        }
                        else -> {
                            binding.message.text = getString(R.string.service_error)
                        }
                    }
                }
            }
        }

        viewModel.getStargazers(args.owner, args.repoName)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.pagingData.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}