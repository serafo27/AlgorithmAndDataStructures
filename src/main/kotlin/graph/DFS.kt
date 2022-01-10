package graph

import graph.UndirectedGraph.Graph
import graph.UndirectedGraph.Edge
import graph.UndirectedGraph.Node
import java.util.*

class DFS {

  fun print(root: Node, graph: Graph) {

    val checkedList = hashSetOf<Node>()
    val stack = ArrayDeque(listOf(root))

    while(stack.isNotEmpty()) {
      val node: Node = stack.pop()
      checkedList.add(node)

      print("${node.label} ")

      val neighbours = graph.getNeighbours(node)
                            .filter { !checkedList.contains(it) && !stack.contains(it) }

      neighbours.forEach { stack.push(it) }
    }
  }
}

fun main() {

  val nodes = (0..5).map { Node(it.toString()) }
  val edges = listOf(
    Edge(nodes[0], nodes[1]),
    Edge(nodes[0], nodes[2]),
    Edge(nodes[1], nodes[3]),
    Edge(nodes[1], nodes[4]),
    Edge(nodes[2], nodes[3]),
    Edge(nodes[2], nodes[4]),
    Edge(nodes[3], nodes[4]),
    Edge(nodes[3], nodes[5]),
    Edge(nodes[4], nodes[5])
  )

  val graph = Graph(nodes, edges)

  DFS().print(nodes.first(), graph)
}