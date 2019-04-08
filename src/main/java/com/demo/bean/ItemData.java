package com.demo.bean;

public class ItemData {

    //类型 BARCODE为条形码 TEXT为文本
    private String type;
    //x轴坐标位置
    private Integer x;
    //Y轴坐标位置
    private Integer y;
    //宽度
    private Integer width;
    //高度
    private Integer height;
    //内容
    private String strContent;
    //条形码模块大小
    private Float moduleWidth;
    //打印机dpi
    private Integer dpi;
    //字体类型
    private String fontName;
    //字体大小
    private Integer fontSize;
    //需要添加图片的页码
    private Integer pageNum;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getStrContent() {
        return strContent;
    }

    public void setStrContent(String strContent) {
        this.strContent = strContent;
    }

    public Float getModuleWidth() {
        return moduleWidth;
    }

    public void setModuleWidth(Float moduleWidth) {
        this.moduleWidth = moduleWidth;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
