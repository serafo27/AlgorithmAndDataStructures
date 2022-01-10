package tree

import java.util.*

class BFS {

  fun print(root: Node, edges: List<Edge>) {

    val queue = ArrayDeque(listOf(root))
    while(queue.isNotEmpty()) {
      val node: Node = queue.poll()

      print("${node.value} ")

      queue.addAll(edges.find{ it.parent == node }?.children.orEmpty())
    }
  }
}

fun main() {

  val nodes = (0..10).map { Node(it) }
  val edges = nodes.mapIndexed { i, node ->
    Edge(node, listOfNotNull(nodes.getOrNull((2*i)+1), nodes.getOrNull((2*i)+2)))
  }.filter { it.children.isNotEmpty() }

  BFS().print(nodes.first(), edges)
}