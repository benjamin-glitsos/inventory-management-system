import scala.math.{round, pow}
import scala.util.Random
import com.devskiller.jfairy.producer.text.TextProducer

trait SeederTrait {
  implicit def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  private def powerOfTen(p: Int): Long = {
    pow(10, p).toLong
  }

  def biasedCoinFlip(probability: Double): Boolean = {
    val precision = powerOfTen(2).toInt
    val flip      = Random.between(1, precision).toDouble / precision
    probability >= flip
  }

  def generatePassword(length: Int): String = {
    Seq.fill(length)(Random.nextPrintableChar()).mkString("")
  }

  def generateMarkdownIpsum(textProducer: TextProducer): String = {
    if (biasedCoinFlip(probability = 0.75)) {
      textProducer.latinSentence(Random.between(1, 5))
    } else {
      ""
    }
  }

  protected val count: Int = 0
  protected def clearData(): Unit = {}
  protected def predefinedData(): Unit = {}
  protected def seed(): Unit = {}
  protected def apply(): Unit = {}
}
