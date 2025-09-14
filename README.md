# Shahzam

A tiny **JavaFX** assistant to manage **todos**, **deadlines**, and **events** in a chat-style UI.

## Requirements
- **JDK 17** (recommended) — check with `java -version`
- IntelliJ IDEA (latest)

## Quick Start (IntelliJ)
1. Open IntelliJ → **Open** project folder → accept defaults.
2. Set **Project SDK** to **JDK 17** (File → Project Structure).
3. Run `src/main/java/Shahzam.java` → **Run 'Shahzam.main()'**.

## Run From JAR
```bash
java -jar shahzam.jar
```

## Commands
| Command | Purpose | Example |
|---|---|---|
| `todo <desc>` | Add To-Do | `todo read book` |
| `deadline <desc> /by <yyyy-MM-dd HH:mm>` | Add Deadline | `deadline submit /by 2025-01-02 18:30` |
| `event <desc> /from <yyyy-MM-dd HH:mm> /to <yyyy-MM-dd HH:mm>` | Add Event | `event workshop /from 2025-01-03 09:00 /to 12:00` |
| `list` | List tasks | `list` |
| `done <index>` / `unmark <index>` | Mark done / not done | `done 1` |
| `delete <index>` | Delete task | `delete 2` |
| `find <query> \| -d \| -t <t\|d\|e>` | Search/filter | `find report`, `find -d`, `find -t e` |
| `bye` | Save & exit | `bye` |

> Dates display in a friendly format (e.g., `Jan 2 2025, 6:30 PM`).

## Saving
Data is saved to disk on `bye` and loaded on next start.

## Assets & Credits
- User avatar: <a href="https://www.freepik.com/free-vector/cute-lightning-bolt-sticker-printable-weather-clipart-vector_18247304.htm#fromView=keyword&page=1&position=40&uuid=c2c5d840-4ce9-44bb-ba7d-95ae91612d27&query=Lightning+bolt+animation">Image by rawpixel.com on Freepik</a>
- Shahzam avatar: <a href="https://www.pngarts.com/explore/130453" target="_blank">Shazam PNG Picture</a>

