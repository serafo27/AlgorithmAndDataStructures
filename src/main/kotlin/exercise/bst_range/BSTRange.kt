package exercise.bst_range

import tree.Edge
import tree.Node
import tree.Tree

class BSTRange {

  fun getNumberInRange(min: Int, max: Int, tree: Tree): Int {
    var numInRange = 0
    val queue = ArrayDeque(listOf(tree.root))

    while (queue.isNotEmpty()) {
      val node = queue.removeFirst()

      if(node.value in min..max)
        numInRange += 1

      queue.addAll(tree.getEdge(node)?.children.orEmpty())
    }

    return numInRange
  }

  fun getNumberInRangeFunctional(range: IntRange, tree: Tree): Int {
    return tree.edges.flatMap { it.children }.plus(tree.root)
      .fold(0) { acc, node -> if (range.contains(node.value)) acc.plus(1) else acc}
  }
}

fun main() {
  val min = 12
  val max = 20
  val nodes = listOf(15, 10, 25, 8, 12, 20, 30, 6, 9, 18, 22).map { Node(it) }
  val edges = listOf(
    Edge(nodes[0], listOf(nodes[1], nodes[2])),
    Edge(nodes[1], listOf(nodes[3], nodes[4])),
    Edge(nodes[2], listOf(nodes[5], nodes[6])),
    Edge(nodes[3], listOf(nodes[7], nodes[8])),
    Edge(nodes[5], listOf(nodes[9], nodes[10])),
  )
  val tree = Tree(nodes[0], edges)

  println(BSTRange().getNumberInRange(min, max, tree))
  println(BSTRange().getNumberInRangeFunctional((min .. max), tree))
}