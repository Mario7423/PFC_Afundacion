# Generated by Django 4.1.2 on 2023-05-29 14:41

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('futrest06app', '0007_hints'),
    ]

    operations = [
        migrations.CreateModel(
            name='News',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('title', models.CharField(max_length=100)),
                ('image', models.CharField(blank=True, max_length=200, null=True)),
                ('date', models.DateField()),
                ('text', models.CharField(max_length=300)),
            ],
        ),
    ]
