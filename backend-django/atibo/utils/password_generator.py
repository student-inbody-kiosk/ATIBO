import string
import secrets

def generate_random_password():
    # The candidate letters
    alphabets = string.ascii_letters
    numbers = string.digits
    special_chars = string.punctuation.replace("'", "").replace('"', "").replace('`', "")

    # Select password chars
    password_list = []
    password_list += [secrets.choice(alphabets) for _ in range(4)]
    password_list += [secrets.choice(numbers) for _ in range(3)]
    password_list += [secrets.choice(special_chars) for _ in range(3)]

    # Shuffle and generate password
    secrets.SystemRandom().shuffle(password_list)
    password = ''.join(password_list)

    return password
