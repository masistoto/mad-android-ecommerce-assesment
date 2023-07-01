package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

import mad.app.plptest.CategoryQuery

data class CategoriesState(
    override var isLoading: Boolean = false,
    val categories: List<CategoryQuery.Item?>? = null, // TODO these are products
    override var errorMessage: String = ""
) : BaseState()
