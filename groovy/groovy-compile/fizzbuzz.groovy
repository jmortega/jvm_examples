1.upto(100) {
  ans = ''
  if (it % 3 == 0) { ans ='Fizz' }
  if (it % 5 == 0) { ans += 'Buzz' }
  if (ans == '') { 
    println it 
  } else { 
    println ans 
  }
}
