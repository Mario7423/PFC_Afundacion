# Generated by Django 4.1.2 on 2023-05-24 07:55

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('futrest06app', '0003_player'),
    ]

    operations = [
        migrations.AlterField(
            model_name='player',
            name='team',
            field=models.CharField(default='Sin equipo', max_length=50),
        ),
    ]
