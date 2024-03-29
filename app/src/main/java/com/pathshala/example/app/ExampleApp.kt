package com.assignment.rxjavakotlinway.app

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.util.ByteConstants
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.MemoryCacheParams
import com.facebook.imagepipeline.core.ImagePipelineConfig

/**
 * Created by Sachin
 */
class ExampleApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initializeFresco()
    }

    companion object {
        private val TAG = ExampleApp::class.java.name
        @get:Synchronized
        var instance: ExampleApp? = null
        private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()

    }

    private val MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB
    private val MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4
    private val IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache"
    private fun initializeFresco() {

        val bitmapCacheParams = MemoryCacheParams(
            MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
            Integer.MAX_VALUE, // Max entries in the cache
            MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
            Integer.MAX_VALUE, // Max length of eviction queue
            Integer.MAX_VALUE
        )

        val config = ImagePipelineConfig.newBuilder(this)
            .setBitmapMemoryCacheParamsSupplier { bitmapCacheParams }
            .setMainDiskCacheConfig(
                DiskCacheConfig.newBuilder(this)
                    .setBaseDirectoryPath(applicationContext.cacheDir)
                    .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                    .setMaxCacheSize(MAX_DISK_CACHE_SIZE.toLong())
                    .build()
            )
            .build()
        Fresco.initialize(this, config)

    }

}
