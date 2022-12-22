package DataParsing

import scala.collection.mutable

abstract class BaseParsing (val path:String, val column:Array[String]) {


  def parse_line(line:Array[String]): mutable.Map[String, String] = {
    val maped_entries:mutable.Map[String, String] = mutable.Map()
    for (entry <- line.indices) {
      var entry_value:String = line(entry)
      if (entry_value == ""){
        entry_value = "null"
      }
      val key_name:String = this.column(entry)
      maped_entries(key_name) = entry_value
    }
    maped_entries
  }

  def parse_file():Map[String, Map[String, String]]
}
