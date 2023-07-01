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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mad.app.madandroidtestsolutions.R

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(),
               rootCategoriesState: LazyListState = rememberLazyListState(),
               categoriesState: LazyListState = rememberLazyListState(),
               onItemClick: (String) -> Unit
) {

    val _rootCategoriesState = mainViewModel.rootCategoriesState.value
    val _categoriesState = mainViewModel.categoriesState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
    )
    {
        // HORIZONTAL ROOT CATEGORIES
        LazyRow(
            state = rootCategoriesState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            _rootCategoriesState.rootCategories?.size?.let { it ->
                items(it) {
                    _rootCategoriesState.rootCategories[it]?.name?.let { it1 ->
                        Text(
                            text = it1,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .clickable { _rootCategoriesState.rootCategories[it]?.uid }
                        )
                    }
                    Spacer(modifier = Modifier.heightIn(15.dp).widthIn(15.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // VERTICAL CATEGORIES PRODUCTS

        LazyVerticalGrid(
            //state = categoriesState,
            columns = GridCells.Fixed(2),
            //horizontalArrangement = Arrangement.spacedBy(10.dp),
            //verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            _categoriesState.categories?.size?.let {
                items(it) {

                    Column {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(10.dp))
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    _categoriesState.categories[it].let { item -> item?.productListFragment?.uid }
                                        ?.let { it1 -> onItemClick(it1) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_launcher_background),
                                contentDescription = ""
                            )
                        }
                        _categoriesState.categories[it]?.productListFragment?.name?.let { it1 ->
                            Text(
                                text = it1,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.width(150.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .width(150.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "add price",
                                fontWeight = FontWeight(600),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

