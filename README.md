[![](https://jitpack.io/v/saury001/statesman.svg)](https://jitpack.io/#saury001/statesman)

# Statesman - An attempt to create react-like state management in android

Statesman is an android library that imitates react-like state management in android programming using MVVM programming pattern. 

## Download and installation

Add the JitPack repository to your project-level build.gradle file
```groovy

 allprojects {
       repositories {
	   //...
	   maven { url 'https://jitpack.io' }
	}
  }

```

Use gradle dependency manager for downloading jars for the library

```groovy

dependencies {
    implementation 'com.github.saury001.statesman:statesman:1.0.4'
    implementation 'com.github.saury001.statesman:statesman-annotations:1.0.4'
    annotationProcessor 'com.github.saury001.statesman:statesman-annotation-processor:1.0.4'
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

Create a simple POJO or data class in case that you are using Kotlin and annotate that class with `State` and then for every field you want in your state just annotate that with `StateField` annotation.

```kotlin

@State
data class Sample(
    @StateField var sampleFieldA: String,
    @StateField var sampleFieldB: Int
)

```



After that just build your project and then the annotation processor will create the state's boilerplate code for you. Always remember that your state name will be `{ClassName}State`. For example, for the above data class, the corresponding generated state class' name will be `SampleState`.
#### NOTE: All the fields of state class are `Bindable` in nature i.e. they can be used in data binding directly

### Creating ViewModel

Start by creating a ViewModel class by extending `BaseViewModel<T>` where T is the generated state class and override the `createInitialState()` method of `BaseViewModel` by returning an instance of the state class that will serve as the initial state.

```kotlin

class SampleViewModel(application: Application) :
    BaseViewModel<SampleState>(application = application) {

    /**
     * Array of some sample name strings
     */
    companion object {
        val sampleNames = arrayOf(
            "Mary", "John", "Ashley", "Alaska", "Chip", "Jake", "Narobi", "Lara", "Eagle", "Chris"
        )
    }


    /**
     * Override this method and return an instance of generated state class that will act as the starting state
     */
    override fun createInitialState(): SampleState {
        return SampleState(
            "Justin",
            0
        )
    }


    /**
     * Method to update field B value of the state
     */
    fun setSampleFieldB(fieldB: Int) {
        setState(
            SampleState(
                sampleFieldB = fieldB
            )
        )
    }

    /**
     * Method to update field A value of the state
     */
    fun setSampleFieldA(fieldA: String) {
        setState(
            SampleState(
                sampleFieldA = fieldA
            )
        )
    }


    /**
     * Method randomly generates an index and pulls the string of that index from sampleNames and set that as value of field A of the state
     */
    fun randomizeFieldA() {
        val randomIndex = (Math.random() * sampleNames.size).toInt()
        setState(
            SampleState(
                sampleFieldA = sampleNames[randomIndex]
            )
        )
    }


    /**
     * Method to clear input i.e. clear the value of sample field A
     */
    fun clearInput() = setState(
        SampleState(
            sampleFieldA = ""
        )
    )

}

```

### Creating Activity

Now create your activity and extend `BaseActivity<T>` in your activity class where `T` is the state class attached to the activity. Let's create an activity that attaches to the above state
```kotlin

class SampleActivity : BaseActivity<SampleState>() {

    companion object {
        const val TAG = "SampleActivity.TAG"
    }

    private lateinit var viewModel: SampleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivitySampleBinding>(this, R.layout.activity_sample)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    override fun createViewModel(): BaseViewModel<SampleState> {
        viewModel = ViewModelProvider(this).get(SampleViewModel::class.java) //Creating instance of our viewModel
        return viewModel
    }


   
    override fun onStateChanged(newState: SampleState, oldState: SampleState) { //This method is called whenever the state is changed.
        Log.d(
            TAG,
            "onStateChanged: New State: {${newState.mSampleFieldA}, ${newState.mSampleFieldB}}" +
                    "\n" +
                    "Old State: {${oldState.mSampleFieldA}, ${oldState.mSampleFieldB}}"
        )
    }

    override fun onNavigationRouteChange(newRoute: NavigationRoute, oldRoute: NavigationRoute) { //This method is called whenever the route is changed
        Log.d(
            TAG,
            "onNavigationRouteChange: New Route: {${newRoute.routeName}, ${newRoute.routeValue}}" +
                    "\n" +
                    "Old Route: {${oldRoute.routeName}, ${oldRoute.routeValue}}"
        )
    }
}
``` 


### Binding with the view

You can directly bind your state class fields with the XML layout file. Convert your layout to a Data binding layout and create a variable for viewModel of your ViewModel type.

```xml
 <data>
     <variable
         name="viewModel"
         type="com.saurabhbadola.statesman.examples.sample.SampleViewModel" />
 </data>
```

Now field can be directly bound to layout elements using observable property inside your viewmodel like:

```xml
   <TextView
            android:id="@+id/textView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{viewModel.observableState.MSampleFieldA}"/>
```

OnClick methods can be called directly from the view using data binding like

```xml
  <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:onClick="@{()->viewModel.setSampleFieldB(4)}"
            android:text="4" />
```

#### For more clarity, please check out the example in the code.

## License
[MIT](https://choosealicense.com/licenses/mit/)
