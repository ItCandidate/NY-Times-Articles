# Android MVVM Architecture for Mobile Application

# Highlights

1. MVVM Architectural pattern
2. Cache Support
3. Unit test demonstration using JUnit and Mockito
4. UI unit test demonstration using Espresso


The application has been built with **cache support** with the help retrofit 2.x interceptor. It has been designed using **Android Architecture components** with **ViewModel** for offline data caching. The application is built in such a way that whenever there is a service call, the result will be stored in cache file.

# Screenshots
<img src="/screenshots/main_page.png" width="346" height="615" alt="Home"/>
<img src="/screenshots/detail_page.png" width="346" height="615" alt="Home"/>

# Programming Practices Followed
1) Android Architectural Components ViewModel<br/>
2) MVVM <br/>
3) Retrofit with Okhttp & RxJava & RxAndroid<br/>
4) Retrofit 2.x for data caching <br/>
5) JUnit and Mockito for Unit testing <br/>
6) Repository pattern <br/>
7) JSoup for HTML Parsing

# How to build ?

Open terminal and type the below command to generate debug build <br/>

``` ./gradlew assembleDebug ```

Open terminal and type the below command to generate release build <br/>

``` ./gradlew assembleRelease ```

# How to generate code coverage report ?

Open terminal and type the following command

```./gradlew clean jacocoTestReport```

The coverage report will be generated on the following path.

``` app/build/reports ```
