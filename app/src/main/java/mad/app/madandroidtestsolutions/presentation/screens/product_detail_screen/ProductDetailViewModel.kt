package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

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
import mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen.states.ProductDetailState

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _productDetailState = mutableStateOf(ProductDetailState())
    val productDetailState: State<ProductDetailState> = _productDetailState

    init {
        fetchProductDetail("MTIwOTIzMQ==") // TODO how to pass an argument on init?
    }

    private fun fetchProductDetail(productUid: String){
        getProductDetailUseCase(productUid).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _productDetailState.value = ProductDetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _productDetailState.value = ProductDetailState(productDetail = result.data)
                }
                is Resource.Error -> {
                    _productDetailState.value =
                        ProductDetailState(errorMessage = result.exception.message ?: "Unexpected error.")
                }
            }
        }.launchIn(viewModelScope)
    }
}