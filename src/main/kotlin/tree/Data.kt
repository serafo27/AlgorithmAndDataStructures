package tree


data class Node(val value: Int)
data class Edge(val parent: Node, val children: List<Node>)

data class Tree(val root: Node, val edges: List<Edge>) {
  fun getEdge(node: Node): Edge?
    = edges.firstOrNull { it.parent == node }
}