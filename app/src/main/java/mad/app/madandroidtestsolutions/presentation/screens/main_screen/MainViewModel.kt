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
import mad.app.madandroidtestsolutions.domain.usecase.GetCategoryUseCase
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.CategoriesState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRootCategoryUseCase : GetRootCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    //private val getProductsForCategoryUseCase: GetProductsForCategoryUseCase
) : ViewModel() {

    private val _rootCategoriesState = mutableStateOf(RootCategoriesState())
    val rootCategoriesState: State<RootCategoriesState> = _rootCategoriesState

    private val _categoriesState = mutableStateOf(CategoriesState())
    val categoriesState: State<CategoriesState> = _categoriesState

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
                    firstCategoryId?.let { categoryId -> fetchCategories(categoryId)}
                }
                is Resource.Error -> {
                    _rootCategoriesState.value =
                        RootCategoriesState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchCategories(categoryId: String){
        getCategoryUseCase(categoryId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _categoriesState.value = CategoriesState(isLoading = true)
                }
                is Resource.Success -> {
                    _categoriesState.value = CategoriesState(categories = result.data?.data?.products?.items ?: emptyList())
                    Log.d("CatalogExample",
                "The first page of mens category with CatId has these products:\n" +
                        "${
                            result.data?.data?.products?.items
                                ?.map {
                                    "${it?.name} (uid: ${it?.productListFragment?.uid}) and SKU ${it?.productListFragment?.sku}"
                                }
                                ?.joinToString(separator = "\n")
                        }")
                }
                is Resource.Error -> {
                    _categoriesState.value =
                        CategoriesState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }
}