package com.alvinalexander.utils

import scala.util.Try
import java.text.DecimalFormat

object MathUtils:

    /**
     * @param percentage A string value like `"5.33%"`.
     * @return A double value like `0.0533` inside a `Try`.
     */
    def convertPercentageStringToDouble(percentage: String): Try[Double] = Try {
        formatToTwoDecimalPlaces(
            toDoubleOrDefault( Some(percentage.stripSuffix("%")), 0.0)
        )
    }

    def formatToTwoDecimalPlaceString(d: Double): String =
        val df = DecimalFormat("0.00")
        val stringRez = df.format(d)
        stringRez

    def formatToTwoDecimalPlaces(d: Double): Double =
        BigDecimal(d)
            .setScale(2, BigDecimal.RoundingMode.HALF_UP)
            .toDouble

    def toDoubleOrDefault(stringOption: Option[String], default: Double): Double =
        try
            val value = stringOption.get.toDouble
            formatToTwoDecimalPlaces(value)
        catch
            case e: Exception => formatToTwoDecimalPlaces(default)



