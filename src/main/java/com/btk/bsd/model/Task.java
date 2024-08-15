package com.btk.bsd.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "tasks")

//Lombok ile alakalı. sadece @data yazmak getter setter ı da içeriyor.
@Data
//Lombok constructer oluşturmaya da yarar
@NoArgsConstructor
@AllArgsConstructor

public class Task {

    @Id
    //Otomatik id oluşturmak için:
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String importance;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }




}
