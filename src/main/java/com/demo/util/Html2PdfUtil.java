package com.demo.util;

import com.demo.bean.ItemData;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class Html2PdfUtil {

    /**
     * 字体路径
     */
    private static final String FONT_PATH = "font/simsun.ttc";

    private static final String PDF_PATH = "printTemp/temp.pdf";

    private static final String NEW_PDF_PATH = "printTemp/new.pdf";

    /**
     * 生成 PDF 文件
     *
     * @param html         HTML字符串
     * @param itemDataList 打印的条码内容
     * @return String 新的PDF文件路径
     */
    public static String createPDF(String html, List<ItemData> itemDataList) {
        if (html == null || html.length() <= 0) {
            return null;
        }
        StringBuffer pdfHtml = new StringBuffer();
        pdfHtml.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                "<html lang=\"en\">" +
                "<head>" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>" +
                "<style type=\"text/css\">" +
                // 设置中文字体
                "body {font-family: SimSun;margin-top:22pt}" +
                // 这个是分页的css 只要在想分页的地方加上<div class='pageNext'></div>即可；
                ".pageNext{page-break-after: always;}" +
                // 设置页面大小
                //"@page{size:a4}" +
                //这是页眉页脚的css，
                // 页眉调用为直接加入<div class='header1'></div> 即可；
                // 页脚调用为直接加入<div class='footer'></div> 即可；
                "@page {" +
                "size:a4;" +
                "margin-left:0.5cm;" +
                "margin-right:0.5cm;" +
                //"margin-top:95pt;" +
                "@top-center {content: element(header)}" +
                "@bottom-center {content: element(footer)}" +
                "}" +
                ".header1 {position: running(header);font-family: SimSun;}" +
                ".footer {position: running(footer);font-family: SimSun;}" +
                //"table{table-layout:fixed; word-break:break-all;}" +
                "table td {text-align: center;}" +
                "</style>" +
                "</head>" +
                "<body>");
        pdfHtml.append(html);
        pdfHtml.append("</body></html>");

        OutputStream os = null;
        try {
            os = new FileOutputStream(PDF_PATH);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(pdfHtml.toString());
            // 解决中文支持问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(FONT_PATH, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            addImg(PDF_PATH, itemDataList);
            return NEW_PDF_PATH;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 添加图片
     *
     * @return String 添加图片后的pdf文件路径
     * @Author ZhangGang
     * @Date 2019/3/6 16:49
     * @Param
     **/
    public static void addImg(String pdfPath, List<ItemData> itemDataList) {

        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
            reader = new PdfReader(pdfPath);
            stamp = new PdfStamper(reader, new FileOutputStream(NEW_PDF_PATH));
            if (itemDataList != null && itemDataList.size() > 0) {
                for (int i = 0; i < itemDataList.size(); i++) {
                    ItemData itemData = itemDataList.get(i);
                    if ("BARCODE".equals(itemData.getType())) {
                        BufferedImage bufferedImage = Barcode4jUtil.geneBarcode(itemData.getStrContent(), 200,
                                10, itemData.getModuleWidth(), itemData.getDpi());
                        Image img = Image.getInstance(bufferedImageToByteArray(bufferedImage));
                        img.setAlignment(Image.LEFT | Image.TEXTWRAP);
                        img.setBorderWidth(0);//边宽
                        img.setAbsolutePosition(itemData.getX(), itemData.getY());
                        img.scaleToFit(itemData.getWidth(), itemData.getHeight());// 大小
                        //获取某一页
                        PdfContentByte pdfContentByte = stamp.getUnderContent(itemData.getPageNum());
                        pdfContentByte.addImage(img);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stamp != null) {
                    stamp.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    /**
     * 将bufferImage转换为byte[]
     *
     * @return byte[]
     * @Author ZhangGang
     * @Date 2019/3/6 16:33
     * @Param bufferedImage 图片
     **/
    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(bufferedImage);
        return os.toByteArray();
    }

    public static void main(String[] args) {
    }
}
