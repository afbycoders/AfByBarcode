package com.afby.barcode.main;

import java.awt.print.PrinterException;
import java.io.IOException;

import com.afby.barcode.BarcodeGenerator;
import com.google.zxing.WriterException;

public class RunBarcode {
	public static void main(String[] args) throws WriterException, PrinterException, IOException {
		new BarcodeGenerator().generateBarcode("some test input");
	}
}
