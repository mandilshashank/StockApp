import java.text.DecimalFormat

import org.joda.time.DateTime

object Main {
  def main(args: Array[String]): Unit = {
    val bufferedSource = io.Source.fromFile("constituents.csv")
    for (line <- bufferedSource.getLines.drop(1)) {
      try {
        val Array(symbol, name, sector) = line.split(",").map(_.trim)
        val stk = new Stock(symbol)
        stk.getHistoricalData((new DateTime).withDate(2015, 1, 1), (new DateTime).withDate(2016, 11, 30))
      } catch {
        case e => e.printStackTrace
      }
    }
    bufferedSource.close
  }
}
