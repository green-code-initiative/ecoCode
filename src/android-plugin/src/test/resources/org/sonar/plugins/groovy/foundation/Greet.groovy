class Greet {
  def name
  Greet(who) { name = who }
  def salute() { println "Hello $name and $name!" }
}

/**
 * Javadoc style
 */
@groovy.beans.Bindable
class Cool {
  double x = 1.4 // Comment
}
