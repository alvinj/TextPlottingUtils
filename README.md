# ASCII/UTF-8/Text Plotting Utilities

These are a couple of ASCII/UTF-8/Text plotting utilities that I have used recently.

The `PlotUtilsSpec.scala` file shows some examples of how to call the functions.
I don’t have any tests for how to call `createAsciiSparklineChart` in color,
but other than that, I hope there’s enough to go on.


## creatAsciiValueInRangeChart

The `creatAsciiValueInRangeChart` function creates output like this:

````
+-------------------------|-------------------------+
             ^
````


## createAsciiSparklineChart

The `createAsciiSparklineChart` function creates output that looks like this, and can also be colorized:

````
  ▓▓▓▓▓ ▓ ▓  ▓▓▓ ▓▓ ▓ ▓▓▓▓  ▓ ▓  ▓▓ ▓ ▓ ▓  ▓  ▓ ▓     ▓   ▓ ▓▓   ▓ ▓  ▓  ▓▓ ▓    ▓ ▓   ▓▓ ▓▓
--------------------------------------------------------------------------------------------
▓▓     ▓ ▓ ▓▓   ▓  ▓ ▓    ▓▓ ▓ ▓▓  ▓ ▓ ▓ ▓▓ ▓▓ ▓ ▓▓▓▓▓ ▓▓▓ ▓  ▓▓▓ ▓ ▓▓ ▓▓  ▓ ▓▓▓▓ ▓ ▓▓▓  ▓  
````

In my private “Chicago Cubs 2024 Scores Sparkline” project, I create a `Seq[Boolean]` like this from
the current Cubs 2024 scores:

```scala
val scoreResults: Seq[Boolean] = 
    elements.asScala
            .map(elem => elem.text.trim)
            .filter(s => s == "W" || s == "L")
            .map(s => if s == "W" then true else false)
            .toSeq

println(createAsciiSparklineChart(scoreResults, true, true))
```

That data was used to create the Sparkline chart shown above.


## Other utilities

These utilities depend on my `MathUtils`, so I included those here as well.



