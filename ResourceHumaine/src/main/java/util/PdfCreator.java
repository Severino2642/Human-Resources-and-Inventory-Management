package util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import demande.Demande;
import demande.ExperienceDemande;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import personne.Experience;
import personne.Personne;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfCreator {
    public Document getPDFForDemande(Demande demande,String filePath) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subTiteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("AVIS DE RECRUTEMENT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph(demande.getIntitule(),normalFont));
            document.add(new Paragraph(demande.getDate_demande().toString(),normalFont));
            document.add(new Paragraph(demande.getSexe().getIntitule(),normalFont));
            document.add(new Paragraph("\n"));

            demande.setExperiences();
            Paragraph subTitle =new Paragraph("Experiences requise :", subTiteFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            ExperienceDemande [] experienceDemandes = demande.getExperiences().toArray(new ExperienceDemande[]{});
            for (int i = 0; i < experienceDemandes.length; i++) {
                int num = i+1;
                document.add(new Paragraph(num+". "+experienceDemandes[i].getTalent().getNom()+" "+experienceDemandes[i].getDuree(),normalFont));
            }
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public Document getPDFForCV(Personne personne,String filePath) {

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subTiteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("CV", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Nom :"+personne.getNom(),normalFont));
            document.add(new Paragraph("Adresse email :"+personne.getEmail(),normalFont));
            document.add(new Paragraph("Sexe :"+personne.getSexe().getIntitule(),normalFont));
            document.add(new Paragraph("\n"));

            personne.setExperiences();
            Paragraph subTitle =new Paragraph("Experiences :", subTiteFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            Experience[] experiences = personne.getExperiences();
            for (int i = 0; i < experiences.length; i++) {
                int num = i+1;
                document.add(new Paragraph(num+". "+experiences[i].getTalent().getNom()+" "+experiences[i].getDuree(),normalFont));
            }
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public void UploadAnnoncePdf (HttpServletRequest request, Demande demande, String fileName) throws IOException {

        File dossier = new File(request.getServletContext().getRealPath("/annonce"));
        if (!dossier.exists()) {
            dossier.mkdir();
        }

        File pdfFile = new File(dossier ,fileName);
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subTiteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("AVIS DE RECRUTEMENT", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph(demande.getIntitule(),subTiteFont));
            document.add(new Paragraph("Date d'annonce : "+demande.getDate_demande().toString(),normalFont));
            document.add(new Paragraph("Sexe : "+demande.getSexe().getIntitule(),normalFont));
            document.add(new Paragraph("\n"));

            demande.setExperiences();
            Paragraph subTitle =new Paragraph("Experiences requise :", subTiteFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            ExperienceDemande [] experienceDemandes = demande.getExperiences().toArray(new ExperienceDemande[]{});
            for (int i = 0; i < experienceDemandes.length; i++) {
                int num = i+1;
                document.add(new Paragraph(num+". "+experienceDemandes[i].getTalent().getNom()+" "+experienceDemandes[i].getDuree()+" ans",normalFont));
            }
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UploadCVPdf (HttpServletRequest request, Personne personne, String fileName) throws IOException {

        File dossier = new File(request.getServletContext().getRealPath("/cv"));
        if (!dossier.exists()) {
            dossier.mkdir();
        }

        File pdfFile = new File(dossier ,fileName);
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font subTiteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            Paragraph title = new Paragraph("CV", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Nom :"+personne.getNom(),normalFont));
            document.add(new Paragraph("Adresse email :"+personne.getEmail(),normalFont));
            document.add(new Paragraph("Sexe :"+personne.getSexe().getIntitule(),normalFont));
            document.add(new Paragraph("\n"));

            personne.setExperiences();
            Paragraph subTitle =new Paragraph("Experiences :", subTiteFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            Experience[] experiences = personne.getExperiences();
            for (int i = 0; i < experiences.length; i++) {
                int num = i+1;
                document.add(new Paragraph(num+". "+experiences[i].getTalent().getNom()+" "+experiences[i].getDuree()+"ans",normalFont));
            }
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
