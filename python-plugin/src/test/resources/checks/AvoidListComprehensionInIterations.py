def non_compliant_example_basic():
    for var in [var2 for var2 in range(1000)]: # Noncompliant {{Use generator comprehension instead of list comprehension in for loop declaration}}
        print(var)

def non_compliant_example_enumerate():
    for idx, var in enumerate([var2 for var2 in range(1000)]): # Noncompliant {{Use generator comprehension instead of list comprehension in for loop declaration}}
        print(var)

def non_compliant_example_zip():
    for var, var_ in zip([var2 for var2 in range(1000)], [var2 for var2 in range(1000)]): # Noncompliant {{Use generator comprehension instead of list comprehension in for loop declaration}} {{Use generator comprehension instead of list comprehension in for loop declaration}}
        print(var)

def non_compliant_example_enumerate_zip():
    for packed_var in enumerate(zip([1, 2, 3], filter(bool, [idx % 2 for idx in range(100)]))): # Noncompliant {{Use generator comprehension instead of list comprehension in for loop declaration}}
        print(packed_var)

def compliant_example_basic_1():
    for var in range(10):
        print(var)

def compliant_example_basic_2():
    for var in (var2 for var2 in range(1000)):
        print(var)

def compliant_example_with_filter():
    for var in filter(lambda x: x > 2, range(100)):
        print(var)

def compliant_example_with_enumerate():
    for idx, var in enumerate(range(1000)):
        print(var)

def compliant_example_with_zip():
    for var, var2 in zip((idx for idx in range(3)), ["a", "b", "c"]):
        print(var)
