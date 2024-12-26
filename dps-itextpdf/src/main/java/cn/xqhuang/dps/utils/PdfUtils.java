package cn.xqhuang.dps.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;


/**
 * pdf跑龙套
 *
 * @author huangxiangquan@yintatech.com
 * @date 2024/06/13 15:02
 */
public final class PdfUtils {

	private PdfUtils() {
	}

	/**
	 * 创建表格
	 *
	 * @param column        表格列数
	 * @param tableWidth    表格宽度
	 * @param isLockedWidth 是否锁定单元格宽度
	 * @param widths        单元格宽度
	 * @param horizontal    对齐方式
	 * @return
	 * @throws DocumentException
	 */
	public static PdfPTable createTable(int column, float tableWidth, boolean isLockedWidth, int[] widths,
			int horizontal) throws DocumentException {
		PdfPTable table = new PdfPTable(column);
		//表格的宽度
		table.setTotalWidth(tableWidth);
		//是否锁定单元格宽度
		table.setLockedWidth(isLockedWidth);
		if (widths != null) {
			//单元格宽度
			table.setWidths(widths);
		}
		//表格的宽度百分比
		table.setWidthPercentage(100);
		//表格对齐方式
		table.setHorizontalAlignment(horizontal);

		return table;
	}

	/**
	 * 创建单元格
	 *
	 * @param value      单元格内容
	 * @param font       字体
	 * @param fontSiz    字体大小
	 * @param fontStyle  字体是否加粗
	 * @param cellBorder 是否有边框   -1为带默认边框
	 * @param horizontal 水平对齐方式
	 * @param vertical   垂直对齐方式
	 * @param padding    内边距大小
	 * @param cellHeight 单元格高度
	 * @return
	 */
	public static PdfPCell createCell(String value, BaseFont font, float fontSiz, int fontStyle, int cellBorder,
			int horizontal, int vertical, float padding, float cellHeight) {
		PdfPCell cell = new PdfPCell(new Paragraph(value, new Font(font, fontSiz, fontStyle)));
		if (1 != cellBorder) {
			//设置单元边框
			cell.setBorder(cellBorder);
		}
		// 水平对齐
		cell.setHorizontalAlignment(horizontal);
		// 垂直对齐
		cell.setVerticalAlignment(vertical);
		//内边距
		cell.setPadding(padding);
		//固定高度
		cell.setFixedHeight(cellHeight);

		return cell;
	}

	/**
	 * 创建空白单元格
	 *
	 * @param font       字体
	 * @param cellBorder 是否有边框   -1为带默认边框
	 * @param cellHeight 单元格高度
	 * @return
	 */
	public static PdfPCell createBlankCell(BaseFont font, int cellBorder, float cellHeight) {
		PdfPCell cell = new PdfPCell(new Paragraph("", new Font(font, 0, Font.NORMAL)));
		//设置单元边框
		cell.setBorder(cellBorder);
		//内边距
		cell.setPadding(0);
		//固定高度
		cell.setFixedHeight(cellHeight);
		return cell;
	}

	/**
	 * 创建条码单元格
	 *
	 * @param cb            文件或图片的绝对位置
	 * @param value         条码内容
	 * @param cellBorder    是否有边框
	 * @param horizontal    水平对齐方式
	 * @param vertical      垂直对齐方式
	 * @param padding       内边距大小
	 * @param barcodeHeight 条码高度
	 * @param cellHeight    单元格高度
	 * @param font          条码下方内容字体  传入null则不显示下方内容
	 * @return
	 */
	public static PdfPCell createLabelCell(PdfContentByte cb, String value, int cellBorder, int horizontal,
			int vertical, float padding, float barcodeHeight, float cellHeight, BaseFont font) {
		// barcode128，将内容生成条形码
		Barcode128 barcode128 = new Barcode128();
		// 设置条形码高
		barcode128.setBarHeight(barcodeHeight);
		// 设置字符与条形码之间的距离
		barcode128.setBaseline(3);
		// 设置条码文字对齐
		barcode128.setTextAlignment(Element.ALIGN_BOTTOM);
		// 设置条码code128风格
		barcode128.setCodeSet(Barcode128.Barcode128CodeSet.AUTO);
		// 设置条形码内容
		barcode128.setCode(value);
		// 字体设置为null，条形码下方的文字就隐藏了
		barcode128.setFont(font);
		if (font != null) {
			barcode128.setSize(15);
		}

		// 将条形码转成图片，并添加到单元格中
		PdfPCell cellBarcode = new PdfPCell(Image.getInstance(barcode128.createImageWithBarcode(cb, null, null)),
				false);
		//设置单元边框
		cellBarcode.setBorder(cellBorder);
		// 水平对齐
		cellBarcode.setHorizontalAlignment(horizontal);
		// 垂直对齐
		cellBarcode.setVerticalAlignment(vertical);
		//内边距
		cellBarcode.setPadding(padding);
		//固定高度
		cellBarcode.setFixedHeight(cellHeight);

		return cellBarcode;
	}

