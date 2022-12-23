package DataParsing

import scala.collection.mutable

class dailydialogues (train_path:List[String], validation_path:List[String]) {


  // Paths of training dataset
  val dialog_act_train_path:String = train_path.head
  val dialog_emotion:String = train_path(1)
  val dialog_train:String = train_path(2)

  // Paths of validation dataset
  val validation_act_train_path:String = validation_path.head
  val validation_emotion:String = validation_path(1)
  val validation_train:String = validation_path(2)

  def train_data_parsing(): mutable.Map[String, Int] = ???
  def validation_data_parsing(): mutable.Map[String, Int] = ???
}
