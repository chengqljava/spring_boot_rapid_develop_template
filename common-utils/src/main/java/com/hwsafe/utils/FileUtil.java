package com.hwsafe.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hwsafe.common.NetworkProtocolEnum;
import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.exception.MacAlarmVideoException;
import com.hwsafe.validate.Check;

/**
 * @ClassName:FileUtil
 * @Description:(文件上传工具类)
 * @since JDK 1.8
 */
public final class FileUtil {

    /**
     * 读取文本文件(.txt)内容
     * 
     * @param filePathAndName
     *            带有完整绝对路径的文件名
     * @param encoding
     *            文本文件打开的编码方式
     * @return 返回文本文件的内容
     * @exception IOException
     *                io异常
     */
    public static String readTxt(String filePathAndName, String encoding)
            throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        BufferedReader br = null;
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data + " ");
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return st;
    }

    /**
     * @Title:getUploadPath
     * @Description TODO(获得上传文件路径).
     * @date 2015年7月28日
     * @author luyao
     * @param secondPath
     *            自定义目录
     * @param file
     *            文件
     * @return String
     * @throws Exception
     *             异常
     */
    public static String getUploadPath(String secondPath, MultipartFile file)
            throws Exception {
        String filename = file.getOriginalFilename();
        String[] names = filename.split("\\.");
        if (names.length > 1) {
            // 添加时间后缀
            filename = new Date().getTime() + "." + names[names.length - 1];
        }
        return secondPath + filename;
    }

    /**
     * @Title:checkFileExist
     * @Description TODO(检查文件是否已经存在 若存在，则返回true 若不存在，则返回false).
     * @date 2015年7月23日
     * @author luyao
     * @param fileString
     *            String
     * @param dirString
     *            String
     * @return boolean
     */
    public boolean checkFileExist(String fileString, String dirString) {
        String[] dirStrings = new File(dirString).list();
        boolean temp = false;
        for (int i = 0, len = dirStrings.length; i < len; i++) {
            if (fileString.equals(dirStrings[i])) {
                temp = true;
                break;
            }
        }
        return temp;
    }

    /**
     * @Title:copyDirectiory
     * @Description TODO(利用java本地拷贝文件及文件夹).
     * @date 2015年7月28日
     * @author luyao
     * @param objDir
     *            目标文件夹
     * @param srcDir
     *            源的文件夹
     * @throws IOException
     *             io异常
     */
    public static void copyDirectiory(String objDir, String srcDir)
            throws IOException {
        (new File(objDir)).mkdirs();
        File[] file = (new File(srcDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                FileInputStream input = new FileInputStream(file[i]);
                FileOutputStream output = new FileOutputStream(
                        objDir + "/" + file[i].getName());
                byte[] b = new byte[1024 * 5];
                int len;
                while ((len = input.read(b)) != -1) {
                    output.write(b, 0, len);
                }
                output.flush();
                output.close();
                input.close();
            }
            if (file[i].isDirectory()) {
                copyDirectiory(objDir + "/" + file[i].getName(),
                        srcDir + "/" + file[i].getName());
            }
        }
    }

    /**
     * @Title:upload
     * @Description TODO(文件本地保存).
     * @date 2015年7月28日
     * @author luyao
     * @param request
     *            HttpServletRequest
     * @param path
     *            自定义目录
     * @param file
     *            MultipartFile
     * @param fileUploadPath
     * @throws Exception
     *             异常
     */
    public static void upload(HttpServletRequest request, String path,
            MultipartFile file) throws Exception {
        String filePath = ResourceUtil.getUploadRootPath(request) + path;
        File dirPath = new File(filePath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        file.transferTo(new File(filePath));
    }

    /**
     * @Title:upload
     * @Description TODO(文件本地保存).
     * @date 2015年7月28日
     * @author luyao
     * @param request
     *            HttpServletRequest
     * @param path
     *            自定义目录
     * @param file
     *            MultipartFile
     * @param fileUploadPath
     * @throws Exception
     *             异常
     */
    public static File upload(String path, MultipartFile file,
            String fileUploadPath) throws Exception {
        String filePath = fileUploadPath + path;
        File dirPath = new File(filePath);
        File fileParent = dirPath.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        file.transferTo(dirPath);
        return dirPath;
    }

    /**
     * 删除文件
     * 
     * @param request
     * @param filePath
     * @return
     */
    public static boolean delete(HttpServletRequest request, String filePath) {
        String path = ResourceUtil.getUploadRootPath(request) + filePath;
        File delFile = new File(path);
        if (delFile.isFile() && delFile.exists()) {
            try {
                delFile.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     * 
     * @param request
     * @param filePath
     * @return
     */
    public static boolean delete(String fileUploadPath, String filePath) {
        String path = fileUploadPath;
        if (StringUtils.isNoneBlank(filePath)) {
            path = path + filePath;
        }
        File delFile = new File(path);
        if (delFile.isFile() && delFile.exists()) {
            try {
                delFile.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 删除文件
     * 
     * @param request
     * @param filePath
     * @return
     */
    public static boolean delete(String filePath) {
        File delFile = new File(filePath);
        if (delFile.isFile() && delFile.exists()) {
            try {
                delFile.delete();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * @Title:stringToFile
     * @Description TODO(将String写入指定文件中(会覆盖原文件数据)).
     * @date 2015年7月28日
     * @author luyao
     * @param text
     *            源String
     * @param fileName
     *            目标文件路径
     * @throws IOException
     *             io异常
     */
    public static void stringToFile(String text, String fileName)
            throws IOException {
        BufferedWriter os = new BufferedWriter(new FileWriter(fileName));
        os.write(text);
        os.flush();
        os.close();
    }

    /**
     * @Title:fileToBytes
     * @Description TODO(获取文件filePath的字节编码byte[]).
     * @date 2015年7月28日
     * @author luyao
     * @param filePath
     *            文件全路径
     * @return 文件内容的字节编码
     */
    public static byte[] fileToBytes(String filePath) {
        if (filePath == null) {
            return null;
        }
        File tmpFile = new File(filePath);
        byte[] retBuffer = new byte[(int) tmpFile.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            fis.read(retBuffer);
            fis.close();
            return retBuffer;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @Title:bytesToFile
     * @Description TODO(将byte[]转化成文件fullFilePath).
     * @date 2015年7月28日
     * @author luyao
     * @param fullFilePath
     *            文件全路径
     * @param content
     *            源byte[]
     */
    public static void bytesToFile(String fullFilePath, byte[] content) {
        if (fullFilePath == null || content == null) {
            return;
        }
        // 创建相应的目录
        File f = new File(getDir(fullFilePath));
        if (f == null || !f.exists()) {
            f.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(fullFilePath);
            fos.write(content);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title:getDir
     * @Description TODO(根据传入的文件全路径，返回文件所在路径).
     * @date 2015年7月28日
     * @author luyao
     * @param fullPath
     *            文件全路径
     * @return 文件所在路径
     */
    public static String getDir(String fullPath) {
        int iPos1 = fullPath.lastIndexOf("/");
        int iPos2 = fullPath.lastIndexOf("\\");
        iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
        return fullPath.substring(0, iPos1 + 1);
    }

    /**
     * @Title:getFileName
     * @Description TODO(根据传入的文件全路径，返回文件全名（包括后缀名）).
     * @date 2015年7月28日
     * @author luyao
     * @param fullPath
     *            文件全路径
     * @return 文件全名（包括后缀名）
     */
    public static String getFileName(String fullPath) {
        int iPos1 = fullPath.lastIndexOf("/");
        int iPos2 = fullPath.lastIndexOf("\\");
        iPos1 = (iPos1 > iPos2 ? iPos1 : iPos2);
        return fullPath.substring(iPos1 + 1);
    }

    /**
     * @Title:getFileSuffix
     * @Description TODO(获得文件名fileName中的后缀名).
     * @date 2015年7月28日
     * @author luyao
     * @param fileName
     *            源文件名
     * @return String 后缀名
     */
    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length());
    }

    /**
     * @Title:wrapFilePath
     * @Description TODO(转换文件路径中的\\为/).
     * @date 2015年7月28日
     * @author luyao
     * @param filePath
     *            要转换的文件路径
     * @return String
     */
    public static String wrapFilePath(String filePath) {
        filePath.replace('\\', '/');
        if (filePath.charAt(filePath.length() - 1) != '/') {
            filePath += "/";
        }
        return filePath;
    }

    /**
     * @Title:deleteDirs
     * @Description TODO(删除整个目录path,包括该目录下所有的子目录和文件).
     * @date 2015年7月28日
     * @author luyao
     * @param path
     *            文件全路径
     */
    public static void deleteDirs(String path) {
        if (path == null) {
            return;
        }
        File rootFile = new File(path);
        File[] files = rootFile.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                deleteDirs(file.getPath());
            } else {
                file.delete();
            }
        }
        rootFile.delete();
    }

    /**
     * @Title:encodeChineseDownloadFileName
     * @Description TODO(解决浏览器下载附件乱码问题).
     * @date 2016年8月19日
     * @author lzqiangPC
     * @param pFileName
     * @return
     * @throws Exception
     */
    public static String encodeChineseDownloadFileName(String pFileName)
            throws Exception {
        String filename = null;
        String agent = SpringContextHolder.getHttpServletRequest()
                .getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {// Firefox
                filename = "=?UTF-8?B?"
                        + (new String(org.apache.commons.codec.binary.Base64
                                .encodeBase64(pFileName.getBytes("UTF-8"))))
                        + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {// Chrome
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {// IE7+
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = StringUtils.replace(filename, "+", "%20");// 替换空格
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }

    /**
     * 附件下载
     * 
     * @param filename
     *            文件名
     * @param path
     *            文件路径
     * @param response
     */
    public static void download(String filename, String path,
            HttpServletResponse response) {
        InputStream in = null;
        OutputStream os = null;
        try {

            filename = encodeChineseDownloadFileName(filename);
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + filename);
            File file = new File(path);
            if (file.exists()) {
                in = new FileInputStream(path);
                os = response.getOutputStream();
                byte[] b = new byte[1024 * 1024];
                int length;
                while ((length = in.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 附件下载
     * 
     * @param filename
     *            文件名
     * @param in
     *            输入流
     * @param response
     */
    public static void download(String filename, InputStream in,
            HttpServletResponse response) {
        OutputStream os = null;
        try {
            filename = encodeChineseDownloadFileName(filename);
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + filename);
            if (in != null) {
                os = response.getOutputStream();
                byte[] b = new byte[1024 * 1024];
                int length;
                while ((length = in.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载项目文件下的模板文件
     * 
     * @param response
     *            response
     * @param rootPath
     *            存放文件模板的项目路径 例template/importfiles/knowledge_import_template/
     * @param fileName
     *            文件名
     * @param notIE
     *            判断是否为ie浏览器下的下载
     * @return 返回结果 成功或者文件不存在
     */
    public static String downloadFile(HttpServletResponse response,
            String rootPath, String fileName, boolean notIE) {
        InputStream stream = FileUtil.class.getClassLoader()
                .getResourceAsStream(rootPath + fileName);
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            String name = java.net.URLEncoder.encode(fileName, "UTF-8");
            if (notIE) {
                name = java.net.URLDecoder.decode(name, "ISO-8859-1");
            }
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + name);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(stream);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            // e1.getMessage()+"系统找不到指定的文件";
            return "系统找不到指定的文件";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    /*
     * @title 根据二进制字符串生成图片
     * 
     * @param data 生成图片的二进制字符串
     * 
     * @param fileName 图片名称(完整路径)
     * 
     * @param type 图片类型
     * 
     * @return
     */
    public static void saveImage(String data, String fileName, String type) {

        BufferedImage image = new BufferedImage(300, 300,
                BufferedImage.TYPE_BYTE_BINARY);
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, byteOutputStream);
            // byte[] date = byteOutputStream.toByteArray();
            byte[] bytes = hex2byte2(data);
            System.out.println("path:" + fileName);
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            file.write(bytes);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反格式化byte
     * 
     * @param s
     * @return
     */
    public static byte[] hex2byte2(String s) {
        byte[] src = s.toLowerCase().getBytes();
        byte[] ret = new byte[src.length / 2];
        for (int i = 0; i < src.length; i += 2) {
            byte hi = src[i];
            byte low = src[i + 1];
            hi = (byte) ((hi >= 'a' && hi <= 'f') ? 0x0a + (hi - 'a')
                    : hi - '0');
            low = (byte) ((low >= 'a' && low <= 'f') ? 0x0a + (low - 'a')
                    : low - '0');
            ret[i / 2] = (byte) (hi << 4 | low);
        }
        return ret;
    }

    public static void getResource(String filename, String path,
            String contentType, HttpServletResponse response) {
        InputStream in = null;
        OutputStream os = null;
        try {
            filename = encodeChineseDownloadFileName(filename);
//            response.setContentType("application/x-msdownload");
            response.setContentType(contentType);
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + filename);
            File file = new File(path);
            if (file.exists()) {
                in = new FileInputStream(path);
                os = response.getOutputStream();
                byte[] b = new byte[1024 * 1024];
                int length;
                while ((length = in.read(b)) > 0) {
                    os.write(b, 0, length);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取图片宽度
     * 
     * @param file
     *            图片文件
     * @return 宽度
     */
    public static int getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getWidth(null); // 得到源图宽
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取图片高度
     * 
     * @param file
     *            图片文件
     * @return 高度
     */
    public static int getImgHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int ret = -1;
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            ret = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 分别获取图片的宽和高
     * 
     * @param file
     * @return
     */
    public static Map<String, Integer> getImgWdAndHt(File file) {
        Map<String, Integer> reMap = new HashMap<String, Integer>(2);
        InputStream inputStream = null;
        BufferedImage bufImage = null;
        try {
            inputStream = new FileInputStream(file);
            bufImage = javax.imageio.ImageIO.read(inputStream);
            reMap.put("width", bufImage.getWidth(null));
            reMap.put("height", bufImage.getHeight(null));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufImage.flush();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    /**
     * @param file
     * @param filePath
     *            "D:\\images\\uploadimg\\";
     * @param dataPath
     *            uploadimg
     * @throws Exception
     */
    public static String uploadFile(MultipartFile file, String filePath,
            String dataBasePath) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String[] names = filename.split("\\.");
        if (names.length > 1) {
            // 添加时间后缀
            filename = names[0] + "_" + new Date().getTime() + "."
                    + names[names.length - 1];
        }
        FileOutputStream out = new FileOutputStream(filePath + filename);
        out.write(file.getBytes());
        out.flush();
        out.close();
        return dataBasePath + "/" + filename;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @return 返回文件相对路径
     * @throws Exception
     */
    public static String excelUploadFile(
            MultipartHttpServletRequest multipartRequest, String fileDirectory,
            String fileAbsoluteDirectory) throws Exception {
        Iterator<?> it = multipartRequest.getFileNames();
        String filePath = null;
        while (it.hasNext()) {
            String key = (String) it.next();
            MultipartFile mFile = multipartRequest.getFile(key);
            // 判断文件是否为空
            Check.checkNotNull(mFile, "文件为空!");

            // 获取文件名
            String name = mFile.getOriginalFilename();
            // 进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
            Check.checkArgument((ExcelUtil.isExcel2003(name)
                    || ExcelUtil.isExcel2007(name)), "文件格式错误!");

            filePath = getUploadPath(fileDirectory, mFile); // 文件子路径
            upload(filePath, mFile, fileAbsoluteDirectory); // 上传文件
        }
        return filePath;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @return 上传多个文件返回多文件相对路径
     * @throws Exception
     */
    public static List<String> uploadMultiFile(
            MultipartHttpServletRequest multipartRequest, String fileDirectory,
            String fileAbsoluteDirectory) throws Exception {
        Iterator<?> it = multipartRequest.getFileNames();
        String filePath = null;
        List<String> fileDirectoryList = new ArrayList<>();
        while (it.hasNext()) {
            String key = (String) it.next();
            MultipartFile mFile = multipartRequest.getFile(key);
            // 判断文件是否为空
            Check.checkNotNull(mFile, "文件为空!");
            filePath = FileUtil.getUploadPath(fileDirectory, mFile); // 文件子路径
            FileUtil.upload(filePath, mFile, fileAbsoluteDirectory); // 上传文件
            fileDirectoryList.add(filePath);
        }
        return fileDirectoryList;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @param fileType
     *            允许的文件类型
     * @return 上传多个文件返回多文件相对路径
     * @throws Exception
     */
    public static List<String> uploadMultiFileType(
            MultipartHttpServletRequest multipartRequest, String fileDirectory,
            String fileAbsoluteDirectory, String fileType) throws Exception {
        Iterator<?> it = multipartRequest.getFileNames();
        String filePath = null;
        List<String> fileDirectoryList = new ArrayList<>();
        while (it.hasNext()) {
            String key = (String) it.next();
            MultipartFile mFile = multipartRequest.getFile(key);
            // 判断文件是否为空
            Check.checkNotNull(mFile, "文件为空!");

            // 获取文件名
            String name = mFile.getOriginalFilename();
            Check.checkArgument(fileType.contains(getFileSuffix(name)),
                    "文件格式错误!");

            filePath = getUploadPath(fileDirectory, mFile); // 文件子路径
            upload(filePath, mFile, fileAbsoluteDirectory); // 上传文件
            fileDirectoryList.add(filePath);
        }
        return fileDirectoryList;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @return 上传单个文件返回文件相对路径
     * @throws Exception
     */
    public static String uploadSignleFile(MultipartFile multipartFile,
            String fileDirectory, String fileAbsoluteDirectory)
            throws Exception {
        String filePath = null;
        // 判断文件是否为空
        Check.checkNotNull(multipartFile, "文件为空!");
        filePath = FileUtil.getUploadPath(fileDirectory, multipartFile); // 文件子路径
        FileUtil.upload(filePath, multipartFile, fileAbsoluteDirectory); // 上传文件

        return filePath;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @param fileType
     *            允许的文件类型
     * @return 上传单个文件返回文件相对路径
     * @throws Exception
     */
    public static String uploadSignleFileType(MultipartFile multipartFile,
            String fileDirectory, String fileAbsoluteDirectory, String fileType)
            throws Exception {
        String filePath = null;
        // 判断文件是否为空
        Check.checkNotNull(multipartFile, "文件为空!");
        // 获取文件名
        String name = multipartFile.getOriginalFilename();
        // 获取文件名
        Check.checkArgument(fileType.contains(getFileSuffix(name)), "文件格式错误!");
        filePath = getUploadPath(fileDirectory, multipartFile); // 文件子路径
        upload(filePath, multipartFile, fileAbsoluteDirectory); // 上传文件

        return filePath;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @return 上传单个文件返回文件相对路径
     * @throws Exception
     */
    public static String uploadSignleFile(
            MultipartHttpServletRequest multipartRequest, String fileDirectory,
            String fileAbsoluteDirectory) throws Exception {
        Iterator<?> it = multipartRequest.getFileNames();
        String filePath = null;
        while (it.hasNext()) {
            String key = (String) it.next();
            MultipartFile mFile = multipartRequest.getFile(key);
            // 判断文件是否为空
            Check.checkNotNull(mFile, "文件为空!");
            filePath = getUploadPath(fileDirectory, mFile); // 文件子路径
            upload(filePath, mFile, fileAbsoluteDirectory); // 上传文件

        }
        return filePath;
    }

    /**
     * @param multipartRequest
     * @param fileDirectory
     *            文件相对目录 template/
     * @param fileAbsoluteDirectory
     *            文件保存绝对目录 C://upload
     * @param fileType
     *            允许的文件类型
     * @return 上传单个文件返回文件相对路径
     * @throws Exception
     */
    public static String uploadSignleFileType(
            MultipartHttpServletRequest multipartRequest, String fileDirectory,
            String fileAbsoluteDirectory, String fileType) throws Exception {
        Iterator<?> it = multipartRequest.getFileNames();
        String filePath = null;
        while (it.hasNext()) {
            String key = (String) it.next();
            MultipartFile mFile = multipartRequest.getFile(key);
            // 判断文件是否为空
            Check.checkNotNull(mFile, "文件为空!");

            // 获取文件名
            String name = mFile.getOriginalFilename();
            // 进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
            Check.checkArgument(fileType.contains(getFileSuffix(name)),
                    "文件格式错误!");

            filePath = getUploadPath(fileDirectory, mFile); // 文件子路径
            upload(filePath, mFile, fileAbsoluteDirectory); // 上传文件

        }
        return filePath;
    }

    /**
     * 根据(无https证书 + http)网络图片地址和目标上传路径进行下载图片至服务器
     * 
     * @param urlList
     * @param path
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws Exception
     * @author liuxb
     * @date 2019-12-12
     */
    public static void downloadNoneCertsPicByUrl(String picHttpUrl, String path)
            throws MacAlarmVideoException, NoSuchAlgorithmException,
            NoSuchProviderException, KeyManagementException, IOException {
        BufferedInputStream dataInputStream = null;
        HttpURLConnection httpsUrlConn = null;
        URL picUrl = new URL(picHttpUrl);
        String lowPicHttpUrlStr = picHttpUrl.toLowerCase();

        if (lowPicHttpUrlStr.contains(NetworkProtocolEnum.HTTPS.getCode())) {
            // 下载https协议网络图片地址
            HttpsURLConnection.setDefaultHostnameVerifier(
                    new FileUtil().new NullHostNameVerifier());
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            // 打开restful链接
            httpsUrlConn = (HttpURLConnection) picUrl.openConnection();
            httpsUrlConn.setConnectTimeout(130000);// 连接超时 单位毫秒
            httpsUrlConn.setReadTimeout(130000);// 读取超时 单位毫秒
            dataInputStream = new BufferedInputStream(
                    httpsUrlConn.getInputStream());
        } else if (lowPicHttpUrlStr
                .contains(NetworkProtocolEnum.HTTP.getCode())) {
            // 下载http协议网络图片地址
            dataInputStream = new BufferedInputStream(picUrl.openStream());
        }

        // 声明文件类(目标文件上传路径不存在时自动创建)
        File dirPathFile = new File(path);
        File fileParent = dirPathFile.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }

        // javaIO流操作下载保存图片至服务端
        BufferedOutputStream fileOutputStream = new BufferedOutputStream(
                new FileOutputStream(dirPathFile));
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        byte[] buffer = new byte[2048];
        int length;
        while ((length = dataInputStream.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        fileOutputStream.write(output.toByteArray());

        // javaIO操作关闭流和链接
        if (null != httpsUrlConn) {
            httpsUrlConn.disconnect();
        }
        dataInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 访问https（忽略证书校验-TrustManager）
     */
    static TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };

    /**
     * 访问https（忽略证书校验-NullHostNameVerifier）
     */
    public class NullHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }

}
