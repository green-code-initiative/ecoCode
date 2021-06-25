def my_function():
    return 6

for i in my_function(): # Noncompliant {{Ne pas appeler de fonction dans la déclaration d’une boucle de type for}}
    print("Test")
    my_function()
    pass



my_function()