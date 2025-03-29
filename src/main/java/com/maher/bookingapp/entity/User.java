    package com.maher.bookingapp.entity;

    import jakarta.persistence.*;
    import lombok.NoArgsConstructor;
    import lombok.*;

    @Entity
    @Table(name = "users")
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @Builder
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String role; // USER / ADMIN


    }
