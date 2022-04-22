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

| Method      | Endpoint                     | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|-------------|------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🟢 *POST*   | */foldes/folder/{userId}*    | Method for creating new folder, by user ID. <br> - HttpStatus **OK** ➡️ Response : ***Folder JSON object*** <br> 1️⃣ Folder created. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. <br> 2️⃣ Forwarded folder name already exists.                                                                                                                                         |
| 🔵 *GET*    | */folders/{userId}*          | Method for getting list of folders by user ID. <br> - HttpStatus **OK** ➡️ Response : ***List of Folder JSON objects*** <br> 1️⃣ Found folders. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of one ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. <br> - HttpStatus **NO_CONTENT** ➡️ Response : ***List of one ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't have created folders. |
| 🔴 *DELETE* | */folders/delete/{folderId}* | Method for deleting folder by folder ID. <br> - HttpStatus **OK** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ Folder deleted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ Folder with forwarded ID doesn't exists or is deleted.                                                                                                                                                                         |

#### 📝 File Controller

| Method      | Endpoint                         | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
|-------------|----------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 🟢 *POST*   | */files/file/{userId}*           | Method for inserting new file by user ID, outside the folder. <br> - HttpStatus **OK** ➡️ Response : ***File JSON object*** <br> 1️⃣ File inserted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists.                                                                                                                                                                                   |
| 🟢 *POST*   | */files/file-folder/{folderId}*  | Method for inserting new file by folder ID, inside the folder. <br> - HttpStatus **OK** ➡️ Response : ***File JSON object*** <br> 1️⃣ File inserted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Folder with forwarded folder ID doesn't exists.                                                                                                                                                                              |
| 🔵 *GET*    | */files/{userId}*                | Method for getting files that are outside the folders by user ID. <br> - HttpStatus **OK** ➡️ Response : ***List of File JSON objects*** <br> Found files. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't exists. <br> - HttpStatus **NO_CONTENT** ➡️ Response : ***List of ResponseMessage JSON object*** <br> 1️⃣ User with forwarded user ID doesn't have inserted files.         |
| 🔵 *GET*    | */files/files-folder/{folderId}* | Method for getting files from specific folder, by folder ID. <br> - HttpStatus **OK** ➡️ Response : ***List of File JSON objects*** <br> Found files. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***List of ResponseMessage JSON object*** <br> 1️⃣ Folder with forwarded folder ID doesn't exists.                                                                                                                                                                     |
| 🟠 *PUT*    | */files/change-location*         | Method for changing location of the file. <br> 1️⃣ From folder : It should be forwarded file ID and user ID. <br> 2️⃣ In folder : It should be forwarded file ID and folder ID. <br> Third parameter is null. <br> - HttpStatus **OK** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ File location changed. <br> - HttpStatus **BAD_REQUEST** ➡️ Response : ***ResponseMessage JSON object*** <br> 1️⃣ Object(user/file/folder) with forwarded ID doesn't exists. |
| 🔴 *DELETE* | */files/delete/{fileId}*         | Method for deleting file by file ID. <br> - HttpStatus **OK** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ File deleted. <br> - HttpStatus **BAD_REQUEST** ➡️ Response: ***ResponseMessage JSON object*** <br> 1️⃣ File with forwarded ID doesn't exists or is deleted.                                                                                                                                                                                           |
