
def scriptName = 'external script'
name = scriptName

println "Hello from ${name} on ${currentDate}!"

def getCurrentDate() {
  new Date().format('yyyy-MM-dd')
}