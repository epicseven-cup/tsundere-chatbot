package model
import scala.math.random
class LSTM (id:Long, weight_value:Long, context:String) extends neurtal (id, weight_value, context){
  override val basis: Long = random.longValue()


  def input_gate():Unit = ???
  def output_gate():Unit = ???
  def forget_gate():Unit = ???
}
