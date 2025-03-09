package com.example.demo.ContractController;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.example.demo.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ContractController {

    public byte[] generateContract(Usuario usuario, String signatureImagePath) {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Phrase("Contrato de Usuario", new Font(Font.FontFamily.HELVETICA, 16)));
            document.add(new Phrase("\n\nNombre: " + usuario.getNombre()));
            document.add(new Phrase("\nCorreo: " + usuario.getEmail()));
            
            String cargo = (usuario.getRol() != null) ? usuario.getRol().getNombreCargo() : "No especificado";
            document.add(new Phrase("\nCargo: " + cargo));
            
            document.add(new Phrase("\nFecha de Ingreso: " + usuario.getFechaIngreso()));
            document.add(new Phrase("\nDías Trabajados: " + usuario.getDiasTrabajados()));

            if (signatureImagePath != null) {
                Path signaturePath = Paths.get(signatureImagePath);
                if (Files.exists(signaturePath)) {
                    Image signature = Image.getInstance(signatureImagePath);
                    signature.setAbsolutePosition(100f, 100f);
                    document.add(signature);
                } else {
                    System.out.println("La imagen de la firma no se encuentra en la ruta especificada: " + signatureImagePath);
                }
            } else {
                System.out.println("No se proporcionó una ruta de firma.");
            }

            document.close();
            System.out.println("Contrato generado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al generar el contrato: " + e.getMessage());
        }

        return baos.toByteArray();
    }
}

