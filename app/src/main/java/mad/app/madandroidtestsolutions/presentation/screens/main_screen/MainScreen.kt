package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mad.app.madandroidtestsolutions.R
import mad.app.madandroidtestsolutions.common.Utils.Companion.combineCurrencyAmount
import mad.app.madandroidtestsolutions.presentation.screens.LoadingSpinnerProgressBar

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(),
               rootCategoriesState: LazyListState = rememberLazyListState(),
               onItemClick: (String) -> Unit
) {

    val _rootCategoriesState = mainViewModel.rootCategoriesState.value
    val _productsForCategoryState = mainViewModel.productsForCategoryState.value
    val _productPaginateState = mainViewModel.productPaginateState.value

    LoadingSpinnerProgressBar(_rootCategoriesState.isLoading || _productsForCategoryState.isLoading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
    )
    {
        // HORIZONTAL ROOT CATEGORIES
        LazyRow(
            state = rootCategoriesState,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            _rootCategoriesState.rootCategories?.size?.let { it ->
                if(it > 0) {
                    items(it) {
                        _rootCategoriesState.rootCategories[it]?.name?.let { it1 ->
                            Text(
                                text = it1,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .clickable {
                                        _rootCategoriesState.rootCategories[it]?.uid?.let { categoryId ->
                                            mainViewModel.initPagination()
                                            _productsForCategoryState.selectedProductCategory =
                                                categoryId
                                            mainViewModel.fetchProductsForCategory(
                                                categoryId,
                                                _productPaginateState.currentPage,
                                                mainViewModel.pageSize
                                            )
                                        }
                                    }
                                    .background(
                                        color = if (_productsForCategoryState.selectedProductCategory == _rootCategoriesState.rootCategories[it]?.uid) Color.Gray else Color.LightGray,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .clip(RoundedCornerShape(5.dp))
                                    .padding(5.dp)
                            )
                        }
                    }
                }
                else{ // no products for this category

                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // VERTICAL CATEGORIES PRODUCTS
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            //contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            _productsForCategoryState.productsForCategory?.items?.size?.let { it ->
                items(it) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clickable {
                                    _productsForCategoryState.productsForCategory.items[it]
                                        .let { item -> item?.productListFragment?.uid }
                                        ?.let { productUid -> onItemClick(productUid) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .height(240.dp)
                                    .fillMaxSize()
                                    .background(color = Color.White),
                                contentScale = ContentScale.Crop,
                                painter = painterResource(R.drawable.product_dress),
                                contentDescription = _productsForCategoryState.productsForCategory.items[it]?.productListFragment?.brand
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            _productsForCategoryState.productsForCategory.items[it]?.productListFragment?.name?.let { it1 ->
                                Text(
                                    modifier = Modifier.align(Alignment.Start),
                                    text = it1,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 14.sp
                                )
                            }

                            _productsForCategoryState.productsForCategory.items[it]?.productListFragment?.brand?.let { it1 ->
                                Text(
                                    modifier = Modifier.align(Alignment.Start),
                                    text = it1,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 10.sp,
                                    color = Color.LightGray
                                )
                            }

                            Text(
                                modifier = Modifier.align(Alignment.Start),
                                text = combineCurrencyAmount(_productsForCategoryState.productsForCategory.items[it]?.productListFragment?.price_range?.priceRangeFragment?.maximum_price?.final_price?.currency?.rawValue.toString(), _productsForCategoryState.productsForCategory.items[it]?.productListFragment?.price_range?.priceRangeFragment?.maximum_price?.final_price?.value.toString()) ,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                            )
                        }
                    }
                }
            }
        }
    }

    NoProductItemsText(_productsForCategoryState.productsForCategory?.items?.size == 0)

    Spacer(modifier = Modifier.height(8.dp))

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
                onClick = { mainViewModel.prevPagination() },
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (_productPaginateState.showPrevButton) 1f else 0f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text(text = "<< Prev")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { mainViewModel.nextPagination() },
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (_productPaginateState.showNextButton) 1f else 0f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text(text = "Next>>")
            }
        }
    }
}

@Composable
fun NoProductItemsText(isShowing: Boolean) {
    if (isShowing) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No products for selected category :( ",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
fun BottomButtons(onPrevClick: () -> Unit, onNextClick: () -> Unit) {
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
                onClick = { onPrevClick() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    contentColor = Color.White)
            ) {
                Text(text = "<< Prev")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { onNextClick() },
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
