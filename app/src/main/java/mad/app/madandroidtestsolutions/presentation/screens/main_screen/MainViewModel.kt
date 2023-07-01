package mad.app.madandroidtestsolutions.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import mad.app.madandroidtestsolutions.domain.usecase.GetCategoryUseCase
import mad.app.madandroidtestsolutions.domain.usecase.GetProductsForCategoryUseCase
import mad.app.madandroidtestsolutions.domain.usecase.GetRootCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRootCategoryUseCase : GetRootCategoryUseCase,
    //private val getCategoryUseCase: GetCategoryUseCase,
    //private val getProductsForCategoryUseCase: GetProductsForCategoryUseCase
) : ViewModel() {

}