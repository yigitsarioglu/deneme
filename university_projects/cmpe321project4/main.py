import sys
from filemanager import processData

inFile = sys.argv[1]
outFile = sys.argv[2]


with open(inFile,'r') as i:
    lines = i.read().splitlines()

processedLines = processData(lines)  # lines send all the lines


with open(outFile,'w') as o:
    for line in processedLines:
        o.write(line)