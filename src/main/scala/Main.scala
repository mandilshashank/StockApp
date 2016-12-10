import org.joda.time.DateTime

object Main {
  def main(args: Array[String]): Unit = {
    val stk = new Stock("MSFT")
    stk.getHistoricalData((new DateTime).withDate(2011,1,1), (new DateTime).withDate(2016,11,30))
  }
}
