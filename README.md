# Files Boxx API

📗 [Swagger specification](https://files-boxx.herokuapp.com/files-boxx/swagger-ui.html)

### 🗃️ List of implemented methods:

#### 💁 User Controller

| Method                | Endpoint                          | Description                                                                                                                                                                                                                                                                                                                                                                    |
|-----------------------|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🟢 *POST*             | */files-boxx/users/auth/sign-up*  | Method for user sign up. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ User created. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ All attributes must be forwarded.  <br> 2️⃣ User with forwarded username already exists.                                                                              |
| 🟢 *POST*             | */files-boxx/users/auth/sign-in*  | Method for user sing in by username and password. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ Found user. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ All attributes must be forwarded. <br> 2️⃣ Forwarded user doesn't exists. <br> 3️⃣ Wrong password! <br> :four: Some user is already signed in. |
| 🟢 *POST*             | */files-boxx/users/auth/sign-out* | Method for user sign out. <br> - HttpStatus **OK** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User successfully signed out! <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ No user is signed in!                                                                                                                     |
| 🔵 *GET*              | */files-boxx/users/{userId}*      | Method for getting user by user ID. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ Found user. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists.                                                                                                                               |
| :orange_circle: *PUT* | */files-boxx/users/{userId}*      | Method for updating user by user ID. <br> - HttpStatus **OK** ➡️ Response : ***User JSON object*** <br> 1️⃣ Found user. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists.                                                                                                                              |

#### 📂 Folder Controller

| Method      | Endpoint                           | Description                                                                                                                                                                                                                                                                                                        |
|-------------|------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🟢 *POST*   | */files-boxx/folders/{userId}*     | Method for creating new folder, by user ID. <br> - HttpStatus **OK** ➡️ Response : ***Folder JSON object*** <br> 1️⃣ Folder created. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists. <br> 2️⃣ Folder with forwarded name already exists. |
| 🔵 *GET*    | */files-boxx/folders/{userId}*     | Method for getting list of folders by user ID. <br> - HttpStatus **OK** ➡️ Response : ***List of Folder JSON objects*** <br> 1️⃣ Found folders. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of one ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists.                              |
| 🔴 *DELETE* | */files-boxx/folders/{folderId}*   | Method for deleting folder by folder ID. <br> - HttpStatus **OK** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ Folder deleted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded folder doesn't exists or is deleted.                                 |

#### 📝 File Controller

| Method      | Endpoint                              | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
|-------------|---------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🟢 *POST*   | */files-boxx/files/{userId}*          | Method for inserting new file by user ID, outside the folder. <br> - HttpStatus **OK** ➡️ Response : ***File JSON object*** <br> 1️⃣ File inserted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists.                                                                                                                                                                                                                                                                                                               |
| 🟢 *POST*   | */files-boxx/files/folder/{folderId}* | Method for inserting new file by folder ID, inside the folder. <br> - HttpStatus **OK** ➡️ Response : ***File JSON object*** <br> 1️⃣ File inserted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded folder doesn't exists or is deleted.                                                                                                                                                                                                                                                                                              |
| 🔵 *GET*    | */files-boxx/files/{userId}*          | Method for getting files that are outside the folders by user ID. <br> - HttpStatus **OK** ➡️ Response : ***List of File JSON objects*** <br> :one: Found files. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of ResponseMessage JSON object*** <br> 1️⃣ Forwarded user doesn't exists.                                                                                                                                                                                                                                                                                          |
| 🔵 *GET*    | */files-boxx/files/folder/{folderId}* | Method for getting files from specific folder, by folder ID. <br> - HttpStatus **OK** ➡️ Response : ***List of File JSON objects*** <br> :one: Found files. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of ResponseMessage JSON object*** <br> 1️⃣ Forwarded folder doesn't exists or is deleted.                                                                                                                                                                                                                                                                               |
| 🟠 *PUT*    | */files-boxx/files/change-location*   | Method for changing location of the file. <br> 1️⃣ From folder : It should be forwarded locationUser object with file ID and user ID. <br> 2️⃣ In folder : It should be forwarded locationFolder object with file ID and folder ID.  <br> - HttpStatus **OK** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ File location changed. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded file doesn't exists or is deleted. <br> :two: Forwarded folder doesn't exists or is deleted. <br> :three: Forwarded user doesn't exists. |
| 🔴 *DELETE* | */files-boxx/files/{fileId}*          | Method for deleting file by file ID. <br> - HttpStatus **OK** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ File deleted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ Forwarded file doesn't exists or is deleted.                                                                                                                                                                                                                                                                                                                  |

#### :incoming_envelope: Message Controller

| Method                    | Endpoint                                              | Description                                                                                                                            |
|---------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| :large_blue_circle: *GET* | */files-boxx/messages/{senderId}/{recipientId}/count* | Method for counting new messages. <br> - HttpStatus **OK** ➡️ Response : ***Long number*** <br> 1️⃣ Number of new messages.            |
| :large_blue_circle: *GET* | */files-boxx/messages/{senderId}/{recipientId}*       | Method for listing all messages. <br> - HttpStatus **OK** ➡️ Response : ***List of Message JSON objects*** <br> 1️⃣ Found messages.    |
| 🔵 *GET*                  | */files-boxx/messages/{id}*                           | Method for getting message by message ID. <br> - HttpStatus **OK** ➡️ Response : ***Message JSON object*** <br> :one: Found message.   |
