def matcher = "Groovy cook book is about Groovy" =~ /(.oo.)\s/
    
println "<${matcher[0][0]}>"
println "<${matcher[0][1]}>"
println "<${matcher[1][0]}>"
println "<${matcher[1][1]}>"

println matcher

matcher.each { match ->
  match.each { group ->
    println "<$group>"
  }
}

def input = "The Groovy Cook Book contains Groovy recipes"
println input.replaceAll(/\b\w*?oo\w*?\b/) { match ->
  match.toUpperCase()
}
