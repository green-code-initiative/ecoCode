from datetime import date

class Client():

    def __init__(self, age):
        self.age = age
    
    def set_year_of_birth(self):
        self.year_of_birth = date.today().year - self.age

    def is_major(self):
        return self.age >= 18