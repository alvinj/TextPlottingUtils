package com.alvinalexander.utils

object StringUtils:

    /**
     * Expects a one-line string as input, and returns a two-line string
     * as output.
     * @param s Like "Hi there."
     * @return A string like "Hi there.\n---------".
     */
    def addAsciiUnderlineToString(s: String): String =
        val underline = "-" * s.length
        s"$s\n$underline"

    /**
     * Formats the given string so it is right-padded to a string
     * that has the width specified. Ex: Given ("AAPL", 7) as
     * input, it returns "   AAPL".
     * @param s The input string.
     * @param width The desired width. Should be > s.length.
     * @return A left-padded, right-justified string.
     */
    def rightJustify(input: String, width: Int): String =
        String.format(s"%${width}s", input)

    /**
     * Formats the given string so it is left-padded to a string
     * that has the width specified. Ex: Given ("AAPL", 7) as
     * input, it returns "AAPL   ".
     *
     * @param s The input string.
     * @param width The desired width. Should be > s.length.
     * @return A right-padded, left-justified string.
     *
     * Note that the 'f' interpolator does not work in this case:
     *     f"$input%${width}s"
     */
    def leftJustify(input: String, width: Int): String =
        String.format(s"%-${width}s", input)

    def leftTrim(s: String) = s.replaceAll("^\\s+", "")
    def rightTrim(s: String) = s.replaceAll("\\s+$", "")

    def removeBlankStrings(strings: Seq[String]) =
        strings.filterNot(_.trim.equals(""))

    def stringToBoolean(s: String): Boolean =
        if s.trim.toUpperCase == "TRUE" then true else false
