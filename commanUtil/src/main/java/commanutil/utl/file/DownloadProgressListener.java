package commanutil.utl.file;

import java.io.File;

public interface DownloadProgressListener {
	void onDownloadSize(long size, long totalSize);
	void onDownloadResult(File result);
}
