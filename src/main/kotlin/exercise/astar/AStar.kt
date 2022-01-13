package exercise.astar

import graph.UndirectedGraph.*
import kotlin.math.abs

class AStar {

  fun findPathBetween(start: Node, end: Node, graph: Graph): List<String> {

    val openNodes = mutableListOf(start)
    val closedNodes = mutableSetOf<Node>()

    val parentNode = mutableMapOf<Node, Node?>(start to null)
    val gMap = mutableMapOf(start to 0)
    val fMap = mutableMapOf(start to getDistance(start, end))

    var foundPath = false

    while(!foundPath && openNodes.isNotEmpty()) {
      val currentNode = getNodeWithLeastFValue(openNodes, fMap)
      openNodes.remove(currentNode)

      val notClosedNeighbours = graph.getNeighbours(currentNode)
                                      .filter { !closedNodes.contains(it) }

      notClosedNeighbours.forEach {

        val h = getDistance(it, end)
        val g = gMap[currentNode]!! + graph.getEdgeCost(it, currentNode)
        val f = h + g

        if(!fMap.contains(it) || f < fMap[it]!! ) {
          gMap[it] = g
          fMap[it] = f
          parentNode[it] = currentNode
        }

        if(it == end)
          foundPath = true
      }

      if(!foundPath)
        openNodes.addAll(notClosedNeighbours)
    }

    val path = mutableListOf(end)
    while (!path.contains(start))
      path.add(parentNode[path.last()]!!)

    return path.map { it.label }.reversed()
  }

  private fun getNodeWithLeastFValue(openNodes: List<Node>, fMap: Map<Node, Int>): Node
    = fMap.filter { openNodes.contains(it.key) }
      .minByOrNull { it.value }!!
      .key

  private fun getDistance(currentNode: Node, end: Node): Int
    = abs(currentNode.position!!.x - end.position!!.x) +
      abs(currentNode.position.y - end.position.y)
}


fun main() {

  val nodes = listOf(
    Node("A", Position(0, 8)),
    Node("B", Position(2, 11)),
    Node("C", Position(3, 8)),
    Node("D", Position(4, 12)),
    Node("H", Position(7, 6)),
    Node("I", Position(9, 5)),
    Node("J", Position(12, 4)),
    Node("N", Position(14, 3)),
    Node("K", Position(12, 8))
  )

  val edges = listOf(
    Edge(nodes[0], nodes[1], 5),
    Edge(nodes[0], nodes[2], 5),
    Edge(nodes[1], nodes[2], 4),
    Edge(nodes[1], nodes[3], 3),
    Edge(nodes[3], nodes[8], 16),
    Edge(nodes[3], nodes[4], 11),
    Edge(nodes[2], nodes[4], 8),
    Edge(nodes[4], nodes[5], 3),
    Edge(nodes[5], nodes[6], 4),
    Edge(nodes[6], nodes[7], 3),
    Edge(nodes[8], nodes[7], 7)
  )

  val graph = Graph(nodes, edges)

  val path = AStar().findPathBetween(nodes[0], nodes[7], graph)

  println(path)
}