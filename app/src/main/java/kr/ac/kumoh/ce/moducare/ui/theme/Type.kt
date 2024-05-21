package kr.ac.kumoh.ce.moducare.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kr.ac.kumoh.ce.moducare.R

val pretendard = FontFamily(
    listOf(
        Font(R.font.pretendardvariable, FontWeight.W200, FontStyle.Normal),
        Font(R.font.pretendardmedium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.pretendardbold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.pretendardblack, FontWeight.Black, FontStyle.Normal),
        Font(R.font.pretendardlight, FontWeight.Light, FontStyle.Normal),
        Font(R.font.pretendardthin, FontWeight.Thin, FontStyle.Normal),
        Font(R.font.pretendardextralight, FontWeight.ExtraLight, FontStyle.Normal),
        Font(R.font.pretendardsemibold, FontWeight.SemiBold, FontStyle.Normal),
        Font(R.font.pretendardextrabold, FontWeight.ExtraBold, FontStyle.Normal),
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    bodySmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp
    ),


    headlineLarge = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),

)


