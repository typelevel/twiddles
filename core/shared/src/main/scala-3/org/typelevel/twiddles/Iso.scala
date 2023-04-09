package org.typelevel.twiddles

import scala.deriving.Mirror

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

private trait IsoLowPriority {
  def instance[A, B](t: A => B)(f: B => A): Iso[A, B] =
    new Iso[A, B] {
      def to(a: A) = t(a)
      def from(b: B) = f(b)
    }

  given inverse[A, B](using iso: Iso[A, B]): Iso[B, A] = iso.inverse

  // inline given productWithUnits[A <: Tuple, B <: Product](using
  //     m: Mirror.ProductOf[B] { type MirroredElemTypes = DropUnits[A] }
  // ): Iso[A, B] =
  //   instance((a: A) => fromTuple(DropUnits.drop(a)))(b => DropUnits.insert(toTuple(b)))

  protected def toTuple[A <: Product](a: A)(using m: Mirror.ProductOf[A]): m.MirroredElemTypes =
    Tuple.fromProductTyped(a)

  protected def fromTuple[A](using m: Mirror.ProductOf[A])(t: m.MirroredElemTypes): A =
    m.fromProduct(t)
}

/** Companion for [[Iso]]. */
object Iso extends IsoLowPriority {

  /** Identity iso. */
  given id[A]: Iso[A, A] = instance[A, A](identity)(identity)

  given product[A <: Tuple, B <: Product](using
      m: Mirror.ProductOf[B] { type MirroredElemTypes = A }
  ): Iso[A, B] =
    instance[A, B](fromTuple)(toTuple)

  given singleton[A, B <: Product](using
      m: Mirror.ProductOf[B] { type MirroredElemTypes = A *: EmptyTuple }
  ): Iso[A, B] =
    instance[A, B](a => fromTuple(a *: EmptyTuple))(b => toTuple(b).head)
}