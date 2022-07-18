pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

rootProject.name = "cherrybomb"
include("cherrybomb-api", "cherrybomb-server")