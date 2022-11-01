package it.hembik.stargazers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import it.hembik.stargazers.tools.utils.getOrAwaitValueTest
import it.hembik.stargazers.ui.input.InputViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InputViewModelTest {
    private lateinit var viewModel: InputViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        viewModel = InputViewModel()
    }

    @Test
    fun `when set empty owner ownerError is true`() {
        // Given
        viewModel.owner = ""

        // When
        val actual = viewModel.ownerError

        // Then
        assertThat(actual.value).isTrue()
    }

    @Test
    fun `when set wrong lenght owner ownerError is true`() {
        // Given
        viewModel.owner = "aa"

        // When
        val actual = viewModel.ownerError

        // Then
        assertThat(actual.value).isTrue()
    }

    @Test
    fun `when set empty repo name repoNameError is true`() {
        // Given
        viewModel.repoName = ""

        // When
        val actual = viewModel.repoNameError

        // Then
        assertThat(actual.value).isTrue()
    }

    @Test
    fun `when set wrong length repo name repoNameError is true`() {
        // Given
        viewModel.repoName = "aa"

        // When
        val actual = viewModel.repoNameError

        // Then
        assertThat(actual.value).isTrue()
    }

    @Test
    fun `when give valid repo name and owner name repo navigate is called`() {
        // Given
        val repoName = "MVIUsers"
        val owner = "yaroslav-hembik"

        // When
        viewModel.checkInputFields(owner, repoName)
        val actual = viewModel.navigateToStargazers.getOrAwaitValueTest()

        // Then
        assertThat(actual).isNotNull()
    }
}