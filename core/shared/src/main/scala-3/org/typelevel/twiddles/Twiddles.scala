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
import cats.syntax.all.*
import scala.compiletime.summonInline

object Twiddles:

  type Prepend[A, B] = B match
    case Tuple => A *: B
    case _ => A *: B *: EmptyTuple

  inline def prepend[F[x]: InvariantSemigroupal, G[x] <: F[x], A, B](fa: F[A], gb: G[B]): F[Prepend[A, B]] =
    inline gb match
      case gbT: G[bh *: bt] =>
        val gbT0: G[bh *: bt] = gbT // Huh?
        val res = fa.product(gbT0).imap[A *: bh *: bt] { case (hd, tl) => hd *: tl } { case hd *: tl => (hd, tl) }
        res.asInstanceOf[F[Prepend[A, B]]]
      case _ =>
        val res = fa.product(gb).imap[A *: B *: EmptyTuple] { case (a, b) => a *: b *: EmptyTuple } {
          case a *: b *: EmptyTuple => (a, b)
        }
        res.asInstanceOf[F[Prepend[A, B]]]

  sealed trait PrependOps[F[_]]:
    extension [A](self: F[A])(using InvariantSemigroupal[F])
      inline def *:[B](that: F[B]): F[Prepend[A, B]] =
        prepend(self, that)

    extension [A, G[x] >: F[x]: InvariantSemigroupal](self: G[A])
      inline def *:[B](that: F[B]): G[Prepend[A, B]] =
        prepend(self, that)


trait TwiddleSyntax[F[_]]:

  given twiddlesPrependOps: Twiddles.PrependOps[F] with {}

  extension [A](fa: F[A])
  
    inline def to[B]: F[B] =
      // Note: defining these as context params results in inference issues
      // See https://github.com/typelevel/twiddles/issues/19 and fix
      // https://github.com/typelevel/twiddles/issues/10#issuecomment-2833403842
      val iso = summonInline[Iso[A, B]]
      val F = summonInline[Invariant[F]]
      F.imap(fa)(iso.to)(iso.from)

  extension [A <: Tuple](fa: F[A])
    inline def dropUnits(using Invariant[F]): F[DropUnits[A]] =
      fa.imap(DropUnits.drop(_))(DropUnits.insert(_))


object syntax:
  extension [F[_], A](fa: F[A])
    inline def *:[G[x] >: F[x], B](gb: G[B])(using InvariantSemigroupal[G]): G[A *: B *: EmptyTuple] =
      Twiddles.prepend(fa, gb).asInstanceOf[G[A *: B *: EmptyTuple]]

    inline def to[B](using iso: Iso[A, B], F: Invariant[F]): F[B] =
      fa.imap(iso.to)(iso.from)

  extension [F[_], A <: Tuple](fa: F[A])
    inline def dropUnits(using Invariant[F]): F[DropUnits[A]] =
      fa.imap(DropUnits.drop(_))(DropUnits.insert(_))

