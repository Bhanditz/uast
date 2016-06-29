package org.jetbrains.uast.java

import com.intellij.psi.PsiJavaFile
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UastLanguagePlugin

class JavaUFile(override val psi: PsiJavaFile, override val languagePlugin: UastLanguagePlugin) : UFile {
    override val packageName: String
        get() = psi.packageName
    
    override val imports by lz {
        psi.importList?.allImportStatements?.map { JavaUImportStatement(it, this) } ?: listOf() 
    }
    override val classes by lz { psi.classes.map { JavaUClass.create(it, this) } }

    override fun equals(other: Any?) = (other as? JavaUFile)?.psi == psi
}