package org.groovy.cookbook.dsl.log


class LogReportDslEngine {

  def void process(Closure cl) {

    Configuration config = new Configuration()
    cl.delegate = config
    cl.resolveStrategy = Closure.DELEGATE_FIRST
    cl()

    config.sources.values().each { Source source ->
      config.reports.values().each { Report report ->

        // Collect report data.
        def reportData = [:]
        source.files.each { File sourceFile ->
          sourceFile.eachLine { String line ->

            // Match the data line.
            if (line =~ config.format) {
              def fields = (line =~ config.format)[0]

              // Map column names
              def fieldMap = fields.collect {}

              // Generate group key, for which to aggregate the data.
              def group = report.groupByColumns
                  .collect { fields[config.columnIndexes[it]] }
                  .join(", ")

              // Create empty group record if it does not exist.
              reportData[group] = reportData[group] ?: emptyRecord

              // Calculate report values for given key.
              def g = reportData[group]
              report.avgColumns.each { String column ->
                g['avg'][column] = g['avg'][column] ?: 0
                g['avg'][column] += fields[config.columnIndexes[column]].toDouble()
              }
              report.sumColumns.each { String column ->
                g['sum'][column] = g['sum'][column] ?: 0
                g['sum'][column] += fields[config.columnIndexes[column]].toDouble()
              }
              g['count'] += 1


            }
          }
        }

        // Produce report output.
        def reportFile = new File("${source.name}_${report.name}.report")
        reportFile.text = ''
        reportData.each { key, data ->
          reportFile << "Report for $key\n"
          reportFile << "  Total records: ${data['count']}\n"
          data['avg'].each { column, value ->
            reportFile << "  Average of ${column} is ${value / data['count']}\n"
          }
          data['sum'].each { column, value ->
            reportFile << "  Sum of ${column} is ${value}\n"
          }
        }


      }
    }
  }

  def getEmptyRecord() {
    [count: 0, avg: [:], sum: [:]]
  }
}
