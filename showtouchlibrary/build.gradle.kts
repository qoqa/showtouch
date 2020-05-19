import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.*

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
    kotlin("kapt")
}

val versionNumber = "1.0.5"
val githubProperties = Properties()

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    val libVersionCode = 1

    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = libVersionCode
        versionName = versionNumber

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    android.libraryVariants.forEach { variant ->
        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                val fileName = "${variant.name}-${versionNumber}.aar"
                println("output file name: $fileName")
                output.outputFileName = fileName
            }
    }
}

val sourcesJar = task<Jar>("androidSourcesJar") {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("android-showtouch") {
                from(components["release"])

                groupId = "com.qoqa"
                artifactId = "android-showtouch"
                version = versionNumber
                artifact(sourcesJar)
            }
        }
        repositories {
            maven {
                url = uri("$buildDir/repo")
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra.get("kotlinVersion")}")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.2.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}
