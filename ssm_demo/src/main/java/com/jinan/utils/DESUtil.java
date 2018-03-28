package com.jinan.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

public class DESUtil {

  private final String DESKEY = "huayanginfo.com";

  private Key key;
  
  private Key paperXmlKey;
  
  private String branchCode;

  private static DESUtil util = new DESUtil();

  private DESUtil() {
    getKey();// 生成密匙
  }

  public static DESUtil getInst() {
    return util;
  }

  /**
   * 根据参数生成KEY
   */
  public void getKey() {
    try {
      KeyGenerator _generator = KeyGenerator.getInstance("DES");
      _generator.init(new SecureRandom(DESKEY.getBytes()));
      this.key = _generator.generateKey();
      _generator = null;
    } catch (Exception e) {
      throw new RuntimeException("Error initializing DESUtil class. Cause: "
          + e);
    }
  }
  
  /**
   * 功能：生成试卷XML KEY
   */
  public void generatePaperKey() {
    try {
      KeyGenerator _generator = KeyGenerator.getInstance("DES");
      _generator.init(new SecureRandom(branchCode.getBytes()));
      paperXmlKey= _generator.generateKey();
      _generator = null;
    } catch (Exception e) {
      throw new RuntimeException("Error initializing DESUtil class. Cause: "
          + e);
    }
  }

  /**
   * 文件file进行加密并保存目标文件destFile中
   * 
   * @param file
   *          要加密的文件 如c:/test/srcFile.txt
   * @param destFile
   *          加密后存放的文件名 如c:/加密后文件.txt
   */
  public void encrypt(String file, String destFile) throws Exception {
    InputStream is = new FileInputStream(file);
    OutputStream os = new FileOutputStream(destFile);
    encrypt(is, os);
  }
  
  /**
   * 功能：试卷XML加密 add by yanghaibo 2014-03-25 15:43
   * @param file
   * @param destFile
   * @param branchCode
   * @throws Exception
   */
  public void encrypt(String file, String destFile, String branchCode) throws Exception {
    InputStream is = new FileInputStream(file);
    OutputStream os = new FileOutputStream(destFile);
    encrypt(is, os, branchCode);
  }
  
  public void encrypt(InputStream is, OutputStream os, String branchCode) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    // cipher.init(Cipher.ENCRYPT_MODE, getKey());
    this.branchCode = branchCode;
    generatePaperKey();
    cipher.init(Cipher.ENCRYPT_MODE, paperXmlKey);
    CipherInputStream cis = new CipherInputStream(is, cipher);
    byte[] buffer = new byte[8192];
    int r;
    while ((r = cis.read(buffer, 0, buffer.length)) > 0) {
      os.write(buffer, 0, r);
    }
    cis.close();
  }

  public void encrypt(InputStream is, OutputStream os) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    // cipher.init(Cipher.ENCRYPT_MODE, getKey());
    cipher.init(Cipher.ENCRYPT_MODE, this.key);
    CipherInputStream cis = new CipherInputStream(is, cipher);
    byte[] buffer = new byte[8192];
    int r;
    while ((r = cis.read(buffer, 0, buffer.length)) > 0) {
      os.write(buffer, 0, r);
    }
    cis.close();
  }

  /**
   * 文件采用DES算法解密文件
   * 
   * @param file
   *          已加密的文件 如c:/加密后文件.txt *
   * @param destFile
   *          解密后存放的文件名 如c:/ test/解密后文件.txt
   */
  public void decrypt(InputStream is, String dest) throws Exception {
    OutputStream os = new FileOutputStream(dest);
    decrypt(is, os);
  }
  public void decrypt(InputStream is, String dest, String branchCode) throws Exception {
    OutputStream os = new FileOutputStream(dest);
    decrypt(is, os, branchCode);
  }

  public void decrypt(InputStream is, OutputStream os) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(Cipher.DECRYPT_MODE, this.key);
    CipherOutputStream cos = new CipherOutputStream(os, cipher);
    byte[] buffer = new byte[8192];
    int r;
    while ((r = is.read(buffer, 0, buffer.length)) >= 0) {
      cos.write(buffer, 0, r);
    }
    try {
      is.close();
      cos.close();
      os.close();
    } catch (Exception e) {

    }
  }
  
  public void decrypt(InputStream is, OutputStream os, String branchCode) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    this.branchCode = branchCode;
    generatePaperKey();
    cipher.init(Cipher.DECRYPT_MODE, paperXmlKey);
    CipherOutputStream cos = new CipherOutputStream(os, cipher);
    byte[] buffer = new byte[8192];
    int r;
    while ((r = is.read(buffer, 0, buffer.length)) >= 0) {
      cos.write(buffer, 0, r);
    }
    try {
      is.close();
      cos.close();
      os.close();
    } catch (Exception e) {

    }
  }
  
  public byte[] decrypt(byte[] desByte, String branchCode) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    this.branchCode = branchCode;
    generatePaperKey();
    cipher.init(Cipher.DECRYPT_MODE, paperXmlKey);
    return cipher.doFinal(desByte);    
  }
  
  public byte[] encrypt(byte[] desByte, String branchCode) throws Exception {
    Cipher cipher = Cipher.getInstance("DES");
    this.branchCode = branchCode;
    generatePaperKey();
    cipher.init(Cipher.ENCRYPT_MODE, paperXmlKey);
    return cipher.doFinal(desByte);
  }

  public String getBranchCode() {
    return branchCode;
  }

  public void setBranchCode(String branchCode) {
    this.branchCode = branchCode;
  }
  

}
