package org.example.modelClasses;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="notebooks")
public class Notebooks {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="notes", nullable = false)
    private String notes;

    @Column(name="important", columnDefinition = "false")
    private boolean important;

    @Column(name="created_when", nullable = false)
    private LocalDateTime createdWhen;

    @Column(name="reminder")
    private String reminder;

    @ManyToOne
    @JoinColumn(name="users_id")
    private Users user;

    public Notebooks() {

    }

    public Notebooks(long id, String name, String notes,
                    boolean important, LocalDateTime createdWhen,
                    String reminder, Users user) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.important = important;
        this.createdWhen = createdWhen;
        this.reminder = reminder;
        this.user = user;
    }

    public Notebooks(String name, String notes,
                    boolean important, LocalDateTime createdWhen,
                    String reminder, Users user) {
        this.name = name;
        this.notes = notes;
        this.important = important;
        this.createdWhen = createdWhen;
        this.reminder = reminder;
        this.user = user;
    }

    public Notebooks(String name, String notes,
                     boolean important, String createdWhen,
                     String reminder, Users user) {
        this.name = name;
        this.notes = notes;
        this.important = important;

        this.createdWhen = LocalDateTime.parse(createdWhen);
        this.reminder = reminder;
        this.user = user;
    }

    public Notebooks(long id, String name, String notes, boolean important, String createdWhen, String reminder, Users user) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.important = important;
        this.createdWhen = LocalDateTime.parse(createdWhen);
        this.reminder = reminder;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public LocalDateTime getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(LocalDateTime createdWhen) {
        this.createdWhen = createdWhen;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
