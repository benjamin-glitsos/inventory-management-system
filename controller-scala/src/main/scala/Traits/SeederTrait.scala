import scala.math.{round, BigDecimal}
import scala.util.Random
import org.apache.commons.lang3.StringUtils.{left, right}
import java.time.{Instant, Duration}

trait SeederTrait {
  implicit final def times(n: Int) = new {
    def times(fn: => Unit) = {
      val seedFactor: Double = System.getenv("CONTROLLER_SEED_FACTOR").toDouble
      val count: Int         = round(n * seedFactor).toInt
      for (i <- 1 to count) fn
    }
  }

  final def randomIntegerBetween(min: Int, max: Int): Int =
    Random.between(min, max)

  final def coinFlip(): Boolean = Random.nextBoolean

  final def biasedCoinFlip(probability: Double): Boolean = {
    val precision = MathUtilities.powerOfTen(2).toInt
    val flip      = Random.between(1, precision).toDouble / precision
    probability >= flip
  }

  final def randomFixedLength(random: () => Char, length: Int): String = {
    Seq.fill(length)(random()).mkString(new String)
  }

  final def randomPrintable(length: Int): String = randomFixedLength(
    Random.nextPrintableChar,
    length
  )

  final def randomNumbers(length: Int): String = randomFixedLength(
    Random.nextInt(9),
    length
  )

  final def randomAlphanumerics(length: Int): String = Random.alphanumeric
    .take(length)
    .mkString

  final def repeatedRunArray[A](n: Int, fn: () => A): List[A] = {
    (1 to n).map(_ => fn()).toList
  }

  final def randomGaussianDiscrete(
      min: Int,
      max: Int,
      mean: Double = 1,
      sd: Double = 1
  ): Int = {
    def sample(i: Int = 1): Int = {
      if (i <= 3) {
        val normalSample: Double = Random.nextGaussian() * sd + mean

        val halfNormalSample: Double = if (normalSample <= mean) {
          normalSample
        } else {
          val d: Double = normalSample - mean
          normalSample + d
        }

        val discreteHalfNormalSample: Int = round(halfNormalSample).toInt

        if (min to max contains discreteHalfNormalSample) {
          discreteHalfNormalSample
        } else {
          sample(i + 1)
        }
      } else {
        min
      }
    }

    sample()
  }

  final def overwriteText(c: Char, s: String): String = s.map(x => c)

  final def createUpc(): String = randomNumbers(12)

  final def createSku(words: String): String = {
    def abbreviate(word: String): String = if (word.length() <= 3) {
      word
    } else {
      val firstChars: String = left(word, 2)
      val lastChar: String   = right(word, 1)
      firstChars + lastChar
    }

    val randomCode: String = randomAlphanumerics(
      randomGaussianDiscrete(min = 1, max = 6)
    )

    val blacklist: List[String] = List("pee", "pus")

    def censorBlacklist(code: String): String = {
      if (blacklist contains code) {
        overwriteText(randomAlphanumerics(1)(0), code)
      } else {
        code
      }
    }

    (words
      .split(" ")
      .take(4)
      .map(abbreviate)
      .map(_.toLowerCase())
      .map(censorBlacklist) :+ randomCode)
      .filter(!StringUtilities.isEmpty(_))
      .mkString("-")
      .toUpperCase()
  }

  final def randomDouble = Random.nextDouble

  final def randomCurrency(): Double = {
    val d = randomDouble * 10
    val b =
      new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP)
    b.doubleValue
  }

  final def randomDateBetween(min: Date, max: Date): Date =
    Date.from(Instant.of(Random.between(min.getTime, max.getTime + 1)))

  final def yearsAgo(years: Int): Date = {
    val daysAgo: Int   = years * 365
    val today: Instant = Instant.now()
    val past: Instant  = today.minus(Duration.ofDays(daysAgo))
    Date.from(past)
  }

  final def addRandomDays(date: Date, min: Int, max: Int): Date = {
    val future: Instant = date.plus(Duration.ofDays(daysAgo))
    Date.from(future)
  }
}
