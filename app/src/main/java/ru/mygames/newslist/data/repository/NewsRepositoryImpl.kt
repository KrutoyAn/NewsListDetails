package ru.mygames.newslist.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.mygames.newslist.data.local.NewsDao
import ru.mygames.newslist.data.local.NewsEntity
import ru.mygames.newslist.data.mapper.NewsMapper.toDomain
import ru.mygames.newslist.data.remote.NewsApi
import ru.mygames.newslist.domain.model.News
import ru.mygames.newslist.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val dao: NewsDao
) : NewsRepository {

    override fun getAllNews(): Flow<List<News>> = dao.getAllNews()
        .map { entities -> entities.map { it.toDomain() } }

    override fun getNewsById(id: String): Flow<News?> = dao.getNewsById(id)
        .map { it?.toDomain() }

    override suspend fun refreshNews() {
        try {
            val mockEntities = listOf(
                // --- ВКЛАДКА "НОВОСТИ" (isNew = true) ---
                NewsEntity(
                    id = "n1",
                    title = "Новые сорта крафта уже в наличии в магазинах",
                    description = "Пролистайте каталог ProStore и выбирайте напитки по вкусу",
                    content = "Мы расширили линейку крафтового пива от лучших локальных пивоварен...",
                    imageUrl = "https://img.freepik.com/free-photo/fresh-beer-glass_144627-16471.jpg",
                    date = "20.01.2022",
                    isNew = true
                ),
                NewsEntity(
                    id = "n2",
                    title = "Открытие нового магазина на ул. Ленина, 50",
                    description = "Ждем всех на торжественное открытие с подарками",
                    content = "В программе: дегустация, розыгрыш призов и скидочные карты первым 100 покупателям...",
                    imageUrl = "https://img.freepik.com/free-photo/supermarket-interior-with-shelves-full-of-products_23-2148169227.jpg",
                    date = "18.01.2022",
                    isNew = true
                ),
                NewsEntity(
                    id = "n3",
                    title = "Итоги розыгрыша «Счастливый чек»",
                    description = "Узнайте, кто стал обладателем главного приза",
                    content = "Поздравляем победителей нашего ежемесячного конкурса...",
                    imageUrl = "https://img.freepik.com/free-photo/winners-concept-with-golden-trophy_23-2148883741.jpg",
                    date = "15.01.2022",
                    isNew = true
                ),
                NewsEntity(
                    id = "n4",
                    title = "График работы магазинов в праздничные дни",
                    description = "Ознакомьтесь с изменениями в режиме работы",
                    content = "Чтобы вы успели купить всё необходимое, мы продлили часы работы...",
                    imageUrl = "https://img.freepik.com/free-photo/open-sign-hanging-on-shop-door_23-2148816743.jpg",
                    date = "10.01.2022",
                    isNew = true
                ),
                NewsEntity(id = "n5", title = "Свежее поступление закусок", description = "Рыба, орехи и чипсы уже на полках", content = "Большой выбор к вашему любимому напитку", imageUrl = "https://img.freepik.com/free-photo/assorted-snacks-on-table_23-2148534600.jpg", date = "05.01.2022", isNew = true),
                NewsEntity(id = "n6", title = "Вакансии в ProStore", description = "Стань частью нашей дружной команды", content = "Мы ищем продавцов и кассиров...", imageUrl = "https://img.freepik.com/free-photo/friendly-staff-smiling-at-camera_23-2148312000.jpg", date = "01.01.2022", isNew = true),
                NewsEntity(id = "n7", title = "Эко-инициатива: сдай пластик — получи баллы", description = "Заботимся о природе вместе", content = "В каждом магазине установлены контейнеры...", imageUrl = "https://img.freepik.com/free-photo/recycling-concept-with-plastic-bottles_23-2148232150.jpg", date = "28.12.2021", isNew = true),
                NewsEntity(id = "n8", title = "Обновление мобильного приложения", description = "Теперь копить баллы стало еще удобнее", content = "Скачивайте новую версию в Google Play...", imageUrl = "https://img.freepik.com/free-photo/hand-holding-smartphone-with-app_23-2148611700.jpg", date = "25.12.2021", isNew = true),

                NewsEntity(
                    id = "a1",
                    title = "Нам 10 лет: повышаем скидку до 10% на всё!",
                    description = "Празднуйте юбилей ProStore вместе с нами",
                    content = "Акция действует во всех магазинах сети при предъявлении карты...",
                    imageUrl = "https://img.freepik.com/free-photo/birthday-celebration-party-concept_23-2148821100.jpg",
                    date = "20.01.2022",
                    isNew = false
                ),
                NewsEntity(
                    id = "a2",
                    title = "Скидка 20% на все импортное пиво",
                    description = "Попробуйте лучшие сорта из Европы и США",
                    content = "Только до конца недели специальные цены на Бельгию и Германию...",
                    imageUrl = "https://img.freepik.com/free-photo/different-beer-bottles-on-table_23-2148243100.jpg",
                    date = "19.01.2022",
                    isNew = false
                ),
                NewsEntity(
                    id = "a3",
                    title = "2+1 на весь разливной квас",
                    description = "Освежающая акция для всей семьи",
                    content = "При покупке двух литров — третий в подарок!",
                    imageUrl = "https://img.freepik.com/free-photo/traditional-russian-kvass_144627-16400.jpg",
                    date = "17.01.2022",
                    isNew = false
                ),
                NewsEntity(
                    id = "a4",
                    title = "Счастливые часы: -15% с 10:00 до 12:00",
                    description = "Выгодные покупки в утреннее время",
                    content = "Скидка не суммируется с другими предложениями...",
                    imageUrl = "https://img.freepik.com/free-photo/clock-and-shopping-cart_23-2148169200.jpg",
                    date = "14.01.2022",
                    isNew = false
                ),
                NewsEntity(id = "a5", title = "Дарим бокалы при покупке ящика пива", description = "Собери коллекцию фирменной посуды", content = "Подробности у продавцов-консультантов", imageUrl = "https://img.freepik.com/free-photo/beer-glasses-composition_23-2148243150.jpg", date = "12.01.2022", isNew = false),
                NewsEntity(id = "a6", title = "Скидка в день рождения -15%", description = "Поздравляем наших любимых покупателей", content = "Скидка действует 3 дня до и 3 дня после...", imageUrl = "https://img.freepik.com/free-photo/gift-box-with-ribbon_23-2148293100.jpg", date = "08.01.2022", isNew = false),
                NewsEntity(id = "a7", title = "Кэшбэк 5% за оплату по QR-коду", description = "Покупайте выгодно с СБП", content = "Баллы начисляются мгновенно...", imageUrl = "https://img.freepik.com/free-photo/qr-code-scanning-concept_23-2149175400.jpg", date = "04.01.2022", isNew = false),
                NewsEntity(id = "a8", title = "Черная пятница: суперцены на всё", description = "Только один день невероятных скидок", content = "Не пропустите главную распродажу года...", imageUrl = "https://img.freepik.com/free-photo/black-friday-sale-label_23-2148312050.jpg", date = "01.12.2021", isNew = false)
            )

            dao.deleteAll()
            dao.insertAll(mockEntities)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}