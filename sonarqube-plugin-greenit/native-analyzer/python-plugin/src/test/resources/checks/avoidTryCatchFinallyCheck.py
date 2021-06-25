import os.path

path = 'hello.txt'


def my_function():
    x=0

    try: # Noncompliant {{Eviter d'utiliser try-catch-finally}}
        print(x)
    except:
        print("Something went wrong")
    finally:
        print("The 'try except' is finished")

def foo():
    try: # Noncompliant {{Eviter d'utiliser try-catch-finally}}
        f = open(path)
        print(f.read())
    except:
        print('No such file '+path)
    finally:
        f.close()

def boo():
    if os.path.isfile(path):
        fh = open(path, 'r')
        print(fh.read())
        fh.close