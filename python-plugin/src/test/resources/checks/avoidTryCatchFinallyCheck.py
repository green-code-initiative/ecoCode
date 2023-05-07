import os.path

path = 'hello.txt'


def my_function():
    x=0

    try: # Noncompliant {{Avoid the use of try-catch}}
        print(x)
    except:
        print("Something went wrong")
    finally:
        print("The 'try except' is finished")

def foo():
    try: # Noncompliant {{Avoid the use of try-catch}}
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