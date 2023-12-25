plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.qiuhua.playerbackpacker"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()  //加载本地仓库
    mavenCentral()  //加载中央仓库
    maven {
        name = "spigotmc-repo"
        url = uri ("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }  //SpigotMC仓库

    maven {
        name = "CodeMC"
        url = uri("https://repo.codemc.io/repository/maven-public/")
    } //nbt操作库
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    compileOnly (fileTree("src/libs/Spigot-1.12.2.jar"))
    compileOnly (fileTree("src/libs/sqlite-jdbc-3.40.1.0_2.jar"))
    compileOnly (fileTree("src/libs/DragonCore-2.4.7.jar"))
    compileOnly (fileTree("src/libs/GermPlugin-1.5.7.jar"))
    implementation("de.tr7zw:item-nbt-api:2.12.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>{
    options.encoding = "UTF-8"
}

tasks.withType<Jar>().configureEach {
    archiveFileName.set("playerbackpacker-测试插件.jar")
    destinationDirectory.set(File ("D:/Server-1.12.2/Plugins"))
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("playerbackpacker-测试插件.jar")
    destinationDirectory.set (File ("D:/Server-1.12.2/Plugins"))
    relocate("de.tr7zw.changeme.nbtapi", "org.qiuhua.nbtapi")
    mergeServiceFiles()
    manifest {
    }
}