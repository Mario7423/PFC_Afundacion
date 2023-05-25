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

    def to_json(self):
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

