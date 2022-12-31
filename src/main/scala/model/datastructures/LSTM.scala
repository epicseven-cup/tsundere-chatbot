package model.datastructures

import scala.math.random
class LSTM_cell(input:neurtal_input, val pervious:neural_output) extends neurtal (input){
  override val basis: Long = random.longValue()
  // Starting off the state will be all 0
  val state:LSTM_state = new LSTM_state(0, 0, 0)


  def calcuated(): neural_output = ???

}
