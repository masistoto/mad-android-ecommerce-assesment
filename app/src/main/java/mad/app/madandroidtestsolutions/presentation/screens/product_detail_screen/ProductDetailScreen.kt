package mad.app.madandroidtestsolutions.presentation.screens.product_detail_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import mad.app.madandroidtestsolutions.R
import mad.app.madandroidtestsolutions.common.Utils.Companion.combineCurrencyAmount
import kotlin.math.roundToInt

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    popBack: () -> Unit // Navigate back to MainScreen
) {

    val state = viewModel.productDetailState.value

    if (state.productDetail != null){
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
            //ZoomableImage()
            val screenHeight = (LocalConfiguration.current).screenHeightDp
            val imageHeight: Int = (0.60 * screenHeight).roundToInt()
            Image(
                modifier = Modifier
                    .height(imageHeight.dp)
                    .fillMaxSize()
                    .background(color = Color.White),
                //contentScale = ContentScale.Crop,
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
            //Spacer(modifier = Modifier.height(8.dp))
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

            Spacer(modifier = Modifier.height(8.dp))
            // TODO and a simple quantity increase/decrease function

            // TODO Pagination button checks and logic
            QuantityButtons(
                viewModel = viewModel,
                onMinusClick = {
                    viewModel.productMinusQuantity()
                },
                onAddClick = {
                    viewModel.productAddQuantity()
                }
            )

            // TODO Bonus

            // Add to cart button
        }
    }
}
@Composable
fun ZoomableImage() {
    val scale = remember { mutableStateOf(1f) }
    val rotationState = remember { mutableStateOf(1f) }
    Box(
        modifier = Modifier
            .clip(RectangleShape)
            .size(300.dp)
            .background(Color.Gray)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    scale.value *= zoom
                    rotationState.value += rotation
                }
            }
    ) {
        Image(
            modifier = Modifier
                //.fillMaxSize()
                .align(Alignment.Center) // keep the image centralized into the Box
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(.5f, minOf(3f, scale.value)),
                    scaleY = maxOf(.5f, minOf(3f, scale.value)),
                    rotationZ = rotationState.value
                ),
            contentDescription = null,
            painter = painterResource(R.drawable.product_dress)
        )
    }
}

@Composable
fun QuantityButtons(viewModel: ProductDetailViewModel, onMinusClick: () -> Unit, onAddClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { onMinusClick() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text(text = "<< Prev")
            }
            Spacer(modifier = Modifier.width(16.dp))

            Text(text = viewModel.quantityCount.value.toString())

            Button(
                onClick = { onAddClick() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text(text = "Next>>")
            }
        }
    }
}