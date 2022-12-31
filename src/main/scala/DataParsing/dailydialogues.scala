package DataParsing

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable
import scala.io.Source
import scala.util.Random
import play.api.libs.json.{JsObject, JsValue, Json}


class dailydialogues (train_path:Array[String], validation_path:Array[String]) {

  if (train_path.length < 4 || validation_path.length < 3) {
    throw new Error("Wrong constructors")
  }


  // Paths of training dataset
  val dialog_act_train_path:String = train_path.head
  val dialog_emotion:String = train_path(1)
  val dialog_train:String = train_path(2)
  val dialog_pos_json:String = train_path(3)

  // Paths of validation dataset
  val validation_act_train_path:String = validation_path.head
  val validation_emotion:String = validation_path(1)
  val validation_train:String = validation_path(2)


  def clean_data():Unit = {
    val file = Source.fromFile(this.dialog_train)
    val file_line = file.getLines()

    var total:List[String] = List()
    for (line <- file_line) {
      val parsed_line: Array[String] = line.split("__eou__")
      var input_line:String = ""
      for (line <- parsed_line) {
        val clean_line:String = line.trim
        input_line = input_line + clean_line
      }
      total =  total :+ input_line

    }

    // close the file
    file.close()

    // Writing to file
    val write_file = new File("data/ijcnlp_dailydialog/train/train/clean_data.txt")
    val write_file_buffer = new BufferedWriter(new FileWriter(write_file))
    for (line <- total){
      val create_line:String = line + "\n"
      write_file_buffer.write(create_line)
    }
    write_file_buffer.close()
  }

  def unique_token_id(token_id_set:mutable.Set[Double]): Option[Double] = {
    var random_id:Double = Random.nextDouble()
    while (token_id_set.contains(random_id)) {
      random_id = Random.nextInt()
    }
    Option(random_id)
  }

  def update_classificer(path:String, index:Int, data:mutable.Map[Int, mutable.Map[String, Array[Double]]], id:Int=0): mutable.Map[Int, mutable.Map[String, Array[Double]]] = {
    var input_id = id
    val output_data:mutable.Map[Int, mutable.Map[String, Array[Double]]] = data
    val current_file = Source.fromFile(path)
    val current_file_lines = current_file.getLines()
    for (line <- current_file_lines) {
      val isolate_state:Array[String] = line.split(" ")
      // update the state
      for (state <- isolate_state) {
        output_data(input_id)("classifiers")(index)= state.toDouble
        input_id = input_id + 1
      }
    }
    current_file.close()
    output_data
  }
  def train_data_parsing(): mutable.Map[Int, Map[String, Array[Double]]] = {

    val text_file = Source.fromFile(this.dialog_train)
    val mapped_sentence:mutable.Map[Int, mutable.Map[String, Array[Double]]] = mutable.Map()
    // sentence id
    var id:Int = 0
    val text_file_lines = text_file.getLines()
    // a tokenizer_dictionary
    val tokenizer_dictionary:mutable.Map[String, Option[Double]] = mutable.Map()
    // Set of token id
    var token_id_set: mutable.Set[Double] = mutable.Set()
    // keeping track of id with option wrapper
    var token_id: Option[Double] = Option(Random.nextDouble())
    // Put all the sentences input the map so I can mapp it later with different annothation
    for (line <- text_file_lines) {
      val sentences:Array[String] = line.split("__eou__")

      for(sentence_index <- sentences.indices){
        val isolated_words:Array[String] = sentences(sentence_index).split(" ")
        val tokenized_sentence:Array[Double] = new Array[Double](isolated_words.length)

        for (word_index <- isolated_words.indices) {
          val word:String = isolated_words(word_index)
          val token:Option[Double] = tokenizer_dictionary.getOrElse(word, None)
          if (token.isEmpty){
            tokenizer_dictionary(word) = token_id
            token_id_set = token_id_set + token_id.get
            token_id = this.unique_token_id(token_id_set)
          }
          tokenized_sentence(word_index) = tokenizer_dictionary(word).get
        }
        mapped_sentence(id) = mutable.Map ("classifiers" -> new Array[Double](2), "tokenized" -> tokenized_sentence)
        id = id + 1
      }
    }
//    println(mapped_sentence)
    // Closing the file for my computer
    text_file.close()

    // Read topic
    val topic_data = this.update_classificer(this.dialog_act_train_path, 0, mapped_sentence)
    val emotion_data = this.update_classificer(this.dialog_emotion, 1, topic_data)



    // Read the pos
    val pos_json_file = Source.fromFile(this.dialog_pos_json)
    val pos_json_string:String = pos_json_file.mkString
    // close buffersource
    pos_json_file.close()
    // use play-json to convert the json string to scala usable datastructures
    val pos_json:JsValue = Json.parse(pos_json_string)
    // Go through the map
    val pos_size: Int = pos_json.asOpt[JsObject].get.value.size
    val pos_mapped: mutable.Map[String, Array[Double]] = mutable.Map()
    // Go through by index
    for (item <- 0 until pos_size) {
      val key: String = item.toString
      val value = pos_json.apply(key)
      // convert Array[Jsvalue] to Array[Int]
      val convert_array: Array[Double] = value.as[Array[JsValue]].map((js_value: JsValue) => {
        val js_int: Double = js_value.as[Double]
        js_int
      })
      pos_mapped(key) = convert_array
    }

    // Going through the mapped result and add my pos_array into it

    for ((key, value) <- emotion_data){
      val current_value:mutable.Map[String, Array[Double]] = value

      current_value("Pos") = pos_mapped(key.toString)
      emotion_data(key) = current_value
    }

    // Write this data down
    val write_file = new File("data/ijcnlp_dailydialog/train/train/parsed_data.txt")
    val write_buffer = new BufferedWriter(new FileWriter(write_file))
    for ((key, value) <- emotion_data) {
      var compute_value: String = ""
      for ((k_value, v_value) <- value) {
        compute_value = k_value + " -> " + v_value.mkString("Array(", ", ", ")") + " " +compute_value
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
