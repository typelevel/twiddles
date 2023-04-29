/*
 * Copyright (c) 2023, Typelevel
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package examples

import org.typelevel.twiddles._
import cats.Applicative

object Example {

  trait Json
  trait Decoder[A] {
    def decode(s: Json): Option[A]
  }
  object Decoder extends TwiddleSyntax[Decoder] {
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

  val unit: Decoder[Unit] = _ => ???
  val fooDecoder2 = (int *: unit *: string *: unit).as[Foo]

  val dropping: Decoder[Int *: String *: EmptyTuple] =
    (int *: unit *: string *: unit).dropUnits

  def foo(t: Int *: String *: Boolean *: EmptyTuple): Unit = ???
  foo((1, "hi", true))

  def bar(t: (Int, String, Boolean)): Unit = ???
  bar(1 *: "hi" *: true *: EmptyTuple)
}
