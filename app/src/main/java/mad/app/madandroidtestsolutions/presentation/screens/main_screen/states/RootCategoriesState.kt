package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

import mad.app.madandroidtestsolutions.presentation.screens.BaseState
import mad.app.plptest.CategoryRootQuery

data class RootCategoriesState (
    override var isLoading: Boolean = false,
    val rootCategories: List<CategoryRootQuery.Child?>? = null,
    override var errorMessage: String = ""
):  BaseState()