
object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
    val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntime}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val composeUiPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeMaterial3 by lazy { "androidx.compose.material3:material3" }
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val junitTestExt by lazy { "androidx.test.ext:${Versions.junitTestExt}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espressoCore}" }
    val coroutinesTest by lazy{"org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"}
    val mockitoCore by lazy{"org.mockito:mockito-core:${Versions.mockito}"}
    val mockitoKotlin by lazy{"org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}"}
    val mockitoInline by lazy{"org.mockito:mockito-inline:${Versions.mockito}"}
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val hiltAndroidTest by lazy {"com.google.dagger:hilt-android-testing:${Versions.hilt}"}
    val androidxArchCore by lazy{"androidx.arch.core:core-testing:${Versions.androidxArchCore}"}
    val composeUiTestJunit by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeUiTool by lazy { "androidx.compose.ui:ui-tooling" }
    val composeUiTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
    val appcompat by lazy{"androidx.appcompat:appcompat:${Versions.appcompat}"}
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val hiltAndroid by lazy{"com.google.dagger:hilt-android:${Versions.hilt}"}
    val hiltAndroidCompiler by lazy{"com.google.dagger:hilt-android-compiler:${Versions.hilt}"}
    val androidxHiltCompiler by lazy {"androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"  }
    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val okhttp by lazy{"com.squareup.okhttp3:okhttp:${Versions.okhttp}"}
    val gsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}" }
    val moshi by lazy{"com.squareup.moshi:moshi-kotlin:${Versions.moshi}"}
    val moshiConverter by lazy {"com.squareup.retrofit2:converter-moshi:${Versions.moshiConverter}"  }
    val loggingInterceptor by lazy {"com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"  }
    val coroutinesCore by lazy {"org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"}
    val coroutinesAndroid by lazy {"org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"}
    val roomRuntime by lazy {"androidx.room:room-runtime:${Versions.room}"}
    val roomCompiler by lazy {"androidx.room:room-compiler:${Versions.room}"}
    val roomKtx by lazy {"androidx.room:room-ktx:${Versions.room}"}
    val coilCompose by lazy {"io.coil-kt:coil-compose:${Versions.coil}"}
}

object Modules{
    const val utilities = ":utilities"
}