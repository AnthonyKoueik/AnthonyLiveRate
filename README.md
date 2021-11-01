# Anthony Test App
Revolut Android Test Application

## Enhancements that could be made

Many missing improvement  for this application:
* Android  UI  handling for Error and Retry
* Swipe-to-refresh
* Include a lazy Image Loading
* Increasing the number of unit test including all potential scenarios

# App Architecture

Application code design using MVVM pattern using:

### *Model* :: > Repository pattern
### *Model* :: > Remote service with RxJava 2 and Retrofit 2
### *ViewModel* :: > ViewModel with Architecture Components
### *View* :: > Activity controller apply changes via *LivedData* to RecyclerView and DiffUtils

### Dependency Injection :: > Dagger 2
