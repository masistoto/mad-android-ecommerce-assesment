package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mad.app.madandroidtestsolutions.domain.usecase.GetRootCategoryUseCase
import mad.app.madandroidtestsolutions.presentation.screens.main_screen.states.RootCategoriesState
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.domain.usecase.GetProductsForCategoryUseCase
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

    private val _prevPageState = MutableLiveData(true)
    val prevPageState: LiveData<Boolean> = _prevPageState

    private val _nextPageState = MutableLiveData(true)
    val nextPageState: LiveData<Boolean> = _nextPageState

    var pageNumber = 1
    val pageSize = 20 // this does not change
    private var totalNumberOfProductsForCategory = 0

    init {
        fetchRootCategories()
    }

    fun initPagination(){
        pageNumber = 1
        //pageSize = 20 // this does not change
        _nextPageState.value = false
    }

    fun prevPagination(): Boolean{
        if(pageNumber > 1)
            pageNumber -= 1
        //pageSize = 20 // this does not change
        return true
    }

    fun nextPagination(): Boolean{


        return true

//        if(totalNumberOfProductsForCategory > 0) {
//            pageNumber += 1
//            totalNumberOfProductsForCategory -= pageSize
//
//            // grey and disable the button
//            _nextPageState.value = totalNumberOfProductsForCategory > 0
//            return true
//        }
//        //pageSize = 20 // this does not change
//        return false
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
                    initPagination()
                    firstCategoryId?.let { categoryId -> fetchProductsForCategory(categoryId, pageNumber, pageSize)} // initial call
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
                    _productsForCategoryState.value.selectedProductCategory = categoryId
                    if(pageNumber == 1 && productsForCategoryState.value.productsForCategory != null) {
                        totalNumberOfProductsForCategory =
                            _productsForCategoryState.value.productsForCategory!!.total_count!!
                    }
                }
                is Resource.Error -> {
                    _productsForCategoryState.value =
                        ProductsCategoryState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }
}