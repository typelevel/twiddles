package org.typelevel.twiddles

import shapeless.HList

trait TwiddleSyntaxPlatform {
  implicit def toTwiddleOpDropUnits[F[_], A <: HList](fa: F[A]): TwiddleOpDropUnits[F, A] = new TwiddleOpDropUnits(fa)
}

