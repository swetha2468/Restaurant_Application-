package com.group4;

public class Util {
   public static String convertMoneyToString(int money)
   {

      int dollars = (money) / 100;
      int cents = (money % 100);

      return String.format("$%d.%02d",dollars, cents);
   }

   public static int getSubTotal() {
      return App.getOrder().calculateTotal();
   }

   public static int getTax() {
      float taxAmt = getSubTotal() * App.getConfiguration().getSales_tax();
      int taxAmtInt = (int)Math.ceil(taxAmt);
      return taxAmtInt;
   }

   public static int getTotal() {
      return getSubTotal() + getTax();
   }
}
