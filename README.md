# Retrofit

CategoryTests:
GET позитивный тест
GET негативный тест

ProductTests:
GET products позитивный тест
POST product позитивный тест (верное тело запроса)
POST product bad request тест (id !=0, по документации должен быть bad request)
PUT product позитивный тест (существующий ID)
PUT product негативный тест (несуществующий ID)
GET product позитивный тест (существующий ID)
GET product error 404 тест (несуществующий ID, по документации должно быть 404)
