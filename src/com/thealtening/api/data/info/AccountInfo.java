/*    */ package com.thealtening.api.data.info;
/*    */ 
/*    */ import com.google.gson.annotations.SerializedName;
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
/*    */ public class AccountInfo
/*    */ {
/*    */   @SerializedName("hypixel.lvl")
/*    */   private int hypixelLevel;
/*    */   @SerializedName("hypixel.rank")
/*    */   private String hypixelRank;
/*    */   @SerializedName("mineplex.lvl")
/*    */   private int mineplexLevel;
/*    */   @SerializedName("mineplex.rank")
/*    */   private String mineplexRank;
/*    */   @SerializedName("labymod.cape")
/*    */   private boolean labymodCape;
/*    */   @SerializedName("5zig.cape")
/*    */   private boolean fiveZigCape;
/*    */   
/*    */   public int getHypixelLevel() {
/* 43 */     return this.hypixelLevel;
/*    */   }
/*    */   
/*    */   public String getHypixelRank() {
/* 47 */     return this.hypixelRank;
/*    */   }
/*    */   
/*    */   public int getMineplexLevel() {
/* 51 */     return this.mineplexLevel;
/*    */   }
/*    */   
/*    */   public String getMineplexRank() {
/* 55 */     return this.mineplexRank;
/*    */   }
/*    */   
/*    */   public boolean hasLabyModCape() {
/* 59 */     return this.labymodCape;
/*    */   }
/*    */   
/*    */   public boolean hasFiveZigCape() {
/* 63 */     return this.fiveZigCape;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     return String.format("AccountInfo[%s:%s:%s:%s:%s:%s]", new Object[] { Integer.valueOf(this.hypixelLevel), this.hypixelRank, Integer.valueOf(this.mineplexLevel), this.mineplexRank, Boolean.valueOf(this.labymodCape), Boolean.valueOf(this.fiveZigCape) });
/*    */   }
/*    */ }


/* Location:              C:\Users\fixme\OneDrive\Pulpit\LiquidBounce1.8.9 — kopia.jar!\com\thealtening\api\data\info\AccountInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */