package vo;

public class FetchUrl {
	//来源名
	private String sourceName;
	//要抓取的url
	private String fetchUrl;
	//分隔符常量
	public static final String SEPARATE = "#sperarate#";
	
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getFetchUrl() {
		return fetchUrl;
	}
	public void setFetchUrl(String fetchUrl) {
		this.fetchUrl = fetchUrl;
	}
	@Override
	public String toString() {
		return sourceName + SEPARATE + fetchUrl;
	}
}
