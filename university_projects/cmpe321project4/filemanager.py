from database import HaloDB


def processData(content):

    for line in content:  # line is a variable corresponds to each line in a file

        sentence = line.split()  #  divides into sentences

        if sentence[0]=="login" :
            print("Login successfully")

        if sentence[0]=="create" :
            print("Type has been created")

            if sentence[1]=="type":

                typename = sentence[2]      # typename is selected
                typefieldsize=sentence[3]   #field size data is taken
                print(typefieldsize)

                mydb = HaloDB(typename + ".db")   # create a type with that name

                fields=[]
                size=0
                while size < int( typefieldsize ) :
                    fields.append( sentence[size+4] )
                    mydb.set(sentence[size+4], "null")  # Sets Value
                    size=size+1


        if sentence[0] =="inherit" :
            print("Type inherited")



    return "Mission completed"