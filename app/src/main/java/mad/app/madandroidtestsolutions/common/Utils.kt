package mad.app.madandroidtestsolutions.common

import java.text.DecimalFormat

class Utils {

     companion object{
         fun combineCurrencyAmount(currency: String, amount: String): String {
             return currency + moneyFormat(amount)
         }

         private fun moneyFormat(amount: String): String? {
             val formatter = DecimalFormat("###,###,##0.00")
             return formatter.format(amount.toDouble())
         }
     }
}