package com.example.matelas.entity;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.persistence.*;

@Entity
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", unique = true)
    private String nom;
    private Double prixRevient;
    private Double largeur;
    private Double longueur;
    private Double hauteur;

    @Column(name = "prix_revient_m3", unique = true)
    private Double prixRevientEnMetreCubique;

    @ManyToOne
    @JoinColumn(name = "id_machine", nullable = false)
    private Machine machine;

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Double getPrixRevientEnMetreCubique() {
        return prixRevientEnMetreCubique;
    }

    public void setPrixRevientEnMetreCubique(Double prixRevientEnMetreCubique) {
        this.prixRevientEnMetreCubique = prixRevientEnMetreCubique;
    }

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @ManyToOne
    @JoinColumn(name = "source", referencedColumnName = "id")
    private Bloc source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(Double prixRevient) {
        this.prixRevient = prixRevient;
    }

    public Double getLargeur() {
        return largeur;
    }

    public void setLargeur(Double largeur) {
        this.largeur = largeur;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }

    public Double getHauteur() {
        return hauteur;
    }

    public void setHauteur(Double hauteur) {
        this.hauteur = hauteur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Bloc getSource() {
        return source;
    }

    public void setSource(Bloc source) {
        this.source = source;
    }

    public double volume() {
        return longueur * largeur * hauteur;
    }

    public Bloc() {

    }

    public Bloc(boolean generate) {
        this.setHauteur();
        this.setLargeur();
        this.setLongueur();
        this.setMachine();
        this.setDateCreation();
        this.setPrixRevientEnMetreCubique(0.0);
    }

    public void setLargeur() {
        double min = 5.0;
        double max = 7.0;
        double randomValue = min + (Math.random() * (max - min));
        this.largeur = randomValue;
    }

    public void setLongueur() {
        double min = 20.0;
        double max = 25.0;
        double randomValue = min + (Math.random() * (max - min));
        this.longueur = randomValue;
    }

    public void setHauteur() {
        double min = 10.0;
        double max = 15.0;
        double randomValue = min + (Math.random() * (max - min));
        this.hauteur = randomValue;
    }

    public void setDateCreation() {
        LocalDateTime start = LocalDateTime.of(2022, 1, 1, 0, 0); // Début de 2022
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59); // Fin de 2024

        LocalDateTime randomDateTime = getRandomWorkingDateTime(start, end);
        this.dateCreation = randomDateTime;
    }

    public void setMachine() {
        int[] idMachine = { 1, 2, 3, 4 };
        Random random = new Random();
        int randomIndex = random.nextInt(idMachine.length);
        int randomValue = idMachine[randomIndex];

        Machine machine = new Machine();
        machine.setId((long) randomValue);
        this.machine = machine;
    }

    public static LocalDateTime getRandomWorkingDateTime(LocalDateTime start, LocalDateTime end) {
        LocalDateTime randomDateTime;
        do {
            randomDateTime = getRandomLocalDateTime(start, end);
        } while (!isWorkingDay(randomDateTime));

        return randomDateTime;
    }

    public static LocalDateTime getRandomLocalDateTime(LocalDateTime start, LocalDateTime end) {
        // Convertir les bornes en secondes depuis l'époque
        long startSeconds = start.toEpochSecond(java.time.ZoneOffset.UTC);
        long endSeconds = end.toEpochSecond(java.time.ZoneOffset.UTC);

        // Générer un nombre aléatoire entre startSeconds et endSeconds
        long randomSeconds = ThreadLocalRandom.current().nextLong(startSeconds, endSeconds + 1);

        // Convertir les secondes aléatoires en LocalDateTime
        return LocalDateTime.ofEpochSecond(randomSeconds, 0, java.time.ZoneOffset.UTC);
    }

    public static boolean isWorkingDay(LocalDateTime dateTime) {

        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }
}
