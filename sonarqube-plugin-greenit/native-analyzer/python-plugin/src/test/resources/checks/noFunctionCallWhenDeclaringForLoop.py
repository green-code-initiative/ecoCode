def my_function():
    return 6

for i in my_function(): # Noncompliant {{Do not call a function in the declaration of a for-type loop}}
    print("Test")
    my_function()
    pass



my_function()