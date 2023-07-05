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
import mad.app.madandroidtestsolutions.domain.usecase.GetProductsForCategoryUseCase
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.ProductPaginateState
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.ProductsCategoryState

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRootCategoryUseCase : GetRootCategoryUseCase,
    private val getProductsForCategoryUseCase: GetProductsForCategoryUseCase,
    //private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _rootCategoriesState = mutableStateOf(RootCategoriesState())
    val rootCategoriesState: State<RootCategoriesState> = _rootCategoriesState

    private val _productsForCategoryState = mutableStateOf(ProductsCategoryState())
    val productsForCategoryState: State<ProductsCategoryState> = _productsForCategoryState

    private val _productPaginateState = mutableStateOf(ProductPaginateState())
    val productPaginateState: State<ProductPaginateState> = _productPaginateState

    val pageSize = 20 // this does not change
    private var totalNumberOfProductsForCategory = 0

    init {
        fetchRootCategories()
        hideShowPaginateButtons()
    }

    fun initPagination(){
        _productPaginateState.value = ProductPaginateState(currentPage = 1, totalNumberOfPages = 0)
    }

    fun prevPagination(){
        if(_productPaginateState.value.currentPage != 1) {
            _productPaginateState.value.currentPage -= 1
            fetchProductsForCategory(_productsForCategoryState.value.selectedProductCategory, _productPaginateState.value.currentPage, 20)
            totalNumberOfProductsForCategory += pageSize
            // hide the button
            hideShowPaginateButtons()
        }
    }

    fun nextPagination(){
        if(totalNumberOfProductsForCategory > 0) {
            _productPaginateState.value.currentPage += 1
            fetchProductsForCategory(_productsForCategoryState.value.selectedProductCategory, _productPaginateState.value.currentPage, 20)
            totalNumberOfProductsForCategory -= pageSize
            // hide the button
            hideShowPaginateButtons()
        }
    }

    private fun hideShowPaginateButtons(){
        _productPaginateState.value.showPrevButton = _productPaginateState.value.currentPage > 1
        _productPaginateState.value.showNextButton = totalNumberOfProductsForCategory > 0
    }

    private fun fetchRootCategories(){
        getRootCategoryUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _rootCategoriesState.value = RootCategoriesState(isLoading = true)
                }
                is Resource.Success -> {
                    _rootCategoriesState.value = RootCategoriesState(rootCategories = result.data?.children, isLoading = false)
                    val firstCategoryId = result.data?.children?.firstOrNull()?.uid // whichever is the first, store could be for women/kids only
                    initPagination()
                    firstCategoryId?.let { categoryId -> fetchProductsForCategory(categoryId, _productPaginateState.value.currentPage, pageSize)} // initial call
                }
                is Resource.Error -> {
                    _rootCategoriesState.value =
                        RootCategoriesState(errorMessage = result.exception.message ?: "Unexpected error.", isLoading = false)
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
                    _productsForCategoryState.value = ProductsCategoryState(productsForCategory = result.data, isLoading = false)
                    _productsForCategoryState.value.selectedProductCategory = categoryId
                    if(pageNumber == 1 && _productsForCategoryState.value.productsForCategory != null) {
                        totalNumberOfProductsForCategory =
                            _productsForCategoryState.value.productsForCategory!!.total_count!! - pageSize
                    }
                }
                is Resource.Error -> {
                    _productsForCategoryState.value =
                        ProductsCategoryState(errorMessage = result.exception.message ?: "Unexpected error.", isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}