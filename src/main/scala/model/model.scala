package model

import DataParsing.BaseParsing

import scala.collection.mutable
abstract class model(val dataset:BaseParsing) {

  val training_data:Array[mutable.Map[String, String]]
  val evaluate_data:Array[mutable.Map[String, String]]

  def train():Unit = ???
}
