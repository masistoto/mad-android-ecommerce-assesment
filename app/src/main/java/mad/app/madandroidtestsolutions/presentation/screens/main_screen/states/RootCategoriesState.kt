package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

import mad.app.plptest.CategoryRootQuery

data class RootCategoriesState (
    val isLoading: Boolean = false,
    val rootCategories: CategoryRootQuery.CategoryList? = null,
    val errorMessage: String = ""
)