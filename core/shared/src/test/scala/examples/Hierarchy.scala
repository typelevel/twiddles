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

import cats.InvariantSemigroupal
import cats.ContravariantSemigroupal
import cats.Applicative
import org.typelevel.twiddles.TwiddleSyntax

object Hierarchy {
  trait Encoder[A]
  object Encoder extends TwiddleSyntax[Encoder] {
    implicit def instance: ContravariantSemigroupal[Encoder] = ???
  }

  trait Decoder[A]
  object Decoder extends TwiddleSyntax[Decoder] {
    implicit def instance: Applicative[Decoder] = ???
  }

  trait Codec[A] extends Encoder[A] with Decoder[A]
  object Codec extends TwiddleSyntax[Codec] {
    implicit def instance: InvariantSemigroupal[Codec] = ???
  }

  def int: Codec[Int] = ???
  def string: Codec[String] = ???
  def bool: Codec[Boolean] = ???

  case class Foo(x: Int, y: String, z: Boolean)

  def a: Codec[Foo] = (int *: string *: bool).to[Foo]
  def b: Decoder[Foo] = (int *: string *: (bool: Decoder[Boolean])).to[Foo]
  def c: Decoder[Foo] = (int *: (string: Decoder[String]) *: bool).to[Foo]
  def d: Decoder[Foo] = ((int: Decoder[Int]) *: string *: bool).to[Foo]
  def e: Encoder[Foo] = (int *: string *: (bool: Encoder[Boolean])).to[Foo]
  def f: Encoder[Foo] = (int *: (string: Encoder[String]) *: bool).to[Foo]
  def g: Encoder[Foo] = ((int: Encoder[Int]) *: string *: bool).to[Foo]
  def h = (int *: string) *: bool
  def i = (int *: string *: bool) *: bool *: bool
}
