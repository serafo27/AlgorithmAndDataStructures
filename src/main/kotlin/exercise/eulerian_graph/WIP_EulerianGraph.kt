package exercise.eulerian_graph

private typealias IntEdge = Edge<Int>

class EulerianGraph {

  fun isEulerian(graph: Graph<Int>): EulerianData {

    var incrementalSeed = 0
    val path = mutableListOf<IntEdge>()
    val totalEdges = graph.edges.size

    while (incrementalSeed < totalEdges || path.size < totalEdges) {
      path.clear()
      val root = graph.edges[incrementalSeed]
      val stack = ArrayDeque(listOf(root))
//      val visitedEdges = mutableListOf<IntEdge>()

      while (stack.isNotEmpty()) {
        val edge = stack.removeFirst()

//        visitedEdges.add(edge)
        path.add(edge)

        val neighbours = graph.getNeighbours(edge)
        val validNeighbours = neighbours.filter {  !path.contains(it) && !stack.contains(it) }
        if(validNeighbours.isNotEmpty())
          validNeighbours.forEach { stack.addFirst(it) }
        else if(path.size < totalEdges)
          while (path[path.size-1].to != stack.first().from)
            path.removeLast()
      }

      incrementalSeed += 1
    }

    return EulerianData(path.size == totalEdges, path)
  }
}

fun main() {

  val edges = listOf(
    Edge(Node(0), Node(1)),
    Edge(Node(1), Node(2)),
    Edge(Node(2), Node(3)),
    Edge(Node(3), Node(0)),
    Edge(Node(0), Node(5)),
    Edge(Node(5), Node(4)),
    Edge(Node(4), Node(3)),
    Edge(Node(3), Node(1)),
    Edge(Node(1), Node(4)),
  )

  val nodes = edges.map { it.from }.union(edges.map { it.to }).distinct()
  val graph = Graph(nodes, edges)

  val isEulerian = EulerianGraph().isEulerian(graph)

  println(isEulerian)
}

data class EulerianData(val isEulerian: Boolean, val path: List<IntEdge>)

data class Node<T>(val value: T)
data class Edge<T>(val from: Node<T>, val to: Node<T>)

data class Graph<T>(val nodes: List<Node<T>>, val edges: List<Edge<T>>) {
  fun getNeighbours(edge: Edge<T>): List<Edge<T>>
    = edges.filter { it.from == edge.to }.map { it }
}