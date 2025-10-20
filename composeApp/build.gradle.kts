import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm {
        testRuns {
            named("test") {
                executionTask.configure {
                    useJUnitPlatform()
                }
            }
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.serialization.json)

            // Exposed for database handling
            implementation("org.jetbrains.exposed:exposed-core:0.56.0")
            implementation("org.jetbrains.exposed:exposed-dao:0.56.0")
            implementation("org.jetbrains.exposed:exposed-jdbc:0.56.0")
            implementation("org.jetbrains.exposed:exposed-java-time:0.56.0")
            implementation("org.xerial:sqlite-jdbc:3.46.1.3")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
            implementation("org.slf4j:slf4j-simple:2.0.16")

        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}


compose.desktop {
    application {
        mainClass = "com.octahedron.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Msi)
            packageName = "octahedron"
            vendor = "octahedron"
            packageVersion = "1.0.4"
            windows {
                menuGroup = "Octahedron"
                dirChooser = true
                perUserInstall = false
                shortcut = true
            }
        }
    }
}
