/*    */ package com.thealtening.api.data;
/*    */ 
/*    */ import com.thealtening.api.data.info.AccountInfo;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountData
/*    */ {
/*    */   private String token;
/*    */   private String password;
/*    */   private String username;
/*    */   private boolean limit;
/*    */   private AccountInfo info;
/*    */   
/*    */   public String getToken() {
/* 36 */     return this.token;
/*    */   }
/*    */   
/*    */   public String getPassword() {
/* 40 */     return this.password;
/*    */   }
/*    */   
/*    */   public String getUsername() {
/* 44 */     return this.username;
/*    */   }
/*    */   
/*    */   public boolean isLimit() {
/* 48 */     return this.limit;
/*    */   }
/*    */   
/*    */   public AccountInfo getInfo() {
/* 52 */     return this.info;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return String.format("AccountData[%s:%s:%s:%s]", new Object[] { this.token, this.username, this.password, Boolean.valueOf(this.limit) });
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 62 */     if (!(obj instanceof AccountData)) {
/* 63 */       return false;
/*    */     }
/* 65 */     AccountData castedAccountInfo = (AccountData)obj;
/* 66 */     return (castedAccountInfo.getUsername().equals(this.username) && castedAccountInfo.getToken().equals(this.token));
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtening\api\data\AccountData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */