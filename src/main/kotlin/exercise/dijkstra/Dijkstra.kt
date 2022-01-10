package exercise.dijkstra

import graph.UndirectedGraph
import graph.UndirectedGraph.Edge
import graph.UndirectedGraph.Node

class Dijkstra {

  fun findShortestPathBetween(start: Node, end: Node, graph: UndirectedGraph.Graph): List<Node> {

    val distanceFromStart = ShortestDistanceTable(start, graph.nodes)

    val visited = mutableSetOf<Node>()
    val unvisited = graph.nodes.toMutableSet()

    while(unvisited.isNotEmpty()) {

      val currentNode = distanceFromStart.getNearestUnvisitedNodeFromStart(unvisited)
      unvisited.remove(currentNode)

      val unvisitedNeighbours = graph.getNeighbours(currentNode).intersect(unvisited).toList()
      val distances = unvisitedNeighbours.associate {
        val edgeCost = graph.getEdgeCost(currentNode, it)
        it.label to Mem(distanceFromStart.getCost(currentNode.label) + edgeCost, currentNode)
      }

      distances
        .filter { it.value.cost < distanceFromStart.getCost(it.key) }
        .forEach { distanceFromStart.updateMem(it.key, it.value) }

      visited.add(currentNode)
    }

    val path = ArrayDeque(listOf(end))
    while(!path.contains(start))
      path.addFirst(distanceFromStart.getPreviousNode(path.first().label))

    return path
  }

}

private data class Mem(val cost: Int, val previousNode: Node?)

private class ShortestDistanceTable(start: Node, nodes: List<Node>) {
  private val map: MutableMap<String, Mem>

  init {
    map = nodes.associate { it.label to Mem(Int.MAX_VALUE, null) }
      .plus(start.label to Mem(0, null))
      .toMutableMap()
  }

  fun getCost(label: String)
      = map[label]!!.cost

  fun getPreviousNode(label: String)
      = map[label]!!.previousNode!!

  fun updateMem(label: String, newMem: Mem) {
    map[label] = newMem
  }

  fun getNearestUnvisitedNodeFromStart(unvisited: Set<Node>): Node
    = map.entries
    .filter { unvisited.contains(Node(it.key)) }
    .minByOrNull { it.value.cost }!!.key
    .let { Node(it) }

}

fun main() {

  val dijkstra = Dijkstra()

  val nodes: Map<String, Node> = ('A'..'E').associate { it.toString() to Node(it.toString()) }
  val edges = listOf(
    Edge(nodes["A"]!!, nodes["B"]!!, 6),
    Edge(nodes["A"]!!, nodes["D"]!!, 1),
    Edge(nodes["D"]!!, nodes["B"]!!, 2),
    Edge(nodes["B"]!!, nodes["E"]!!, 2),
    Edge(nodes["D"]!!, nodes["E"]!!, 1),
    Edge(nodes["B"]!!, nodes["C"]!!, 5),
    Edge(nodes["E"]!!, nodes["C"]!!, 5),
  )

  val graph = UndirectedGraph.Graph(nodes.values.toList(), edges)

  println(dijkstra.findShortestPathBetween(nodes["A"]!!, nodes["C"]!!, graph))
}
