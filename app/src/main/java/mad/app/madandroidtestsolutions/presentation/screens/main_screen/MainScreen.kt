package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel(),
               rootCategoriesState: LazyListState = rememberLazyListState(),
) {

    val state = mainViewModel.rootCategoriesState.value

    // horizontal root categories

    LazyRow(
        state = rootCategoriesState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        state.rootCategories?.size?.let { it ->
            items(it) {
                state.rootCategories[it]?.name?.let { it1 ->
                    Text(
                        text = it1,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.heightIn(15.dp))
            }
        }
    }


}

