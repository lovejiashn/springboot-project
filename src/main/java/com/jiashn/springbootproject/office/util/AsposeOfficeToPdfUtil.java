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
                        log.info("{}:????????????????????????",suffix);
                }
            }
        } catch (Exception e){
            log.error("aspose??????license?????????{}",e.getMessage());
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
     * ???office?????????pdf??????
     * @param sourceFile ?????????
     * @param destFile ??????????????????
     * @param suffix ??????????????????
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
                log.info("{}:??????????????????????????????",suffix);
                break;
        }
        return changeRes;
    }

    /**
     * Word???Pdf
     * @param sourceFile ?????????
     * @param destFile ????????????
     * @return ??????????????????
     */
    private boolean wordToPdf(String sourceFile, String destFile){
        FileOutputStream fos = null;
        Document doc;
        boolean changeRes = Boolean.FALSE;
        //linux?????????????????????????????????usr/share/font??????windows?????????
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
            log.error("doc??????pdf??????:{}",e.getMessage());
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
            log.error("Excel???pdf?????????{}",e.getMessage());
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
            log.error("ppt???pdf?????????{}",e.getMessage());
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
        //linux?????????????????????????????????usr/share/font??????windows?????????
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
            log.error("doc??????pdf??????:{}",e.getMessage());
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
