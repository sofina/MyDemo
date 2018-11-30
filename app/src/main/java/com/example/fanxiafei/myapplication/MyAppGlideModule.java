package com.example.fanxiafei.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.module.AppGlideModule;
import com.example.fanxiafei.myapplication.glide.ThumbnailModelLoader;

import java.io.InputStream;
import java.net.URL;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setLogLevel(Log.DEBUG);
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.prepend(String.class, InputStream.class, new ThumbnailModelLoader.ThumbnailStreamFactory());
    }
}
