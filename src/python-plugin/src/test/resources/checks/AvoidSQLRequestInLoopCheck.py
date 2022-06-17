import mysql.connector


class AvoidSQLRequestInLoopCheck:
    def testWithNoLoop(self):
        try :
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor=db.cursor()
            query = "SELECT * FROM users"
            cursor.execute(query)
            with row in cursor:
                print(row.id)
            cursor.close()
            db.close()
        except :
            print("Got an exception")
            db.close()

    def testWithForLoop():
        try:
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor=db.cursor()
            for i in range(0,20):
                query+=str(i)
                cursor.execute("SELECT * from users") #Noncompliant

            with row in cursor:
                print(row.name)
            cursor.close()
        except :
            print("Got an exception")
            db.close()

    def testWithWhileLoop():
          try:
              db = mysql.connector.connect(option_files='my.conf', use_pure=True)
              query = "SELECT * "
              i = 0
              while i<20:
                  cursor=db.cursor()
                  cursor.execute("SELECT * FROM users where id = "+str(i)) #Noncompliant
                  i+=1
              with row in cursor:
                        print(row.name)
              cursor.close()
          except :
              print("Got an exception")
              db.close()
    def testWithWhileLoop():
        try:
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            i = 0
            while i<20:
                cursor=db.cursor()
                cursor.execute("UPDATE users set id="+str(i))
                i+=1
            with row in cursor:
                print(row.name)
            cursor.close()
        except :
            print("Got an exception")
            db.close()


