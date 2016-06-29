@file:JvmMultifileClass
@file:JvmName("UastUtils")
package org.jetbrains.uast

/**
 * Get the topmost parent qualified expression for the call expression.
 *
 * Example 1:
 *  Code: variable.call(args)
 *  Call element: E = call(args)
 *  Qualified parent (return value): Q = [getQualifiedCallElement](E) = variable.call(args)
 *
 * Example 2:
 *  Code: call(args)
 *  Call element: E = call(args)
 *  Qualified parent (return value): Q = [getQualifiedCallElement](E) = call(args) (no qualifier)
 *
 *  @return containing qualified expression if the call is a child of the qualified expression, call element otherwise.
 */
fun UExpression.getQualifiedParentOrThis(): UExpression {
    fun findParent(current: UExpression?, previous: UExpression): UExpression? = when (current) {
        is UQualifiedReferenceExpression -> {
            if (current.selector == previous)
                findParent(current.parent as? UExpression, current) ?: current
            else
                previous
        }
        is UParenthesizedExpression -> findParent(current.expression, previous) ?: previous
        else -> null
    }

    return findParent(parent as? UExpression, this) ?: this
}


fun UExpression.asQualifiedPath(): List<String>? {
    if (this is USimpleNameReferenceExpression) {
        return listOf(this.identifier)
    } else if (this !is UQualifiedReferenceExpression) {
        return null
    }

    var error = false
    val list = mutableListOf<String>()
    fun addIdentifiers(expr: UQualifiedReferenceExpression) {
        val receiver = expr.receiver.unwrapParenthesis()
        val selector = expr.selector as? USimpleNameReferenceExpression ?: run { error = true; return }
        when (receiver) {
            is UQualifiedReferenceExpression -> addIdentifiers(receiver)
            is USimpleNameReferenceExpression -> list += receiver.identifier
            else -> {
                error = true
                return
            }
        }
        list += selector.identifier
    }

    addIdentifiers(this)
    return if (error) null else list
}

/**
 * Return the list of qualified expressions.
 *
 * Example:
 *   Code: obj.call(param).anotherCall(param2).getter
 *   Qualified chain: [obj, call(param), anotherCall(param2), getter]
 *
 * @return list of qualified expressions, or the empty list if the received expression is not a qualified expression.
 */
fun UExpression.getQualifiedChain(): List<UExpression> {
    fun collect(expr: UQualifiedReferenceExpression, chains: MutableList<UExpression>) {
        val receiver = expr.receiver.unwrapParenthesis()
        if (receiver is UQualifiedReferenceExpression) {
            collect(receiver, chains)
        } else {
            chains += receiver
        }

        val selector = expr.selector.unwrapParenthesis()
        if (selector is UQualifiedReferenceExpression) {
            collect(selector, chains)
        } else {
            chains += selector
        }
    }

    val qualifiedExpression = this.getOutermostQualified() ?: return emptyList()
    val chains = mutableListOf<UExpression>()
    collect(qualifiedExpression, chains)
    return chains
}

/**
 * Return the outermost qualified expression.
 *
 * @return the outermost qualified expression,
 *  this element if the parent expression is not a qualified expression,
 *  or null if the element is not a qualified expression.
 *
 *  Example:
 *   Code: a.b.c(asd).g
 *   Call element: c(asd)
 *   Outermost qualified (return value): a.b.c(asd).g
 */
fun UExpression.getOutermostQualified(): UQualifiedReferenceExpression? {
    tailrec fun getOutermostQualified(current: UElement?, previous: UExpression): UQualifiedReferenceExpression? = when (current) {
        is UQualifiedReferenceExpression -> getOutermostQualified(current.parent, current)
        is UParenthesizedExpression -> getOutermostQualified(current.parent, previous)
        else -> if (previous is UQualifiedReferenceExpression) previous else null
    }

    return getOutermostQualified(this.parent, this)
}
