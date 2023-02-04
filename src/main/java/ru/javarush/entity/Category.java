package ru.javarush.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "category")
//@Data
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    @UpdateTimestamp    // время обновления будет само обновляться при изменении данных объекта класса
    private LocalDateTime lastUpdate;

    @ManyToMany
    @JoinTable(name = "film_category", // связующая таблица
            joinColumns = @JoinColumn(name = "category_id", // колонка из связующей таблицы
                    referencedColumnName = "category_id"),  // колонка из таблицы category
            inverseJoinColumns = @JoinColumn(name = "film_id", // колонка из связующей таблицы
                    referencedColumnName = "film_id")) // колонка из таблицы film
    private Set<Film> films;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
