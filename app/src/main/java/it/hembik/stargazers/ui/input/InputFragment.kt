package it.hembik.stargazers.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import it.hembik.stargazers.R
import it.hembik.stargazers.databinding.FragmentInputBinding

@AndroidEntryPoint
class InputFragment : Fragment(R.layout.fragment_input) {
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setObservers()
    }

    private fun setupUI() {
        with(binding) {
            showBtn.setOnClickListener {
                viewModel.checkInputFields(ownerTiet.text.toString(), repoTiet.text.toString())
            }
        }
    }

    private fun setObservers() {
        viewModel.ownerError.observe(viewLifecycleOwner) { error ->
            binding.ownerTil.error = if (error) {
                getString(R.string.error_owner)
            } else {
                null
            }
        }

        viewModel.repoNameError.observe(viewLifecycleOwner) { error ->
            binding.repoTil.error = if (error) {
                getString(R.string.error_repo_name)
            } else {
                null
            }
        }

        viewModel.navigateToStargazers.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_inputFragment_to_stargazersFragment, bundleOf(
                    "owner" to binding.ownerTiet.text.toString(),
                    "repoName" to binding.repoTiet.text.toString()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}