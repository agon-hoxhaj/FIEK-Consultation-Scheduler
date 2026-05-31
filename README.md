# FIEK Consultation Scheduler

Sistem për planifikimin e konsultimeve mes studentëve dhe profesorëve në Fakultetin e Inxhinierisë Elektrike dhe Kompjuterike (FIEK).

---

## Përshkrimi

FIEK Consultation Scheduler është një aplikacion desktop i ndërtuar me **JavaFX** që u mundëson studentëve të rezervojnë termin konsultimi me profesorët e tyre, ndërkohë që profesorët dhe stafi administrativ menaxhojnë oraret, terminet dhe feedback-et. Aplikacioni mbështet dy gjuhë — shqip dhe anglisht.

---

## Teknologjitë e përdorura

| Teknologjia       | Versioni  |
|-------------------|-----------|
| Java              | 25        |
| JavaFX            | 21.0.6    |
| PostgreSQL        | 42.7.3    |
| Maven             | Wrapper   |
| JUnit Jupiter     | 5.12.1    |

---

## Struktura e projektit

```
FIEK-Consultation-Scheduler/
├── src/main/java/
│   ├── Application/          # Pika hyrëse e aplikacionit (RunApplication.java)
│   ├── controllers/          # Kontrolluesit e JavaFX për secilën pamje (view)
│   ├── database/             # Lidhja me bazën e të dhënave (DBConnection.java)
│   ├── enums/                # Llojet e numëruara: roli, statusi, ditët e javës, gjinia
│   ├── models/               # Modelet e të dhënave dhe DTO-të
│   ├── repository/           # Shtresa e aksesit në bazën e të dhënave
│   ├── services/             # Logjika e biznesit dhe menaxhimi i sesionit/skenës
│   └── utils/                # Utilitare ndihmëse (SceneLocator)
├── src/main/resources/
│   ├── SQL/                  # Skriptet SQL për krijimin dhe populimin e bazës
│   ├── languages/            # Skedarët e gjuhës (shqip dhe anglisht)
│   ├── views/                # Skedarët FXML të ndërfaqes grafike
│   └── views/images/         # Logoja e universitetit
└── pom.xml
```

---

## Funksionalitetet kryesore

### Studenti
- Shikon oraret e konsultimit të profesorëve sipas lëndës
- Rezervon termin konsultimi
- Shikon terminet e rezervuara dhe statusin e tyre
- Jep feedback dhe vlerësim për profesorët

### Profesori
- Shton dhe menaxhon oraret e konsultimit
- Shikon terminet e rezervuara nga studentët
- Shikon feedback-et e marra nga studentët
- Shikon listën e studentëve dhe lëndëve të tij

### Stafi Administrativ
- Menaxhon të dhënat e profesorëve
- Menaxhon të dhënat e studentëve
- Shikon statistika të profesorëve sipas departamentit

### Të gjithë përdoruesit
- Autentikim me emër përdoruesi dhe fjalëkalim (me hash + salt)
- Ndryshim fjalëkalimi
- Ndërrimi i gjuhës (shqip / anglisht)

---

## Konfigurimi i bazës së të dhënave

Aplikacioni lidhet me **PostgreSQL** në `localhost:5432`.

1. Krijoni bazën e të dhënave:
   ```sql
   CREATE DATABASE "FIEK-Consultation-Scheduler";
   ```

2. Ekzekutoni skriptin e strukturës:
   ```
   src/main/resources/SQL/Baza e te dhenave
   ```

3. Populoni bazën me të dhëna fillestare:
   ```
   src/main/resources/SQL/Vendosja e te dhenave
   ```

4. Sigurohuni që kredencialet në `DBConnection.java` të përputhen me konfigurimin tuaj:
   ```java
   private static final String DB_URL = "jdbc:postgresql://localhost:5432/FIEK-Consultation-Scheduler";
   private static final String USER = "postgres";
   private static final String PASSWORD = "fjalëkalimi_juaj";
   ```

> ⚠️ **Shënim:** Mos lini kredencialet e bazës së të dhënave në kod për projektet produksionale. Përdorni variabla mjedisi ose skedarë konfigurimi të jashtëm.

---

## Si të ekzekutoni projektin

### Parakushtet
- **Java 25** e instaluar
- **Maven** (ose përdorni Maven Wrapper të bashkëlidhur)
- **PostgreSQL** duke ekzekutuar me bazën e krijuar

### Hapat

1. Klononi repozitorin:
   ```bash
   git clone <url-e-repozitorit>
   cd FIEK-Consultation-Scheduler
   ```

2. Konfiguroni bazën e të dhënave sipas seksionit më lart.

3. Ekzekutoni aplikacionin:
   ```bash
   ./mvnw clean javafx:run
   ```
   ose në Windows:
   ```cmd
   mvnw.cmd clean javafx:run
   ```

---

## Llogari për testim

Skedarë me emra përdoruesish dhe fjalëkalime të paracaktuara gjenden në:
```
src/main/resources/passwordsWithUsername
```

---

## Gjuhët e mbështetura

| Gjuha   | Kodi |
|---------|------|
| Shqip   | `sq` |
| Anglisht | `en` |

Gjuha ndryshohet drejtpërdrejt nga ndërfaqja e aplikacionit.

---

## Statuset e termineve

| Statusi     | Përshkrimi                        |
|-------------|-----------------------------------|
| `Pending`   | Termin i pritur për aprovim       |
| `Approved`  | Termin i aprovuar nga profesori   |
| `Rejected`  | Termin i refuzuar                 |
| `Cancelled` | Termin i anuluar nga studenti     |
| `Completed` | Konsultim i përfunduar            |
