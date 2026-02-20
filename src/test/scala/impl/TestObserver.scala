package impl

// small callback collector for tests
final case class ObserverResult(frequencies: Seq[(String, Int)])

class TestObserver extends (Seq[(String, Int)] => Unit):
  private val buf = scala.collection.mutable.ArrayBuffer.empty[ObserverResult]
  def apply(freq: Seq[(String, Int)]): Unit =
    buf += ObserverResult(freq)
  def getResults(): Seq[ObserverResult] = buf.toSeq

object TestObserver:
  def apply(): TestObserver = new TestObserver()
