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
package org.jetbrains.uast.visitor

import org.jetbrains.uast.*

interface UastVisitor {
    open fun visitElement(node: UElement): Boolean

    fun visitFile(node: UFile) = visitElement(node)
    fun visitImportStatement(node: UImportStatement) = visitElement(node)
    fun visitAnnotation(node: UAnnotation) = visitElement(node)
    fun visitCatchClause(node: UCatchClause) = visitElement(node)
    fun visitType(node: UType) = visitElement(node)

    // Declarations
    fun visitClass(node: UClass) = visitElement(node)
    fun visitFunction(node: UFunction) = visitElement(node)
    fun visitVariable(node: UVariable) = visitElement(node)

    // Expressions
    fun visitLabeledExpression(node: ULabeledExpression) = visitElement(node)
    fun visitDeclarationsExpression(node: UDeclarationsExpression) = visitElement(node)
    fun visitBlockExpression(node: UBlockExpression) = visitElement(node)
    fun visitQualifiedExpression(node: UQualifiedExpression) = visitElement(node)
    fun visitSimpleReferenceExpression(node: USimpleReferenceExpression) = visitElement(node)
    fun visitCallExpression(node: UCallExpression) = visitElement(node)
    fun visitBinaryExpression(node: UBinaryExpression) = visitElement(node)
    fun visitBinaryExpressionWithType(node: UBinaryExpressionWithType) = visitElement(node)
    fun visitParenthesizedExpression(node: UParenthesizedExpression) = visitElement(node)
    fun visitUnaryExpression(node: UUnaryExpression) = visitElement(node)
    fun visitPrefixExpression(node: UPrefixExpression) = visitElement(node)
    fun visitPostfixExpression(node: UPostfixExpression) = visitElement(node)
    fun visitSpecialExpressionList(node: USpecialExpressionList) = visitElement(node)
    fun visitIfExpression(node: UIfExpression) = visitElement(node)
    fun visitSwitchExpression(node: USwitchExpression) = visitElement(node)
    fun visitSwitchClauseExpression(node: USwitchClauseExpression) = visitElement(node)
    fun visitWhileExpression(node: UWhileExpression) = visitElement(node)
    fun visitDoWhileExpression(node: UDoWhileExpression) = visitElement(node)
    fun visitForExpression(node: UForExpression) = visitElement(node)
    fun visitForEachExpression(node: UForEachExpression) = visitElement(node)
    fun visitTryExpression(node: UTryExpression) = visitElement(node)
    fun visitLiteralExpression(node: ULiteralExpression) = visitElement(node)
    fun visitThisExpression(node: UThisExpression) = visitElement(node)
    fun visitSuperExpression(node: USuperExpression) = visitElement(node)
    fun visitReturnExpression(node: UReturnExpression) = visitElement(node)
    fun visitBreakExpression(node: UBreakExpression) = visitElement(node)
    fun visitContinueExpression(node: UContinueExpression) = visitElement(node)
    fun visitThrowExpression(node: UThrowExpression) = visitElement(node)
    fun visitArrayAccessExpression(node: UArrayAccessExpression) = visitElement(node)
    fun visitCallableReferenceExpression(node: UCallableReferenceExpression) = visitElement(node)
    fun visitClassLiteralExpression(node: UClassLiteralExpression) = visitElement(node)
    fun visitLambdaExpression(node: ULambdaExpression) = visitElement(node)
    fun visitObjectLiteralExpression(node: UObjectLiteralExpression) = visitElement(node)

    // After

    fun afterVisitElement(node: UElement) {}

    fun afterVisitFile(node: UFile) { afterVisitElement(node) }
    fun afterVisitImportStatement(node: UImportStatement) { afterVisitElement(node) }
    fun afterVisitAnnotation(node: UAnnotation) { afterVisitElement(node) }
    fun afterVisitCatchClause(node: UCatchClause) { afterVisitElement(node) }
    fun afterVisitType(node: UType) { afterVisitElement(node) }

