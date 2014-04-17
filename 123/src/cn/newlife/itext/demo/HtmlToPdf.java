package cn.newlife.itext.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

public class HtmlToPdf {
	private String html_source;
	private String save_target_dir;

	public HtmlToPdf(String html_source, String save_target_dir) {
		this.html_source = html_source;
		this.save_target_dir = save_target_dir;
	}

	public void parseHtml2Pdf(String pdf_file_name) throws Exception {
		parseHtml2Pdf(pdf_file_name, null);
	}

	public void parseHtml2Pdf(String pdf_file_name, String cssfile_source)
			throws Exception {
		if (html_source == null || "".equals(html_source)
				|| !new File(html_source).exists())
			throw new Exception("源文件不存在");
		if (save_target_dir == null || "".equals(save_target_dir)
				|| !new File(save_target_dir).exists())
			throw new Exception("目标文件不存在");
		Document document = new Document();
		PdfWriter pdfWriter = PdfWriter.getInstance(document,
				new FileOutputStream(save_target_dir + "\\" + pdf_file_name));
		document.open();
		XMLWorkerHelper helper = XMLWorkerHelper.getInstance();
		if (cssfile_source != null && !"".equals(cssfile_source)
				&& new File(cssfile_source).exists()) {
			parseHtmlWithCSS(helper, document, pdfWriter, html_source,
					cssfile_source);

		} else {
			helper.parseXHtml(pdfWriter, document, new FileInputStream(
					html_source));
		}
		document.close();
	}

	private void parseHtmlWithCSS(XMLWorkerHelper helper, Document document,
			PdfWriter pdfWriter, String html_source, String cssfile_source)
			throws Exception {

		CSSResolver cssResolver = new StyleAttrCSSResolver();
		CssFile cssFile = helper.getCSS(new FileInputStream(cssfile_source));
		cssResolver.addCss(cssFile);

		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		//可以设置成想要的字体，这里比较简单
		//windows的字体路径在C:\Windows\Fonts下
		//linux 不清楚
		fontProvider.register("C:/windows/fonts/GARA.TTF");
		fontProvider.register("C:/windows/fonts/GARAIT.TTF");
		fontProvider.register("C:/windows/fonts/GARABD.TTF");
		fontProvider.addFontSubstitute("lowagie", "garamond");
		CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
		
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, pdfWriter);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new FileInputStream(html_source));
	}

	public String getHtml_source() {
		return html_source;
	}

	public void setHtml_source(String html_source) {
		this.html_source = html_source;
	}

	public String getSave_target_dir() {
		return save_target_dir;
	}

	public void setSave_target_dir(String save_target_dir) {
		this.save_target_dir = save_target_dir;
	}

}
