
file = new File("output.txt")


test {
  
	file.text = 'Just a text'
 
}

test {

	file.text = '''What's in a name? That which we call a rose
	By any other name would smell as sweet.'''

}


test {
  
	file.bytes = [ 65, 66, 67, 68 ] as byte[]
  
}
  

test {
  
  file.withWriter { Writer writer ->
    writer << "What's in a name? That which we call a rose\n"
    writer << "By any other name would smell as sweet."
  }
  
}


test {
  
  file.write("To be or not to be")
  
}


test {
  
  file.withOutputStream { OutputStream stream ->
    stream << "What's in a name? That which we call a rose\n"
    stream << "By any other name would smell as sweet."
  }

  
}


def test(Closure cl) {
  cl()
  println file.text
}