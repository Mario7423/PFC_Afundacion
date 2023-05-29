import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import User, Player, Hints
from django.db import IntegrityError


def server_status(request):
    http_response = {"is_ok": True}
    return JsonResponse(http_response)


@csrf_exempt
def registeUser(request):
    if request.method != 'POST':
        return JsonResponse({"error": "HTTP method not supported"}, status=405)

    body = json.loads(request.body)
    email = body.get('email')
    password = body.get('password')
    nickname = body.get('nickname')

    if email is None or password is None or nickname is None:
        return JsonResponse({"error": "Missing parameter"}, status=400)

    if email.count("@") == 0:
        return JsonResponse({"error": "Not valid email"}, status=400)

    salted_password = bcrypt.hashpw(password.encode("utf8"), bcrypt.gensalt()).decode('utf8')

    try:
        user_object = User(email=email,password=salted_password,nickname=nickname,token=None)
        user_object.save()
    except IntegrityError:
        return JsonResponse({"error": "An user with this email already exists"}, status=409)
    return JsonResponse({"user_created": True},status=201)

@csrf_exempt
def loginUser(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)

    body = json.loads(request.body)
    email = body.get('email')
    password = body.get('password')

    if email is None or password is None:
        return JsonResponse({"error": "Missing parameter"}, status=400)

    try:

        db_user = User.objects.get(email=email)

    except User.DoesNotExist:
        return JsonResponse({"error": "This user does not exist"}, status=404)

    if bcrypt.checkpw(password.encode('utf8'), db_user.password.encode('utf8')):
        token = secrets.token_hex(100)
        db_user.token = token
        db_user.save()
        return JsonResponse({"email": email, "token": token}, status=201)
    else:
        return JsonResponse({"error": "This password is incorrect"}, status=400)


@csrf_exempt
def addPlayer(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)

    body = json.loads(request.body)
    name = body.get('name')
    age = body.get('age')
    number = body.get('number')
    image = body.get('image')
    team = body.get('team')
    nationality = body.get('nationality')
    nickname = body.get('nickname')
    position = body.get('position')

    if name is None or age is None or number is None or image is None or nationality is None or team is None or nickname is None:
        return JsonResponse({'error': 'Missing parameter'}, status=400)

    new_player = Player(name=name,age=age,number=number,image=image,team=team,nationality=nationality, nickname=nickname, position=position)
    new_player.save()
    return JsonResponse({'created new player': True}, status=201)


@csrf_exempt
def addHint(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)

    body = json.loads(request.body)
    hint1 = body.get('hint1')
    hint2 = body.get('hint2')
    hint3 = body.get('hint3')
    hint4 = body.get('hint4')
    hint5 = body.get('hint5')
    solution = body.get('solution')

    if hint1 is None or hint2 is None or hint3 is None or hint4 is None or hint5 is None or solution is None:
        return JsonResponse({'error': 'Missing parameter'}, status=400)

    new_hint = Hints(hint1=hint1,hint2=hint2,hint3=hint3,hint4=hint4,hint5=hint5,solution=solution)
    new_hint.save()
    return JsonResponse({'created new hint': True}, status=201)



@csrf_exempt
def getHints(request):
    if request.method != 'GET':
        return JsonResponse({'eror': 'HTTP method not supported'}, status=405)

    rows = Hints.objects.all()

    response = []

    for row in rows:
        response.append(row.hint_to_json())

    return JsonResponse(response,safe=False, status=201)

@csrf_exempt
def getPlayers(request):
    if request.method != 'GET':
        return JsonResponse({'error': 'HTTP method not supported'}, status=405)

    rows = Player.objects.order_by('name')
    jsonArray = []
    for row in rows:
        jsonArray.append(row.player_to_json())
    return JsonResponse(jsonArray, safe=False, status=200)

