package exercise.find_path_airport

data class Airport(val iata: String)
data class Route(val departure: Airport, val arrival: Airport)

class FindPathAirport {

  fun getPath(routes: List<Route>): List<Airport> {

    val totalAirports = routes.map { it.arrival }.union(routes.map { it.departure }).distinct().size
    val path = mutableListOf<Airport>()
    val visited = mutableListOf<Airport>()
    var seed = 0

    while(path.size < totalAirports) {

      path.clear()
      visited.clear()
      val randomStartingAirport = routes[seed].departure
      val stack = ArrayDeque(listOf(randomStartingAirport))

      while(stack.isNotEmpty()) {
        val airport = stack.removeFirst()
        visited.add(airport)
        path.add(airport)

        val neighbours = getNeighbours(airport, routes)

        if(neighbours.isNotEmpty())
          neighbours
            .filter { !path.contains(it) && !visited.contains(it) && !stack.contains(it) }
            .forEach { stack.addFirst(it) }
        else if (path.size < totalAirports)
          path.remove(airport)

      }

      seed += 1
    }

    return path
  }

  private fun getNeighbours(airport: Airport, routes: List<Route>): List<Airport>
     = routes.filter { it.departure.iata == airport.iata }.map { it.arrival }
}

fun main() {

  val itinerariesInput = """HKG -> DXB
                       FRA -> HKG
                       DEL -> FRA"""

  val itineraries = itinerariesInput
    .split("\n").map { it.trimIndent().trim() }
    .map { it.split(" -> ") }
    .map { Route(Airport(it[0]), Airport(it[1])) }

  println(FindPathAirport().getPath(itineraries))
}

