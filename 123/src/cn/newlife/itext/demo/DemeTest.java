package cn.newlife.itext.demo;

public class DemeTest {
	
	public static void main(String[] args) {
		HtmlToPdf demo = new HtmlToPdf("D:\\html1.html", "D:\\");
		try {
			demo.parseHtml2Pdf("test.pdf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
