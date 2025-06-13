package com.company.apis.Utils;

import java.io.ByteArrayOutputStream;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

public class Convert {
    public String[] convertStringToObject(String listImage) {
        if (listImage == null) {
            return null;
        }
        String jsonString = listImage;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String[] imageUrls = mapper.readValue(jsonString, String[].class);
            return imageUrls;
        } catch (Exception e) {
        }
        return null;
    }

    public static byte[] convertHtmlToPdf(String html) {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
        }
        return outputStream.toByteArray();

    }
}
