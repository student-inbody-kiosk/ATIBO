from datetime import datetime


"""
"2023-07-25T14:12:23.460783+09:00" => "2023-7-25 14:12:23"
"""
def datetime_to_date_time(datetime):
    date_time= datetime[:-6]
    return date_time.replace('T', ' ')