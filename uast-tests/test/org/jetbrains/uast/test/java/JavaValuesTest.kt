package org.jetbrains.uast.test.java

import org.junit.Test

class JavaValuesTest : AbstractJavaValuesTest() {
    @Test fun testAliveThenElse() = doTest("Simple/AliveThenElse.java")

    @Test fun testCascadeIf() = doTest("Simple/CascadeIf.java")

    @Test fun testDeadElse() = doTest("Simple/DeadElse.java")

    @Test fun testDependents() = doTest("Simple/Dependents.java")

    @Test fun testEnumChoice() = doTest("Simple/EnumChoice.java")

    @Test fun testEnumSwitch() = doTest("Simple/EnumSwitch.java")

    @Test fun testReturnMinusX() = doTest("Simple/ReturnMinusX.java")

    @Test fun testReturnSum() = doTest("Simple/ReturnSum.java")

    @Test fun testReturnX() = doTest("Simple/ReturnX.java")

}