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


## License
[MIT](https://choosealicense.com/licenses/mit/)
