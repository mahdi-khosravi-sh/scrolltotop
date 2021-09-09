# Scroll To Top

A simple, fast and beautiful widget for scrolling RecyclerView

 <div align="center">

  https://user-images.githubusercontent.com/88603424/132222272-24f435ee-a503-4768-b635-f8277a800e73.mp4

</div>

[![](https://jitpack.io/v/mahdidev78/scrolltotop.svg)](https://jitpack.io/#mahdidev78/scrolltotop)
![GitHub repo size](https://img.shields.io/github/repo-size/mahdidev78/scrolltotop)
![GitHub language count](https://img.shields.io/github/languages/count/mahdidev78/scrolltotop)
![GitHub top language](https://img.shields.io/github/languages/top/mahdidev78/scrolltotop)
![GitHub last commit](https://img.shields.io/github/last-commit/mahdidev78/scrolltotop?color=red)

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#download">Download</a></li>
        <li><a href="#usage">Usage</a></li>
      </ul>
    </li>
    <li><a href="#stats">Stats</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- GETTING STARTED -->
## Getting Started

### Download

Download the latest AAR from jitpack via Gradle:

<ul>
  <li>
Gradle

Project build.gradle
  
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
app module build.gradle

```gradle
dependencies {
  implementation 'com.github.mahdidev78:scrolltotop:2.1.9'
}
```
    
  </li>
  <li>
Maven
    
Add the JitPack repository to your build file
    
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Add the dependency
```xml
<dependency>
  <groupId>com.github.mahdidev78</groupId>
  <artifactId>scrolltotop</artifactId>
  <version>Tag</version>
</dependency>
```
  </li>
</ul>

<!-- USAGE EXAMPLES -->
## Usage

### Step 1

Add teh ScrollToTop or ScrollToTop2 to your layout :

```xml
<com.mahdikh.vision.scrolltotop.widget.ScrollToTop
    android:id="@+id/scrollToTop"
    android:layout_width="35dp"
    android:layout_height="35dp"
    android:layout_margin="10dp"
    app:heavyCheckup="true"
    app:minimumPosition="NO_POSITION"
    app:minimumScroll="250dp"
    app:rippleColor="#999999"
    app:shortScroll="true"
    app:smoothScroll="true" />
```
### Step 2

Setup your code : 
<ul>
  <li>
    Kotlin
    
```kotlin
val scrollToTop:ScrollToTop = findViewById(R.id.scrollToTop)
scrollToTop.setupWithRecyclerView(recyclerView)    
```
  </li>
  <li>
    Java
    
```java
ScrollToTop scrollToTop = findViewById(R.id.scrollToTop);
scrollToTop.setupWithRecyclerView(recyclerView);
```
  </li>
</ul>

### Advanced Step 3

<ul>
  <li>
  kotlin

```kotlin
scrollToTop.animator = ScaleAnimator().apply {
  duration = 250
  fromScale = 0.8F
  maxAlpha = 0.8F
  interpolator = FastOutSlowInInterpolator()
}
```
  </li>
  <li>
  Java

```java
ScaleAnimator animator = new ScaleAnimator();    
animator.setDuration(250);
animator.setFromScale(0.8F);
animator.setMaxAlpha(0.8F);
animator.setInterpolator(new FastOutSlowInInterpolator());
scrollToTop.setAnimator(animator);
```
  </li>
</ul>

#### Animators

`FadeAnimator`, `ScaleAnimator`, `SlideAnimator`, `FlyAnimator`

## Attributes

| attribute | Description | Options(examples)|
| --- | --- | --- |
| smoothScroll | The default value is false | true/false |
| shortScroll | The default value is false. If shortScroll is true, smoothScroll must also be true. | true/false |
| heavyCheckup | If true, reacts when scrolling list, otherwise react when scrollState changes. | true/false |
| rippleColor | oval ripple color. | #999999 |
| minimumScroll | If the scroll value exceeds this value, ScrollToTop is displayed, otherwise it will be hidden. If the minimumPosition value is anything other than NOPOSITION, this value is ineffective. | 250dp |
| minimumPosition | If the minimumPosition value is anything other than NOPOSITION, it means that if this position is scrolled and hidden, scrollToTop will appear. | 0, 2, 10 and etc |

<!-- _For more examples, please refer to the [Documentation](https://example.com)_ -->

## Stats

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url]

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- LICENSE -->
## License

Distributed under the Apache2.0 License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

Mahdi Khosravi - mahdi.khosravi.dev78@gmail.com

Project Link: [https://github.com/mahdidev78/scrolltotop](https://github.com/mahdidev78/scrolltotop)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/mahdidev78/ScrollToTop.svg?
[contributors-url]: https://github.com/mahdidev78/scrolltotop/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/mahdidev78/ScrollToTop.svg?
[forks-url]: https://github.com/mahdidev78/scrolltotop/network/members
[stars-shield]: https://img.shields.io/github/stars/mahdidev78/ScrollToTop.svg?
[stars-url]: https://github.com/mahdidev78/scrolltotop/stargazers
[issues-shield]: https://img.shields.io/github/issues/mahdidev78/ScrollToTop.svg?
[issues-url]: https://github.com/mahdidev78/scrolltotop/issues
[license-shield]: https://img.shields.io/github/license/mahdidev78/ScrollToTop.svg?
[license-url]: https://github.com/mahdidev78/scrolltotop/blob/master/LICENSE.txt
[product-screenshot]: images/screenshot.png
