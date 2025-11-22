# Student Management System – Java (OOP, File Handling, Multithreading)

## 📌 Overview

This project is a **Java-based Student Management System** developed as part of Lab Assignment 5. It demonstrates advanced Object-Oriented Programming concepts, including inheritance, abstraction, interfaces, custom exceptions, collections, multithreading, sorting, and file handling. Student data is stored in a text file to ensure persistence even after the program closes.

---

## 🧠 Key Concepts Used

### ✔ Object-Oriented Programming

* **Person (abstract class)**
* **Student (child class)**
* **RecordActions (interface)**
* **StudentManager (implements interface)**

### ✔ Exception Handling

* Custom exception: `StudentNotFoundException`
* Input validation for marks, roll numbers, and empty fields

### ✔ Multithreading

* `Loader` thread displays a loading animation before operations

### ✔ File Handling

* Student data saved in `students.txt`
* Load, save, read, and append using:

  * `BufferedReader`
  * `BufferedWriter`
  * `FileWriter`
  * `RandomAccessFile`

### ✔ Collections

* `ArrayList<Student>` used for dynamic record storage

### ✔ Sorting & Searching

* Sort by marks (descending)
* Search by student name
* Delete record by name

---

## 📂 File Structure

```
Person.java
Student.java
RecordActions.java
StudentManager.java
Loader.java
StudentNotFoundException.java
Main.java
students.txt (created automatically)
```

---

## ▶️ How to Run

### 1️⃣ Compile

```bash
javac *.java
```

### 2️⃣ Run

```bash
java Main
```

---

## 📸 Features

* Add new student records
* Delete student by name
* Search student by name
* Display all students
* Sort records
* Auto grade calculation
* Persistent file storage

---

## 🎯 Learning Outcomes

Through this project, students learn:

* Applying OOP principles in real applications
* Handling exceptions safely
* Using multithreading for enhanced user experience
* Managing records using Java collections
* Implementing persistent storage through file handling
