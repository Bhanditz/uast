package org.jetbrains.uast

import com.intellij.psi.PsiClassInitializer

import org.jetbrains.uast.internal.acceptList
import org.jetbrains.uast.internal.log
import org.jetbrains.uast.visitor.UastTypedVisitor
import org.jetbrains.uast.visitor.UastVisitor

/**
 * A class initializer wrapper to be used in [UastVisitor].
 */
interface UClassInitializer : UDeclaration, PsiClassInitializer {
    override val psi: PsiClassInitializer

    /**
     * Returns the body of this class initializer.
     */
    val uastBody: UExpression

    @Deprecated("Use uastBody instead.", ReplaceWith("uastBody"))
    override fun getBody() = psi.body

    override fun accept(visitor: UastVisitor) {
        if (visitor.visitInitializer(this)) return
        annotations.acceptList(visitor)
        uastBody.accept(visitor)
        visitor.afterVisitInitializer(this)
    }

    override fun asRenderString() = buildString {
        append(modifierList)
        appendln(uastBody.asRenderString().withMargin)
    }

    override fun <D, R> accept(visitor: UastTypedVisitor<D, R>, data: D) =
            visitor.visitClassInitializer(this, data)

    override fun asLogString() = log("isStatic = $isStatic")
}