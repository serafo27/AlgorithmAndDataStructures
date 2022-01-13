package graph

class UndirectedGraph {
  data class Position(val x: Int, val y: Int)
  data class Node(val label: String, val position: Position? = null)
  data class Edge(val from: Node, val to: Node, val weight: Int = 1)

  data class Graph(val nodes: List<Node>, val edges: List<Edge>) {

    fun getNeighbours(node: Node): List<Node>
      = edges.filter{ it.from == node }.map{ it.to }

    fun getEdgeCost(first: Node, second: Node): Int
      = edges.first{ it.from == first && it.to == second || it.from == second && it.to == first }.weight
  }
}