    // Declarations
    fun afterVisitClass(node: UClass) { afterVisitElement(node) }
    fun afterVisitFunction(node: UFunction) { afterVisitElement(node) }
    fun afterVisitVariable(node: UVariable) { afterVisitElement(node) }

    // Expressions
    fun afterVisitLabeledExpression(node: ULabeledExpression) { afterVisitElement(node) }
    fun afterVisitDeclarationsExpression(node: UDeclarationsExpression) { afterVisitElement(node) }
    fun afterVisitBlockExpression(node: UBlockExpression) { afterVisitElement(node) }
    fun afterVisitQualifiedExpression(node: UQualifiedExpression) { afterVisitElement(node) }
    fun afterVisitSimpleReferenceExpression(node: USimpleReferenceExpression) { afterVisitElement(node) }
    fun afterVisitCallExpression(node: UCallExpression) { afterVisitElement(node) }
    fun afterVisitBinaryExpression(node: UBinaryExpression) { afterVisitElement(node) }
    fun afterVisitBinaryExpressionWithType(node: UBinaryExpressionWithType) { afterVisitElement(node) }
    fun afterVisitParenthesizedExpression(node: UParenthesizedExpression) { afterVisitElement(node) }
    fun afterVisitUnaryExpression(node: UUnaryExpression) { afterVisitElement(node) }
    fun afterVisitPrefixExpression(node: UPrefixExpression) { afterVisitElement(node) }
    fun afterVisitPostfixExpression(node: UPostfixExpression) { afterVisitElement(node) }
    fun afterVisitSpecialExpressionList(node: USpecialExpressionList) { afterVisitElement(node) }
    fun afterVisitIfExpression(node: UIfExpression) { afterVisitElement(node) }
    fun afterVisitSwitchExpression(node: USwitchExpression) { afterVisitElement(node) }
    fun afterVisitSwitchClauseExpression(node: USwitchClauseExpression) { afterVisitElement(node) }
    fun afterVisitWhileExpression(node: UWhileExpression) { afterVisitElement(node) }
    fun afterVisitDoWhileExpression(node: UDoWhileExpression) { afterVisitElement(node) }
    fun afterVisitForExpression(node: UForExpression) { afterVisitElement(node) }
    fun afterVisitForEachExpression(node: UForEachExpression) { afterVisitElement(node) }
    fun afterVisitTryExpression(node: UTryExpression) { afterVisitElement(node) }
    fun afterVisitLiteralExpression(node: ULiteralExpression) { afterVisitElement(node) }
    fun afterVisitThisExpression(node: UThisExpression) { afterVisitElement(node) }
    fun afterVisitSuperExpression(node: USuperExpression) { afterVisitElement(node) }
    fun afterVisitReturnExpression(node: UReturnExpression) { afterVisitElement(node) }
    fun afterVisitBreakExpression(node: UBreakExpression) { afterVisitElement(node) }
    fun afterVisitContinueExpression(node: UContinueExpression) { afterVisitElement(node) }
    fun afterVisitThrowExpression(node: UThrowExpression) { afterVisitElement(node) }
    fun afterVisitArrayAccessExpression(node: UArrayAccessExpression) { afterVisitElement(node) }
    fun afterVisitCallableReferenceExpression(node: UCallableReferenceExpression) { afterVisitElement(node) }
    fun afterVisitClassLiteralExpression(node: UClassLiteralExpression) { afterVisitElement(node) }
    fun afterVisitLambdaExpression(node: ULambdaExpression) { afterVisitElement(node) }
    fun afterVisitObjectLiteralExpression(node: UObjectLiteralExpression) { afterVisitElement(node) }
}

abstract class AbstractUastVisitor : UastVisitor {
    override fun visitElement(node: UElement): Boolean = false
}

object EmptyUastVisitor : AbstractUastVisitor()