package com.mohammadassad.githubusersearchapp.followingscreen.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammadassad.githubusersearchapp.followingscreen.domain.FetchFollowingUseCase
import com.mohammadassad.githubusersearchapp.remoteapi.models.GithubUserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val useCase: FetchFollowingUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle.get<String>("username") 
        ?: throw IllegalStateException("Username is required")

    private val _uiState = MutableStateFlow(FollowingUiState())
    val uiState: StateFlow<FollowingUiState> = _uiState.asStateFlow()

    init {
        fetchFollowing()
    }

    fun fetchFollowing() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            useCase.execute(username).collect { following ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        following = following,
                        errorMessage = if (following.isEmpty()) "No following found" else null
                    )
                }
            }
        }
    }
}

data class FollowingUiState(
    val following: List<GithubUserItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) 