class TimePrinter implements Runnable {
  void run() {
    (1..10).each {
      println Calendar.getInstance().getTime()
      Thread.sleep(1000)
    }
  }
}