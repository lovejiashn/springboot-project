package com.jiashn.springbootproject.office.util;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Objects;

/**
 * @author jiangjs
 * @date 2021-11-27 11:42
 */
@Component
public class AsposeOfficeToPdfUtil {
    private final static Logger log = LoggerFactory.getLogger(AsposeOfficeToPdfUtil.class);

    private final static String SYSTEM_PROPERTY = "os.name";
    private final static String SYSTEM_NAME = "linux";

    public boolean checkedLicense(String suffix){
        boolean checkRes = Boolean.FALSE;
        InputStream licStream = null;
        try {
            licStream = AsposeOfficeToPdfUtil.class.getClassLoader().getResourceAsStream("license.xml");
            if (Objects.nonNull(licStream)){
                switch (suffix){
                    case "doc":
                    case "docx":
                        License license = new License();
                        license.setLicense(licStream);
                        checkRes = Boolean.TRUE;
                        break;
                    case "xls":
                    case "xlsx":
                        com.aspose.cells.License cellLicense = new com.aspose.cells.License();
                        cellLicense.setLicense(licStream);
                        checkRes = Boolean.TRUE;
                        break;
                    case "ppt":
                    case "pptx":
                        com.aspose.slides.License sliLicense = new com.aspose.slides.License();
                        sliLicense.setLicense(licStream);
                        checkRes = Boolean.TRUE;
                        break;
                    default:
                        log.info("{}:该文件格式不支持",suffix);
                }
            }
        } catch (Exception e){
            log.error("aspose校验license报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(licStream)){
                     licStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return checkRes;
    }

    /**
     * 将office转换成pdf文件
     * @param sourceFile 源文件
     * @param destFile 目标文件地址
     * @param suffix 源文件后缀名
     */
    public boolean officeToPdf(String sourceFile, String destFile, String suffix){
        boolean changeRes = Boolean.FALSE;
        switch (suffix){
            case "txt":
                break;
            case "doc":
            case "docx":
                changeRes = this.wordToPdf(sourceFile,destFile);
                break;
            case "xls":
            case "xlsx":
                changeRes = this.excelToPdf(sourceFile,destFile);
                break;
            case "ppt":
            case "pptx":
                changeRes = this.pptToPdf(sourceFile, destFile);
                break;
            default:
                log.info("{}:该文件格式暂不支持。",suffix);
                break;
        }
        return changeRes;
    }

    /**
     * Word转Pdf
     * @param sourceFile 源文件
     * @param destFile 目标文件
     * @return 返回操作结果
     */
    private boolean wordToPdf(String sourceFile, String destFile){
        FileOutputStream fos = null;
        Document doc;
        boolean changeRes = Boolean.FALSE;
        //linux环境转换出现乱码，则在usr/share/font安装windows字符集
        if (System.getProperty(SYSTEM_PROPERTY).toLowerCase().contains(SYSTEM_NAME)){
            FontSettings.getDefaultInstance().setFontsFolder(File.separator + "usr"+
                    File.separator + "share" + File.separator + "font" + File.separator,true);
        }
        try {
            fos = new FileOutputStream(destFile);
            doc = new Document(sourceFile);
            doc.save(fos, SaveFormat.PDF);
            changeRes = Boolean.TRUE;
        } catch (Exception e) {
            log.error("doc转换pdf报错:{}",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fos)){
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return changeRes;
    }

    private boolean excelToPdf(String sourceFile, String destFile){
        FileOutputStream fos = null;
        boolean changeRes = Boolean.FALSE;
        try {
            fos = new FileOutputStream(destFile);
            Workbook wb = new Workbook(sourceFile);
            wb.save(fos,com.aspose.cells.SaveFormat.PDF);
            changeRes = Boolean.TRUE;
        } catch (Exception e) {
            log.error("Excel转pdf报错：{}",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fos)){
                    fos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return changeRes;
    }

    private boolean pptToPdf(String sourceFile, String destFile){
        FileOutputStream bos = null;
        boolean changeRes = Boolean.FALSE;
        try {
            bos = new FileOutputStream(destFile);
            Presentation pt = new Presentation(sourceFile);
            pt.save(bos, com.aspose.slides.SaveFormat.Pdf);
            changeRes = Boolean.TRUE;
        } catch (FileNotFoundException e) {
            log.error("ppt转pdf报错：{}",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(bos)){
                    bos.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return changeRes;
    }


    public InputStream changeFileToDocx(InputStream sourceFile, String destFile){
        FileOutputStream fos = null;
        FileInputStream fis = null;
        Document doc;
        //linux环境转换出现乱码，则在usr/share/font安装windows字符集
        if (System.getProperty(SYSTEM_PROPERTY).toLowerCase().contains(SYSTEM_NAME)){
            FontSettings.getDefaultInstance().setFontsFolder(File.separator + "usr"+
                    File.separator + "share" + File.separator + "font" + File.separator,true);
        }
        try {
            fos = new FileOutputStream(destFile);
            doc = new Document(sourceFile);
            doc.save(fos, SaveFormat.DOCX);
            fis = FileUtils.openInputStream(new File(destFile));
        } catch (Exception e) {
            log.error("doc转换pdf报错:{}",e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fos)){
                    fos.close();
                }
                if (StringUtils.isNotBlank(destFile)){
                    File file = new File(destFile);
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return fis;
    }
}
