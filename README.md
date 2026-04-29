# 📚 Library Management System

<p align="center">
  <b>A console-based system showcasing Data Structures + OOP in action</b><br>
  Built with Java • CSV Persistence • Custom Implementations
</p>

---

## 🚀 Overview

A fully integrated **Library Management System** combining concepts from:

- 🛒 Grocery Store Management  
- 🎓 University Course Management  
- 📖 Smart Library Project  

> Focus: **real-world application of data structures + clean OOP design**

---

## 🧠 Core Concepts

| Concept | Implementation |
|--------|---------------|
| 🌳 Binary Search Tree | O(log n) ISBN search |
| 📦 ArrayList | Fast inventory access |
| 🔗 Singly Linked List | Borrowing cart |
| 🗂️ Stack | History + Undo |
| 🧬 Generics | Type-safe collections |
| 💾 File I/O | CSV persistence |
| 🎭 Polymorphism | Category-based late fees |

---

## 📊 System Snapshot

| Metric | Value |
|------|------|
| 📚 Total Books | **61** |
| ✅ Available | **38** |
| ❌ Issued | **23** |
| 🏷️ Categories | Fiction, Biography, Economics, History & more |

---

## 🗂️ Project Structure

```bash
LibraryManagementSystem/
│
├── model/
├── datastructures/
├── managers/
├── undo/
├── books.csv
└── Main.java

<details> <summary>📂 Expand Full Structure</summary>

model/
├── LibraryItemADT.java
├── Book.java
└── BorrowRecord.java

datastructures/
├── BSTNode.java
├── BST.java
├── Stack.java
├── CartNode.java
└── CartList.java

undo/
└── UndoStack.java

managers/
├── InventoryManager.java
├── BorrowingHistoryManager.java
└── BookManager.java

</details>```


## ⚙️ Data Structures Breakdown

| Structure            | Purpose        | Complexity        |
|---------------------|---------------|------------------|
| 🌳 BST              | ISBN search   | O(log n)         |
| 📦 ArrayList        | Inventory     | O(1) access      |
| 🔗 Linked List      | Cart          | O(n)             |
| 🗂️ Stack (History) | LIFO tracking | O(1)             |
| 🔄 Stack (Undo)     | Undo actions  | O(1)             |
| 🧬 Generics         | Type safety   | Compile-time     |

---
## ✨ Features

### 📚 Catalogue
- View all books
- 🔍 Search by ISBN (BST)
- 🔎 Search by title, author, category
- 📊 Filter by status
- 🏷️ View categories

### 🛒 Borrowing Cart
- Add books (reserve system)
- View cart with fees
- Remove items
- ↩️ Undo last action

### 💳 Checkout & History
- Checkout with receipt
- 📜 View borrowing history (LIFO)

### 📊 System Tools
- BST statistics
- Generics demo

---
## 🛠️ Setup

```
# Compile
javac Main.java

# Run
java Main
```

---
## ## 📖 Sample Dataset

| ID  | Title                  | Author                | Category       | Status    |
|-----|------------------------|----------------------|----------------|-----------|
| 1   | Steve Jobs             | Walter Isaacson      | Biography      | issued    |
| 11  | City of Heavenly Fire  | Cassandra Clare      | Fiction        | available |
| 12  | Da Vinci Code          | Dan Brown            | Fiction        | available |
| 16  | Wings Of Fire          | A.P.J. Abdul Kalam   | Autobiography  | available |
| 19  | Rich Dad Poor Dad      | Robert T. Kiyosaki   | Business       | available |
| 45  | 1984                   | George Orwell        | Story          | available |
| 48  | Harry Potter           | J.K. Rowling         | Story          | available |

## 💻 Usage Examples
### 🔍 BST Search
```
Enter Book ID: 12

✅ BOOK FOUND
Title: Da Vinci Code
Author: Dan Brown
Status: available

⏱️ Search time: 0.45 µs
```

### 🛒 Borrow Book
```
Enter book ID: 12
✓ Added to cart
✓ Status: available → reserved
```

### 📜 Borrowing History

| ID  | Title            | Author              | Borrow Time         | Due Date   |
|-----|------------------|---------------------|---------------------|------------|
| 12  | Da Vinci Code    | Dan Brown           | 2024-01-15 14:30:22 | 2024-01-29 |
| 45  | 1984             | George Orwell       | 2024-01-10 10:15:03 | 2024-01-24 |
| 16  | Wings Of Fire    | A.P.J. Abdul Kalam  | 2024-01-05 09:45:17 | 2024-01-19 |

---
## 🎓 Learning Outcomes
- ✔️ Custom data structures
- ✔️ Recursive BST search
- ✔️ Stack (Undo + History)
- ✔️ Generics (<T extends LibraryItemADT>)
- ✔️ File handling (CSV)
- ✔️ Interface-based design (ADT)
- ✔️ Clean modular architecture

---
## 🔄 Program Flow
```
Start
 ↓
Load CSV → Build BST
 ↓
User Menu
 ↓
Search / Borrow / Checkout / History / Undo
 ↓
Save to CSV
```

---
## 📊 Complexity Analysis

| Operation       | Structure     | Complexity |
|----------------|--------------|------------|
| Search ISBN    | BST          | O(log n)   |
| Search Title   | ArrayList    | O(n)       |
| Add to Cart    | Linked List  | O(n)       |
| Undo           | Stack        | O(1)       |
| View History   | Stack        | O(n)       |
| Checkout       | Mixed        | O(n)       |

---

## ⚠️ Error Handling
- Invalid inputs
- Missing books
- Duplicate borrow attempts
- Empty cart actions
- CSV parsing issues

---

## 🔮 Future Improvements
- 🔁 Redo system
- 🔔 Due date alerts
- 👤 Multi-user support
- 💰 Fine calculation
- 🤖 Smart recommendations
- 🗄️ Database (MySQL/SQLite)

--- 

## 📝 Notes
- Built to demonstrate Data Structures + OOP integration
- Emphasis on clean code, modular design, and real-world simulation

---
<p align="center"> ⭐ If you like this project, consider starring it! </p> ```


