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

  test("should subtype in a reasonable amount of time") {
    val compiled = compileWithin(
      q"""import org.typelevel.twiddles._
          val inferred =
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
            1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *: 1 *:
              EmptyTuple
          type Expected = 
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
            Int *: Int *: Int *: Int *: Int *: Int *: Int *: Int *:
              EmptyTuple
          inferred: Expected""",
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
