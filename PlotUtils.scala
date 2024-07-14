package com.alvinalexander.utils

import com.alvinalexander.utils.FileUtils.writeFile
import com.alvinalexander.utils.MathUtils.*
import scala.io.AnsiColor.*

object PlotUtils:

    // see: https://en.wikipedia.org/wiki/Box-drawing_characters
    val sparklineChar: Char = '\u2593'

    /**
     * Output looks like this:
     *
     *     +-------------------------|-------------------------+
     *                 ^
     *
     * Requirements that are *not* enforced in the code:
     *     1) `plotWidthInChars` must be an even number
     *     2) `high` must be > than `low`
     *     3) `current` must be >= `low` and <= `high`
     *
     * Sample usage:
     *
     *     println(asciiValueInRangeChart(0, 100, 10))
     *     println(asciiValueInRangeChart(0, 100, 33, 50, true))
     *
     * Sample values for thinking: hi=100, low=25, diff=75, current=33
     * (the sample line chart is not for these numbers)
     */
    def asciiValueInRangeChart(
        low: Double,
        high: Double,
        current: Double,
        plotWidthInChars: Int = 50, // must be an even number
        showNumbersOnPlot: Boolean = false,
        debug: Boolean = false
    ): String =
        val plotLine = createPlotLine(plotWidthInChars)
        val (diff, valuePerDash, halfwayValue, dashesForCurrentValue) = calculateNeededValues(
            low, high, current, plotWidthInChars
        )
        if debug then printDebugInfo(diff, valuePerDash, halfwayValue, dashesForCurrentValue)
        val indicatorLine = createIndicatorLine(current, halfwayValue, dashesForCurrentValue)
        if showNumbersOnPlot then
            val firstLine = createLowHighValuesLine(low, high, plotWidthInChars)
            val lastLine  = createCurrentPriceLine(current, dashesForCurrentValue, plotWidthInChars)
            s"${firstLine}\n${plotLine}\n${indicatorLine}\n${lastLine}"
        else
            s"$plotLine\n$indicatorLine"

    private def createCurrentPriceLine(
        current: Double,
        dashesForCurrentValue: Int,
        plotWidthInChars: Int
    ): String =
        val roundedCurrentValue = formatToTwoDecimalPlaceString(current)
        val roundedCurrentWidth = roundedCurrentValue.length
        val approxNumDashes = dashesForCurrentValue
        val lastLineNumBlanks = approxNumDashes + "+".length - (roundedCurrentWidth/2)
        val lastLine = " " * lastLineNumBlanks + roundedCurrentValue
        lastLine

    private def createLowHighValuesLine(
        low: Double,
        high: Double,
        plotWidthInChars: Int
    ): String =
        val roundedLow = formatToTwoDecimalPlaceString(low)
        val roundedHigh = formatToTwoDecimalPlaceString(high)
        val lowWidth = roundedLow.length
        val highWidth = roundedHigh.length
        val spacesNeeded = plotWidthInChars - highWidth - lowWidth + s"+|+".length
        val firstLine = s"$roundedLow" + " " * spacesNeeded + roundedHigh
        firstLine

    private def printDebugInfo(
        diff: Double,
        valuePerDash: Double,
        halfwayValue: Double,
        dashesForCurrentValue: Int
    ): Unit =
        System.err.println(s"diff=$diff, valuePerDash=$valuePerDash, halfwayValue=$halfwayValue, dashesForCurrentValue=$dashesForCurrentValue")

    private def calculateNeededValues(
        low: Double,
        high: Double,
        current: Double,
        plotWidthInChars: Int
    ): (Double, Double, Double, Int) =
        val diff = high - low
        val valuePerDash = diff / plotWidthInChars
        val halfwayValue = diff / 2
        val dashesForCurrentValue = (current / valuePerDash).round.toInt - (low / valuePerDash).round.toInt
        (diff, valuePerDash, halfwayValue, dashesForCurrentValue)

    private def createPlotLine(plotWidthInChars: Int): String =
        val halfWidth = plotWidthInChars / 2
        val plotLine = "+" + "-" * halfWidth + "|" + "-" * halfWidth + "+"
        plotLine

    private def createIndicatorLine(
        currentValue: Double,
        halfwayValue: Double,
        dashesForCurrentValue: Int
    ): String =
        if currentValue < halfwayValue then
            " " * (dashesForCurrentValue) + "^"
        else
            // add a space for the middle "|" symbol
            " " * (dashesForCurrentValue) + " " + "^"


    /**
     * This function returns an ASCII Sparkline chart like this as a
     * multiline String:
     *
     *     | ||| |   ||
     *      |   | |||
     *
     * when given an input like this:
     *
     *     Seq(true, false, true, true, true, false, true, false, false, false, true, true)
     *
     * @param results `true` for a win (or gain), `false` otherwise.
     * @return A multiline String, as shown in the example above.
     */
    def createAsciiSparklineChart(
        results: Seq[Boolean],
        useColor: Boolean = true,
        useDividingLine: Boolean = false
    ): String =
        if results.isEmpty then ""
        else
            val (topRow, middleRow, bottomRow) = asciiSparklineChartRows(results)
            formatRows(topRow, middleRow, bottomRow, useColor, useDividingLine)

    private def formatRows(
        topRow: String,
        middleRow: String,
        bottomRow: String,
        useColor: Boolean,
        useDividingLine: Boolean
    ): String =
        val rows = if useDividingLine then
            Seq(
                colorize(topRow, GREEN, useColor), 
                colorize(middleRow, WHITE, useColor, true), 
                colorize(bottomRow, RED, useColor)
            )
        else
            Seq(
                colorize(topRow, GREEN, useColor), 
                colorize(bottomRow, RED, useColor)
            )
        rows.mkString("\n")

    private def colorize(
        text: String, 
        color: String,
        useColor: Boolean,
        dim: Boolean = false
    ): String =
        if useColor then applyColor(text, color, dim) else text

    private def applyColor(text: String, color: String, dim: Boolean): String =
        val DIMMED = "\u001b[2m"
        val dimPrefix = if dim then DIMMED else ""
        s"$color$dimPrefix$text$RESET"

    /**
     * Returns topRow, middleRow, bottomRow
     */
    private def asciiSparklineChartRows(
        results: Seq[Boolean]
    ): (String, String, String) =
        val topRow    = results.map(if _ then sparklineChar else " ").mkString
        val middleRow = results.map(_ => "-").mkString
        val bottomRow = results.map(if _ then " " else sparklineChar).mkString
        (topRow, middleRow, bottomRow)

end PlotUtils

