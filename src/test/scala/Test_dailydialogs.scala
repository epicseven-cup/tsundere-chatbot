import DataParsing.dailydialogues

object Test_dailydialogs {
  def main(args: Array[String]): Unit = {

    val training_dataset:Array[String] = Array("data/ijcnlp_dailydialog/train/train/dialogues_act_train.txt", "data/ijcnlp_dailydialog/train/train/dialogues_emotion_train.txt", "data/ijcnlp_dailydialog/train/train/dialogues_train.txt")
    val validation_dataset:Array[String] = Array("data/ijcnlp_dailydialog/validation/validation/dialogues_act_validation.txt", "data/ijcnlp_dailydialog/validation/validation/dialogues_emotion_validation.txt", "data/ijcnlp_dailydialog/validation/validation/dialogues_validation.txt")

    val dailydialogs:dailydialogues = new dailydialogues(training_dataset, validation_dataset)
    val data = dailydialogs.train_data_parsing()
    dailydialogs.clean_data()
//    println(data)
  }
}
