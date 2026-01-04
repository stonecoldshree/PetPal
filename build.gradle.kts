buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    // leave empty; module plugins applied in module build files
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
