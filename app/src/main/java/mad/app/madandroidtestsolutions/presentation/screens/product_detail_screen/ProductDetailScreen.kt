package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    popBack: () -> Unit // Navigate back to MainScreen
) {

    val state = viewModel.productDetailState.value

    // TODO check if we have the data we need to display
    if (state.productDetail != null){
        Text(state.productDetail.toString())
    }

    // TODO 4. Making the image on the PDP tappable to animate to fullscreen, and then be zoomable from there.
    // we might need an extra service for this some thing like getProductImages(productUid), with a list of urls for lazy loading
}