/*    */ package com.thealtening.api.utils.exceptions;
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
/*    */ public class TheAlteningException
/*    */   extends RuntimeException
/*    */ {
/*    */   public TheAlteningException(String error, String errorMessage) {
/* 27 */     super(String.format("[%s]: %s", new Object[] { error, errorMessage }));
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtening\ap\\utils\exceptions\TheAlteningException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */