# Twiddles

A twiddle list is a list of one or more values, potentially of differing types, that supports incremental creation and supports conversion to case classes that are "shape compatible" with the constituent types of the twiddle list.

```scala
// Enable twiddle syntax for arbitrary types with appropriate type class instances
import org.typelevel.twiddles.syntax._

case class Foo(x: Int, y: String)

val a = Option(42)
// a: Option[Int] = Some(value = 42)
val b = Option("Hi")
// b: Option[String] = Some(value = "Hi")

val foo = (a *: b).as[Foo]
// foo: Option[Foo] = Some(value = Foo(x = 42, y = "Hi"))
```

In this example, `a *: b` creates an `Option[Int *: String *: EmptyTuple]`. We then convert that value to an `Option[Foo]` via `.as[Foo]`.

The term "twiddle list" was first coined by [Rob Norris](https://github.com/tpolecat) in the [Skunk](https://github.com/tpolecat/skunk) library, where a twiddle list was defined as a left nested tuple. For example, a 4 element twiddle list consisting of an `Int`, `String`, `Boolean`, and `Double` was represented as `(((Int, String), Boolean), Double)`. This library uses a different encoding -- twiddle lists are encoded as tuples on Scala 3 and Shapeless heterogeneous lists on Scala 2. The previous 4 element twiddle list is represented as `(Int, String, Boolean, Double)` or equivalently, `Int *: String *: Boolean *: Double *: EmptyTuple`. With the import `org.typelevel.twiddles._`, the latter syntax works with Scala 2. More specifically, the import aliases `*:` to `shapeless.::`, `EmptyTuple` to `HNil`, and `Tuple` to `HList`.

The expression `a *: b *: c` builds an `F[A *: B *: C *: EmptyTuple]` from an `F[A]`, `F[B]`, and `F[C]`. The `*:` operation requires that the type constructor `F` has a `cats.InvariantSemigroupal` instance.


## Library Usage

```scala
import org.typelevel.twiddles.TwiddleSyntax
import cats.Applicative

trait Json
trait Decoder[A] {
  def decode(j: Json): Option[A]
}
object Decoder extends TwiddleSyntax {
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
// int: Decoder[Int] = repl.MdocSession$MdocApp0$$Lambda$47562/0x00000008036d0cb0@3c825f80
val string: Decoder[String] = _ => ???
// string: Decoder[String] = repl.MdocSession$MdocApp0$$Lambda$47563/0x00000008036d10f8@771e39d9

case class Foo(x: Int, y: String)
val fooDecoder = (int *: string).as[Foo]
// fooDecoder: Decoder[Foo] = repl.MdocSession$$anon$7$$Lambda$47566/0x00000008036d1d78@30672f93
```

In this example, the `Decoder` type has an `Applicative` instance defined in its companion object, and the companion object extends `TwiddleSyntax`. The latter enables use of `*:` and `as` with `Decoder` values without adding explicit imports (that is, there's no need to import `org.typelevel.twiddles.syntax._` at usage sites).