# Twiddles

A twiddle list is a list of one or more values, potentially of differing types, that supports incremental creation and supports converstion to case classes that are "shape compatible".

```scala mdoc
import org.typelevel.twiddles.syntax._

case class Foo(x: Int, y: String)

val a: Option[Int] = Some(42)
val b: Option[String] = Some("Hi")

val foo = (a *: b).as[Foo]
```