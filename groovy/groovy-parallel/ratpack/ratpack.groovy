
get("/") {
	text "Welcome to the world criminal database"
}
get("/us") {
  Thread.sleep(rnd(10000L))
	render "us.json"
}

get("/canada") {
  Thread.sleep(rnd(5000L))
  render "can.json"
}

get("/germany") {
  //response.setContentType("application/json")
  Thread.sleep(rnd(7000L))
  render "de.json"

}

static rnd(long maxMilliseconds) {
  Math.abs(new Random().nextInt() % maxMilliseconds + 1000)
}