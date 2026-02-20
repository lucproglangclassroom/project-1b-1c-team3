package edu.luc.cs.cs371.echo
package impl

class DoubleEcho extends main.Echo:
  // use a single space between repetitions so that empty arguments
  // render as a single space, matching test expectations
  def echo(msg: String) = msg + " " + msg
end DoubleEcho
