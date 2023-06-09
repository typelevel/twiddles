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

import shapeless._

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

private[twiddles] trait IsoLowPriority {
  def instance[A, B](t: A => B)(f: B => A): Iso[A, B] =
    new Iso[A, B] {
      def to(a: A) = t(a)
      def from(b: B) = f(b)
    }

  implicit def inverse[A, B](implicit iso: Iso[A, B]): Iso[B, A] = iso.inverse

  implicit def productWithUnits[A <: HList, B, Repr <: HList](implicit
      g: Generic.Aux[B, Repr],
      du: DropUnits.Aux[A, Repr]
  ): Iso[A, B] =
    instance((a: A) => g.from(du.drop(a)))(b => du.insert(g.to(b)))
}

/** Companion for [[Iso]]. */
object Iso extends IsoLowPriority {

  def apply[A, B](implicit instance: Iso[A, B]): Iso[A, B] = instance

  /** Identity iso. */
  implicit def id[A]: Iso[A, A] = instance[A, A](identity)(identity)

  def product[A](implicit gen: Generic[A]): Iso[A, gen.Repr] =
    instance[A, gen.Repr](gen.to(_))(gen.from(_))

  implicit def productInstance[A <: Tuple, B](implicit gen: Generic.Aux[B, A]): Iso[A, B] =
    instance[A, B](gen.from)(gen.to)

  implicit def singletonInstance[A, B](implicit gen: Generic.Aux[B, A *: EmptyTuple]): Iso[A, B] =
    instance[A, B](a => gen.from(a *: EmptyTuple))(b => gen.to(b).head)
}
