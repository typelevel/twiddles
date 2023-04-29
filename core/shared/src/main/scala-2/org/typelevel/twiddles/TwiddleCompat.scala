package org.typelevel.twiddles

import shapeless.{::, HList, HNil}

/** Mix-in trait that provides source compatibility between Scala 2 tuples and Twiddles. */
trait TwiddleCompat {

  type Tuple = HList
  @inline val Tuple = HList

  type EmptyTuple = HNil
  @inline val EmptyTuple: EmptyTuple = HNil

  type *:[A, B <: Tuple] = ::[A, B]
  @inline val *: = ::

  implicit def toTupleOps[T <: Tuple](t: T): TupleOps[T] =
    new TupleOps[T](t)

  import shapeless.syntax.std.tuple._

  implicit def tuple2ToHList[A, B](t: (A, B)): A *: B *: EmptyTuple = t.productElements
  implicit def tuple3ToHList[A, B, C](t: (A, B, C)): A *: B *: C *: EmptyTuple = t.productElements
  implicit def tuple4ToHList[A, B, C, D](t: (A, B, C, D)): A *: B *: C *: D *: EmptyTuple = t.productElements
  implicit def tuple5ToHList[A, B, C, D, E](t: (A, B, C, D, E)): A *: B *: C *: D *: E *: EmptyTuple = t.productElements
  implicit def tuple6ToHList[A, B, C, D, E, F](t: (A, B, C, D, E, F)): A *: B *: C *: D *: E *: F *: EmptyTuple = t.productElements
  implicit def tuple7ToHList[A, B, C, D, E, F, G](t: (A, B, C, D, E, F, G)): A *: B *: C *: D *: E *: F *: G *: EmptyTuple = t.productElements
  implicit def tuple8ToHList[A, B, C, D, E, F, G, H](t: (A, B, C, D, E, F, G, H)): A *: B *: C *: D *: E *: F *: G *: H *: EmptyTuple = t.productElements
  implicit def tuple9ToHList[A, B, C, D, E, F, G, H, I](t: (A, B, C, D, E, F, G, H, I)): A *: B *: C *: D *: E *: F *: G *: H *: I *: EmptyTuple = t.productElements
  implicit def tuple10ToHList[A, B, C, D, E, F, G, H, I, J](t: (A, B, C, D, E, F, G, H, I, J)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: EmptyTuple = t.productElements
  implicit def tuple11ToHList[A, B, C, D, E, F, G, H, I, J, K](t: (A, B, C, D, E, F, G, H, I, J, K)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: EmptyTuple = t.productElements
  implicit def tuple12ToHList[A, B, C, D, E, F, G, H, I, J, K, L](t: (A, B, C, D, E, F, G, H, I, J, K, L)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: EmptyTuple = t.productElements
  implicit def tuple13ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M](t: (A, B, C, D, E, F, G, H, I, J, K, L, M)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: EmptyTuple = t.productElements
  implicit def tuple14ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: EmptyTuple = t.productElements
  implicit def tuple15ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: EmptyTuple = t.productElements
  implicit def tuple16ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: EmptyTuple = t.productElements
  implicit def tuple17ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: EmptyTuple = t.productElements
  implicit def tuple18ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: EmptyTuple = t.productElements
  implicit def tuple19ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: EmptyTuple = t.productElements
  implicit def tuple20ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: EmptyTuple = t.productElements
  implicit def tuple21ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: EmptyTuple = t.productElements
  implicit def tuple22ToHList[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](t: (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)): A *: B *: C *: D *: E *: F *: G *: H *: I *: J *: K *: L *: M *: N *: O *: P *: Q *: R *: S *: T *: U *: V *: EmptyTuple = t.productElements

  implicit def hlistToTuple[L <: Tuple, T](l: L)(implicit tupler: shapeless.ops.hlist.Tupler.Aux[L, T]): T = l.tupled
}