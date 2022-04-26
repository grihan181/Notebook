package org.example.NotebookClasses;

public class Notebook {
    private long id;
    private String name;
    private String notes;
    private boolean important;
    private String createdWhen;
    private String reminder;
    private long usersID;

    public Notebook() {

    }

    public Notebook(long id, String name, String notes,
                    boolean important, String createdWhen,
                    String reminder, long usersID) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.important = important;
        this.createdWhen = createdWhen;
        this.reminder = reminder;
        this.usersID = usersID;
    }

    public Notebook(String name, String notes,
                    boolean important, String createdWhen,
                    String reminder, long usersID) {
        this.name = name;
        this.notes = notes;
        this.important = important;
        this.createdWhen = createdWhen;
        this.reminder = reminder;
        this.usersID = usersID;
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

    public String getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(String createdWhen) {
        this.createdWhen = createdWhen;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public long getUsersID() {
        return usersID;
    }

    public void setUsersID(long usersID) {
        this.usersID = usersID;
    }
}
