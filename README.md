ExceptionWear - library for handling Exceptions on Android Wear
=======

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ExceptionWear-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1470) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/pl.tajchert/exceptionwear/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/pl.tajchert/exceptionwear)


ExceptionWear (:exclamation::watch:) is very simple library to solve problem of not passing exceptions from Android Wear devices to the phone. So if you release an app for smartwatches to Google Play and it will crash (and it will) you won't get any information about it (sic!).

###How to start?
To start with handling your exceptions, all you need is to add one line to `Application` class on Wear device. From now on all your exceptions from smartwatch will be thrown as well on the phone. Plus you can add custom handlers for them!

###Custom Handler
After receiving exception on a phone you can send a email with log, save it for any crash reporting app etc. - just add own class that `implements ExceptionWearHandler` and set using `ExceptionDataListenerService.setHandler()` before receiving a crash.

###Add ExceptionWear to your project

Gradle:
```gradle
    //library:
    compile 'pl.tajchert:exceptionwear:0.1.2'
    //needed dependency:
    compile 'com.google.android.gms:play-services-wearable:+'
```
Add it to both projects - mobile and wear.

Maven:
```xml
<dependency>
    <groupId>pl.tajchert</groupId>
    <artifactId>exceptionwear</artifactId>
    <version>0.1.2</version>
</dependency>
```

[Maven Central Link](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22pl.tajchert%22%20AND%20a%3A%22exceptionwear%22)

###Code
Wear app:
```java
public class WearableApplication extends Application {
    public void onCreate() {
        //...
        ExceptionWear.initialize(this);
    }
}
```
Phone (for custom handler):
```java
public class PhoneApplication extends Application {
    public void onCreate() {
        //...
        ExceptionDataListenerService.setHandler(new CustomExceptionHandler());//need to implements ExceptionWearHandler
    }
}
```
Other sample handlers are already implemented in sample project. There are: Log only handler, sending email, Crashlytics handler.

###License

ExceptionWear binaries and source code can be used according to the [Apache License, Version 2.0](LICENSE).

###Thanks

Goes to Sebastian Mauer for that [gist](https://gist.github.com/mauimauer/c6f40ec89863906e3b7a) (whole library is based on it), and also to: Aleksander Piotrowski, David Vavra.
