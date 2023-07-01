package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mad.app.madandroidtestsolutions.domain.usecase.GetRootCategoryUseCase
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.RootCategoriesState
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mad.app.madandroidtestsolutions.domain.Resource

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRootCategoryUseCase : GetRootCategoryUseCase,
    //private val getCategoryUseCase: GetCategoryUseCase,
    //private val getProductsForCategoryUseCase: GetProductsForCategoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RootCategoriesState())
    val rootCategoriesState: State<RootCategoriesState> = _state

    init {
        fetchRootCategories()
    }

    private fun fetchRootCategories(){
        getRootCategoryUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = RootCategoriesState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = RootCategoriesState(rootCategories = result.data?.children)
                }
                is Resource.Error -> {
                    _state.value =
                        RootCategoriesState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }
}