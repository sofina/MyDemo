package com.example.fanxiafei.myapplication.glide;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.File;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThumbnailModelLoader<Data> implements ModelLoader<String, Data> {

    private final ModelLoader<Uri, Data> uriLoader;

    // Public API.
    @SuppressWarnings("WeakerAccess")
    public ThumbnailModelLoader(ModelLoader<Uri, Data> uriLoader) {
        this.uriLoader = uriLoader;
    }

    @Override
    public LoadData<Data> buildLoadData(@NonNull String model, int width, int height,
                                        @NonNull Options options) {
        Uri uri = replaceSize(model, width, height);
        return uri == null ? null : uriLoader.buildLoadData(uri, width, height, options);
    }

    @Override
    public boolean handles(@NonNull String model) {
        Uri uri = Uri.parse(model.toString());
        String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        String subString = model.substring(model.lastIndexOf(host) + host.length() + 1);
        boolean startValid = host.startsWith("t") || host.startsWith("f");
        boolean endValid = host.endsWith("market.xiaomi.com") || host.endsWith("market.mi-img.com");
        boolean hostValid = startValid && endValid;
        return hostValid && subString.startsWith("thumbnail");
    }

    @Nullable
    private static Uri parseUri(String model) {
        Uri uri;
        if (TextUtils.isEmpty(model)) {
            return null;
            // See https://pmd.github.io/pmd-6.0.0/pmd_rules_java_performance.html#simplifystartswith
        } else if (model.charAt(0) == '/') {
            uri = toFileUri(model);
        } else {
            uri = Uri.parse(model);
            String scheme = uri.getScheme();
            if (scheme == null) {
                uri = toFileUri(model);
            }
        }
        return uri;
    }

    private static Uri toFileUri(String path) {
        return Uri.fromFile(new File(path));
    }

    private Uri replaceSize(String model, int width, int height) {
        if (TextUtils.isEmpty(model)) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\/(w|h)(\\d+)\\/");
        Matcher matcher = pattern.matcher(model);

        String replace = "/w" + width + "h" + height + "/";
        if (matcher.find()) {
            model = matcher.replaceFirst(replace);
        }
        return parseUri(model);
    }

    public static class ThumbnailStreamFactory implements ModelLoaderFactory<String, InputStream> {

        @NonNull
        @Override
        public ModelLoader<String, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
            return new ThumbnailModelLoader<>(multiFactory.build(Uri.class, InputStream.class));
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }
}
