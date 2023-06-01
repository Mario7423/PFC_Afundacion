# Generated by Django 4.2.1 on 2023-06-01 14:00

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('futrest06app', '0008_news'),
    ]

    operations = [
        migrations.CreateModel(
            name='Game',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('home', models.CharField(max_length=50)),
                ('visiting', models.CharField(max_length=50)),
                ('date', models.DateField()),
                ('hour', models.CharField(max_length=10)),
            ],
        ),
    ]