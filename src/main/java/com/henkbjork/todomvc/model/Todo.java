package com.henkbjork.todomvc.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "todos")
public class Todo implements Serializable {

    private static final long serialVersionUID = 4313469736027255869L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "todoText")
    private String todoText;
    @Column(name = "checked")
    private boolean checked;

    public Todo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", todoText='" + todoText + '\'' +
                ", checked=" + checked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;

        Todo todo = (Todo) o;

        return id == todo.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
