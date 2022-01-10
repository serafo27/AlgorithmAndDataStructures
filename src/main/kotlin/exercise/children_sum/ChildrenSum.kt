package exercise.children_sum

import tree.Edge
import tree.Node
import tree.Tree

class ChildrenSum {

  fun isChildrenSumTree(tree: Tree): Boolean {

    val queue = ArrayDeque(listOf(tree.root))
    while (queue.isNotEmpty()) {
      val node = queue.removeFirst()
      val childrenSum = tree.getEdge(node)?.children.orEmpty().sumOf{ it.value }

      if (childrenSum != node.value)
        return false
    }
    return true
  }
}

fun main() {

  val nodes = listOf(25, 12, 13, 7, 5, 6, 7).map { Node(it) }
  val edges = listOf(
    Edge(nodes[0], listOf(nodes[1], nodes[2])),
    Edge(nodes[1], listOf(nodes[3], nodes[4])),
    Edge(nodes[2], listOf(nodes[5], nodes[6]))
  )

  val tree = Tree(nodes[0], edges)

  println(ChildrenSum().isChildrenSumTree(tree))
}