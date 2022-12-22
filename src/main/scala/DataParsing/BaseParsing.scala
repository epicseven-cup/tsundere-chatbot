package DataParsing

import scala.collection.mutable
import org.apache.commons.csv.{CSVFormat, CSVRecord}

import java.io.FileReader


class BaseParsing (val path:String, val colums:Array[String]) {
  val data:mutable.Map[Long, mutable.Map[String, String]] = parse_file()
  def parse_file(): mutable.Map[Long, mutable.Map[String, String]] = {
    val file = new FileReader(path)
    val mapped_entries:mutable.Map[Long, mutable.Map[String, String]] = mutable.Map()
    CSVFormat.RFC4180.withHeader(this.colums: _*).withFirstRecordAsHeader().parse(file).forEach((record:CSVRecord)=> {
      val recordNumber:Long = record.getRecordNumber
      val innerMap:mutable.Map[String, String] = this.parse_line(record)
      mapped_entries(recordNumber) = innerMap
    })

    mapped_entries
  }

  def parse_line(record: CSVRecord): mutable.Map[String, String]= {
    val innerMap:mutable.Map[String, String] = mutable.Map()
    for (record_name <- this.colums.indices){
        innerMap(this.colums(record_name)) = record.get(this.colums(record_name))
      }
    innerMap
  }
}
