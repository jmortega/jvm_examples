def inputFile = new File("input.txt")
def outputFile = new File("output.txt")

inputFile.withReader { reader ->
  outputFile.withWriter { writer ->
    reader.transformLine(writer) { line ->
      line.replaceAll('\t', '  ')
    }
  }
}

println outputFile.text

inputFile.withReader { reader ->
  reader.transformLine(outputFile.newWriter()) { line ->
    line.replaceAll('\t', '  ')
  }
}

println outputFile.text

outputFile.text = inputFile.text.replaceAll('\t', '  ')

println outputFile.text

outputFile.withWriter { Writer writer ->
  inputFile.withReader { Reader reader ->
    reader.transformChar(writer) { String chr ->
      chr == '\t' ? '  ' : chr
    }
  }
}

println outputFile.text