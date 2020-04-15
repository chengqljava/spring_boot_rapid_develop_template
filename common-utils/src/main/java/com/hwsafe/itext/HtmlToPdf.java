package com.hwsafe.itext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.HTML;
import com.itextpdf.tool.xml.html.TagProcessorFactory;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * 
 * 
 * 项目名称：common-utils 类名称：HtmlToPdf 类描述：html转化为PDF 创建人：chengql 创建时间：2020年3月18日
 * 下午3:17:24 修改人：chengql 修改时间：2020年3月18日 下午3:17:24 修改备注：
 * 
 * @version
 *
 */
public class HtmlToPdf {

    private static void htmlToPdf(String html, File file) throws Exception {
        Document document = null;
        ByteArrayInputStream bais = null;
        try {
            // 定义一个A4大小的矩形组件
            // Rectangle rect = new Rectangle(PageSize.A4);
            // 设置背景颜色为浅灰色
            // rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
            // 设置border类型为box
            // rect.setBorder(rect.BOX);
            // 设置border的颜色为浅灰色
            // rect.setBorderColor(BaseColor.LIGHT_GRAY);
            // 设置border的宽度
            // rect.setBorderWidth(5);
            // step 1
            // 创建一个PDF文档,将rect作为文档的预设样式,后面的10,10,10,10是文档的边外距
            document = new Document();
            // 页边空白
            document.setMargins(20, 20, 15, 30);
            BaseFont bfChinese;
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            MyFontProvider myFontProvider = new MyFontProvider(BaseColor.BLACK, "", "", false, false, 9, 1, bfChinese);
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            // step 3
            document.open();

            final TagProcessorFactory tagProcessorFactory = Tags.getHtmlTagProcessorFactory();
            tagProcessorFactory.removeProcessor(HTML.Tag.IMG);
            tagProcessorFactory.addProcessor(new ImageTagProcessor(), HTML.Tag.IMG);

            final CssFilesImpl cssFiles = new CssFilesImpl();
            cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
            final StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);
            final HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(myFontProvider));
            hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(tagProcessorFactory);
            final HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(document, writer));
            final Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);

            final XMLWorker worker = new XMLWorker(pipeline, true);

            final Charset charset = Charset.forName("UTF-8");
            final XMLParser xmlParser = new XMLParser(true, worker, charset);

            bais = new ByteArrayInputStream(html.getBytes("UTF-8"));
            xmlParser.parse(bais, charset);

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            // step 5
            if (document != null)
                document.close();
            if (bais != null)
                bais.close();
        }
    }

    public static void main(String[] args) {
        String content = "<html><head><meta content=\"text/html;charset=UTF-8\"></meta></head><body><p>哈哈</p><img src='c:/upload/1573784966103.jpg'></img></body></html>";
        File file = new File("d:/genter/he.pdf");
        try {
            htmlToPdf(content, file);
            file = new File("d:/genter/executorHtmlToPdf.pdf");
            // executorHtmlToPdf(content, file);
            readContent();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    static String readContent() {
        // 读文件到Stringbuffer
        StringBuffer sb = new StringBuffer();
        String sourcePath = "D:\\genter\\hot.html";// 源文件路径(*.*)
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(sourcePath));
            String str;
            while ((str = br.readLine()) != null) {// 逐行读取
                sb.append(str);// 加在StringBuffer尾
                // sb.append("\r\n");// 行尾加换行符
            }
            br.close();// 别忘记，切记
            System.out.println(sb.toString());
            File file = new File("d:/genter/hot.pdf");
            htmlToPdf(sb.toString(), file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 线程对列
     */
    private static BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10); // 固定为10的线程队列
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 10, 1, TimeUnit.HOURS, queue);

    /**
     * 封装 executor
     * 
     * @param htmlContent
     * @param file
     * @return
     */
    public static void executorHtmlToPdf(String htmlContent, File file) {
        executor.execute(getThreadHtmlToPdf(htmlContent, file));

    }

    private static Runnable getThreadHtmlToPdf(String htmlContent, File file) {
        return new Runnable() {
            public void run() {
                try {
                    htmlToPdf(htmlContent, file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
