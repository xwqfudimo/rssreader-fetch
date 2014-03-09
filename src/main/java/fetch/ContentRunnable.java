package fetch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import util.HtmlParserUtil;
import util.MemcacheUtil;
import util.MongoUtil;

/**
 * 文章内容抓取线程 
 */
public class ContentRunnable implements Runnable {
	private static Logger log = Logger.getLogger(ContentRunnable.class);
	private String title;
	private String link;
	private String publishedDate;
	private String sourceName;
	
	public ContentRunnable(String title, String link, String publishedDate, String sourceName) {
		this.title = title;
		this.link = link;
		this.publishedDate = publishedDate;
		this.sourceName = sourceName;
	}

	@Override
	public void run() {
		String content = getContentByHtmlParser(link);
		try {
			if(null != content) {
				Map<String, Object> map = new HashMap<String, Object>();
				String articleTitle = this.title + " [" + sourceName + "]"; 
				map.put("title", articleTitle);
				map.put("link", this.link);
				map.put("published_date", this.publishedDate);
				map.put("content", content);
				
				MongoUtil.insert("article", map);
				
				//link不存在的入库后再存入memcache
				MemcacheUtil.set(this.link.hashCode() + "", this.link);
				
				int flag = content == null? 0 : 1;
				log.debug(articleTitle + "---" + link + "---" + publishedDate + "---" + flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			//连接异常时处理
			
		}
	}
	
	private String getContentByHtmlParser(String link) {
		String content = HtmlParserUtil.getContentFromLink(link);

		//如果没有获取到内容，则再取一次
		if(content == null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			content = HtmlParserUtil.getContentFromLink(link);
		}
			
		return TextExtract.parse(content);
	}

	@SuppressWarnings("unused")
	public static String getContent(String link) {
		URLConnection conn = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(link);
			conn = url.openConnection();
			conn.connect();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			char[] buff = new char[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer();
			
			while((len = reader.read(buff, 0, buff.length)) > 0) {
				sb.append(buff);
			}
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
