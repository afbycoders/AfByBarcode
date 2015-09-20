package com.afby.barcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 *
 * @author afbyCoders
 */
public class BarcodeGenerator {

	private static final int WIDTH = 156;
	private static final int HEIGHT = 32;
	private static final String FILE_TYPE="png";
	private static final String FILE_PATH="C:/temp/barcode.png";


	public void generateBarcode(String input) throws WriterException, PrinterException, IOException {

		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		BitMatrix byteMatrix = multiFormatWriter.encode(input, BarcodeFormat.CODE_128, WIDTH, HEIGHT, hintMap);

		//logic to output to file
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		graphics.setColor(Color.BLACK);

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		ImageIO.write(image, FILE_TYPE, new File(FILE_PATH));

		//TODO fix logic to print directly
		/*PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new OutputPrinter(byteMatrix));
		boolean doPrint = job.printDialog();
		if (doPrint) {
			job.print();
		}*/
	}

	private class OutputPrinter implements Printable{

		private BitMatrix byteMatrix;

		public OutputPrinter(BitMatrix byteMatrix) {
			this.byteMatrix = byteMatrix;
        }

		@Override
        public int print(Graphics graphicsPrint, PageFormat pageFormat, int pageIndex) throws PrinterException {
			if (pageIndex > 0) {
	            return NO_SUCH_PAGE;
	        }

			Graphics2D graphics = (Graphics2D) graphicsPrint;

			/*BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, WIDTH, HEIGHT);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < WIDTH; i++) {
				for (int j = 0; j < HEIGHT; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}*/
	        return PAGE_EXISTS;
        }

	}

}
