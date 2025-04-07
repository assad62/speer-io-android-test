package com.mohammadassad.githubusersearchapp.followersscreen.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammadassad.githubusersearchapp.followersscreen.domain.FetchFollowersUseCase
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val useCase: FetchFollowersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle.get<String>("username") 
        ?: throw IllegalStateException("Username is required")

    private val _uiState = MutableStateFlow(FollowersUiState())
    val uiState: StateFlow<FollowersUiState> = _uiState.asStateFlow()

    init {
        fetchFollowers()
    }

    fun fetchFollowers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            useCase.execute(username).collect { followers ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        followers = followers,
                        errorMessage = if (followers.isEmpty()) "No followers found" else null
                    )
                }
            }
        }
    }
}

data class FollowersUiState(
    val followers: List<GithubUserItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) 