@Grab('com.github.fge:json-schema-validator:2.0.1')
package org.groovy.cookbook

import com.github.fge.jsonschema.main.JsonSchemaFactory
import com.github.fge.jsonschema.report.ProcessingReport
import com.github.fge.jsonschema.util.JsonLoader
import com.github.fge.jsonschema.main.JsonSchema


def factory = JsonSchemaFactory.byDefault()

def metadata = JsonLoader.fromFile(new File('vehicle_schema.json'))
def data = JsonLoader.fromFile(new File('vehicle.json'))

def schema = factory.getJsonSchema(metadata)


ProcessingReport report = schema.validate(data)

final boolean success = report.isSuccess()
println("Validation " + (success ? "succeeded" : "failed"))

if (!success) {
  println("---- BEGIN REPORT ----")
  report.each { message -> println message}
  println("---- END REPORT ----")
}