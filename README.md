# glowingicon
GlowIconView is a view for displaying an icon with a glowing effect behind it. It'y not perfect, but it can be used.

## Installation

Library is installed by putting aar file into libs folder:

```
module/libs (ex. app/libs)
```

and adding the aar dependency to your `build.gradle` file:
```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.0-alpha05'
    implementation 'androidx.core:core-ktx:1.8.0'
    implementation "org.mini2Dx:gdx-math:1.9.13"
    implementation files("libs/glowingicon-1.0.0.aar")
}
```

## Screenshots
![](https://github.com/mgolebiowski95/autosizetextview/blob/master/screenshots/Screenshot_1659352280.png)

## Usage
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <LinearLayout
        android:id="@+id/root"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/grey_700"
        android:divider="@drawable/divider_space_16dp"
        android:gravity="center"
        android:orientation="vertical"
        android:showDividers="middle">

        <com.mgsoftware.glowingicon.GlowIconView
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:GlowIconView_glowEnabled="true"
            app:GlowIconView_glowInset="16dp"
            app:GlowIconView_glowRadius="8dp"
            app:GlowIconView_glowStyle="solid"
            app:GlowIconView_srcInset="18dp"
            app:srcCompat="@drawable/medal" />

        <com.mgsoftware.glowingicon.GlowIconView
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:GlowIconView_glowEnabled="true"
            app:GlowIconView_glowInset="16dp"
            app:GlowIconView_glowRadius="8dp"
            app:GlowIconView_glowStyle="outer"
            app:GlowIconView_srcInset="18dp"
            app:srcCompat="@drawable/medal" />

        <com.mgsoftware.glowingicon.GlowIconView
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:GlowIconView_glowEnabled="true"
            app:GlowIconView_glowInset="16dp"
            app:GlowIconView_glowRadius="8dp"
            app:GlowIconView_glowStyle="normal"
            app:GlowIconView_srcInset="18dp"
            app:srcCompat="@drawable/science" />

        <com.mgsoftware.glowingicon.GlowIconView
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:GlowIconView_glowColor="@color/yellow_500"
            app:GlowIconView_glowEnabled="true"
            app:GlowIconView_glowInset="16dp"
            app:GlowIconView_glowRadius="8dp"
            app:GlowIconView_glowStyle="inner"
            app:GlowIconView_srcInset="18dp"
            app:srcCompat="@drawable/science" />

    </LinearLayout>

</layout>
```


### Attributes
| Attribute | Format | Default |
|:---|:---:|:---:|
| GlowIconView_glowEnabled | boolean | false
| GlowIconView_glowColor | color | white
| GlowIconView_glowRadius | dimension | 1dp
| GlowIconView_glowStyle | normal, solid, outer, inner | outer
| GlowIconView_srcInset | dimension | 0
| GlowIconView_glowInset | dimension | 0