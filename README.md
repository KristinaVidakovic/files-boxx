# Files Boxx API

### 🗃️ List of implemented methods:

#### 💁 User Controller

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| 🟢 *POST* | */auth/signup* | Method for user registration. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object***  <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ One of the attributes isn't forwarded. <br> 2️⃣ Forwarded username already exists. |
| 🟢 *POST* | */auth/signin* | Method for getting user by username and password. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Username or password isn't forwarded. <br> 2️⃣ User with forwarded username doesn't exists. <br> 3️⃣ Wrong password. |
| 🔵 *GET* | *user/{userId}* | Method for getting user by user ID. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object***  <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. |
