package org.jetbrains.uast.evaluation

import com.intellij.lang.Language
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.*
import org.jetbrains.uast.values.UValue

interface UEvaluatorExtension {

    companion object {
        val EXTENSION_POINT_NAME: ExtensionPointName<UEvaluatorExtension> =
                ExtensionPointName.create<UEvaluatorExtension>("org.jetbrains.uast.evaluation.UEvaluatorExtension")
    }

    infix fun UValue.to(state: UEvaluationState) = UEvaluationInfo(this, state)

    val language: Language

    fun evaluatePostfix(
            operator: UastPostfixOperator,
            operandValue: UValue,
            state: UEvaluationState
    ): UEvaluationInfo

    fun evaluatePrefix(
            operator: UastPrefixOperator,
            operandValue: UValue,
            state: UEvaluationState
    ): UEvaluationInfo

    fun evaluateBinary(
            binaryExpression: UBinaryExpression,
            leftValue: UValue,
            rightValue: UValue,
            state: UEvaluationState
    ): UEvaluationInfo

    fun evaluateQualified(
            accessType: UastQualifiedExpressionAccessType,
            receiverInfo: UEvaluationInfo,
            selectorInfo: UEvaluationInfo
    ): UEvaluationInfo

    fun evaluateMethodCall(
            target: PsiMethod,
            argumentValues: List<UValue>,
            state: UEvaluationState
    ): UEvaluationInfo

    fun evaluateVariable(
            variable: UVariable,
            state: UEvaluationState
    ): UEvaluationInfo
}