a = 0
for i in range(10):  
    for j in range(10):
        for k in range(10):
            for l in range(10): # Noncompliant {{Don't use for in a loop}}
                a+=1

a = 0
for i in range(10): 
    b = 3 
    for j in range(10):
        b+=1
        for k in range(10): # Noncompliant {{Don't use for in a loop}}
            a+=1

a = 0
for i in range(10): 
    b = 3 
    for j in range(10):
        b+=1

i = 0
while(i <10):
    for j in range(20):
        for k in range(20): # Noncompliant {{Don't use for in a loop}}
            i+=1

i = 0
for j in range(20):
    for k in range(20): 
        while(i <10): # Noncompliant {{Don't use for in a loop}}
            i+=1

a = 0
for i in range(10): 
    b = 3 
    for j in range(10):
        b+=1
        for k in range(10): # Noncompliant {{Don't use for in a loop}}
            a+=1

a = 0
for i in range(10): 
    b = 3 
    for j in range(10):
        b+=1

i = 0
while(i <10):
    for j in range(20):
        for k in range(20): # Noncompliant {{Don't use for in a loop}}
            i+=1

i = 0
for j in range(20):
    for k in range(20): 
        while(i <10): # Noncompliant {{Don't use for in a loop}}
            i+=1
