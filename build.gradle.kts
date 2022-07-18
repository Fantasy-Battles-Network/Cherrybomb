import io.papermc.paperweight.util.constants.PAPERCLIP_CONFIG
val paperMavenPublicUrl = "https://repo.papermc.io/repository/maven-public/"

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2" apply false
    id("io.papermc.paperweight.patcher") version "1.3.8"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content { onlyForConfigurations(PAPERCLIP_CONFIG) }
    }
}

dependencies {
    remapper("net.fabricmc:tiny-remapper:0.8.2:fat")
    decompiler("net.minecraftforge:forgeflower:1.5.605.7")
    paperclip("io.papermc:paperclip:3.0.2")
}

subprojects {
    apply(plugin = "java")

    java {
        toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

paperweight {
    serverProject.set(project(":cherrybomb-server"))

    remapRepo.set(paperMavenPublicUrl)
    decompileRepo.set(paperMavenPublicUrl)

    usePaperUpstream(providers.gradleProperty("paperRef")) {
        withPaperPatcher {
            apiPatchDir.set(layout.projectDirectory.dir("patches/api"))
            apiOutputDir.set(layout.projectDirectory.dir("cherrybomb-api"))

            serverPatchDir.set(layout.projectDirectory.dir("patches/server"))
            serverOutputDir.set(layout.projectDirectory.dir("cherrybomb-server"))
        }
    }
}