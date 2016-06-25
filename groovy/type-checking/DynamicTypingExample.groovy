// Dynamic Typing Test
// use groovyc to verify that the compiler catches the errors


boolean trueOrFalse() {
  true
}

@groovy.transform.TypeChecked
void test() {
  int x = 1 + test()
}

@groovy.transform.TypeChecked
int test() {
  return "fired!"
}

@groovy.transform.TypeChecked
def test() {

  List myList = []
  println myList.class.name
  myList = "I'm a String"
  println myList.class.name
}

@groovy.transform.TypeChecked
def gstring() {
  def name = 'hello'
  print "hello, this is $naame"
}

def test() {

  def name = 'hello'
  print naame

}

def upperInteger(myInteger) {
  println myInteger.toUpperCase()  
}

def moreCollections() {
  int[] array = new int[2]
  array[0] = 100
  array[1] = "100"
}

void typeInference() {

  def l = ['a','b']
  println l[0].toUpperCase()

}

@groovy.transform.TypeChecked
def test() {
  List<Integer> aList = ['a','b']
  println aList
}


@groovy.transform.TypeChecked
class Sample {
  def test() {
    ['one','two','three'].collect {println it.toUpperCase()}
  }
}

// ---- SCRIPT MAIN BODY

new Sample().test()
