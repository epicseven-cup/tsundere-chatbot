package DataParsing

import scala.collection.mutable
import org.apache.commons.csv.{CSVFormat, CSVRecord}

import java.io.FileReader


class BaseParsing (val path:String, val colums:Array[String]) {
  val data:Array[mutable.Map[String, String]] = parse_file()
  def parse_file(): Array[mutable.Map[String, String]] = {
    val file = new FileReader(path)

    val csv_object = CSVFormat.RFC4180.withHeader(this.colums: _*).withFirstRecordAsHeader().parse(file).getRecords()
    val length:Int = csv_object.size()
    val array_entries:Array[mutable.Map[String, String]] = new Array[mutable.Map[String, String]](length)
//    println(length)

    println(csv_object.size())
    csv_object.forEach((record:CSVRecord)=> {
      val recordNumber:Int = (record.getRecordNumber - 1).toInt
      val innerMap:mutable.Map[String, String] = this.parse_line(record)
      array_entries(recordNumber) = innerMap
//      println(array_entries.mkString("Array(", ", ", ")"))
    })
    array_entries
  }

  def parse_line(record: CSVRecord): mutable.Map[String, String]= {
    val innerMap:mutable.Map[String, String] = mutable.Map()
    for (record_name <- this.colums.indices){
        innerMap(this.colums(record_name)) = record.get(this.colums(record_name))
      }
    innerMap
  }
}
