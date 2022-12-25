import DataParsing.dailydialogues

import play.api.libs.json.{JsValue, Json}
import scala.io.Source

object Test_dailydialogs {
  def main(args: Array[String]): Unit = {

    val training_dataset:Array[String] = Array("data/ijcnlp_dailydialog/train/train/dialogues_act_train.txt", "data/ijcnlp_dailydialog/train/train/dialogues_emotion_train.txt", "data/ijcnlp_dailydialog/train/train/dialogues_train.txt", "data/ijcnlp_dailydialog/train/train/clean_data.txt.json")
    val validation_dataset:Array[String] = Array("data/ijcnlp_dailydialog/validation/validation/dialogues_act_validation.txt", "data/ijcnlp_dailydialog/validation/validation/dialogues_emotion_validation.txt", "data/ijcnlp_dailydialog/validation/validation/dialogues_validation.txt")

    val dailydialogs:dailydialogues = new dailydialogues(training_dataset, validation_dataset)
//    val data = dailydialogs.train_data_parsing()
//    dailydialogs.clean_data()
    val pos_json_file = Source.fromFile(training_dataset(3))
    val pos_json_string:String = pos_json_file.mkString
//    val pos_json_string:String = pos_json_file.mkString // WTF my laptop does not have enough memory to read the entire file
//    val pos_json_iterator:Iterator[String] = pos_json_file.mkString
//    var pos_json_string:String =
//    for (line <- pos_json_iterator) {
//      pos_json_string = pos_json_string + line
//    }
//    println(pos_json_string)
    // close buffersource
    pos_json_file.close()
    // use play-json to convert the json string to scala usable datastructures
    val pos_json:JsValue = Json.parse(pos_json_string)
    val pos_data = pos_json("sentences")
    println(pos_data)
//    println(data)
  }
}
