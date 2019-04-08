package com.demo.util;

import com.demo.bean.ItemData;
import com.demo.bean.ReceiveMessage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;

import javax.print.*;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.awt.print.*;
import java.io.*;
import java.util.List;

/**
 * 打印工具类
 *
 * @ClassName Print
 * @Author ZhangGang
 * @Date 2019/1/23 9:57
 **/
public class Print {


    /**
     * 添加打印内容
     *
     * @Author ZhangGang
     * @Date 2019/1/23 14:01
     **/
    public void addItemData(ItemData itemData) {
        DrawPrintContent.itemDataList.add(itemData);
    }

    /**
     * 接收消息,执行打印
     *
     * @Author ZhangGang
     * @Date 2019/2/13 9:39
     **/
    public void printExec(ReceiveMessage receiveMessage) {

        //判断接收消息是否为空
        if (receiveMessage == null || "".equals(receiveMessage)) {
            return;
        }

        //解析发送过来的信息
        String printer = receiveMessage.getPrinterName();
        if (printer != null) {
            if ("LABEL".equals(receiveMessage.getPrintType())) {
                LabelPrint(printer, receiveMessage.getOrient(), receiveMessage.getPageWidth(),
                        receiveMessage.getPageHeight(), receiveMessage.getCopies(), receiveMessage.getItemData());
            } else if ("A4".equals(receiveMessage.getPrintType())) {
                String newPdfPath = Html2PdfUtil.createPDF(receiveMessage.getHtml(), receiveMessage.getItemData());
                //获取文件调用打印机打印
                PDFprint(printer, receiveMessage.getPageWidth(), receiveMessage.getPageHeight(), receiveMessage.getOrient(), receiveMessage.getCopies(), new File(newPdfPath));
            } else {
                System.err.println("打印类型不存在!");
            }

        }
    }


    /**
     * 标签打印
     *
     * @Author ZhangGang
     * @Date 2019/2/13 9:41
     * @Param msg 接收到的信息
     **/
    public void LabelPrint(String printerName, Integer orient, Double pageWidth, Double pageHeight, Integer copies, List<ItemData> itemDataList) {
        //解析接收到的消息,进行打印
        try {
            Book book = new Book();
            PageFormat pf = new PageFormat();
            pf.setOrientation(orient);
            // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符
            Paper p = new Paper();
            // 纸张大小
            //PageFormat指明打印页格式（页面大小以点为计量单位，1点为1英才的1/72，1英寸为25.4毫米。A4纸大致为595×842点）
            p.setSize(pageWidth, pageHeight);
            //设置图片区域大小
            p.setImageableArea(0, 0, pageWidth, pageHeight);
            pf.setPaper(p);
            // 把 PageFormat 和 Printable 添加到book中，组成一个页面
            book.append(new DrawPrintContent(), pf);
            Print pr = new Print();

            for (int i = 0; i < itemDataList.size(); i++) {
                pr.addItemData(itemDataList.get(i));
            }

            // 获取打印服务对象
            PrinterJob job = PrinterJob.getPrinterJob();
            // 设置打印类
            job.setPageable(book);
            //设置打印份数
            job.setCopies(copies);
            //指定打印机名称
            HashAttributeSet hs = new HashAttributeSet();

            hs.add(new PrinterName(printerName, null));
            PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);

            if (pss.length == 0) {
                JOptionPane.showMessageDialog(null, "打印失败，未找到名称为:" + printerName + "的打印机，请检查。", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            job.setPrintService(pss[0]);
            job.print();
            DrawPrintContent.itemDataList.clear();

        } catch (Exception ex) {
            ex.printStackTrace();
            DrawPrintContent.itemDataList.clear();
        }
    }


    /**
     * PDF文件打印
     *
     * @Author ZhangGang
     * @Date 2019/2/13 9:32
     * @Param printerName 打印机名称
     * @Param orient 打印方向
     * @Param copies 打印份数
     * @Param file 文件
     **/
    public void PDFprint(String printerName, Double pageWidth, Double pageHeight, Integer orient, Integer copies, File file) {
        PDDocument document = null;
        try {
            document = PDDocument.load(file);
            PrinterJob printJob = PrinterJob.getPrinterJob();
            printJob.setJobName(file.getName());
            if (printerName != null) {
                // 查找并设置打印机
                //获得本台电脑连接的所有打印机
                PrintService[] printServices = PrinterJob.lookupPrintServices();
                if (printServices == null || printServices.length == 0) {
                    JOptionPane.showMessageDialog(null, "打印失败，未找到可用打印机，请检查。", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PrintService printService = null;
                //匹配指定打印机
                for (int i = 0; i < printServices.length; i++) {
                    if (printServices[i].getName().contains(printerName)) {
                        printService = printServices[i];
                        break;
                    }
                }
                if (printService != null) {
                    printJob.setPrintService(printService);
                } else {
                    JOptionPane.showMessageDialog(null, "打印失败，未找到名称为" + printerName + "的打印机，请检查。", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            //设置纸张及缩放
            PDFPrintable pdfPrintable = new PDFPrintable(document, Scaling.ACTUAL_SIZE);
            //设置多页打印
            Book book = new Book();
            PageFormat pageFormat = new PageFormat();
            //设置打印方向
            pageFormat.setOrientation(orient);
            //设置纸张
            Paper paper = getPaper(pageWidth, pageHeight);
            pageFormat.setPaper(paper);
            book.append(pdfPrintable, pageFormat, document.getNumberOfPages());
            printJob.setPageable(book);
            //设置打印份数
            printJob.setCopies(copies);
            //添加打印属性
            HashPrintRequestAttributeSet pars = new HashPrintRequestAttributeSet();
            //设置单双页
            pars.add(Sides.DUPLEX);
            printJob.print(pars);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PrinterException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 设置纸张
     *
     * @Author ZhangGang
     * @Date 2019/2/13 9:32
     **/
    public Paper getPaper(Double pageWidth, Double pageHeight) {
        Paper paper = new Paper();
//        // 默认为A4纸张，对应像素宽和高分别为 595, 842
//        int width = 595;
//        int height = 842;
//        // 设置边距，单位是像素，10mm边距，对应 28px
        int marginLeft = 0;
        int marginRight = 0;
        int marginTop = 0;
        int marginBottom = 0;
        paper.setSize(pageWidth, pageHeight);
        // 下面一行代码，解决了打印内容为空的问题
        paper.setImageableArea(marginLeft, marginRight, pageWidth - (marginLeft + marginRight), pageHeight - (marginTop + marginBottom));
        return paper;
    }

}
