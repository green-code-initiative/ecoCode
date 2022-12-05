def mysql_loop():
    connection = mysql.connector.connect(database='local')
    cursor = connection.cursor()
    results = []
    for id in range(5):
      results.append(cursor.execute("SELECT name FROM user WHERE id = ?", (id)).fetchone()) # OK
    connection.close()
    return results

def pyodbc_loop():
    connection = pyodbc.connect('DRIVER={ODBC Driver 17 for SQL Server};SERVER=localhost;DATABASE=local')
    cursor = connection.cursor()
    results = []
    for id in range(5):
      results.append(cursor.execute("SELECT name FROM user WHERE id = ?", (id)).fetchone()) # OK
    connection.close()
    return results

def sqlite3_loop():
    connection = sqlite3.connect("local.db")
    cursor = connection.cursor()
    results = []
    for id in range(5):
      results.append(cursor.execute("SELECT name FROM user WHERE id = ?", (id)).fetchone()) # OK
    connection.close()
    return results
