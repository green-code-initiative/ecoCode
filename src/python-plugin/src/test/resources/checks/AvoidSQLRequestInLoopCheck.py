import mysql.connector


class AvoidSQLRequestInLoopCheck:
    maxrows = 20

    def testWithNoLoop(self):
        try :
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor = db.cursor(dictionary=True)

            cursor.execute('SELECT id, name FROM users LIMIT %(limit)s', {'limit': self.maxrows})
            for row in cursor.fetchall():
                print("{}: {}".format(row['id'], row['name']))

            cursor.close()
            db.close()
        except mysql.connector.Error as err:
            print("Got an exception: {}".format(err))
            db.close()

    def testWithForLoop(self):
        try:
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor = db.cursor(dictionary=True)

            for i in range(0, self.maxrows):
                cursor.execute("SELECT id, name FROM users WHERE id = %(id)s", {'id': i+1}) #Noncompliant
                for row in cursor.fetchall():
                    print("{}: {}".format(row['id'], row['name']))

            cursor.close()
            db.close()
        except mysql.connector.Error as err:
            print("Got an exception: {}".format(err))
            db.close()

    def testWithWhileLoop(self):
        try:
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor = db.cursor(dictionary=True)

            i = 0
            while i < self.maxrows:
                cursor.execute("SELECT id, name FROM users WHERE id = %(id)s", {'id': i+1}) #Noncompliant
                for row in cursor.fetchall():
                    print("name: {}".format(row['name']))
                i += 1

            cursor.close()
            db.close()
        except mysql.connector.Error as err:
            print("Got an exception: {}".format(err))
            db.close()

    def testWithWhileLoopUpdate(self):
        try:
            db = mysql.connector.connect(option_files='my.conf', use_pure=True)
            cursor=db.cursor()

            i = 0
            while i < self.maxrows:
                cursor.execute('UPDATE users set name=%(name)s where id=%(id)s', {'name': "anonymous", 'id': i+1})
                i+=1
            db.commit()

            cursor.close()
            db.close()
        except mysql.connector.Error as err:
            print("Got an exception: {}".format(err))
            db.close()

if __name__ == '__main__':
  test = AvoidSQLRequestInLoopCheck()

  print("testWithNoLoop")
  test.testWithNoLoop()
  print("testWithForLoop")
  test.testWithForLoop()
  print("testWithWhileLoop")
  test.testWithWhileLoop()
  print("testWithWhileLoopUpdate")
  test.testWithWhileLoopUpdate()
