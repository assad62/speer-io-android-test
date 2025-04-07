package com.mohammadassad.githubusersearchapp.userscreen.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohammadassad.githubusersearchapp.common.VersionControlUser
import com.mohammadassad.githubusersearchapp.userscreen.domain.FetchUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val useCase: FetchUserProfileUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val username: String = savedStateHandle.get<String>("username") 
        ?: throw IllegalStateException("Username is required")

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    init {
        Log.d("UserProfileViewModel", "Initializing with username: $username")
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            try {
                Log.d("UserProfileViewModel", "Starting to fetch user profile")
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                
                useCase.execute(username).collect { user ->
                    Log.d("UserProfileViewModel", "Received user data: $user")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = user,
                            errorMessage = if (user == null) "Failed to load profile" else null
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("UserProfileViewModel", "Error in fetchUserProfile", e)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.message ?: "Unknown error"}"
                    )
                }
            }
        }
    }
}

data class UserProfileUiState(
    val user: VersionControlUser? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) 