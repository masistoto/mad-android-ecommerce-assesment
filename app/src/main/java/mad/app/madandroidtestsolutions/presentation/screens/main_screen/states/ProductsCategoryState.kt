package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

import mad.app.plptest.CategoryQuery

data class ProductsCategoryState(
    override var isLoading: Boolean = false,
    val productsForCategory: CategoryQuery.Products? = null,
    override var errorMessage: String = ""
) : BaseState()
