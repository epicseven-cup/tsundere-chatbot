import DataParsing.{dailydialogues, pos_json}
import com.fasterxml.jackson.databind.json.JsonMapper
import play.api.libs.json.{JsArray, JsObject, JsValue, Json, Reads}

import scala.collection.mutable
import scala.io.Source

object Test_dailydialogs {
  def main(args: Array[String]): Unit = {

    val training_dataset:Array[String] = Array("data/ijcnlp_dailydialog/train/dialogues_act_train.txt", "data/ijcnlp_dailydialog/train/dialogues_emotion_train.txt", "data/ijcnlp_dailydialog/train/dialogues_train.txt", "data/ijcnlp_dailydialog/train/clean_data.txt.json")
    val validation_dataset:Array[String] = Array("data/ijcnlp_dailydialog/validation/dialogues_act_validation.txt", "data/ijcnlp_dailydialog/validation/dialogues_emotion_validation.txt", "data/ijcnlp_dailydialog/validation/dialogues_validation.txt")

//    val dailydialogs:dailydialogues = new dailydialogues(training_dataset, validation_dataset)
//    val pos_json_file = Source.fromFile(training_dataset(3))
//    val pos_json_string:String = pos_json_file.mkString
//    pos_json_file.close()
//    // use play-json to convert the json string to scala usable datastructures
//    val pos_json:JsValue = Json.parse(pos_json_string)
//    val pos_data = pos_json("sentences")
//    println(pos_data)


    val file_json:String = "data/ijcnlp_dailydialog/train/shorter_clean_data.json"
    val pos_json_file = Source.fromFile(file_json)
    val pos_json_string: String = pos_json_file.mkString
    // close buffersource
    pos_json_file.close()
    // use play-json to convert the json string to scala usable datastructures
    val pos_json: JsValue = Json.parse(pos_json_string)
//    implicit  val pos_jsonReads: Reads[pos_json] = Json.reads[pos_json]
//    println(Json.fromJson[pos_json](pos_json1))

//    val pos_size:Int = pos_json.asOpt[JsObject].get.value.size
//    val pos_mapped:mutable.Map[String, Array[Int]] = mutable.Map()
//    for (item <- 0 until pos_size){
//      val key:String = item.toString
//      val value = pos_json.apply(key)
//      val convert_array:Array[Int] = value.as[Array[JsValue]].map( (js_value:JsValue) => {
//        val js_int:Int = js_value.as[Int]
//        js_int
//      })
//      pos_mapped(key) = convert_array
//    }
  }
}
