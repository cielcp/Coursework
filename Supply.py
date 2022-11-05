# CS4102 Spring 2022 - Unit B Programming
#################################
# Collaboration Policy: You are encouraged to collaborate with up to 3 other
# students, but all work submitted must be your own independently written
# solution. List the computing ids of all of your collaborators in the
# comments at the top of each submitted file. Do not share written notes,
# documents (including Google docs, Overleaf docs, discussion notes, PDFs), or
# code. Do not seek published or online solutions, including pseudocode, for
# this assignment. If you use any published or online resources (which may not
# include solutions) when completing this assignment, be sure to cite them. Do
# not submit a solution that you are unable to explain orally to a member of
# the course staff. Any solutions that share similar text/code will be
# considered in breach of this policy. Please refer to the syllabus for a
# complete description of the collaboration policy.
#################################
# Your Computing ID: ccp7gcp
# Collaborators:
# Sources: Introduction to Algorithms, Cormen
#################################
class Node:
    def __init__(self, id, name, type):
        self.id = id
        self.name = name
        self.type = type


class DisjointSet:
    def __init__(self, n):
        self.disjointsets = []  # must use disjointsets variable name !
        for i in range(n):
            self.disjointsets.append(i)

    def find(self, i):  # implement find
        if self.disjointsets[i] == i:
            return i
        return self.find(self.disjointsets[i])

    def union(self, i, j):  # implement union
        label1 = self.find(i)
        label2 = self.find(j)
        self.disjointsets[label1] = label2


class Supply:
    def __init__(self):
        return

    # FIRST PARSE INPUT AND CREATE GRAPH

    # N = the first element in first line of code = number of nodes in G
    # the next N lines are in nodeName, nodeType order
    # L = the second element in the first line of code = number of links in G
    # the next L lines are in u, v, cost order
    # make sure to check that links are valid before considering them

    # Port: Items that arrive at a port can be transported to a rail hub or a distribution center
    # Rail Hub: Items that arrive at a rail hub can be transported to another rail hub or a distribution center
    # Distribution Center: Each distribution center can only transport goods to a particular set of stores. When a
    # distribution center is defined in the input, the stores associated with that will be listed immediately after
    # that to reflect this dependency.
    # Store: Goods will arrive at a store from a distribution center or from another store. Each store
    # can only be served by one distribution center
    def parse_file(self, file):
        N, L = file[0].split()
        N = int(N)
        L = int(L)

        nodes = []
        links = []
        id = 0
        i = 1

        # parse nodes
        while i <= N:
            nodeName, nodeType = file[i].split()
            newNode = Node(id, nodeName, nodeType)
            nodes.append(newNode)
            id = id + 1
            i = i + 1

        # create dictionary of valid stores for each dist-center
        j = 0
        distGroups = {}
        while j < len(nodes):
            if nodes[j].type == 'dist-center':  # if a current index in nodes is a dist center
                currDist = nodes[j].name
                currStores = []
                k = j + 1
                while k < len(nodes):  # while following nodes are stores, add store name to list
                    if nodes[k].type == 'store':
                        currStores.append(nodes[k].name)
                        k = k + 1
                    else:
                        break
                distGroups[currDist] = currStores
            j = j + 1

        # parse links
        while i <= N + L:
            u, v, cost = file[i].split()
            for j in nodes:
                if u == j.name:
                    nodeU = j
                elif v == j.name:
                    nodeV = j
            if nodeU.type == 'port' and (nodeV.type == 'rail-hub' or nodeV.type == 'dist-center'):
                links.append([nodeU, nodeV, cost])
            elif (nodeU.type == 'rail-hub' or nodeU.type == 'dist-center') and nodeV.type == 'port':
                links.append([nodeU, nodeV, cost])
            elif nodeU.type == 'rail-hub' and (nodeV.type == 'rail-hub' or nodeV.type == 'dist-center'):
                links.append([nodeU, nodeV, cost])
            elif (nodeU.type == 'rail-hub' or nodeU.type == 'dist-center') and nodeV.type == 'rail-hub':
                links.append([nodeU, nodeV, cost])
            elif nodeU.type == 'dist-center' and nodeV.type == 'store':
                stores = distGroups.get(nodeU.name)
                if nodeV.name in stores:
                    links.append([nodeU, nodeV, cost])
            elif nodeU.type == 'store' and nodeV.type == 'dist-center':
                stores = distGroups.get(nodeV.name)
                if nodeU.name in stores:
                    links.append([nodeU, nodeV, cost])
            elif nodeU.type == 'store' and nodeV.type == 'store':
                links.append([nodeU, nodeV, cost])
            i = i + 1

        G = [nodes, links]
        return G

    def kruskals(self, nodes, links):
        edgesAccepted = 0
        cost = 0
        n = len(nodes)
        links = sorted(links, key=lambda x: x[2])  # simply sort links by cost instead of heap
        disjointsets = DisjointSet(n)  # initialize disjset object so all items in separate set
        i = 0
        while edgesAccepted < n - 1:
            e = links[i]  # smallest weight edge
            uset = disjointsets.find(e[0].id)
            vset = disjointsets.find(e[1].id)
            if uset != vset:  # if not already in the mst, add edge cost to total and union
                edgesAccepted = edgesAccepted + 1
                cost = cost + int(e[2])
                disjointsets.union(uset, vset)
            i = i + 1

        return cost

    # This is the method that should set off the computation
    # of the supply chain problem.  It takes as input a list containing lines of input
    # as strings.  You should parse that input and then call a
    # subroutine that you write to compute the total edge-weight sum
    # and return that value from this method
    #
    # @return the total edge-weight sum of a tree that connects nodes as described
    # in the problem statement
    def compute(self, file_data):
        # your function to compute the result should be called here
        edgeWeightSum = 0
        G = self.parse_file(file_data)
        edgeWeightSum += self.kruskals(G[0], G[1])  # perform kruskals on graph
        return edgeWeightSum