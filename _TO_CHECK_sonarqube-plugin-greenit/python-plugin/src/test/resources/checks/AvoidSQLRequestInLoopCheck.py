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
            query = "SELECT * FROM users where id = "
            for i in range(0,20):
                cursor=db.cursor()
                query+=str(i)
                cursor.execute(query) #Noncompliant
                with row in cursor:
                    print(row.name)
                cursor.close()
        except :
            print("Got an exception")
            db.close()

    def testWithWhileLoop():
          try:
              db = mysql.connector.connect(option_files='my.conf', use_pure=True)
              query = "SELECT * FROM users where id = "
              i = 0
              while i<20:

                  cursor=db.cursor()
                  query+=str(i)
                  cursor.execute(query) #Noncompliant
                  with row in cursor:
                      print(row.name)
                  cursor.close()
                  i+=1
          except :
              print("Got an exception")
              db.close()

    def testWithExecuteMany():
        try:
            db =db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            query = "SELECT * FROM users where id = %d"
            cursor=db.cursor()
            data = [i for i in range(20)]
            cursor.executemany(query,data)
            with row in cursor:
                print(row.name)
            cursor.close()
        except:
            print("Got an exception")
            db.close()