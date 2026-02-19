package impl

import org.scalatest.funsuite.AnyFunSuite

class TopWordsTest extends AnyFunSuite:

  test("empty input produces no output"):
  val observer = TestObserver()
  val processor = TopWords(3, 2, 5, observer)
  processor.processWords(Iterator.empty)

  assert(observer.getResults().isEmpty)

  test("fewer words than window size produces no output"):
  val observer = TestObserver()
  val processor = TopWords(3, 2, 5, observer)
  processor.processWords(Iterator("aa", "bb", "cc"))

  assert(observer.getResults().isEmpty)

  test("exactly window size produces first output"):
  val observer = TestObserver()
  val processor = TopWords(3, 2, 5, observer)
  processor.processWords(Iterator("aa", "bb", "cc", "aa", "bb"))

  val results = observer.getResults()
  assert(results.size == 1)

  val topWords = results.head.frequencies
  assert(topWords(0) == ("aa", 2) || topWords(0) == ("bb", 2))

  test("ignores short words"):
  val observer = TestObserver()
  val processor = TopWords(3, 3, 3, observer)
  processor.processWords(Iterator("a", "ab", "abc", "abcd", "abcde"))

  val results = observer.getResults()
  assert(results.size == 1)
  assert(results.head.frequencies.size <= 3)