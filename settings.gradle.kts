pluginManagement {
    repositories {
        gradlePluginPortal()
        maven(url = "https://mirrors.tencent.com/nexus/repository/maven-public/")


    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven(url = "https://mirrors.tencent.com/nexus/repository/maven-public/")
    }
}
rootProject.name = "T820"
include(":app")
