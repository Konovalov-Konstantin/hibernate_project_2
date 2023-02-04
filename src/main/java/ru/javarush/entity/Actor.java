package ru.javarush.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "actor")
//@Data
public class Actor {

    @Id
    @Column(name = "actor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_update")
    @UpdateTimestamp    // время обновления будет само обновляться при изменении данных объекта класса
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(name = "film_actor", // связующая таблица
            joinColumns = @JoinColumn(name = "actor_id", // колонка из связующей таблицы
                    referencedColumnName = "actor_id"),  // колонка из таблицы actor
            inverseJoinColumns = @JoinColumn(name = "film_id", // колонка из связующей таблицы
                    referencedColumnName = "film_id")) // колонка из таблицы film
    private Set<Film> films;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<Film> getFilms() {
        return films;
    }

    public void setFilms(Set<Film> films) {
        this.films = films;
    }
}