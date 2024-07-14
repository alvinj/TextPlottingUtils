package com.alvinalexander.utils

import scala.util.{Try, Using}
import java.io.IOException
import java.io.{BufferedReader, FileReader, BufferedWriter, FileWriter, File}
import scala.util.Using
import java.text.DecimalFormat

object FileUtils:

    def readFile(filename: String): Try[String] =
        Using(BufferedReader(FileReader(filename))) { reader =>
            Iterator.continually(reader.readLine())
                    .takeWhile(_ != null)
                    .toSeq
                    .mkString("\n")
        }

    def writeFile(filename: String, content: String): Try[Unit] =
        Using(BufferedWriter(FileWriter(File(filename)))) { bufferedWriter =>
            bufferedWriter.write(content)
        }


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



