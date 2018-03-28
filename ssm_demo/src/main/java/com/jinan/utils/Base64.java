package com.jinan.utils;

import java.util.zip.DataFormatException;

/**
 * 提供从bytes到base64的编码和反编码
 */
public class Base64 {

  /**
   * 返回一个经过base64编码之后的字符数组。.
   * 
   * @param data
   *          将要进行编码的字节数组。
   * @return char[] 编码之后的字符数组。
   */

  public static char[] encode(byte[] data) {
    char[] out = new char[((data.length + 2) / 3) * 4];

    //
    // 3 bytes encode to 4 chars. Output is always an even
    // multiple of 4 characters.
    //
    for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
      boolean quad = false;
      boolean trip = false;

      int val = (0xFF & (int) data[i]);
      val <<= 8;
      if ((i + 1) < data.length) {
        val |= (0xFF & (int) data[i + 1]);
        trip = true;
      }
      val <<= 8;
      if ((i + 2) < data.length) {
        val |= (0xFF & (int) data[i + 2]);
        quad = true;
      }
      out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
      val >>= 6;
      out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
      val >>= 6;
      out[index + 1] = alphabet[val & 0x3F];
      val >>= 6;
      out[index + 0] = alphabet[val & 0x3F];
    }
    return out;
  }
  
  /**
   * 功能:返回经过base64加密后的字符串后6位 add by yanghaibo 2012-11-15 09:45
   * @param idCard  	考生身份证号码
   * @param term    	考生所在期数
   * @param paperCode	考生试卷代码
   * @return
   */
  public static String getFtpPassword(String srcPwd){
	  char[] basePwd = encode(srcPwd.getBytes());
	  String result = new String(basePwd);
	  if(result.length() >= 7){
		  return result.substring(result.length()-6);
	  }else{
		  return result;
	  }
  }

  /**
   * 反编码base64.
   * 
   * @param data
   *          需要进行反编码的字符数组。
   * @return byte[] 经过反编码的字节数组。
   * @throws DataFormatException
   *           如果数据格式不对抛出此异常。
   */

  public static byte[] decode(char[] data) throws DataFormatException {
    int len = ((data.length + 3) / 4) * 3;
    if (data.length > 0 && data[data.length - 1] == '=') {
      --len;
    }
    if (data.length > 1 && data[data.length - 2] == '=') {
      --len;
    }
    byte[] out = new byte[len];

    int shift = 0; // # of excess bits stored in accum
    int accum = 0; // excess bits
    int index = 0;

    for (int ix = 0; ix < data.length; ix++) {
      int value = codes[data[ix] & 0xFF]; // ignore high byte of char
      if (value >= 0) { // skip over non-code
        accum <<= 6; // bits shift up by 6 each time thru
        shift += 6; // loop, with new bits being put in
        accum |= value; // at the bottom.
        if (shift >= 8) { // whenever there are 8 or more shifted in,
          shift -= 8; // write them out (from the top, leaving any
          out[index++] =
          // excess at the bottom for next iteration.
          (byte) ((accum >> shift) & 0xff);
        }
      }
    }
    if (index != out.length) {
      throw new DataFormatException("miscalculated data length!");
    }

    return out;
  }

  /**
   * 字符
   */
  private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
      .toCharArray();

  /**
   * 字符对照表
   */
  private static byte[] codes = new byte[256];
  static {
    for (int i = 0; i < 256; i++) {
      codes[i] = -1;
    }
    for (int i = 'A'; i <= 'Z'; i++) {
      codes[i] = (byte) (i - 'A');
    }
    for (int i = 'a'; i <= 'z'; i++) {
      codes[i] = (byte) (26 + i - 'a');
    }
    for (int i = '0'; i <= '9'; i++) {
      codes[i] = (byte) (52 + i - '0');
    }
    codes['+'] = 62;
    codes['/'] = 63;
  }
}