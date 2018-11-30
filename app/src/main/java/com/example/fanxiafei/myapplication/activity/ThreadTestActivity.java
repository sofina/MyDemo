package com.example.fanxiafei.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pools;
import android.util.Log;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.signature.EmptySignature;
import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Synthetic;
import com.bumptech.glide.util.Util;
import com.bumptech.glide.util.pool.FactoryPools;
import com.bumptech.glide.util.pool.StateVerifier;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTestActivity extends Activity {
    private final LruCache<Key, String> loadIdToSafeHash = new LruCache<>(1000);
    private final Pools.Pool<PoolableDigestContainer> digestPool = FactoryPools.threadSafe(10,
            new FactoryPools.Factory<PoolableDigestContainer>() {
                @Override
                public PoolableDigestContainer create() {
                    try {
                        return new PoolableDigestContainer(MessageDigest.getInstance("SHA-256"));
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyRunnable runnable = new MyRunnable();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            getSafeKey(new ObjectKey(System.currentTimeMillis()));
        }
    }

    public String getSafeKey(Key key) {
        Log.d("sofina", " getSafeKey   key:" + key + "  "+Thread.currentThread().getName());
        String safeKey;
        synchronized (loadIdToSafeHash) {
            safeKey = loadIdToSafeHash.get(key);
            Log.d("sofina", " get   key:" + key + "   " + "     safeKey:" + safeKey + "  " + Thread.currentThread().getName());
        }

        Log.d("sofina", " before   key:" + key + "   " + "     safeKey:" + safeKey + "  " + Thread.currentThread().getName());
        if (safeKey == null) {
            safeKey = calculateHexStringDigest(key);
        }
        Log.d("sofina", " after   key:" + key + "   " + "     safeKey:" + safeKey + "  " + Thread.currentThread().getName());

        synchronized (loadIdToSafeHash) {
            loadIdToSafeHash.put(key, safeKey);
            Log.d("sofina", " put   key:" + key + "   " + "     safeKey:" + safeKey + "  " + Thread.currentThread().getName());
        }
        return safeKey;
    }

    private String calculateHexStringDigest(Key key) {
        PoolableDigestContainer container = Preconditions.checkNotNull(digestPool.acquire());
        try {
            key.updateDiskCacheKey(container.messageDigest);
            // calling digest() will automatically reset()
            return Util.sha256BytesToHex(container.messageDigest.digest());
        } finally {
            digestPool.release(container);
        }
    }

    private static final class PoolableDigestContainer implements FactoryPools.Poolable {

        @Synthetic
        final MessageDigest messageDigest;
        private final StateVerifier stateVerifier = StateVerifier.newInstance();

        PoolableDigestContainer(MessageDigest messageDigest) {
            this.messageDigest = messageDigest;
        }

        @NonNull
        @Override
        public StateVerifier getVerifier() {
            return stateVerifier;
        }
    }

}
