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

import shapeless.{::, HList, HNil}
import shapeless.ops.hlist.Tupler

/** Mix-in trait that provides source compatibility between Scala 2 tuples and Twiddles. */
trait TwiddleCompat {

  type Tuple = HList
  @inline val Tuple = HList

  type EmptyTuple = HNil
  @inline val EmptyTuple: EmptyTuple = HNil

  type *:[+A, +B <: Tuple] = ::[A, B]
  @inline val *: = ::

  implicit def orgTypelevelTwiddleCompatToTupleOps[T <: Tuple](t: T): TupleOps[T] =
    new TupleOps[T](t)

  import shapeless.syntax.std.tuple._

  // format: off
  implicit def orgTypelevelTwiddleCompatTuple2ToHList[A, B](t: (A, B)): A *: B *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple3ToHList[A, B, C](t: (A, B, C)): A *: B *: C *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple4ToHList[A, B, C, D](t: (A, B, C, D)): A *: B *: C *: D *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple5ToHList[A, B, C, D, E](t: (A, B, C, D, E)): A *: B *: C *: D *: E *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple6ToHList[A, B, C, D, E, F](t: (A, B, C, D, E, F)): A *: B *: C *: D *: E *: F *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple7ToHList[A, B, C, D, E, F, G](t: (A, B, C, D, E, F, G)): A *: B *: C *: D *: E *: F *: G *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple8ToHList[A, B, C, D, E, F, G, H](t: (A, B, C, D, E, F, G, H)): A *: B *: C *: D *: E *: F *: G *: H *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple9ToHList[A, B, C, D, E, F, G, H, I](t: (A, B, C, D, E, F, G, H, I)): A *: B *: C *: D *: E *: F *: G *: H *: I *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple10ToHList[A, B, C, D, E, F, G, H, I, J](t: (A, B, C, D, E, F, G, H, I, J)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple11ToHList[A, B, C, D, E, F, G, H, I, J, K](t: (A, B, C, D, E, F, G, H, I, J, K)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple12ToHList[A, B, C, D, E, F, G, H, I, J, K, L](t: (A, B, C, D, E, F, G, H, I, J, K, L)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple13ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M](t: (A, B, C, D, E, F, G, H, I, J, K, L, M)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple14ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple15ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple16ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple17ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple18ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple19ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple20ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple21ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: EmptyTuple = t.productElements
  implicit def orgTypelevelTwiddleCompatTuple22ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: V *: EmptyTuple = t.productElements

  implicit def orgTypelevelTwiddleCompatHlistToTuple0[Z <: Tuple](z: Z)(implicit
      tupler: shapeless.ops.hlist.Tupler.Aux[Z, Unit]
  ): Unit = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple1[Z <: Tuple, A](z: Z)(implicit
      tupler: shapeless.ops.hlist.Tupler.Aux[Z, Tuple1[A]]
  ): Tuple1[A] = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple2[Z <: Tuple, A, B](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B)]
  ): (A, B) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple3[Z <: Tuple, A, B, C](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C)]
  ): (A, B, C) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple4[Z <: Tuple, A, B, C, D](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D)]
  ): (A, B, C, D) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple5[Z <: Tuple, A, B, C, D, E](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E)]
  ): (A, B, C, D, E) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple6[Z <: Tuple, A, B, C, D, E, F](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F)]
  ): (A, B, C, D, E, F) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple7[Z <: Tuple, A, B, C, D, E, F, G](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G)]
  ): (A, B, C, D, E, F, G) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple8[Z <: Tuple, A, B, C, D, E, F, G, H](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H)]
  ): (A, B, C, D, E, F, G, H) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple9[Z <: Tuple, A, B, C, D, E, F, G, H, I](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I)]
  ): (A, B, C, D, E, F, G, H, I) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple10[Z <: Tuple, A, B, C, D, E, F, G, H, I, J](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J)]
  ): (A, B, C, D, E, F, G, H, I, J) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple11[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K)]
  ): (A, B, C, D, E, F, G, H, I, J, K) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple12[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple13[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple14[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple15[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple16[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple17[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple18[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple19[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple20[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple21[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) = tupler(z)
  implicit def orgTypelevelTwiddleCompatHlistToTuple22[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](z: Z)(implicit
      tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]
  ): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) = tupler(z)
  // format: on

  // Unused but kept for binary compatibility
  def hlistToTuple[L <: Tuple, T](l: L)(implicit
      tupler: shapeless.ops.hlist.Tupler.Aux[L, T]
  ): T = l.tupled

  @deprecated("use orgTypelevelTwiddleCompatToTupleOps", "0.6.3") def toTupleOps[T <: Tuple](
      t: T
  ): TupleOps[T] = orgTypelevelTwiddleCompatToTupleOps(t)

  // format: off
  @deprecated("use orgTypelevelTwiddleCompatTuple2ToHList", "0.6.3") def tuple2ToHList[A, B](t: (A, B)): A *: B *: EmptyTuple = orgTypelevelTwiddleCompatTuple2ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple3ToHList", "0.6.3") def tuple3ToHList[A, B, C](t: (A, B, C)): A *: B *: C *: EmptyTuple = orgTypelevelTwiddleCompatTuple3ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple4ToHList", "0.6.3") def tuple4ToHList[A, B, C, D](t: (A, B, C, D)): A *: B *: C *: D *: EmptyTuple = orgTypelevelTwiddleCompatTuple4ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple5ToHList", "0.6.3") def tuple5ToHList[A, B, C, D, E](t: (A, B, C, D, E)): A *: B *: C *: D *: E *: EmptyTuple = orgTypelevelTwiddleCompatTuple5ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple6ToHList", "0.6.3") def tuple6ToHList[A, B, C, D, E, F](t: (A, B, C, D, E, F)): A *: B *: C *: D *: E *: F *: EmptyTuple = orgTypelevelTwiddleCompatTuple6ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple7ToHList", "0.6.3") def tuple7ToHList[A, B, C, D, E, F, G](t: (A, B, C, D, E, F, G)): A *: B *: C *: D *: E *: F *: G *: EmptyTuple = orgTypelevelTwiddleCompatTuple7ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple8ToHList", "0.6.3") def tuple8ToHList[A, B, C, D, E, F, G, H](t: (A, B, C, D, E, F, G, H)): A *: B *: C *: D *: E *: F *: G *: H *: EmptyTuple = orgTypelevelTwiddleCompatTuple8ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple9ToHList", "0.6.3") def tuple9ToHList[A, B, C, D, E, F, G, H, I](t: (A, B, C, D, E, F, G, H, I)): A *: B *: C *: D *: E *: F *: G *: H *: I *: EmptyTuple = orgTypelevelTwiddleCompatTuple9ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple10ToHList", "0.6.3") def tuple10ToHList[A, B, C, D, E, F, G, H, I, J](t: (A, B, C, D, E, F, G, H, I, J)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: EmptyTuple = orgTypelevelTwiddleCompatTuple10ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple11ToHList", "0.6.3") def tuple11ToHList[A, B, C, D, E, F, G, H, I, J, K](t: (A, B, C, D, E, F, G, H, I, J, K)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: EmptyTuple = orgTypelevelTwiddleCompatTuple11ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple12ToHList", "0.6.3") def tuple12ToHList[A, B, C, D, E, F, G, H, I, J, K, L](t: (A, B, C, D, E, F, G, H, I, J, K, L)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: EmptyTuple = orgTypelevelTwiddleCompatTuple12ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple13ToHList", "0.6.3") def tuple13ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M](t: (A, B, C, D, E, F, G, H, I, J, K, L, M)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: EmptyTuple = orgTypelevelTwiddleCompatTuple13ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple14ToHList", "0.6.3") def tuple14ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: EmptyTuple = orgTypelevelTwiddleCompatTuple14ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple15ToHList", "0.6.3") def tuple15ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: EmptyTuple = orgTypelevelTwiddleCompatTuple15ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple16ToHList", "0.6.3") def tuple16ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: EmptyTuple = orgTypelevelTwiddleCompatTuple16ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple17ToHList", "0.6.3") def tuple17ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: EmptyTuple = orgTypelevelTwiddleCompatTuple17ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple18ToHList", "0.6.3") def tuple18ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: EmptyTuple = orgTypelevelTwiddleCompatTuple18ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple19ToHList", "0.6.3") def tuple19ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: EmptyTuple = orgTypelevelTwiddleCompatTuple19ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple20ToHList", "0.6.3") def tuple20ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: EmptyTuple = orgTypelevelTwiddleCompatTuple20ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple21ToHList", "0.6.3") def tuple21ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: EmptyTuple = orgTypelevelTwiddleCompatTuple21ToHList(t)
  @deprecated("use orgTypelevelTwiddleCompatTuple22ToHList", "0.6.3") def tuple22ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: V *: EmptyTuple = orgTypelevelTwiddleCompatTuple22ToHList(t)

  // format: off
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple0", "0.6.3") def hlistToTuple0[Z <: Tuple](z: Z)(implicit tupler: shapeless.ops.hlist.Tupler.Aux[Z, Unit]): Unit = orgTypelevelTwiddleCompatHlistToTuple0(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple1", "0.6.3") def hlistToTuple1[Z <: Tuple, A](z: Z)(implicit tupler: shapeless.ops.hlist.Tupler.Aux[Z, Tuple1[A]]): Tuple1[A] = orgTypelevelTwiddleCompatHlistToTuple1(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple2", "0.6.3") def hlistToTuple2[Z <: Tuple, A, B](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B)]): (A, B) = orgTypelevelTwiddleCompatHlistToTuple2(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple3", "0.6.3") def hlistToTuple3[Z <: Tuple, A, B, C](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C)]): (A, B, C) = orgTypelevelTwiddleCompatHlistToTuple3(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple4", "0.6.3") def hlistToTuple4[Z <: Tuple, A, B, C, D](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D)]): (A, B, C, D) = orgTypelevelTwiddleCompatHlistToTuple4(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple5", "0.6.3") def hlistToTuple5[Z <: Tuple, A, B, C, D, E](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E)]): (A, B, C, D, E) = orgTypelevelTwiddleCompatHlistToTuple5(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple6", "0.6.3") def hlistToTuple6[Z <: Tuple, A, B, C, D, E, F](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F)]): (A, B, C, D, E, F) = orgTypelevelTwiddleCompatHlistToTuple6(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple7", "0.6.3") def hlistToTuple7[Z <: Tuple, A, B, C, D, E, F, G](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G)]): (A, B, C, D, E, F, G) = orgTypelevelTwiddleCompatHlistToTuple7(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple8", "0.6.3") def hlistToTuple8[Z <: Tuple, A, B, C, D, E, F, G, H](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H)]): (A, B, C, D, E, F, G, H) = orgTypelevelTwiddleCompatHlistToTuple8(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple9", "0.6.3") def hlistToTuple9[Z <: Tuple, A, B, C, D, E, F, G, H, I](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I)]): (A, B, C, D, E, F, G, H, I) = orgTypelevelTwiddleCompatHlistToTuple9(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple10", "0.6.3") def hlistToTuple10[Z <: Tuple, A, B, C, D, E, F, G, H, I, J](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J)]): (A, B, C, D, E, F, G, H, I, J) = orgTypelevelTwiddleCompatHlistToTuple10(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple11", "0.6.3") def hlistToTuple11[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K)]): (A, B, C, D, E, F, G, H, I, J, K) = orgTypelevelTwiddleCompatHlistToTuple11(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple12", "0.6.3") def hlistToTuple12[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L)]): (A, B, C, D, E, F, G, H, I, J, K, L) = orgTypelevelTwiddleCompatHlistToTuple12(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple13", "0.6.3") def hlistToTuple13[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M)]): (A, B, C, D, E, F, G, H, I, J, K, L, M) = orgTypelevelTwiddleCompatHlistToTuple13(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple14", "0.6.3") def hlistToTuple14[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N) = orgTypelevelTwiddleCompatHlistToTuple14(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple15", "0.6.3") def hlistToTuple15[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O) = orgTypelevelTwiddleCompatHlistToTuple15(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple16", "0.6.3") def hlistToTuple16[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P) = orgTypelevelTwiddleCompatHlistToTuple16(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple17", "0.6.3") def hlistToTuple17[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q) = orgTypelevelTwiddleCompatHlistToTuple17(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple18", "0.6.3") def hlistToTuple18[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R) = orgTypelevelTwiddleCompatHlistToTuple18(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple19", "0.6.3") def hlistToTuple19[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S) = orgTypelevelTwiddleCompatHlistToTuple19(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple19", "0.6.3") def hlistToTuple20[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T) = orgTypelevelTwiddleCompatHlistToTuple20(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple19", "0.6.3") def hlistToTuple21[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U) = orgTypelevelTwiddleCompatHlistToTuple21(z)
  @deprecated("use orgTypelevelTwiddleCompatHlistToTuple19", "0.6.3") def hlistToTuple22[Z <: Tuple, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](z: Z)(implicit tupler: Tupler.Aux[Z, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]): (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V) = orgTypelevelTwiddleCompatHlistToTuple22(z)
  // format: on
}
