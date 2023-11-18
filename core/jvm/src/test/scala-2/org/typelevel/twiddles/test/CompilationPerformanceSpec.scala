package org.typelevel.twiddles.test

import munit.FunSuite
import org.typelevel.twiddles.Tuple

import scala.concurrent._
import scala.concurrent.duration._
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._
import scala.tools.reflect.ToolBox

class CompilationPerformanceSpec extends FunSuite {
  private implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  override val munitTimeout: Duration = 30.seconds

  test("should compile long twiddles in a reasonable amount of time") {
    val compiled = compileWithin(
      q"""import org.typelevel.twiddles._
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
          1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            EmptyTuple""",
      10.seconds
    )

    assert(compiled.isInstanceOf[Tuple])
  }

  private val toolbox = {
    val toolbox = currentMirror.mkToolBox()
    // warmup
    toolbox.compile(q"")
    toolbox
  }

  private def compileWithin(t: Tree, atMost: Duration)(implicit ec: ExecutionContext) =
    Await.result(Future(toolbox.compile(t)()), atMost)
}
