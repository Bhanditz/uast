/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.uast

import org.jetbrains.uast.visitor.UastVisitor

/**
 * Represents an expression or statement (which is considered as an expression in Uast).
 */
interface UExpression : UElement {
    fun evaluate(): Any? = null
    fun evaluateString(): String? = evaluate() as? String

    /**
     * Returns true, if this expression is *always* a statement.
     * [getExpressionType] should return null in this case.
     *
     * Calling [isStatement] should be relatively cheap, and it should not depend on [getExpressionType].
     */
    val isStatement: Boolean
        get() = false

    /**
     * Returns expression type.
     *
     * @return expression type, or null if type can not be inferred, or if this expression is a statement.
     */
    fun getExpressionType(): UType? = null

    override fun accept(visitor: UastVisitor) {
        visitor.visitElement(this)
        visitor.afterVisitElement(this)
    }
}

/**
 * Helper interface for [UAnnotated] elements without any annotations specified.
 */
interface NoAnnotations : UAnnotated {
    override val annotations: List<UAnnotation>
        get() = emptyList()
}

/**
 * Helper interface for [UModifierOwner] elements without any modifiers specified.
 */
interface NoModifiers : UModifierOwner {
    override fun hasModifier(modifier: UastModifier) = false
}

/**
 * In some cases (user typing, syntax error) elements, which are supposed to exist, are missing.
 * The obvious example — the lack of the condition expression in [UIfExpression], e.g. `if () return`.
 * [UIfExpression.condition] is required to return not-null values,
 *  and Uast implementation should return something instead of `null` in this case.
 *
 * Use [UastEmptyExpression] in this case.
 */
object UastEmptyExpression : UExpression {
    override val parent: UElement?
        get() = null

    override fun logString() = "EmptyExpression"
}