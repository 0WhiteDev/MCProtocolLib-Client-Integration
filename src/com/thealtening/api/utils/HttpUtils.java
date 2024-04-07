/*    */ package com.thealtening.api.utils;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HttpUtils
/*    */ {
/*    */   protected String connect(String link) throws IOException {
/* 30 */     URL url = new URL(link);
/* 31 */     InputStream inputStream = url.openStream();
/* 32 */     BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
/*    */     
/* 34 */     StringBuilder stringBuilder = new StringBuilder(); String line;
/* 35 */     while ((line = br.readLine()) != null) {
/* 36 */       stringBuilder.append(line).append("\n");
/*    */     }
/* 38 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtening\ap\\utils\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */