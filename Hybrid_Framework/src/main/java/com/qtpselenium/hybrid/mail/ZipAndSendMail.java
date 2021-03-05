package com.qtpselenium.hybrid.mail;

import com.qtpselenium.hybrid.util.Constants;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

public class ZipAndSendMail {
    static String[] toEmails = {"kv_pat2010@yahoo.com", "kv_pat2000@yahoo.com"};
    static String fromUser = "kamlesh.vp";// just the id alone without @gmail.com
    static String password = "Nitirisha2010";
    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;

    public static void main(String[] args) throws Exception {
        String reportFolder = Constants.REPORTS_FOLDER;

//		Find latest folder
        File dir = new File(reportFolder);
        File[] files = dir.listFiles();
        File lastModified = Arrays.stream(files).filter(File::isDirectory).max(Comparator.comparing(File::lastModified)).orElse(null);
        System.out.println("Report Folder Name: " + lastModified.getName());

        // zip
        Zip.zipDir(reportFolder + "/" + lastModified.getName(), reportFolder + "/" + lastModified.getName() + ".zip");

        // mail
        Mail javaEmail = new Mail();

        javaEmail.setMailServerProperties();

        javaEmail.createEmailMessage("Automation Test Reports", // subject
                "Please find the reports in attachment.", // body
                reportFolder + "/" + lastModified.getName() + ".zip", // attachment path
                "Reports.zip", // name of attachment
                toEmails// receivers
        );
        javaEmail.sendEmail(fromUser, password);

    }

//	mention correct package name : com.qtpselenium.hybrid.mail
//	mvn exec:java -Djava.version=1.8 -D"exec.mainClass"="com.qtpselenium.hybrid.mail.ZipAndSendMail"

}