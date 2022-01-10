package tree

import java.util.*

class DFS {

  fun print(root: Node, edges: List<Edge>) {

    val stack = ArrayDeque(listOf(root))

    while(stack.isNotEmpty()) {
      val node: Node = stack.pop()
      print(node.value)

      edges.find{ it.parent == node }?.children.orEmpty().forEach { stack.push(it) }
    }
    println()
  }

  fun printRecursive(root: Node, edges: List<Edge>) {
    print(root.value)

    edges.find { it.parent == root }?.children?.forEach {
      printRecursive(it, edges)
    }
  }
}

fun main() {

  val nodes = (0..9).map { Node(it) }
  val edges = nodes.mapIndexed { i, node ->
    Edge(node, listOfNotNull(nodes.getOrNull((2*i)+1), nodes.getOrNull((2*i)+2)))
  }.filter { it.children.isNotEmpty() }

  DFS().print(nodes.first(), edges)
  DFS().printRecursive(nodes.first(), edges)
}