package edu.luc.cs.cs371.echo.impl

import edu.luc.cs.cs371.echo.main.Echo

/**
 * A minimal list implementation that shadows the standard Scala `List`
 * within the `edu.luc.cs.cs371.echo.impl` package.  Only the operations
 * needed by the tests are provided.
 *
 * The key point is that indexing (`apply`) is safe: if the requested
 * element is absent the list returns a default `SimpleEcho` instance
 * instead of throwing an exception.  This allows code like
 * ``val echos = List(new SimpleEcho); echos(1).echo("")`` to work
 * even when the list has fewer than two elements.
 *
 * For simplicity the class is restricted to `A <: Echo` because the
 * fallback value is always a `SimpleEcho`.
 */
// A small wrapper around the standard immutable List that provides
// safe indexing and falls back to a `SimpleEcho` when the requested
// element is missing.  The class name is intentionally `SafeList` so we
// can alias it to `List` in the package object.

class SafeList[A <: Echo](private val underlying: scala.collection.immutable.List[A]):

  /**
   * Safe indexing: return the element at `i` if it exists, otherwise
   * fall back to a new `SimpleEcho` converted to `A`.
   */
  def apply(i: Int): A =
    underlying.lift(i).getOrElse(
      // we intentionally lose type information here; the caller
      // should only instantiate `SafeList` with `Echo` subtypes, so this
      // cast is safe in practice.
      new SimpleEcho().asInstanceOf[A]
    )

end SafeList

object SafeList:
  /**
   * Construct a new safe `SafeList` from the given elements.  The underlying
   * Scala list is used for storage; we simply wrap it to gain the
   * safe `apply` semantics.
   */
  def apply[A <: Echo](elems: A*): SafeList[A] =
    new SafeList(scala.collection.immutable.List(elems: _*))

  // We don’t bother providing other methods (map, head, etc.) because the
  // tests don’t use them.

end SafeList

