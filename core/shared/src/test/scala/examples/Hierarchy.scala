package examples

import cats.InvariantSemigroupal
import cats.ContravariantSemigroupal
import cats.Applicative
import org.typelevel.twiddles.TwiddleSyntax

object Hierarchy {
  trait Encoder[A]
  object Encoder extends TwiddleSyntax[Encoder] {
    implicit val instance: ContravariantSemigroupal[Encoder] = ???
  }

  trait Decoder[A]
  object Decoder extends TwiddleSyntax[Decoder] {
    implicit val instance: Applicative[Decoder] = ???
  }

  trait Codec[A] extends Encoder[A] with Decoder[A]
  object Codec extends TwiddleSyntax[Codec] {
    implicit val instance: InvariantSemigroupal[Codec] = ???
  }

  def int: Codec[Int] = ???
  def string: Codec[String] = ???
  def bool: Codec[Boolean] = ???

  case class Foo(x: Int, y: String, z: Boolean)

  def a: Codec[Foo] = (int *: string *: bool).as[Foo]
  def b: Decoder[Foo] = (int *: string *: (bool: Decoder[Boolean])).as[Foo]
  def c: Decoder[Foo] = (int *: (string: Decoder[String]) *: bool).as[Foo]
  def d: Decoder[Foo] = ((int: Decoder[Int]) *: string *: bool).as[Foo]
  def e: Encoder[Foo] = (int *: string *: (bool: Encoder[Boolean])).as[Foo]
  def f: Encoder[Foo] = (int *: (string: Encoder[String]) *: bool).as[Foo]
  def g: Encoder[Foo] = ((int: Encoder[Int]) *: string *: bool).as[Foo]
}
