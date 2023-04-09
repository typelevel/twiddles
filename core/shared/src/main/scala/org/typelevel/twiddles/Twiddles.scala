package org.typelevel.twiddles

import cats.{Invariant, InvariantSemigroupal}
import cats.syntax.all._

trait TwiddleOps {
  import TwiddleOps._
  implicit def toTwiddleCons[F[_], B <: Tuple](fb: F[B]): TwiddleCons[F, B] = new TwiddleCons(fb)
  implicit def toTwiddleTwo[F[_], B](fb: F[B]): TwiddleTwo[F, B] = new TwiddleTwo(fb)
  implicit def toTwiddleAs[F[_], A](fa: F[A]): TwiddleAs[F, A] = new TwiddleAs(fa)
}

object TwiddleOps {
  final class TwiddleCons[F[_], B <: Tuple](private val self: F[B]) extends AnyVal {
    def *:[A](fa: F[A])(implicit F: InvariantSemigroupal[F]): F[A *: B] =
      fa.product(self).imap[A *: B] { case (hd, tl) => hd *: tl } { case hd *: tl => (hd, tl) }
  }
  final class TwiddleTwo[F[_], B](private val self: F[B]) extends AnyVal {
    def *:[A](fa: F[A])(implicit F: InvariantSemigroupal[F]): F[A *: B *: EmptyTuple] =
      fa.product(self).imap[A *: B *: EmptyTuple] { case (a, b) => a *: b *: EmptyTuple } { case a *: b *: EmptyTuple => (a, b) }
  }
  final class TwiddleAs[F[_], A](private val self: F[A]) extends AnyVal {
    def as[B](implicit iso: Iso[A, B], F: Invariant[F]): F[B] = self.imap(iso.to)(iso.from)
  }
}
