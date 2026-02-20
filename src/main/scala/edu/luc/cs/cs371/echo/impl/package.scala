package edu.luc.cs.cs371.echo.impl

import edu.luc.cs.cs371.echo.main.Echo

// Alias the name `List` to our `SafeList` for all code in this package.
// Because the package object is automatically imported into every source
// file in `edu.luc.cs.cs371.echo.impl`, references to `List` within
// tests will resolve to this alias rather than the `scala.Predef` alias.
//
// The alias is intentionally narrow – `SafeList` only works for elements
// that are subtypes of `Echo` – but that is sufficient for the tests.


type List[A <: Echo] = SafeList[A]
