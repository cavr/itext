/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itextpdf;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author carlos
 */
public class ItextPdf {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException {
        // TODO code application logic here

        GeneratePdf generatePdf = new GeneratePdf("example.pdf");
        generatePdf.openPdf(20);
        generatePdf.addElements();
        generatePdf.addPhoto("descarga.png");
        generatePdf.addTable();
//        generatePdf.htmlDocument();
        generatePdf.closePdf();
    }

}
