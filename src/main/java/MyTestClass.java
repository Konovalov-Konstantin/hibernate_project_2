import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import ru.javarush.dao.*;
import ru.javarush.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Data
public class MyTestClass {

    private SessionFactory sessionFactory;
    private final ActorDAO actorDAO;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final CustomerDAO customerDAO;
    private final FilmDAO filmDAO;
    private final FilmTextDAO filmTextDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public MyTestClass() {

        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.URL, "jdbc:p6spy:mysql://localhost:3306/movie");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "MYSQLpassword123");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");    // для многотопоточки и cash
        properties.put(Environment.HBM2DDL_AUTO, "validate");

        sessionFactory = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(City.class)
                .addAnnotatedClass(Country.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Feature.class)
                .addAnnotatedClass(Film.class)
                .addAnnotatedClass(FilmText.class)
                .addAnnotatedClass(Inventory.class)
                .addAnnotatedClass(Language.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Rating.class)
                .addAnnotatedClass(Rental.class)
                .addAnnotatedClass(Staff.class)
                .addAnnotatedClass(Store.class)
                .addProperties(properties)
                .buildSessionFactory();

        actorDAO = new ActorDAO(sessionFactory);
        addressDAO = new AddressDAO(sessionFactory);
        categoryDAO = new CategoryDAO(sessionFactory);
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
        customerDAO = new CustomerDAO(sessionFactory);
        filmDAO = new FilmDAO(sessionFactory);
        filmTextDAO = new FilmTextDAO(sessionFactory);
        inventoryDAO = new InventoryDAO(sessionFactory);
        languageDAO = new LanguageDAO(sessionFactory);
        paymentDAO = new PaymentDAO(sessionFactory);
        rentalDAO = new RentalDAO(sessionFactory);
        staffDAO = new StaffDAO(sessionFactory);
        storeDAO = new StoreDAO(sessionFactory);

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void newFilmWasMade() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Language language = getLanguageDAO().getItems(0,20).stream().unordered().findAny().get();
            List<Category> categories = getCategoryDAO().getItems(0, 5);
            List<Actor> actors = getActorDAO().getItems(0, 15);

            Film film = new Film();
            film.setActors(new HashSet<>(actors));
            film.setRating(Rating.NC_17);
            film.setSpecialFeatures(Set.of(Feature.TRAILERS, Feature.COMMENTARIES));
            film.setLength((short)88);
            film.setReplacementCost(BigDecimal.valueOf(100));
            film.setRentalRate(BigDecimal.ZERO);
            film.setLanguage(language);
            film.setDescription("new film");
            film.setTitle("my new film");
            film.setRentalDuration((byte)64);
            film.setOriginalLanguage(language);
            film.setCategories(new HashSet<>(categories));
            film.setYear(Year.now());
            getFilmDAO().save(film);

            FilmText filmText = new FilmText();
            filmText.setFilm(film);
            filmText.setDescription("new film");
            filmText.setTitle("my new film");
            filmText.setId(film.getId());
            getFilmTextDAO().save(filmText);

            session.getTransaction().commit();
        }
    }

    public void customerRentInventory(Customer customer) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Film film = getFilmDAO().getFirstAvailableFilmForRent();
            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            Store store = getStoreDAO().getItems(0, 1).get(0);
            inventory.setStore(store);
            getInventoryDAO().save(inventory);

            Staff staff = store.getStaff();
            Rental rental = new Rental();
            rental.setRentalDate(LocalDateTime.now());
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setStaff(staff);
            getRentalDAO().save(rental);

            Payment payment = new Payment();
            payment.setRental(rental);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setCustomer(customer);
            payment.setAmount(BigDecimal.valueOf(137.88));
            payment.setStaff(staff);
            getPaymentDAO().save(payment);

            session.getTransaction().commit();
        }
    }

    public void customerReturnInventoryToStore() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Rental rental = getRentalDAO().getUnreturnedRental();
            rental.setReturnDate(LocalDateTime.now());
            getRentalDAO().save(rental);

            session.getTransaction().commit();
        }
    }

    public Customer createCustomer() {
        try(Session session = sessionFactory.getCurrentSession() ) {
            session.beginTransaction();
            Store store = getStoreDAO().getItems(0, 1).get(0);
            City city = getCityDAO().getByName("Korolev");

            Address address = new Address();
            address.setAddress("Mira str. 43");
            address.setPhone("+7-913-865-19-88");
            address.setCity(city);
            address.setDistrict("Some district");
            getAddressDAO().save(address);

            Customer customer = new Customer();
            customer.setEmail("konor_email@mail.com");
            customer.setIsActive(true);
            customer.setAddress(address);
            customer.setStore(store);
            customer.setFirstName("John");
            customer.setLastName("Konor");
            getCustomerDAO().save(customer);

            session.getTransaction().commit();
            return customer;
        }
    }
}
