package es.joshluq.cabishop.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import es.joshluq.cabishop.R

val fonts = FontFamily(
    Font(
        resId = R.font.cabify_circular_regular,
        weight = FontWeight.W900,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.cabify_circular_regular,
        weight = FontWeight.W900,
        style = FontStyle.Italic
    ),
    Font(
        resId = R.font.cabify_circular_regular,
        weight = FontWeight.W700,
        style = FontStyle.Normal
    )

)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)