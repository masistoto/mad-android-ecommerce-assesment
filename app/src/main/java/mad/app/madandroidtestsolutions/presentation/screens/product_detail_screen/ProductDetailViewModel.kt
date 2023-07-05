package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mad.app.madandroidtestsolutions.domain.Resource
import mad.app.madandroidtestsolutions.domain.usecase.GetProductDetailUseCase
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen.states.ProductDetailState

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _productDetailState = mutableStateOf(ProductDetailState())
    val productDetailState: State<ProductDetailState> = _productDetailState

    init {
        val productUid = savedStateHandle.get<String>("productUid")
        if (productUid == null) {
            Log.e("productUid", "productUid empty, cannot get product details")
        } else {
            fetchProductDetail(productUid)
        }
    }

    private fun fetchProductDetail(productUid: String){
        getProductDetailUseCase(productUid).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _productDetailState.value = ProductDetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _productDetailState.value = ProductDetailState(productDetail = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    _productDetailState.value =
                        ProductDetailState(errorMessage = result.exception.message ?: "Unexpected error.", isLoading = false)
                }
            }
        }.launchIn(viewModelScope)
    }
}