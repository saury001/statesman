[![](https://jitpack.io/v/saury001/statesman.svg)](https://jitpack.io/#saury001/statesman)

# Statesman - An attempt to create react-like state management in android

Statesman is an android library that imitates react-like state management in android programming. 

## Download and installation

Use gradle dependency manager for downloading jars for the library

```groovy

dependencies {
    implementation 'com.github.saury001.statesman:statesman:1.0.2'
    implementation 'com.github.saury001.statesman:statesman-annotations:1.0.2'
    annotationProcessor 'com.github.saury001.statesman:statesman-annotation-processor:1.0.2'
}

```

If you are using Kotlin, replace annotationProcessor with kapt.

### In your app level build.gradle file, make following changes:


```groovy

    buildFeatures {
        dataBinding = true
    }

    sourceSets {
        main {
            java {
                srcDir "${buildDir.absolutePath}/generated/source/kaptKotlin/"
            }
        }
    }

```

## How to use

### Creating State class

Create a simple POJO or data class in case that you are using Kotlin and annotate that class with State and then for every field you want in your state just annotate that with StateField annotation.

```kotlin

@State
data class MyObject(
  @StateField valueA:String,
  @StateField valueB:Int
)

```

After that just build your project and then the annotation processor will create the state's boilerplate code for you. Always remember that your state name will be {ClassName}State. For example, for the above data class, the corresponding generated state class' name will be MyObjectState.

### Creating ViewModel

Start by creating a viewmodel class by extending BaseViewModel<T> where T is the generated state class and override the onCreateInitialState() method of BaseViewModel by return an instance of the state class that will serve as initial state.

```kotlin

 class MyViewModel(application: Application) : BaseViewModel<MyObjectState>(application) {
    override fun createInitialState(): MyObjectState {
        //Return an instance of MyObjectState as an initial state to start with
       return MyObjectState(
         "initial value",
         "0"
       )
    }
 }

```

Now create your activity and extend BaseActivity<T> in your activity class where T is the state class attached to the activity. Let's create an activity that attaches to the above state
```kotlin

class MyActivity : BaseActivity<MyObjectState>() {

    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // your rest of code comes here...
    }

    override fun createViewModel(): BaseViewModel<MyObjectState> {
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        return viewModel
    }

    override fun onNavigationRouteChange(navigationRoute: NavigationRoute) {
        TODO("Not yet implemented")
    }

    override fun onStateChanged(newState: TestState) {
        TODO("Not yet implemented")
    }
}

``` 

## License
[MIT](https://choosealicense.com/licenses/mit/)
