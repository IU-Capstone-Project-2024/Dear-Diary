package com.example.deardairy.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.deardairy.R

val regularPlayfairDisplay = Font(R.font.playfairdisplay_regular, FontWeight.Normal)
val italicPlayfairDisplay = Font(R.font.playfairdisplay_italic, FontWeight.Normal, FontStyle.Italic)
val mediumPlayfairDisplay = Font(R.font.playfairdisplay_medium, FontWeight.Medium)
val mediumItalicPlayfairDisplay = Font(R.font.playfairdisplay_mediumitalic, FontWeight.Medium, FontStyle.Italic)
val semiBoldPlayfairDisplay = Font(R.font.playfairdisplay_semibold, FontWeight.SemiBold)
val semiBoldItalicPlayfairDisplay = Font(R.font.playfairdisplay_semibolditalic, FontWeight.SemiBold, FontStyle.Italic)
val boldPlayfairDisplay = Font(R.font.playfairdisplay_bold, FontWeight.Bold)
val boldItalicPlayfairDisplay = Font(R.font.playfairdisplay_bolditalic, FontWeight.Bold, FontStyle.Italic)
val extraBoldPlayfairDisplay = Font(R.font.playfairdisplay_extrabold, FontWeight.ExtraBold)
val extraBoldItalicPlayfairDisplay = Font(R.font.playfairdisplay_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic)
val blackPlayfairDisplay = Font(R.font.playfairdisplay_black, FontWeight.Black)
val blackItalicPlayfairDisplay = Font(R.font.playfairdisplay_blackitalic, FontWeight.Black, FontStyle.Italic)


val playfairDisplayFontFamily = FontFamily(
    regularPlayfairDisplay,
    italicPlayfairDisplay,
    mediumPlayfairDisplay,
    mediumItalicPlayfairDisplay,
    semiBoldPlayfairDisplay,
    semiBoldItalicPlayfairDisplay,
    boldPlayfairDisplay,
    boldItalicPlayfairDisplay,
    extraBoldPlayfairDisplay,
    extraBoldItalicPlayfairDisplay,
    blackPlayfairDisplay,
    blackItalicPlayfairDisplay
)

val TitleTextStyle = TextStyle(
    fontSize = 22.sp,
    color = DarkBlueColor,
    fontFamily = playfairDisplayFontFamily,
    fontWeight = FontWeight.ExtraBold
)

val ButtonTextStyle = TextStyle(
    fontSize = 20.sp,
    fontFamily = playfairDisplayFontFamily,
    fontWeight = FontWeight.Normal
)

val BodyTextStyle = TextStyle(
    fontSize = 16.sp,
    color = Color(0xFF886699),
    fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Bold
)

val HintTextStyle = TextStyle(
    fontSize = 16.sp,
    color = Color(0xFFCCCCCC),
    fontFamily = FontFamily.Serif,
    fontWeight = FontWeight.Bold
)
