package fetch;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import config.reader.FetchConfigReader;

/**
 * 原始数据抓取
 */
public class OriginalDataFetch {
	
	public static void main(String[] args) {
		LoadFetchUrl.loadUrlFromDB();
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new FetchRunnable(), 100, FetchConfigReader.getInterval(), TimeUnit.MILLISECONDS);
	}
}






