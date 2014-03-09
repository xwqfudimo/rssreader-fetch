package util;

import org.htmlparser.beans.StringBean;

public class HtmlParserUtil {
	private static StringBean sb = new StringBean(); 
	
	public static String getContentFromLink(String link) {
        sb.beginParsing();
        //设置不需要得到页面所包含的链接信息  
        sb.setLinks(false);  
        //设置将不间断空格由正规空格所替代  
        sb.setReplaceNonBreakingSpaces(true);  
        //设置将一序列空格由一个单一空格所代替  
        sb.setCollapse(true);  
        //传入要解析的URL  
        sb.setURL(link);  
        //返回解析后的网页纯文本信息  
        String content = sb.getStrings();
        
        sb.finishedParsing();
        
        return content;
	}
}
