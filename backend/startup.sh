#! /bin/bash

# Generate a random secret key
GENERATED_KEY=$(python -c 'import random; import string; print("".join(random.SystemRandom().choice(string.ascii_letters + string.digits + "!@#$%^&*(-_=+)") for _ in range(50)))')
echo "SECRET_KEY=$GENERATED_KEY" >> /app/secret/.env

# Collect staticfiles
python manage.py collectstatic

# Initiate Database
python manage.py migrate
python manage.py loaddata accounts.json school.json students.json gym.json

# run gunicorn
gunicorn atibo.wsgi:application -b 0.0.0.0:8000 --log-file - --access-logfile - --workers 3