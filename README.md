# ShowTouch by QoQa - Android library

This library allow user to add a layout that will show any click on the screen.
The click is shown using a circle on which color, size and disappear delay can be customized.

## Setup

### Add dependencies:

```kotlin
implementation("com.qoqa:showtouch:1.0")
```

### Add a TouchView in your main layout

```kotlin
<com.qoqa.showtouchlibrary.TouchView
        android:id="@+id/touchZone"
        android:layout_width="match_parent"
        android:layout_height="match_parent
        app:elevation="48dp" />
```

### Override dispatchTouchEvent in your MainActivity

```kotlin
override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
    event?.let { touchZone.handleTouch(MotionEvent.obtain(it)) }
    return super.dispatchTouchEvent(event)
}
```

### Customize touch using the following parameters:
* TouchView.dotSize : Float
* TouchView.clearDelay : Long
* TouchView.dotColor : Color
