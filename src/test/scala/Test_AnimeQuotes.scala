import DataParsing.AnimeQuotes

object Test_AnimeQuotes {

  def main(args: Array[String]): Unit = {
    val animeQuotes_colums:Array[String] = Array("Quote","Character","Anime")
    val animequotes:AnimeQuotes = new AnimeQuotes("data/AnimeQuotes.csv", animeQuotes_colums)
    val data = animequotes.parse_file()
//    println(data.mkString("Array(", ", ", ")"))
  }
}
