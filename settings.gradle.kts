pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/oneentry/p/main/oneentry")
            credentials {
                username = "Dinarus"
                password = "WASALwasal123!"
            }
        }
    }
}

rootProject.name = "OneEntrySDKSample"
include(":app")
