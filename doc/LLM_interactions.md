* Added new Scala 3 class `TopWords` in `src/main/scala/main/TopWords.scala`.  The class maintains a sliding window of recent words, ignores short words, tracks frequencies, and invokes a provided callback with the top cloudSize words when the window fills.  Implementation uses Scala 3 significant indentation and mutable collections for bounded space.
* Adjusted frequency update logic to suppress Scala compiler warnings.



* Used Copilot to correct Scala 3 indentation errors introduced in `TopWords`.
* Added helper methods and a `TestObserver` class to facilitate testing of streaming behavior.
* Modified test expectations to account for sliding-window semantics in `TopWordsTest`.
* Employed Copilot for debugging the `Echo` implementations; an edge case involving
  list indexing required manual analysis due to limitations in altering production
  code.
