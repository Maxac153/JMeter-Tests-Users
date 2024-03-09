import requests

from faker import Faker

fake = Faker()


class RegisterUser:
    @staticmethod
    def random_user() -> str:
        name = fake.name()
        email = fake.email()
        password = fake.password()
        return f'{name},{email},{password}\n'


if __name__ == '__main__':
    print('Start pre gen users')

    # Запись данных в CSV файл
    for _ in range(0, 1000):
        requests.post(f'http://17.150.0.103:9191/sts/ADD?FILENAME=users_data.csv&LINE={RegisterUser.random_user()}&ADD_MODE=LAST&UNIQUE=FALSE')

    print('End pre gen users')
