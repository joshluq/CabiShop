package es.joshluq.cabishop.common.extension

import java.text.NumberFormat
import java.util.Locale


fun Double.toAmount(): String {
    val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("es", "ES")).apply {
        minimumFractionDigits = 2
    }
    return "${numberFormat.format(this)} €"
}