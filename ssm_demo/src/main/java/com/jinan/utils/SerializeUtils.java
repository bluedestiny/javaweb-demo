package com.jinan.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtils {  
      
    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);  
      
     

    /**
     * 序列化对象到硬盘
     * 
     * @param obj
     * @param fName
     * @return
     */
    public static boolean serializeObject(Object obj, String fName) {
      boolean result = false;
      FileOutputStream fos = null;
      ObjectOutputStream oos = null;
      try {
        fos = new FileOutputStream(fName);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        result = true;
      } catch (FileNotFoundException e) {
        logger.error("[Serialize Object] error :" + e.getMessage(), e);
      } catch (IOException e) {
        logger.error("[Serialize Object] error :" + e.getMessage(), e);
      } catch (Exception e) {
        logger.error("[Serialize Object] error :" + e.getMessage(), e);
      } finally {
        try {
          if (oos != null)
            oos.close();
          if (fos != null)
            fos.close();
        } catch (Exception e) {
          logger.error("[Serialize Object] error :" + e.getMessage());
        }
      }
      return result;
    }

    /**
     * 从文件反序列化对象
     * 
     * @param fName
     * @return
     */
    public static Object deseralizeObject(String fName) {
      return deseralizeObject(new File(fName));
    }

    /**
     * 从文件反序列化对象
     * 
     * @param fName
     * @return
     */
    public static Object deseralizeObject(File file) {
      FileInputStream fins = null;
      ObjectInputStream ois = null;
      try {
        fins = new FileInputStream(file);
        ois = new ObjectInputStream(fins);
        return ois.readObject();
      } catch (Exception e) {
        logger.error("[Deserialize Object] error :" + e, e);
      } finally {
        try {
          ois.close();
          fins.close();
        } catch (IOException e) {
          logger.error("[Deserialize Object] error :" + e, e);
        }
      }
      return null;
    }
    /** 
     * 反序列化 
     * @param bytes 
     * @return 
     */  
    public static Object deserialize(byte[] bytes) {  
          
        Object result = null;  
          
        if (isEmpty(bytes)) {  
            return null;  
        }  
  
        try {  
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);  
            try {  
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);  
                try {  
                    result = objectInputStream.readObject();  
                }  
                catch (ClassNotFoundException ex) {  
                    throw new Exception("Failed to deserialize object type", ex);  
                }  
            }  
            catch (Throwable ex) {  
                throw new Exception("Failed to deserialize", ex);  
            }  
        } catch (Exception e) {  
            logger.error("Failed to deserialize",e);  
        }  
        return result;  
    }  
      
    public static boolean isEmpty(byte[] data) {  
        return (data == null || data.length == 0);  
    }  
  
    /** 
     * 序列化 
     * @param object 
     * @return 
     */  
    public static byte[] serialize(Object object) {  
          
        byte[] result = null;  
          
        if (object == null) {  
            return new byte[0];  
        }  
        try {  
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);  
            try  {  
                if (!(object instanceof Serializable)) {  
                    throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + " requires a Serializable payload " +  
                            "but received an object of type [" + object.getClass().getName() + "]");  
                }  
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);  
                objectOutputStream.writeObject(object);  
                objectOutputStream.flush();  
                result =  byteStream.toByteArray();  
            }  
            catch (Throwable ex) {  
                throw new Exception("Failed to serialize", ex);  
            }  
        } catch (Exception ex) {  
            logger.error("Failed to serialize",ex);  
        }  
        return result;  
    }  
}  