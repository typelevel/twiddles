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

/** Describes an isomorphism between two `HList`s, `K` and `L`, where `L` has the same shape as `K` except unit
  * values have been removed.
  */
sealed trait DropUnits[K <: HList] {
  type L <: HList
  def drop(k: K): L
  def insert(l: L): K
}

/** Low priority implicits for [[DropUnits]]. */
private[twiddles] sealed trait DropUnitsLowPriority {

  /* Keep this low priority so that head of `K` is checked for Unit before this is used. This avoids computing
   * H =!:= Unit for each component type.
   */
  implicit def `non-empty K and L where head of K and L are same type`[H, KT <: HList, LT <: HList](
      implicit rest: DropUnits.Aux[KT, LT]
  ): DropUnits.Aux[H :: KT, H :: LT] = new DropUnits[H :: KT] {
    type L = H :: LT
    def drop(k: H :: KT): H :: LT = k.head :: rest.drop(k.tail)
    def insert(l: H :: LT): H :: KT = l.head :: rest.insert(l.tail)
  }
}

/** Companion for [[DropUnits]]. */
object DropUnits extends DropUnitsLowPriority {

  type Aux[K0 <: HList, L0 <: HList] = DropUnits[K0] { type L = L0 }

  implicit lazy val base: DropUnits.Aux[HNil, HNil] = new DropUnits[HNil] {
    type L = HNil
    def drop(k: HNil): HNil = HNil
    def insert(l: HNil): HNil = HNil
  }

  implicit def `non-empty K and any L where head of K is Unit`[KT <: HList, L0 <: HList](implicit
      rest: DropUnits.Aux[KT, L0]
  ): DropUnits.Aux[Unit :: KT, L0] = new DropUnits[Unit :: KT] {
    type L = L0
    def drop(k: Unit :: KT): L = rest.drop(k.tail)
    def insert(l: L): Unit :: KT = () :: rest.insert(l)
  }
}
