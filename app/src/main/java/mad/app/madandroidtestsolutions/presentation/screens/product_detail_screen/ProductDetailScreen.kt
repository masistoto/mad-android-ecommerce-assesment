package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import mad.app.madandroidtestsolutions.R
import mad.app.madandroidtestsolutions.common.Utils.Companion.combineCurrencyAmount
import mad.app.madandroidtestsolutions.presentation.screens.LoadingSpinnerProgressBar
import kotlin.math.roundToInt

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    popBack: () -> Unit // Navigate back to MainScreen
) {
    val state = viewModel.productDetailState.value

    LoadingSpinnerProgressBar(state.isLoading)

    if (state.productDetail != null){
        state.isLoading = false
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(.10.dp)
        )
        {
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

            // large image
            val screenHeight = (LocalConfiguration.current).screenHeightDp
            val imageHeight: Int = (0.60 * screenHeight).roundToInt()
            val scale = remember { mutableStateOf(1f) }
            Image(
                modifier = Modifier
                    .height(imageHeight.dp)
                    .fillMaxSize()
                    .background(color = Color.White)
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rotation ->
                            scale.value *= zoom
                        }
                    }
                    .graphicsLayer(
                        scaleX = maxOf(.5f, minOf(3f, scale.value)),
                        scaleY = maxOf(.5f, minOf(3f, scale.value))
                    ),
                painter = painterResource(R.drawable.product_dress),
                contentDescription = "product dress"
            )

            Spacer(modifier = Modifier.height(8.dp))
            // product name
            state.productDetail.productFragment.productListFragment.name?.let { productName ->
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                )
            }
            //Spacer(modifier = Modifier.height(8.dp))
            // price
            state.productDetail.productFragment.productListFragment.price_range.priceRangeFragment.minimum_price.let { productPrice ->
                Text(
                    text = combineCurrencyAmount(
                        productPrice.final_price.currency?.name.toString(),
                        productPrice.final_price.value.toString()
                    ),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                )
            }

            // description
            state.productDetail.productFragment.description?.html?.let { productDescription ->
//                AndroidView(
//                    modifier = Modifier
//                        .align(Alignment.Start)
//                        .padding(15.dp),
//                    factory = {
//                        MaterialTextView(it)
//                    },
//                    update = {
//                        it.text = productDescription
//                    }
//                )
                
                            Text(
                    text = HtmlCompat.fromHtml(productDescription, HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
                    fontSize = 15.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                )
            }
            QuantityButtons()
        }
    }
}

@Composable
fun QuantityButtons() {
    val quantityCountState = remember {mutableStateOf(1)}
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(16.dp)
        ) {
            Text(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "-",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(30.dp)
                    .wrapContentHeight()
                    .clickable {
                        if(quantityCountState.value > 1) {
                            quantityCountState.value-- // TODO add a limit
                        }
                    }
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .padding(5.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                textAlign = TextAlign.Center,
                text = quantityCountState.value.toString(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align (Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "+",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(30.dp)
                    .wrapContentHeight()
                    .clickable {
                        quantityCountState.value++ // TODO add a limit
                    }
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .clip(RoundedCornerShape(5.dp))
                    .padding(5.dp)
            )
        }
    }
}