package org.jetbrains.uast.java.internal

import com.intellij.psi.PsiComment
import org.jetbrains.uast.UComment
import org.jetbrains.uast.UElement

interface JavaUElementWithComments : UElement {
    override val comments: List<UComment>
        get() {
            val psi = psi ?: return emptyList()
            return psi.children.filter { it is PsiComment }.map { UComment(it, this) }
        }
}