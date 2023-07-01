package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import android.util.Log
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
import mad.app.madandroidtestsolutions.domain.usecase.GetProductsForCategoryUseCase
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.ProductsCategoryState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRootCategoryUseCase : GetRootCategoryUseCase,
    private val getProductsForCategoryUseCase: GetProductsForCategoryUseCase
) : ViewModel() {

    private val _rootCategoriesState = mutableStateOf(RootCategoriesState())
    val rootCategoriesState: State<RootCategoriesState> = _rootCategoriesState

    private val _productsForCategoryState = mutableStateOf(ProductsCategoryState())
    val productsForCategoryState: State<ProductsCategoryState> = _productsForCategoryState

    init {
        fetchRootCategories()
    }

    private fun fetchRootCategories(){
        getRootCategoryUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _rootCategoriesState.value = RootCategoriesState(isLoading = true)
                }
                is Resource.Success -> {
                    _rootCategoriesState.value = RootCategoriesState(rootCategories = result.data?.children)
                    val firstCategoryId = result.data?.children?.firstOrNull()?.uid // whichever is the first, store could be for women/kids only
                    firstCategoryId?.let { categoryId -> fetchProductsForCategory(categoryId, 1, 20)} // initial call
                }
                is Resource.Error -> {
                    _rootCategoriesState.value =
                        RootCategoriesState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun fetchProductsForCategory(categoryId: String, pageNumber: Int, pageSize: Int){ // called by pagination, keep/maintain on screen? figure out later
        // TODO pageNo and size will come from pagination
        getProductsForCategoryUseCase(categoryId, pageNumber, pageSize).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _productsForCategoryState.value = ProductsCategoryState(isLoading = true)
                }
                is Resource.Success -> {
                    _productsForCategoryState.value = ProductsCategoryState(productsForCategory = result.data)
                }
                is Resource.Error -> {
                    _productsForCategoryState.value =
                        ProductsCategoryState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }
}