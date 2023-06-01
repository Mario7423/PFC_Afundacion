from django.db import models


class User(models.Model):
    email = models.CharField(max_length=80, unique=True)
    password = models.CharField(max_length=70)
    nickname = models.CharField(max_length=30)
    token = models.CharField(blank=True, null= True, max_length=200)


class Player(models.Model):
    name = models.CharField(max_length=80)
    age = models.IntegerField()
    number = models.IntegerField()
    image = models.CharField(blank=True,null=True, max_length=200)
    team = models.CharField(max_length=50, default='Sin equipo')
    nationality = models.CharField(max_length=30)
    nickname = models.CharField(max_length=30, default='Sin apodo')
    position = models.CharField(max_length=20, default='Centrocampista')

    def player_to_json(self):
        return {
            "name": self.name,
            "age": self.age,
            "number": self.number,
            "image": self.image,
            "team": self.team,
            "nationality": self.nationality,
            "nickname": self.nickname,
            "position": self.position
        }


class Hints(models.Model):
    hint1 = models.CharField(max_length=80)
    hint2 = models.CharField(max_length=80)
    hint3 = models.CharField(max_length=80)
    hint4 = models.CharField(max_length=80)
    hint5 = models.CharField(max_length=80)
    solution = models.CharField(max_length=80)

    def hint_to_json(self):
        return {
            "hint1": self.hint1,
            "hint2": self.hint2,
            "hint3": self.hint3,
            "hint4": self.hint4,
            "hint5": self.hint5,
            "solution": self.solution
        }


class News(models.Model):
    title = models.CharField(max_length=100)
    image = models.CharField(blank=True, null=True, max_length=200)
    date = models.DateField()
    text = models.CharField(max_length=300)

    def new_to_json(self):
        return {
            "title": self.title,
            "image": self.image,
            "date": self.date,
            "text": self.text
        }


class Game(models.Model):
    home = models.CharField(max_length=50)
    visiting = models.CharField(max_length=50)
    date = models.DateField()
    hour = models.CharField(max_length=10)

    def game_to_json(self):
        return{
            "home": self.home,
            "visiting": self.visiting,
            "date": self.date,
            "hour": self.hour
        }

