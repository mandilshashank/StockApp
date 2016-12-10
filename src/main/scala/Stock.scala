import java.sql.DriverManager
import java.sql.Connection

import scala.io.Source
import sys.process._
import java.net.URL
import java.io.File
import org.joda.time._

class Database(db: String) {
  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost/"+db
  val username = "root"
  val password = "tezbazaari"

  def executeInsertQuery(query: String) = {
    var connection:Connection = null
    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
      val statement = connection.createStatement()
      val resultSet = statement.execute(query)
    } catch {
      case e => e.printStackTrace
    } finally {
      if(connection != null) {
        connection.close()
      }
    }
  }
}

class Stock(val stockSymbol:String) {
  def getStats() = {
    //This function is to get the stats page data from Yahoo finance

  }

  def getHistoricalData(fromDate: DateTime, toDate: DateTime) = {
    //Get data from
    //http://chart.finance.yahoo.com/table.csv?s=MSFT&a=10&b=10&c=2016&d=11&e=10&f=2016&g=d&ignore=.csv
    //Save this data into a file for now
    val filename: String = stockSymbol+".csv"

    val a = fromDate.getMonthOfYear
    val b = fromDate.getDayOfMonth
    val c = fromDate.getYear
    val d = toDate.getMonthOfYear
    val e = toDate.getDayOfMonth
    val f = toDate.getYear
    val url = "http://chart.finance.yahoo.com/table.csv?s="+stockSymbol+"&a="+a+"&b="+b+"&c="+c+"" +
      "&d="+d+"&e="+e+"&f="+f+"&g=d&ignore=.csv"
    new URL(url) #> new File(filename) !!

    loadCsvToDatabase(filename)
  }

  private def loadCsvToDatabase(filename: String): Unit ={
    val db = new Database("stockapp")
    val bufferedSource = io.Source.fromFile(filename)
    for (line <- bufferedSource.getLines.drop(1)) {
      val Array(date, open, high, low, close, volume, adj_close) = line.split(",").map(_.trim)
      //Create insert query and Store into mysql
      val query = "insert into stockhistdata(stocksymbol, date, high, low, close, volume, adj_close) values" +
        " ('"+stockSymbol+"', '"+date+"', "+high+", "+low+", "+close+", "+volume+", "+adj_close+")"
      db.executeInsertQuery(query)
    }
    bufferedSource.close
    new File(filename).delete()
  }
}