	/**
	 * 创建图片单元格
	 *
	 * @param imgUrl            图片地址
	 * @param imgScalePercent   图片缩放比例
	 * @param imgXsize          图片长
	 * @param imgYsize          图片宽
	 * @param imgBorder         图片边框宽度
	 * @param horizontal        水平对齐方式
	 * @param vertical        	垂直对齐方式
	 * @param padding        	内边距大小
	 * @param cellHeight        单元格高度
	 * @return
	 */
	public static PdfPCell createImgCell(String imgUrl, float imgScalePercent, float imgXsize, float imgYsize,
			int imgBorder, int horizontal, int vertical, float padding, float cellHeight)
			throws IOException, BadElementException {
		// 图片对象
		Image img = Image.getInstance(imgUrl);
		// 图片边框宽度
		img.setBorder(imgBorder);
		// 图片居中
		img.setAlignment(Image.ALIGN_CENTER);
		// 将图像缩放到绝对宽度和绝对高度。
		img.scaleAbsolute(imgXsize, imgYsize);

		// 创建单元格
		PdfPCell cellImg = new PdfPCell();
		// 水平对齐
		cellImg.setHorizontalAlignment(horizontal);
		// 垂直对齐
		cellImg.setVerticalAlignment(vertical);
		// 内边距
		cellImg.setPadding(padding);
		// 固定高度
		cellImg.setFixedHeight(cellHeight);
		cellImg.setImage(img);

		return cellImg;
	}

	/**
	 * 创建跨行或者跨列单元格
	 *
	 * @param value      单元格内容
	 * @param font       字体
	 * @param fontSiz    字体大小
	 * @param fontStyle  字体是否加粗
	 * @param cellBorder 是否有边框   -1为带默认边框
	 * @param horizontal 水平对齐方式
	 * @param vertical   垂直对齐方式
	 * @param padding    内边距大小
	 * @param cellHeight 单元格高度
	 * @param rows 		 跨行数
	 * @param cols 		 跨列数
	 * @return
	 */
	public static PdfPCell PdfSpanRowOrColumnPCell(String value, BaseFont font, float fontSiz, int fontStyle,
			int cellBorder, int horizontal, int vertical, float padding, float cellHeight, Integer rows, Integer cols) {
		PdfPCell cell = new PdfPCell(new Paragraph(value, new Font(font, fontSiz, fontStyle)));
		if (1 != cellBorder) {
			//设置单元边框
			cell.setBorder(cellBorder);
		}
		// 水平对齐
		cell.setHorizontalAlignment(horizontal);
		// 垂直对齐
		cell.setVerticalAlignment(vertical);
		//内边距
		cell.setPadding(padding);
		//固定高度
		cell.setFixedHeight(cellHeight);
		if (rows != null) {
			//跨行
			cell.setRowspan(rows);
		}
		if (cols != null) {
			//跨列
			cell.setColspan(cols);
		}
		return cell;
	}

	/**
	 * 创建带下划线单元格
	 *
	 * @param value           单元格内容
	 * @param font            字体
	 * @param fontSiz         字体大小
	 * @param fontStyle       字体是否加粗
	 * @param horizontal      水平对齐方式
	 * @param vertical        垂直对齐方式
	 * @param padding         内边距大小
	 * @param cellHeight      单元格高度
	 * @param underscoreWidth 下划线宽度
	 * @param yPosition       相对于y轴绝对位置的距离
	 * @return
	 */
	public static PdfPCell createUnderscoreCell(String value, BaseFont font, float fontSiz, int fontStyle,
			int horizontal, int vertical, float padding, float cellHeight, float underscoreWidth, float yPosition) {
		Chunk chunk = new Chunk(value, new Font(font, fontSiz, fontStyle));
		//设置下划线
		chunk.setUnderline(underscoreWidth, yPosition);
		Phrase phrase = new Paragraph();
		phrase.add(chunk);
		PdfPCell cell = new PdfPCell(phrase);
		//设置单元边框
		cell.setBorder(PdfPCell.NO_BORDER);
		// 水平对齐
		cell.setHorizontalAlignment(horizontal);
		// 垂直对齐
		cell.setVerticalAlignment(vertical);
		//内边距
		cell.setPadding(padding);
		//固定高度
		cell.setFixedHeight(cellHeight);

		return cell;
	}

	/**
	 * 创建跨行或者跨列单元格
	 * @param cell
	 * @param rows
	 * @param cols
	 * @return
	 */
	public static PdfPCell createCrossCell(PdfPCell cell, Integer rows, Integer cols){
		if (rows != null) {
			//跨行
			cell.setRowspan(rows);
		}
		if (cols != null) {
			//跨列
			cell.setColspan(cols);
		}
		return cell;
	}

	/**
	 * 创建图片单元格
	 *
	 * @param imgUrl            图片地址
	 * @param imgScalePercent   图片缩放比例
	 * @param imgXsize          图片长
	 * @param imgYsize          图片宽
	 * @param imgBorder         图片边框宽度
	 * @return
	 */
	public static Image createImg(String imgUrl, float imgScalePercent, float imgXsize, float imgYsize,
			int imgBorder) throws IOException, BadElementException {

		// 图片对象
		Image img = Image.getInstance(imgUrl);
		// 图片边框宽度
		img.setBorder(imgBorder);
		// 将图像缩放到一定百分比
		img.scalePercent(imgScalePercent);
		// 图片居中
		img.setAlignment(Image.ALIGN_CENTER);
		// 将图像缩放到绝对宽度和绝对高度
		img.setAbsolutePosition(imgXsize, imgYsize);

		return img;
	}
}
