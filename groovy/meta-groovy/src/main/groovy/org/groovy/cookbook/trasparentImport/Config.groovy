//package org.groovy.cookbook.tranparentImport

import org.codehaus.groovy.control.customizers.ImportCustomizer

def imports = new ImportCustomizer()
//imports.addImports('org.some.*')
imports.addStaticStar('java.lang.Math')
imports.addStarImports('org.groovy.cookbook.domain')
configuration.addCompilationCustomizers(imports)

