/**
 *
 */

package com.example.fanxiafei.myapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@SuppressWarnings({"unused"})
public class FileUtils {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static int sBufferSize = 8192;


    public static String computeSize(long cacheSize) {
        String result = "";
        if (cacheSize == 0) {
            result = "";
        } else if (cacheSize > 0 && cacheSize < 1024) {
            result = cacheSize + "B";
        } else if (cacheSize >= 1024 && cacheSize < (1024 * 1024)) {
            result = cacheSize / 1024 + "K";
        } else if (cacheSize >= 1024 * 1024) {
            result = cacheSize / (1024 * 1024) + "M";
        }
        return result;
    }

    /**
     * 统计目录文件大小
     *
     * @param file
     * @return
     */
    public static long countFile(File file) {

        long size = 0L;
        if (file == null || !file.exists()) {
            return size;
        }

        if (file.isFile()) {
            return file.length();
        }

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list == null) {
                return size;
            }
            for (File childFile : list) {
                size += countFile(childFile);
            }
        }

        return size;
    }

    /**
     * 删除目录下的文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists() || !file.canWrite()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        }

        boolean success = true;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list == null) {
                return true;
            }
            for (File childFile : list) {
                success &= deleteFile(childFile);
            }
        }

        return success;
    }


    /**
     * 读取源文件字符数组
     *
     * @param file 获取字符数组的文件
     * @return 字符数组
     */
    public static byte[] readFileByte(File file) {
        FileInputStream fis = null;
        FileChannel fc = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(file);
            fc = fis.getChannel();
            data = new byte[(int) (fc.size())];
            fc.read(ByteBuffer.wrap(data));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (fc != null) {
                try {
                    fc.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return data;
    }

    public static String readFile(String path) {
        String result = null;
        InputStreamReader in = null;
        if (!new File(path).exists()) {
            return null;
        }
        try {
            in = new FileReader(path);
            StringBuffer sb = new StringBuffer();
            char[] buffer = new char[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                sb.append(buffer, 0, length);
            }
            result = sb.toString();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * 字符数组写入文件
     *
     * @param file 被写入的文件
     * @return 字符数组
     * @parambytes 被写入的字符数组
     */
    public static boolean writeByteFile(byte[] bytes, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                } catch (Exception e) {
                }
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    static boolean mExternalStorageAvailable = false;
    static boolean mExternalStorageWriteable = false;

    private static void checkStorageState() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but
            // all we need
            // to know is we can neither read nor write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    public static boolean isSdcardWriteable() {
        checkStorageState();
        return mExternalStorageWriteable;
    }

    public static String loadAssetsFile(Context context, String file) {
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = context.getAssets().open(file);
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            char[] buffer = new char[1024];
            int length = 0;
            while ((length = in.read(buffer)) > 0) {
                sb.append(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    // support folder and file
    public static void copyAssetsToFiles(Context context, String path) {
        AssetManager assetManager = context.getAssets();
        try {
            String[] assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(context, path);
            } else {
                for (int i = 0; i < assets.length; ++i) {
                    copyAssetsToFiles(context, path + "/" + assets[i]);
                }
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    private static void copyFile(Context context, String filename) {
        AssetManager assetManager = context.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            String filePath = context.getFilesDir().getAbsolutePath();
            String[] split = filename.split("/");
            for (int i = 0; i < split.length - 1; i++) {
                filePath += "/" + split[i];
                File dir = new File(filePath);
                if (!dir.exists())
                    dir.mkdir();
            }
            filePath += "/" + split[split.length - 1];
            String newFileName = filePath;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[sBufferSize];
            int len;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String readFile2String(final File file) {
        if (!isFileExists(file)) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            if ((line = reader.readLine()) != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(LINE_SEP).append(line);
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final OnReplaceListener listener,
                                          final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        // 如果源文件和目标文件相同则返回 false
        if (srcFile.equals(destFile)) return false;
        // 源文件不存在或者不是文件则返回 false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {// 目标文件存在
            if (listener.onReplace()) {// 需要覆盖则删除旧文件
                if (!destFile.delete()) {// 删除文件失败的话返回 false
                    return false;
                }
            } else {// 不需要覆盖直接返回即可 true
                return true;
            }
        }
        // 目标目录不存在返回 false
        if (!createOrExistsDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveFile(final File srcFile,
                                   final File destFile,
                                   final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    public interface OnReplaceListener {
        boolean onReplace();
    }
}
