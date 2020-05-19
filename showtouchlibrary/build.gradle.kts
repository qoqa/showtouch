import java.io.FileInputStream
import java.util.*
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
    kotlin("kapt")
}

val versionNumber = "1.0.1"
//val fis = FileInputStream(rootProject.file("github.properties"))
val githubProperties = Properties()

android {
    compileSdkVersion (29)
    buildToolsVersion ("29.0.3")

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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

/*publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.qoqa"
                artifactId = "showtouch"
                version = versionNumber
                artifact("$buildDir/outputs/aar/showtouchlibrary-release.aar")
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            /** Configure path of your package repository on Github
             *  Replace GITHUB_USERID with your/organisation Github userID and REPOSITORY with the repository name on GitHub
             */
            url = uri("https://maven.pkg.github.com/qoqa/showtouch") // Github Package
            credentials {
                githubProperties.load(fis)
                //Fetch these details from the properties file or from Environment variables
                username = githubProperties.getProperty("gpr.usr") as String? ?: System.getenv("GPR_USER")
                password = githubProperties.getProperty("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
            }
        }
    }
}*/

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${rootProject.extra.get("kotlinVersion")}")
    implementation( "androidx.appcompat:appcompat:1.1.0")
    implementation ("androidx.core:core-ktx:1.2.0")
    testImplementation("junit:junit:4.12")
    androidTestImplementation ("androidx.test.ext:junit:1.1.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.2.0")
}
