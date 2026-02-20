package main

import mainargs.{arg, main, ParserForMethods}

object Main:

  @main
  def run(
           @arg(short = 'c', name = "cloud-size", doc = "cloud size")
           cloudSize: Int = 10,
           @arg(short = 'l', name = "length-at-least", doc = "min word length")
           lengthAtLeast: Int = 6,
           @arg(short = 'w', name = "window-size", doc = "window size")
           windowSize: Int = 1000
         ): Unit =
    def printWordCloud(results: Seq[(String, Int)]): Unit =
      val output = results.map((word, count) => s"$word: $count").mkString(" ")
      println(output)
      if scala.sys.process.stdout.checkError() then
        sys.exit(1)

    val lines = scala.io.Source.stdin.getLines()
    import scala.language.unsafeNulls
    val words = lines.flatMap(line => line.split("(?U)[^\\p{Alpha}0-9']+"))

    val processor = TopWords(windowSize, lengthAtLeast, cloudSize, printWordCloud)

    try
      words.foreach(processor.process)
    catch
      case _: java.io.IOException =>
        sys.exit(0)

  def main(args: Array[String]): Unit =
    ParserForMethods(this).runOrExit(args.toIndexedSeq)