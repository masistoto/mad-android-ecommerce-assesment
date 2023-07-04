package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    popBack: () -> Unit // Navigate back to MainScreen
) {

    val state = viewModel.productDetailState.value

    // TODO check if we have the data we need to display
    if (state.productDetail != null){
        //Text(state.productDetail.toString())

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(text ="Back",
                    Modifier.clickable {
                        popBack()
                     }
                )
            }
        }
    }

    // TODO 2. A Product Detail Page (PDP) which will be navigated to after tapping a product in the PLP. This PDP will show a
    // large image,
    // name,
    // price,
    // description
    // and a simple quantity increase/decrease function

    // TODO 4. Making the image on the PDP tappable to animate to fullscreen, and then be zoomable from there.
    // we might need an extra service for this some thing like getProductImages(productUid), with a list of urls for lazy loading
}