# Shahzam — User Guide

A tiny JavaFX assistant that helps you track todos, deadlines, and events — with a friendly chat UI.

---

## 🔧 Quick start

1. **Install Java 17**  
   Open a terminal and check:
   ```bash
   java --version
   ```
2. **Download** the latest **Shahzam** release from this repo’s Releases page.
3. **Launch**
    - Double-click the `.jar` (desktop), or
    - Run from terminal:
      ```bash
      java -jar shahzam.jar
      ```
4. **Try a command** in the input box (e.g. `todo eat food`) and hit **Enter**.

> Tip: **Enter** sends the message.

---

## ✨ What Shahzam can do

- **Keep track of tasks**
    - **todo** — plain tasks
    - **deadline** — tasks due by a specific date/time
    - **event** — tasks with a start **and** end date/time
- **Display your tasks** (ordered list with types and status)
- **Mark tasks as done / not done**
- **Powerful search**
    - find by description
- **Delete tasks**
- **Auto-save on exit** (`bye`)

---

## 🖥️ Using the GUI

- Type commands in the text field and press **Enter** (or click **Send**).
- Messages appear as chat bubbles; Shahzam replies below.
- The window keeps scrolling automatically to the latest message.

---

## 📜 Commands Overview

| Command    | What it does | Syntax |
|------------|---|---|
| `todo`     | Adds a To-Do task | `todo <description>` |
| `deadline` | Adds a Deadline task | `deadline <description> /by <yyyy-MM-dd HH:mm>` |
| `event`    | Adds an Event with start + end | `event <description> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>` |
| `list`     | Lists all tasks | `list` |
| `mark`     | Marks a task as done | `done <index>` |
| `unmark`   | Marks a task as not done | `unmark <index>` |
| `delete`   | Deletes a task | `delete <index>` |
| `find`     | Finds tasks by description and/or flags | `find <query>`, `find -d`, `find -t <t|d|e>` |
| `bye`      | Saves data and exits | `bye` |

> **Date/time format:** examples like `2025-01-02 18:30`. Shahzam will display times in a friendly format (e.g., `Jan 2 2025, 6:30 PM`).

---

## 🧪 Usage Examples

### `todo` — add a To-Do
**Input**
```
todo download Shahzam
```
**Expected**
```
Got it. I've added this task:
[T][ ] download Shahzam
Now you have 1 task in the list.
```

---

### `deadline` — add a Deadline
**Input**
```
deadline submit report /by 2025-01-02 18:30
```
**Expected**
```
Got it. I've added this task:
[D][ ] submit report (by: Jan 2 2025, 6:30 PM)
Now you have 2 tasks in the list.
```

---

### `event` — add an Event
**Input**
```
event project workshop /from 2025-01-03 09:00 /to 2025-01-03 12:00
```
**Expected**
```
Got it. I've added this task:
[E][ ] project workshop (from: Jan 3 2025, 9:00 AM to: Jan 3 2025, 12:00 PM)
Now you have 3 tasks in the list.
```

---

### `list` — list all tasks
**Input**
```
list
```
**Expected**
```
Here are your tasks!
1. [T][ ] download Shahzam
2. [D][ ] submit report (by: Jan 2 2025, 6:30 PM)
3. [E][ ] project workshop (from: Jan 3 2025, 9:00 AM to: Jan 3 2025, 12:00 PM)
```

---

### `mark` — mark as done
**Input**
```
mark 1
```
**Expected**
```
Nice! I've marked this task as done:
[T][X] download Shahzam
```

---

### `unmark` — mark as not done
**Input**
```
unmark 1
```
**Expected**
```
OK, I've marked this task as not done:
[T][ ] download Shahzam
```

---

### `delete` — delete a task
**Input**
```
delete 2
```
**Expected**
```
Noted. I've removed this task:
[D][ ] submit report (by: Jan 2 2025, 6:30 PM)
Now you have 2 tasks in the list.
```

---

### `find` — search & filter
You can search by text, filter not-done tasks, or filter by type.

**By text**
```
find workshop
```
**Expected**
```
Here's what I found!
1. [E][ ] project workshop (from: Jan 3 2025, 9:00 AM to: Jan 3 2025, 12:00 PM)
```

---

### `bye` — save and exit
**Input**
```
bye
```
**Expected**
```
Thunder quiets. SHAHZAM signing off, until next time.
```

---

## 💾 Saving the data

- Task data is written to disk when you use `bye`.
- The save file is created in your app’s data folder (e.g., inside the project or jar directory).  
  Keep the file if you want your tasks to persist next time.

---

## 🧰 Troubleshooting

- **Nothing happens when I press Enter**: Make sure the text field has focus (click it) and try again.
- **Date parsing failed**: Use the `yyyy-MM-dd HH:mm` format, e.g., `2025-01-02 18:30`.
- **App won’t start**: Verify `java --version` shows **17**.

---

## 🙏 Image Credits

- User avatar: <a href="https://www.freepik.com/free-vector/cute-lightning-bolt-sticker-printable-weather-clipart-vector_18247304.htm#fromView=keyword&page=1&position=40&uuid=c2c5d840-4ce9-44bb-ba7d-95ae91612d27&query=Lightning+bolt+animation">Image by rawpixel.com on Freepik</a>
- Shahzam avatar: <a href=https://www.pngarts.com/explore/130453>Shazam PNG Picture</a>

---

Happy tasking! If you have ideas or issues, please open an Issue or PR. ⚡️
