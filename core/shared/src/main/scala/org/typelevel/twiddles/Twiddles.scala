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

package org.typelevel.twiddles

import cats.{Invariant, InvariantSemigroupal}
import cats.syntax.all._

trait TwiddleSyntaxGeneric extends TwiddleSyntaxGenericPlatform {
  implicit def toTwiddleOpCons[F[_], B <: Tuple](fb: F[B]): TwiddleOpCons[F, B] = new TwiddleOpCons(
    fb
  )
  implicit def toTwiddleOpTwo[F[_], B](fb: F[B]): TwiddleOpTwo[F, B] = new TwiddleOpTwo(fb)

  implicit def toTwiddleOpAs[F[_], A](fa: F[A]): TwiddleOpAs[F, A] = new TwiddleOpAs(fa)
}

trait TwiddleSyntax[F[_]] extends TwiddleSyntaxPlatform[F] {
  implicit def toTwiddleOpCons[B <: Tuple](fb: F[B]): TwiddleOpCons[F, B] = new TwiddleOpCons(
    fb
  )
  implicit def toTwiddleOpTwo[B](fb: F[B]): TwiddleOpTwo[F, B] = new TwiddleOpTwo(fb)
  implicit def toTwiddleOpAs[A](fa: F[A]): TwiddleOpAs[F, A] = new TwiddleOpAs(fa)
}

object syntax extends TwiddleSyntaxGeneric

final class TwiddleOpCons[F[_], B <: Tuple](private val self: F[B]) extends AnyVal {
  def *:[G[x] >: F[x], A](ga: G[A])(implicit G: InvariantSemigroupal[G]): G[A *: B] =
    ga.product(self).imap[A *: B] { case (hd, tl) => hd *: tl } { case hd *: tl => (hd, tl) }
}

final class TwiddleOpTwo[F[_], B](private val self: F[B]) extends AnyVal {
  def *:[G[x] >: F[x], A](ga: G[A])(implicit G: InvariantSemigroupal[G]): G[A *: B *: EmptyTuple] =
    ga.product(self).imap[A *: B *: EmptyTuple] { case (a, b) => a *: b *: EmptyTuple } {
      case a *: b *: EmptyTuple => (a, b)
    }
}
final class TwiddleOpAs[F[_], A](private val self: F[A]) extends AnyVal {
  def as[B](implicit iso: Iso[A, B], F: Invariant[F]): F[B] = self.imap(iso.to)(iso.from)
}
