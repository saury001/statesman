[![](https://jitpack.io/v/saury001/statesman.svg)](https://jitpack.io/#saury001/statesman)

# Statesman - An attempt to create react-like state management in android

Statesman is an android library that imitates react-like state management in android programming. 

## Download and installation

Use gradle dependency manager for downloading jars for the library

```bash

dependencies {
    implementation 'com.github.saury001.statesman:statesman:1.0.2'
    implementation 'com.github.saury001.statesman:statesman-annotations:1.0.2'
    annotationProcessor 'com.github.saury001.statesman:statesman-annotation-processor:1.0.2'
}
```

If you are using Kotlin, replace annotationProcessor with kapt.

### In your app level build.gradle file, make following changes:


```bash

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

Create a simple POJO or data class in case that you are using Kotlin and annotate that class with State and then for every field you want in your state just annotate that with StateField annotation.

```bash
@State
data class MyObject(
  @StateField valueA:String,
  @StateField valueB:Int
)

```

## License
[MIT](https://choosealicense.com/licenses/mit/)
