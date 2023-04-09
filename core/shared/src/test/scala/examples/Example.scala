package examples

import org.typelevel.twiddles._
import cats.Applicative

object Example {

  trait Json
  trait Decoder[A] {
    def decode(s: Json): Option[A]
  }
  object Decoder extends TwiddleOps {
    implicit val applicative: Applicative[Decoder] = new Applicative[Decoder] {
      def pure[A](a: A): Decoder[A] = _ => Some(a)
      def ap[A, B](ff: Decoder[A => B])(fa: Decoder[A]): Decoder[B] = j =>
        for {
          a <- fa.decode(j)
          f <- ff.decode(j)
        } yield f(a)
    }
  }

  val int: Decoder[Int] = _ => ???
  val string: Decoder[String] = _ => ???

  case class Foo(x: Int, y: String)
  val fooDecoder: Decoder[Foo] = (int *: string).as[Foo]

  case class Bar(x: Int)
  val barDecoder: Decoder[Bar] = int.as[Bar]

  case class Baz(x: Foo, y: Bar)
  val bazDecoder = (fooDecoder *: barDecoder).as[Baz]
}