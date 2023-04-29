package org.typelevel.twiddles

final class TupleOps[T <: Tuple](private val self: T) extends AnyVal {
  def *:[A](a: A): A *: T = new shapeless.::(a, self)
}
