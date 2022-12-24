package DataParsing

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable
import scala.io.Source
import scala.util.Random

class dailydialogues (train_path:Array[String], validation_path:Array[String]) {


  // Paths of training dataset
  val dialog_act_train_path:String = train_path.head
  val dialog_emotion:String = train_path(1)
  val dialog_train:String = train_path(2)

  // Paths of validation dataset
  val validation_act_train_path:String = validation_path.head
  val validation_emotion:String = validation_path(1)
  val validation_train:String = validation_path(2)


  def unique_token_id(token_id_set:mutable.Set[Int]): Option[Int] = {
    var random_id:Int = Random.nextInt()
    while (token_id_set.contains(random_id)) {
      random_id = Random.nextInt()
    }
    Option(random_id)
  }

  def update_classificer(path:String, index:Int, data:mutable.Map[Int, Map[String, Array[Int]]], id:Int=0): mutable.Map[Int, Map[String, Array[Int]]] = {
    var input_id = id
    val output_data:mutable.Map[Int, Map[String, Array[Int]]] = data
    val current_file = Source.fromFile(path)
    val current_file_lines = current_file.getLines()
    for (line <- current_file_lines) {
      val isolate_state:Array[String] = line.split(" ")
      // update the state
      for (state <- isolate_state) {
        output_data(input_id)("classifiers")(index)= state.toInt
        input_id = input_id + 1
      }
    }
    current_file.close()
    output_data
  }
  def train_data_parsing(): mutable.Map[Int, Map[String, Array[Int]]] = {

    val text_file = Source.fromFile(this.dialog_train)
    val mapped_sentence:mutable.Map[Int, Map[String, Array[Int]]] = mutable.Map()
    // sentence id
    var id:Int = 0
    val text_file_lines = text_file.getLines()
    // a tokenizer_dictionary
    val tokenizer_dictionary:mutable.Map[String, Option[Int]] = mutable.Map()
    // Set of token id
    var token_id_set: mutable.Set[Int] = mutable.Set()
    // keeping track of id with option wrapper
    var token_id: Option[Int] = Option(Random.nextInt())
    // Put all the sentences input the map so I can mapp it later with different annothation
    for (line <- text_file_lines) {
      val sentences:Array[String] = line.split("__eou__")

      for(sentence_index <- sentences.indices){
        val isolated_words:Array[String] = sentences(sentence_index).split(" ")
        val tokenized_sentence:Array[Int] = new Array[Int](isolated_words.length)

        for (word_index <- isolated_words.indices) {
          val word:String = isolated_words(word_index)
          val token:Option[Int] = tokenizer_dictionary.getOrElse(word, None)
          if (token.isEmpty){
            tokenizer_dictionary(word) = token_id
            token_id_set = token_id_set + token_id.get
            token_id = this.unique_token_id(token_id_set)
          }
          tokenized_sentence(word_index) = tokenizer_dictionary(word).get
        }
        mapped_sentence(id) = Map ("classifiers" -> new Array[Int](2), "tokenized" -> tokenized_sentence)
        id = id + 1
      }
    }
//    println(mapped_sentence)
    // Closing the file for my computer
    text_file.close()

    // Read topic
    val topic_data = this.update_classificer(this.dialog_act_train_path, 0, mapped_sentence)
    val emotion_data = this.update_classificer(this.dialog_emotion, 1, topic_data)


    // Write this data down
    val write_file = new File("data/ijcnlp_dailydialog/train/train/parsed_data.txt")
    val write_buffer = new BufferedWriter(new FileWriter(write_file))
    for ((key, value) <- emotion_data) {
      var compute_value: String = ""
      for ((k_value, v_value) <- value) {
        compute_value = k_value + " -> " + v_value.mkString("Array(", ", ", ")") + " " +compute_value
//        println(k_value)
      }
      val create_line:String = key.toString + " -> " + compute_value + "\n"
      write_buffer.write(create_line)
    }
    write_buffer.close()


    // Write dictionary
    val write_dictionary = new File("data/ijcnlp_dailydialog/train/train/dictionary_data.txt")
    val write_dictionary_writer = new BufferedWriter(new FileWriter(write_dictionary))
    for ((key, value) <- tokenizer_dictionary){
      val create_line:String = key + " -> " + value.get.toString + "\n"
      write_dictionary_writer.write(create_line)
    }
    write_dictionary_writer.close()

    emotion_data
  }
  def validation_data_parsing(): mutable.Map[String, Int] = ???
}
