global_var = 'global'

def print_global_var_details():
    print(len(global_var)) # Noncompliant
    print('Global var : ', global_var) # Noncompliant
    print('Global var : ' + global_var) # Noncompliant
    for c in global_var: # Noncompliant
        print(c)
    if len(global_var) > 0: # Noncompliant
        print('global_var len positive')
    elif 0 < len(global_var): # Noncompliant
        print('global_var len negative')
    else:
        print('global_var len = 0')

    try:
        print(global_var) # Noncompliant
    except:
        print(global_var) # Noncompliant
    else:
        print(global_var) # Noncompliant
    finally:
        print(global_var) # Noncompliant

    assert len(global_var) > 0, 'Failed' # Noncompliant
    assert len('test') > 0, 'Failed : ' + global_var # Noncompliant
    test = ''
    test += global_var # Noncompliant
    test = {'test': global_var, 'global_var': 1 } # Noncompliant

# Compliant
def print_var_length(local_var = global_var):
    global_var = 'local' # Here it create a local var and do not use the global var
    global_var: str = 'local' # Here it create a local var and do not use the global var
    global_var += 'local' # Here it create a local var and do not use the global var
    global_var = 'local' if True else 'global' # Here it create a local var and do not use the global var
    print(len(global_var)) # Compliant for the use, but not for the name of the var
    print(len(local_var))
    for global_var in local_var: # Compliant but not for the name
        print(global_var)

print_global_var_details()
print_var_length(global_var)
