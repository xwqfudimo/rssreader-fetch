package fetch;

import org.htmlparser.beans.StringBean;
import org.junit.Test;

public class TextExtractTest {

	@Test
	public void test() {
		String link = "http://www.csdn.net/article/2014-02-13/2818405-story-about-function-of-getting-red-package-in-weixin";
//		String html = ContentRunnable.getContent(link);
//		
//		String content = TextExtract.getInstance().parse(html);
//		System.out.println(content);
		
		StringBean sb = new StringBean();  
        
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
        
        String result = TextExtract.parse(content);
        
        System.out.println(result);
	}

}
