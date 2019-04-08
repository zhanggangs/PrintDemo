package com.demo.bean;

import java.util.List;

public class ReceiveMessage {
    /* 任务名称 */
    private String strPrintTask;
    /*
     打印页面方向
     0: 原点在纸的左下角，x从下到上，y从左到右。
     1: 原点在纸的左上角，x向右，y向下。
     2: 原点在纸的右上角，x *从上到下，y从右到左。
    */
    private Integer orient;
    /* 页面宽度 */
    private Double pageWidth;
    /* 页面高度 */
    private Double pageHeight;
    /* 页面名称*/
    private String pageName;
    /* 打印机名称 */
    private String printerName;
    /* 打印类型 */
    private String printType;
    /* 打印信息 */
    private List<ItemData> itemData;
    /* html打印信息*/
    private String html;
    /* 设置打印份数 */
    private Integer copies;

    public String getStrPrintTask() {
        return strPrintTask;
    }

    public void setStrPrintTask(String strPrintTask) {
        this.strPrintTask = strPrintTask;
    }

    public Integer getOrient() {
        return orient;
    }

    public void setOrient(Integer orient) {
        this.orient = orient;
    }

    public Double getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(Double pageWidth) {
        this.pageWidth = pageWidth;
    }

    public Double getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(Double pageHeight) {
        this.pageHeight = pageHeight;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public List<ItemData> getItemData() {
        return itemData;
    }

    public void setItemData(List<ItemData> itemData) {
        this.itemData = itemData;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
}
