package org.typelevel

package object twiddles {
  type Tuple = shapeless.HList
  @inline val Tuple = shapeless.HList

  type EmptyTuple = shapeless.HNil
  @inline val EmptyTuple = shapeless.HNil

  type *:[A, B <: Tuple] = shapeless.::[A, B]
  @inline val *: = shapeless.::

  implicit class TupleOps[T <: Tuple](private val self: T) extends AnyVal {
    def *:[A](a: A): A *: T = new shapeless.::(a, self)
  }
}