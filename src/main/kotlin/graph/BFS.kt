package graph

import graph.UndirectedGraph.Graph
import graph.UndirectedGraph.Edge
import graph.UndirectedGraph.Node
import java.util.*

class BFS {

  fun print(root: Node, graph: Graph) {

    val checkedList = hashSetOf<Node>()
    val queue = ArrayDeque(listOf(root))

    while(queue.isNotEmpty()) {
      val node: Node = queue.poll()
      checkedList.add(node)

      print("${node.label} ")

      val neighbours = graph.getNeighbours(node)
                            .filter { !checkedList.contains(it) && !queue.contains(it) }

      queue.addAll(neighbours)
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

  val graph = UndirectedGraph.Graph(nodes, edges)

  BFS().print(nodes.first(), graph)
}