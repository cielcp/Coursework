# CS4102 Spring 2022 -- Unit C Programming
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
import math


class SeamCarving:
    def __init__(self):
        return

    ourImage = []
    memory = []
    energies = []
    seam = []
    seamWeight = 0

    # method to calculate energy of a pixel
    def energy(self, j, i):  # j is the row num, i is column num
        lastRow = len(self.ourImage) - 1
        rowStart = j - 1
        rowEnd = j + 1
        if j == 0:
            rowStart = rowStart + 1
        if j == lastRow:
            rowEnd = rowEnd - 1
        lastColumn = len(self.ourImage[0]) - 1
        colStart = i - 1
        colEnd = i + 1
        if i == 0:
            colStart = colStart + 1
        if i == lastColumn:
            colEnd = colEnd - 1
        sums = 0.0
        p1 = self.ourImage[j][i]
        for y in range(rowStart, rowEnd + 1):
            for x in range(colStart, colEnd + 1):
                p2 = self.ourImage[y][x]
                if p1 != p2:
                    sums = sums + self.distance(p1, p2)
        neighbors = (rowEnd - rowStart + 1) * (colEnd - colStart + 1) - 1
        energy = sums / neighbors
        return energy  # const time

    # method to calculate distance between two pixel colors
    def distance(self, p1, p2):
        redDiff = p2[0] - p1[0]
        greenDiff = p2[1] - p1[1]
        blueDiff = p2[2] - p1[2]
        dist = math.sqrt(redDiff ** 2 + greenDiff ** 2 + blueDiff ** 2)
        return dist  # const time

    # method called to perform the seam carving
    # @return the seam's weight
    def run(self, image):
        self.ourImage = image
        rows = len(image)
        columns = len(image[0])
        # initialize energies and memory arrays in O(NM) time
        for j in range(rows):
            self.energies.append([])
            self.memory.append([])
            for i in range(columns):
                self.energies[j].append(self.energy(j, i))
                self.memory[j].append(0.0)
        # perform dynamic programming to find optimal seam
        self.memoize(rows, columns)  # O(NM) time
        self.seamWeight, seamEnd = self.optSeam(rows, columns)  # O(M) time
        seam = self.backtrack(rows, columns, seamEnd)
        self.seam = list(reversed(seam))  # O(NM) time
        return self.seamWeight  # 3 O(NM) + O(M) = O(NM)

    # method to fill memory with min energy sum of possible seams ending at that pixel
    def memoize(self, rows, columns):
        # store energies of bottom row in memory
        for i in range(columns):
            self.memory[0][i] = self.energies[0][i]
        # calculate lowest energy seam for rest of rows
        for j in range(1, rows):
            for i in range(columns):
                rowBelow = j - 1
                if i == 0:  # if we are at left edge
                    minSum = min(self.memory[rowBelow][i], self.memory[rowBelow][i + 1])
                elif i == columns - 1:  # elif we are at right edge
                    minSum = min(self.memory[rowBelow][i - 1], self.memory[rowBelow][i])
                else:  # else get min of 3 pixels below
                    minSum = min(self.memory[rowBelow][i - 1], self.memory[rowBelow][i], self.memory[rowBelow][i + 1])
                self.memory[j][i] = self.energies[j][i] + minSum
        return  # performs O(NM) min() calls

    # method to find where the optimal seam ends and weight of optimal seam
    def optSeam(self, rows, columns):
        topRow = rows - 1
        rightColumn = columns - 1
        optSum = self.memory[topRow][rightColumn]
        seamEnd = 0
        for i in range(rightColumn):
            currSum = self.memory[topRow][i]
            if currSum < optSum:
                seamEnd = i
                optSum = currSum
        return optSum, seamEnd  # performs O(M) comparisons

    # method to backtrack and construct the optimal seam in memory
    def backtrack(self, rows, columns, seamEnd):
        seam = []
        seam.append(seamEnd)
        restOfRows = rows - 2
        for j in range(restOfRows, -1, -1):
            currx = seam[len(seam) - 1]
            # if we are at left edge
            if currx == 0:
                if self.memory[j][0] < self.memory[j][1]:
                    x = 0
                else:
                    x = 1
            # elif we are at right edge
            elif currx == columns - 1:
                if self.memory[j][columns - 1] < self.memory[j][columns - 2]:
                    x = columns - 1
                else:
                    x = columns - 2
            # else find min of three pixels below
            else:
                x = currx - 1
                if self.memory[j][currx] < self.memory[j][x]:
                    x = currx
                if self.memory[j][currx + 1] < self.memory[j][x]:
                    x = currx + 1
            seam.append(x)
        return seam  # performs O(NM) sets of comparisons

    # Get the seam, in order from top to bottom, where the top-left corner of the
    # image is denoted (0,0).
    #
    # Since the y-coordinate (row) is determined by the order, only return the x-coordinate
    #
    # @return the ordered list of x-coordinates (column number) of each pixel in the seam
    #         as an array
    def getSeam(self):
        return self.seam
