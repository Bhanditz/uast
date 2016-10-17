package org.jetbrains.uast.values

import com.intellij.psi.PsiEnumConstant
import com.intellij.psi.PsiType
import org.jetbrains.uast.UResolvable
import org.jetbrains.uast.UVariable

sealed class UValue : UOperand {

    // Constants

    interface Constant {
        val value: Any?
    }

    abstract class AbstractConstant(override val value: Any?) : UValue(), Constant {
        override fun equals(other: Any?) = other is AbstractConstant && value == other.value

        override fun hashCode() = value?.hashCode() ?: 0
    }

    class NumericInt(override val value: Long, val bytes: Int = 8) : AbstractConstant(value) {
        override fun plus(other: UValue) = when (other) {
            is NumericInt -> NumericInt(value + other.value, Math.max(bytes, other.bytes))
            is NumericFloat -> other + this
            else -> super.plus(other)
        }

        override fun unaryMinus() = NumericInt(-value, bytes)
    }

    class NumericFloat(override val value: Double) : AbstractConstant(value) {
        override fun plus(other: UValue) = when (other) {
            is NumericInt -> NumericFloat(value + other.value)
            is NumericFloat -> NumericFloat(value + other.value)
            else -> super.plus(other)
        }

        override fun unaryMinus() = NumericFloat(-value)
    }

    class Bool(override val value: Boolean) : AbstractConstant(value)

    class EnumEntry(override val value: PsiEnumConstant) : AbstractConstant(value)

    class ClassLiteral(override val value: PsiType) : AbstractConstant(value)

    object Null : AbstractConstant(null)

    // Dependencies and dependents

    interface Dependency

    open class Wrapped(
            val value: UValue,
            override val dependencies: List<Dependency> = emptyList()
    ) : UValue() {

        override fun unwrap() = value.unwrap()

        private fun wrapBinary(result: UValue, arg: UValue): Wrapped {
            val resultDependencies = dependencies + ((arg as? Wrapped)?.dependencies ?: emptyList())
            return Wrapped(result, resultDependencies)
        }

        override fun plus(other: UValue) = wrapBinary(unwrap() + other.unwrap(), other)

        override fun minus(other: UValue) = wrapBinary(unwrap() - other.unwrap(), other)

        override fun unaryMinus() = Wrapped(-value, dependencies)

        override fun merge(other: UValue) = when (other) {
            this -> this
            is Variable -> other.merge(this)
            value -> this
            else -> Phi(this, other)
        }
    }

    // Value of some (possibly evaluable) variable
    class Variable(
            val variable: UVariable,
            value: UValue,
            dependencies: List<Dependency> = emptyList()
    ) : Wrapped(value, dependencies), Dependency {

        override fun merge(other: UValue): UValue = when (other) {
            is Wrapped -> merge(other.value)
            else -> super.merge(other)
        }
    }

    // Value of something resolvable (e.g. call or property access)
    // that we cannot or do not want to evaluate
    class External(val resolvable: UResolvable) : UValue(), Dependency {
        override fun equals(other: Any?) = other is External && resolvable == other.resolvable

        override fun hashCode() = resolvable.hashCode()
    }

    class Phi(val values: List<UValue>): UValue() {

        constructor(vararg values: UValue) : this(values.toList())

        override val dependencies: List<Dependency> = values.flatMap { it.dependencies }

        override fun equals(other: Any?) = other is Phi && values == other.values

        override fun hashCode() = values.hashCode()
    }

    // Miscellaneous

    // Something that never can be created
    object Nothing : UValue()

    // Something with value that cannot be evaluated
    object Undetermined : UValue()

    // Methods

    override operator fun plus(other: UValue): UValue = if (other is Wrapped) other + this else Undetermined

    override operator fun minus(other: UValue): UValue = this + (-other)

    override fun unaryMinus(): UValue = Undetermined

    open fun unwrap(): UValue = this

    open fun merge(other: UValue): UValue = when (other) {
        this -> this
        is Variable -> other.merge(this)
        else -> Phi(this, other)
    }

    open val dependencies: List<Dependency>
        get() = emptyList()
}