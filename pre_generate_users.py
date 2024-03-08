import os

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
    os.remove('./test_data/users_data.csv')

    # Запись данных в CSV файл
    with open('./test_data/users_data.csv', 'a') as file:
        for _ in range(0, 50000):
            file.write(RegisterUser.random_user())

    print('End pre gen users')
