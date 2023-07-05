package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

import mad.app.madandroidtestsolutions.presentation.screens.BaseState
import mad.app.plptest.CategoryQuery

data class ProductsCategoryState(
    override var isLoading: Boolean = false,
    val productsForCategory: CategoryQuery.Products? = null,
    var selectedProductCategory: String = "",
    override var errorMessage: String = ""
) : BaseState()
