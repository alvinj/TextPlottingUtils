//> using scala "3"
//> using lib "dev.zio::zio::2.1.6"
//> using lib "dev.zio::zio-test::2.1.6"
//> using lib "dev.zio::zio-test-sbt::2.1.6"
package plot_utils_tests

import zio.test.ZIOSpecDefault
import zio.test._
import zio.test.Assertion._
import com.alvinalexander.utils.PlotUtils.{createAsciiValueInRangeChart, createAsciiSparklineChart}
import com.alvinalexander.utils.StringUtils.*

/**
 * TODO: These are currently a little out of sync with the PlotUtils code.
 */
object PlotUtilsSpec extends ZIOSpecDefault:

    override def spec = suite("StockNewTests")(

        test("Sparkline test 1") {
            val expected =
                """#▓ ▓ ▓
                   # ▓ ▓ """.stripMargin('#')
            val rez = createAsciiSparklineChart(
                Seq(true, false, true, false, true),
                useColor = false,
                useDividingLine = false
            )
            assertTrue(
                rez == expected
            )
        },

        test("Sparkline test 2") {
            val expected =
                """#▓ ▓▓▓ ▓   ▓▓
                   # ▓   ▓ ▓▓▓  """.stripMargin('#')
            val rez = createAsciiSparklineChart(
                Seq(true, false, true, true, true, false, true, false, false, false, true, true),
                useColor = false,
                useDividingLine = false
            )
            assertTrue(
                rez == expected
            )
        },

        test("Sparkline test 3") {
            val expected =
                """#'▓▓▓   '
                   #'   ▓▓▓'""".stripMargin('#')
            val rez = createAsciiSparklineChart(
                Seq(true, true, true, false, false, false),
                useColor = false,
                useDividingLine = false
            )
            // because IntelliJ keeps deleting my blanks at the end
            // of the lines:
            val rez2 = rez.split("\n").map(s => s"'$s'").mkString("\n")
            assertTrue(
                rez2 == expected
            )
        },

        test("Sparkline test 3, with dividing line") {
            val expected =
                """#'▓▓▓   '
                   #'------'
                   #'   ▓▓▓'""".stripMargin('#')
            val rez = createAsciiSparklineChart(
                Seq(true, true, true, false, false, false),
                useColor = false,
                useDividingLine = true
            )
            val rez2 = rez.split("\n").map(s => s"'$s'").mkString("\n")
            assertTrue(
                rez2 == expected
            )
        },

        test("createAsciiValueInRangeChart 1") {
            val expected =
                """|+-----|-----+
                   | ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(0, 100, 10, 10)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 2") {
            val expected =
                """|+-----|-----+
                   |  ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(0, 100, current=20, 10)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 3") {
            val expected =
                """|+-----|-----+
                   |      ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(0, 100, current=50, 10)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 4") {
            val expected =
                """|+----------|----------+
                   |               ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(46.15, 58.48, current=54.74, 20)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 5") {
            val expected =
                """|+----------|----------+
                   |  ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(6.55, 66.55, current=11.77, 20)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 6") {
            val expected =
                """|+----------|----------+
                   |           ^
                   |""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(6.55, 66.55, current=36.55, 20)
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 7") {
            val expected =
                """10.00             90.00
                  |+----------|----------+
                  |           ^
                  |         50.00""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(10, 90, current=50, 20, true)
            val output   =
                s"""
                   |EXPECTED:
                   |${expected}
                   |
                   |REZ:
                   |${rez}
                   |""".stripMargin
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 8") {
            val expected =
                """10.51            100.52
                  |+----------|----------+
                  |           ^
                  |         55.51""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(10.51, 100.52, current=55.51, 20, true)
            val output   =
                s"""
                   |EXPECTED:
                   |${expected}
                   |
                   |REZ:
                   |${rez}
                   |""".stripMargin
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 9") {
            val expected =
                """75.01                     1075.02
                  |+---------------|---------------+
                  |                ^
                  |             575.22""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(75.01, 1075.02, current=575.22, 30, true)
            val output   =
                s"""
                   |EXPECTED:
                   |${expected}
                   |
                   |REZ:
                   |${rez}
                   |""".stripMargin
            assertTrue(
                rez == expected
            )
        },

        // TODO the decimal formatting is not right here
        test("createAsciiValueInRangeChart 10") {
            val expected =
                """46.30                       58.48
                  |+---------------|---------------+
                  |                     ^
                  |                   54.45""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(46.30, 58.48, 54.45, 30, true)
            val output   =
                s"""
                   |EXPECTED:
                   |${expected}
                   |
                   |REZ:
                   |${rez}
                   |""".stripMargin
            assertTrue(
                rez == expected
            )
        },

        test("createAsciiValueInRangeChart 11") {
            val expected =
                """127.15                     173.82
                  |+---------------|---------------+
                  |                             ^
                  |                          171.48""".stripMargin.trim
            val rez = createAsciiValueInRangeChart(127.15, 173.82, 171.48, 30, true)
            val output   =
                s"""
                   |EXPECTED:
                   |${expected}
                   |
                   |REZ:
                   |${rez}
                   |""".stripMargin
            assertTrue(
                rez == expected
            )
        },

    )
