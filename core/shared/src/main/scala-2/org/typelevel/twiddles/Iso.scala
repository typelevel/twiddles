package org.typelevel.twiddles

import shapeless._

@annotation.implicitNotFound("""Could not prove ${A} is isomorphic to ${B}.""")
trait Iso[A, B] {
  self =>
  def to(a: A): B
  def from(b: B): A

  final def inverse: Iso[B, A] = new Iso[B, A] {
    def to(b: B) = self.from(b)
    def from(a: A) = self.to(a)
  }
}

private[twiddles] trait IsoLowPriority {
  def instance[A, B](t: A => B)(f: B => A): Iso[A, B] =
    new Iso[A, B] {
      def to(a: A) = t(a)
      def from(b: B) = f(b)
    }

  implicit def inverse[A, B](implicit iso: Iso[A, B]): Iso[B, A] = iso.inverse

  // inline given productWithUnits[A <: Tuple, B <: Product](using
  //     m: Mirror.ProductOf[B] { type MirroredElemTypes = DropUnits[A] }
  // ): Iso[A, B] =
  //   instance((a: A) => fromTuple(DropUnits.drop(a)))(b => DropUnits.insert(toTuple(b)))

//   protected def toTuple[A <: Product](a: A)(using m: Mirror.ProductOf[A]): m.MirroredElemTypes =
//     Tuple.fromProductTyped(a)

//   protected def fromTuple[A](using m: Mirror.ProductOf[A])(t: m.MirroredElemTypes): A =
//     m.fromProduct(t)
}

/** Companion for [[Iso]]. */
object Iso extends IsoLowPriority {

  /** Identity iso. */
  implicit def id[A]: Iso[A, A] = instance[A, A](identity)(identity)

  implicit def product[A <: Tuple, B](implicit gen: Generic.Aux[B, A]): Iso[A, B] =
    instance[A, B](gen.from)(gen.to)

  implicit def singleton[A, B](implicit gen: Generic.Aux[B, A *: EmptyTuple]): Iso[A, B] =
    instance[A, B](a => gen.from(a *: EmptyTuple))(b => gen.to(b).head)
}