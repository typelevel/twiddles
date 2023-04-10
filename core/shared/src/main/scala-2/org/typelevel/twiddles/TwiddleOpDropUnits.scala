package org.typelevel.twiddles

import cats.Invariant
import cats.syntax.all._
import shapeless.HList

final class TwiddleOpDropUnits[F[_], A <: HList](private val self: F[A]) extends AnyVal {
  def dropUnits(implicit du: DropUnits[A], F: Invariant[F]): F[du.L] = self.imap(du.removeUnits(_))(du.addUnits(_))
}
