# CS4102 Spring 2022 -- Unit D Programming
#################################
# Collaboration Policy: You are encouraged to collaborate with up to 3 other
# students, but all work submitted must be your own independently written
# solution. List the computing ids of all of your collaborators in the comment
# at the top of your java or python file. Do not seek published or online
# solutions for any assignments. If you use any published or online resources
# (which may not include solutions) when completing this assignment, be sure to
# cite them. Do not submit a solution that you are unable to explain orally to a
# member of the course staff.
#################################
# Your Computing ID: ccp7gcp
# Collaborators: 
# Sources: Introduction to Algorithms, Cormen
#################################
import networkx


class TilingDino:
    def __init__(self):
        return

    # This is the method that should set off the computation
    # of tiling dino.  It takes as input a list lines of input
    # as strings.  You should parse that input, find a tiling,
    # and return a list of strings representing the tiling
    #
    # @return the list of strings representing the tiling
    def compute(self, lines):
        output = self.parse(lines)
        if output[0] == "impossible":
            return ["impossible"]
        G = output[0]
        blackPixels = int(output[1])
        output = self.maxFlow(G, blackPixels)
        if output[0] == "impossible":
            return ["impossible"]
        return output

    def parse(self, lines):
        # FIRST PARSE IMAGE INTO BIPARTITE GRAPH
        G = networkx.DiGraph()
        G.add_node("source")
        G.add_node("sink")
        r = len(lines)
        c = len(lines[0])

        # create a node for each black pixel
        blackPixels = 0
        numRed = 0
        numBlue = 0
        for y in range(r):
            for x in range(c):
                if lines[y][x] == "#":
                    blackPixels += 1
                    # if both even or both odd, color red
                    if (x % 2 == 0 and y % 2 == 0) or (x % 2 != 0 and y % 2 != 0):
                        G.add_node(str(x) + " " + str(y), color="red")
                        numRed += 1
                        G.add_edge("source", str(x) + " " + str(y), capacity=1)
                    # if one even and one odd, color blue
                    else:
                        G.add_node(str(x) + " " + str(y), color="blue")
                        numBlue += 1
                        G.add_edge(str(x) + " " + str(y), "sink", capacity=1)

        if blackPixels % 2 != 0:  # any graph with an odd length cycle cannot be bipartite
            return ["impossible"]
        if numRed != numBlue:  # a perfect bipartite match has equal sized left and right subsets
            return ["impossible"]

        # add edges between all adjacent pixels
        edges = 0
        for y in range(r):
            for x in range(c):
                if lines[y][x] == "#":
                    # if pixel on right is black, add edge
                    if x + 1 < c and lines[y][x + 1] == "#":
                        if (x % 2 == 0 and y % 2 == 0) or (x % 2 != 0 and y % 2 != 0):  # if currPixel is red
                            G.add_edge(str(x) + " " + str(y), str(x + 1) + " " + str(y), capacity=1)
                            edges += 1
                        else:  # else if currPixel is blue
                            G.add_edge(str(x + 1) + " " + str(y), str(x) + " " + str(y), capacity=1)
                            edges += 1
                    # if pixel below is black, add edge
                    if y + 1 < r and lines[y + 1][x] == "#":
                        if (x % 2 == 0 and y % 2 == 0) or (x % 2 != 0 and y % 2 != 0):  # if currPixel is red
                            G.add_edge(str(x) + " " + str(y), str(x) + " " + str(y + 1), capacity=1)
                            edges += 1
                        else:  # else if currPixel is blue
                            G.add_edge(str(x) + " " + str(y + 1), str(x) + " " + str(y), capacity=1)
                            edges += 1

        for n in G:  # all nodes in a perfect bipartite match have a matching edge
            if not G.in_edges(n) and not G.out_edges(n):
                return ["impossible"]

        return [G, blackPixels]

    def maxFlow(self, G, blackPixels):
        # NEXT COMPUTE MAXFLOW ON G
        flow_value, flow_dict = networkx.maximum_flow(G, "source", "sink")

        if flow_value != blackPixels // 2:  # check that maxflow equals V/2
            return ["impossible"]

        # FINALLY RETURN M AS ALL MIDDLE EDGES WITH FLOW 1
        # flow_dict contains each node in G
        # each node name is the key, the value is a dict list of outgoing edges
        tiled = []
        M = []
        for n in flow_dict:
            if n != "source" and n != "sink":
                currEdges = flow_dict[n]
                for e in currEdges:
                    if e != "source" and e != "sink" and currEdges[e] == 1 and e not in tiled and n not in tiled:
                        tiled.append(e)
                        tiled.append(n)
                        M.append(e + " " + n)
        return M
