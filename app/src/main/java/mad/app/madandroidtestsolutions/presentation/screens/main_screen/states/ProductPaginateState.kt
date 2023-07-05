package mad.app.madandroidtestsolutions.presentation.screens.main_screen.states

data class ProductPaginateState (
    var showPrevButton: Boolean = false,
    var showNextButton: Boolean = true,
    var currentPage: Int = 1,
    var totalNumberOfPages: Int = 0
)