# Files Boxx API

### 🗃️ List of implemented methods:

#### 💁 User Controller

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| 🟢 *POST* | */auth/signup* | Method for user registration. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ User created. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ One of the attributes isn't forwarded. <br> 2️⃣ Forwarded username already exists. |
| 🟢 *POST* | */auth/signin* | Method for getting user by username and password. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ Found user. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Username or password isn't forwarded. <br> 2️⃣ User with forwarded username doesn't exists. <br> 3️⃣ Wrong password. |
| 🔵 *GET* | *user/{userId}* | Method for getting user by user ID. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ Found user. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. |

#### 📂 Folder Controller

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| 🟢 *POST* | */foldes/folder/{userId}* | Method for creating new folder, by user ID. <br> - HttpStatus **OK** ➡️ Response : ***Folder JSON object*** <br> 1️⃣ Folder created. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. <br> 2️⃣ Forwarded folder name already exists. |
| 🔵 *GET* | *folders/{userId}* | Method for getting list of folders by user ID. <br> - HttpStatus **OK** ➡️ Response : ***List of Folder JSON objects*** <br> 1️⃣ Found folders. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of one ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. <br> - HttpStatus **NO_CONTENT** ➡️ Response : ***List of one ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't have created folders. |
