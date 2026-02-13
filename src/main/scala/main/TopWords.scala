package main

import scala.collection.mutable

/**
 * Keeps a sliding window of the most recently seen words (filtered by length)
 * and invokes a user-supplied callback whenever the window is exactly full.
 *
 * @param windowSize number of words to hold in the sliding window
 * @param minLength minimum length a word must have to be considered
 * @param cloudSize number of top entries to report when the window fills
 * @param callback function to receive the sequence of (word, count) tuples
 */
class TopWords(
    windowSize: Int,
    minLength: Int,
    cloudSize: Int,
    callback: Seq[(String, Int)] => Unit
):

  // queue holds only words that pass the length filter and are in the current window
  private val queue = mutable.Queue[String]()
  // frequency map for words in the window
  private val freq   = mutable.Map.empty[String, Int]

  /** Process a single word.  If it is long enough it will be added to the
    * sliding window.  When the window contains exactly `windowSize` elements the
    * callback is invoked with the top `cloudSize` most frequent words.
    */
  def process(word: String): Unit =
    if word.length >= minLength then
      // add to window
      queue.enqueue(word)
      freq.updateWith(word){
        case None    => Some(1)
        case Some(n) => Some(n + 1)
      }

      // drop older elements if oversized
      if queue.size > windowSize then
        val old = queue.dequeue()
        freq.updateWith(old){
          case Some(n) if n > 1 => Some(n - 1)
          case _                => None // remove entry
        }
        () // avoid discarded-value warning

      // trigger callback when window exactly full
      if queue.size == windowSize then
        // compute top cloudSize entries by frequency descending
        val results = freq
          .toSeq
          .sortBy(-_._2)
          .take(cloudSize)
        callback(results)

end TopWords
