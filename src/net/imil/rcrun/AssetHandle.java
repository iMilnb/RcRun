package net.imil.rcrun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class AssetHandle {
	private static final String TAG = "RcRunAssets";
	/* Mostly c/p'ed from http://androidcodesnippetsblog.blogspot.fr/2013/01/copying-file-from-assets-to-sd-card-in.html */
	public static void copyAsset(Context context, String path, String file) {
		AssetManager assetManager = context.getAssets();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = assetManager.open(file);
			out = new FileOutputStream(path + File.separator + file);
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (IOException e) {
			Log.e(TAG, "Failed to copy asset file: " + file, e);
		}
	}

	private static void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

}