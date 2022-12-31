package model.datastructures

class LSTM_state (var input_gate:Int, var output_gate:Int, var forget_gate:Int) {

  // New state when the nerual is feed new infromations
  def new_state(): LSTM_state = ???

}
