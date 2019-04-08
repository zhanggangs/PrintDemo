package com.demo.util;

import com.demo.bean.ItemData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

/**
 * 绘制打印的内容
 *
 * @ClassName DrawPrintContent
 * @Author ZhangGang
 * @Date 2019/1/23 9:57
 **/
public class DrawPrintContent implements Printable {

    public static List<ItemData> itemDataList = new ArrayList();

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {

        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) graphics;
        g2.scale(90.0 / 203, 90.0 / 203);

        double startX = pageFormat.getImageableX();
        double startY = pageFormat.getImageableY();

        //打印条码和字体
        for (int i = 0; i < itemDataList.size(); i++) {
            ItemData itemData = itemDataList.get(i);
            if ("BARCODE".equals(itemData.getType())) {
                BufferedImage barCodeImage = Barcode4jUtil.geneBarcode(itemData.getStrContent(), itemData.getWidth(),
                        itemData.getHeight(), itemData.getModuleWidth(), itemData.getDpi());
               g2.drawImage(barCodeImage, itemData.getX(), itemData.getY(), null);
            } else if ("TEXT".equals(itemData.getType())) {
                Font font = new Font(itemData.getFontName(), Font.PLAIN, itemData.getFontSize());
                g2.setFont(font);
                g2.drawString(itemData.getStrContent(), (float) (startX + itemData.getX()), (float) (startY + itemData.getY()));
            }
        }
        return PAGE_EXISTS;
    }

}
