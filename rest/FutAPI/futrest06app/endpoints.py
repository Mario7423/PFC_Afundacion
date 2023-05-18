import json
import secrets

import bcrypt
from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import User
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

    if bcrypt.checkpw(password.encode('utf8'), db_user.password.enconde('utf8')):
        token = secrets.token_hex(100)
        db_user.token = token
        db_user.save()
        return JsonResponse({"email": email, "token": token}, status=201)
    else:
        return JsonResponse({"error": "This password is incorrect"}, status=400)