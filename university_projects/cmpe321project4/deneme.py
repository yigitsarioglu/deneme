print("merhaba dunya")


 # https://stackoverflow.com/questions/20157824/how-to-take-input-file-from-terminal-for-python-script
 # https://github.com/bauripalash/foobardb
# https://www.freecodecamp.org/news/how-to-write-a-simple-toy-database-in-python-within-minutes-51ff49f47f1/
"""
f = open("testfile.txt", "r")
print(f.readline())
print(f.readline())





with open("testfile.txt") as file_in:
    lines = []
    for line in file_in:
        lines.append(line)
        
        
print(lines)
"""




f = open("testfile.txt", "r")
content = f.read().splitlines()

linelist=[]

for element in content:
    print(element)
    x=element.split()

    print(x[0])








