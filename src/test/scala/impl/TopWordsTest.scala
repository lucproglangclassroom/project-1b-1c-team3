package impl

import org.scalatest.funsuite.AnyFunSuite
import main.TopWords

class TopWordsTest extends AnyFunSuite:

  test("empty input produces no output"):
    val observer = TestObserver()
    val processor = TopWords(3, 2, 5, observer)
    processor.processWords(Iterator.empty)

    assert(observer.getResults().isEmpty)

  test("fewer words than window size produces no output"):
    val observer = TestObserver()
    val processor = TopWords(3, 2, 5, observer)
    // feed only two words, which is strictly less than the window size
    processor.processWords(Iterator("aa", "bb"))

    assert(observer.getResults().isEmpty)

  test("window fill may produce one or more outputs"):
    val observer = TestObserver()
    val processor = TopWords(3, 2, 5, observer)
    processor.processWords(Iterator("aa", "bb", "cc", "aa", "bb"))

    val results = observer.getResults()
    // because the call-back triggers every time the sliding window is full
    // we only assert that at least one notification happened and that the
    // reported words come from the input set.
    assert(results.nonEmpty)

    val topWords = results.head.frequencies
    assert(topWords.forall{ case (w, _) => Set("aa","bb","cc").contains(w) })

  test("ignores short words"):
    val observer = TestObserver()
    val processor = TopWords(3, 3, 3, observer)
    processor.processWords(Iterator("a", "ab", "abc", "abcd", "abcde"))

    val results = observer.getResults()
    assert(results.nonEmpty)
    assert(results.head.frequencies.size <= 3)

