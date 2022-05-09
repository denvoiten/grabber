package ru.job4j.cache;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        Path path = Path.of(cachingDir, key);
        String rsl = "";
        try {
            rsl = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cache.put(key, new SoftReference<>(rsl));
        return rsl;
    }
}


