# Twiddles

A twiddle list is a list of one or more values, potentially of differing types, that supports incremental creation and supports conversion to case classes that are "shape compatible" with the constituent types of the twiddle list.

Twiddle lists are useful in the creation of protocols (e.g., decoders, encoders, codecs), where a protocol for a complex type is built from simpler constituent protocols. This technique was first popularized by parser combinators with syntax like `lparen ~ expr ~ rparen`. In contrast to type driven derivation schemes, where protocols are implicitly determined by the constituent types of a data constructor, twiddle lists keep the focus on the protocol.

This library provides the ability to work with twiddle lists for arbitrary types and provides a single API that works for both Scala 3 and Scala 2. On Scala 3, twiddle lists are represented as generic tuples -- e.g., `F[Int *: String *: Boolean *: EmptyTuple]` or equivalently `F[(Int, String, Boolean)]`. On Scala 2, twiddle lists are represented as Shapeless heterogeneous lists. The `org.typelevel.twiddles` package provides type aliases that allow for source compatibility (`*:` is aliased to `shapeles.::` and `EmptyTuple` is aliased to `shapeless.HNil`).

## Getting Started

Artifacts are published for Scala 2.12, 2.13, and 3 and all platforms (JVM, Scala.js, and Scala Native).

```scala
libraryDependencies += "org.typelevel" %% "twiddles-core" % "0.7.2" // check Releases for the latest version
```

```scala mdoc
// Enable twiddle syntax for arbitrary types
import org.typelevel.twiddles.syntax._

case class Foo(x: Int, y: String)

val a = Option(42)
val b = Option("Hi")

val foo = (a *: b).to[Foo]
```

In this example, `a *: b` creates an `Option[Int *: String *: EmptyTuple]`. We then convert that value to an `Option[Foo]` via `.to[Foo]`.

The `*:` operation comes from the imported twiddle syntax and is similar to the Scala 3 built-in tuple cons operation, but works on applied type constructors. The expression `a *: b *: c` builds an `F[A *: B *: C *: EmptyTuple]` from an `F[A]`, `F[B]`, and `F[C]`. The `*:` operation requires that the type constructor `F` has a `cats.InvariantSemigroupal` instance.

The `to` operation also comes from the imported twiddle syntax. Calling `.to[X]` on an `F[T]` for some twiddle list `T` results in an `F[X]` provided that `T` is shape compatible with `X`. In the most common case where `X` is a case class, shape compatibility is defined as `T` having the same types in the same order as the parameters of `X`. The `to` operation requires that the type constructor `F` has a `cats.Invariant` instance.

Invariant semigroupals are much more general than (covariant) functors, which means twiddle list support works for a wide variety of data types. For instance, contravariant functors are invariant semigroupals allowing us to use twiddle list syntax to incrementally build instances:

```scala mdoc
val fooOrdering = (summon[Ordering[Int]] *: summon[Ordering[String]]).to[Foo]
```

## Library Usage

When designing a library that uses twiddle lists, the `TwiddleSyntax` trait can be mixed in to the companion object of a type constructor. This has the effect of providing twiddle syntax without requiring users of the library to import `org.typelevel.twiddles.syntax._` at each call site.

```scala mdoc:reset
import org.typelevel.twiddles.TwiddleSyntax
import cats.Applicative

trait Json
trait Decoder[A] {
  def decode(j: Json): Option[A]
}
object Decoder extends TwiddleSyntax[Decoder] {
  implicit val applicative: Applicative[Decoder] = new Applicative[Decoder] {
    def pure[A](a: A): Decoder[A] = _ => Some(a)
    def ap[A, B](ff: Decoder[A => B])(fa: Decoder[A]): Decoder[B] = j =>
      for {
        f <- ff.decode(j)
        a <- fa.decode(j)
      } yield f(a)
  }
}

val int: Decoder[Int] = _ => ???
val string: Decoder[String] = _ => ???

case class Foo(x: Int, y: String)
val fooDecoder = (int *: string).to[Foo]
```

In this example, the `Decoder` type has an `Applicative` instance defined in its companion object (and `Applicative` extends `InvariantSemigroupal`), and the companion object extends `TwiddleSyntax`. The latter enables use of `*:` and `to` with `Decoder` values without adding explicit imports (that is, there's no need to import `org.typelevel.twiddles.syntax._` at call sites).

## Etymology

The term "twiddle list" was first coined by [Rob Norris](https://github.com/tpolecat) in the [Skunk](https://github.com/tpolecat/skunk) library, where a twiddle list was defined as a left nested tuple. For example, a 4 element twiddle list consisting of an `Int`, `String`, `Boolean`, and `Double` was represented as `(((Int, String), Boolean), Double)`.

This library uses a different encoding -- twiddle lists are encoded as tuples on Scala 3 and Shapeless heterogeneous lists on Scala 2. The previous 4 element twiddle list is represented as `Int *: String *: Boolean *: Double *: EmptyTuple`.

We adopt the name "twiddle list" to refer to the general technique of incremental construction of complex protocols.
