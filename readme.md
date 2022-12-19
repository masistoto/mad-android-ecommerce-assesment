# MAD Android Assessment

### Description
This is a basic Android project with some included code. It is intended to from the basis for a Technical Assessment. The assessment consists of developing two very simple features that are common across many e-commerce applications.

The project includes a single activity; a GraphQL schema; integration with Apollo Kotlin (https://www.apollographql.com/docs/kotlin/), a simple API Service providing coroutine access to simple API functions. 

The included API functions are in [ICatalogApiService.kt](https://github.com/mad9000/mad-android-ecommerce-assesment/blob/main/app/src/main/java/mad/app/madandroidtestsolutions/service/catalog/ICatalogApiService.kt):
1. `fetchRootCategory` - Fetches root category for E-commerce platform
2. `getCategory` - Fetches category and all metadata for category based on uid
3. `getProductsForCategory` - Fetches all products for category. Wrapper around `getCategory`
4. `getProduct` - Gets detailed information on a particular product, given a uid

The basic `MainActivity.kt` currently contains a lifecycle method that will fetch some data and print it to Logcat. This can be used a guide to how the API works. 


### Specification
The assessment requires two features be delivered:
1. A Product Listing Page (PLP) which will scroll and perform paging. The maximum page size to be used with the API is 20. 
2. ~~A Product Detail Page (PDP) which will be navigated to after tapping a product in the PLP. This PDP will show a large image, name, price, description and a simple quantity increase/decrease function.~~

The designs for the given project can be found in this [Figma Diagram](https://www.figma.com/file/DmC626VVpC1sR4SHIUdezX/PLP?node-id=0%3A1)

The developer can use any libraries they choose; even the given `APIService` is merely a suggestion.
UI can be built using a custom framework, standard activities/fragments, Jetpack Compose, etc.

### Completion and Comms
Submission of a complete project should be done by creating a Fork of this repo; completing the project on that fork; and then submitting the forked repo address. You can use the version control platform of your choice; however Github seems easiest. 

It may be necessary to contact the team at MAD. You can do so via an issues message on this repo; but a better way would be to contact the individual from MAD/recruiter and ask for contact details. We are very open to questions. 

### Bonus points
Bonus Points will be granted for:

1. Dependency Injection.
2. View Binding implementations.
3. Implementation Testing of Navigation
4. Making the image on the PDP tappable to animate to fullscreen, and then be zoomable from there.
5. Anything else would you like to show off?
