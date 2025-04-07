package com.mohammadassad.githubusersearchapp.searchscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.core.Resource
import com.mohammadassad.githubusersearchapp.searchscreen.domain.FetchUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchAndListViewModel @Inject constructor(
    private val useCase: FetchUserListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserSearchAndListUiState())
    val uiState: StateFlow<UserSearchAndListUiState> = _uiState.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    private var searchJob: Job? = null

    // Auto-search is disabled by default, can be toggled in the UI
    private val _autoSearchEnabled = MutableStateFlow(false)
    val autoSearchEnabled: StateFlow<Boolean> = _autoSearchEnabled.asStateFlow()

    init {
        setupAutoSearch()
    }

    @OptIn(FlowPreview::class)
    private fun setupAutoSearch() {
        _searchInput
            .debounce(800) // Wait for typing to stop for 800ms
            .onEach { text ->
                if (text.isNotEmpty() && _autoSearchEnabled.value) {
                    performSearch(text)
                }
            }
            .launchIn(viewModelScope)
    }

    fun toggleAutoSearch() {
        _autoSearchEnabled.update { !it }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update { it.copy(searchText = text) }
        _searchInput.value = text

        if (text.isEmpty()) {
            resetSearchState()
        }
    }

    private fun resetSearchState() {
        _uiState.update {
            it.copy(
                hasSearched = false,
                users = emptyList(),
                errorMessage = null
            )
        }
    }

    fun searchUsers() {
        val searchText = _uiState.value.searchText
        if (searchText.isEmpty()) {
            resetSearchState()
            return
        }

        performSearch(searchText)
    }

    private fun performSearch(query: String) {
        // Cancel any ongoing search
        searchJob?.cancel()

        _uiState.update {
            it.copy(
                isSearching = true,
                errorMessage = null
            )
        }

        searchJob = viewModelScope.launch {
            // Add a small delay for better UX when results load quickly
            delay(300)

            useCase.execute(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isSearching = true) }
                    }
                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isSearching = false,
                                hasSearched = true,
                                users = resource.data ?: emptyList(),
                                lastSearchQuery = query
                            )
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update {
                            it.copy(
                                isSearching = false,
                                hasSearched = true,
                                errorMessage = resource.message,
                                lastSearchQuery = query
                            )
                        }
                    }
                }
            }
        }
    }

    fun retryLastSearch() {
        val lastQuery = _uiState.value.lastSearchQuery
        if (lastQuery.isNotEmpty()) {
            performSearch(lastQuery)
        }
    }
}

data class UserSearchAndListUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val users: List<VersionControlUser> = emptyList(),
    val errorMessage: String? = null,
    val hasSearched: Boolean = false,
    val lastSearchQuery: String = ""
)