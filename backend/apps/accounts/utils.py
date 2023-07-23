import random
import string

def generate_password():
    # The candidate letters
    alphabets = string.ascii_letters
    numbers = string.digits
    special_chars = string.punctuation.replace("'", "").replace('"', "")

    # Select password chars
    password_list = []
    password_list += random.sample(alphabets, 4)
    password_list += random.sample(numbers,3)
    password_list += random.sample(special_chars,3)

    # Shuffle and generate password
    random.shuffle(password_list)
    password = ''.join(password_list)

    return password

