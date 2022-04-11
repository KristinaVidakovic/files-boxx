package com.filesboxx.ws;

import com.filesboxx.ws.model.connections.BelongsFileFolder;
import com.filesboxx.ws.model.connections.BelongsFileUser;
import com.filesboxx.ws.model.file.File;
import com.filesboxx.ws.model.folder.Folder;
import com.filesboxx.ws.model.user.User;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ObjectMother {

    public static File createFile() {
        File file = new File();
        file.setFileId(UUID.randomUUID());
        file.setName("File name");
        file.setData("Hello".getBytes());
        file.setDeleted(false);
        return file;
    }

    public static MockMultipartFile createMultipartFile() {
        return new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
    }

    public static User createUser(Optional<Map> map) {
        User user = new User(
                UUID.randomUUID(),
                "Ime",
                "Prezime",
                "user",
                "pass",
                "mail@mail.com",
                null);

        if (map.isPresent()) {
            if (map.get().containsKey("userId")) {
                user.setUserId((UUID) map.get().get("userId"));
            }
            if (map.get().containsKey("firstName")) {
                user.setFirstName((String) map.get().get("firstName"));
            }
            if (map.get().containsKey("lastName")) {
                user.setFirstName((String) map.get().get("lastName"));
            }
            if (map.get().containsKey("username")) {
                user.setFirstName((String) map.get().get("username"));
            }
            if (map.get().containsKey("password")) {
                user.setFirstName((String) map.get().get("password"));
            }
            if (map.get().containsKey("email")) {
                user.setFirstName((String) map.get().get("email"));
            }
            if (map.get().containsKey("token")) {
                user.setFirstName((String) map.get().get("token"));
            }
        }
        return user;
    }

    public static BelongsFileUser createBelongsFileUser(Optional<Map> properties) {
        BelongsFileUser bfu = new BelongsFileUser(
                UUID.randomUUID(),
                false,
                createUser(Optional.empty()),
                createFile()
        );

        if (properties.isPresent()) {
            if (properties.get().containsKey("bfuId")) {
                bfu.setBfuId((UUID) properties.get().get("bfuId"));
            }
            if (properties.get().containsKey("user")) {
                bfu.setUser((User) properties.get().get("user"));
            }
            if (properties.get().containsKey("file")) {
                bfu.setFile((File) properties.get().get("file"));
            }
            if (properties.get().containsKey("deleted")) {
                bfu.setDeleted((Boolean) properties.get().get("deleted"));
            }
        }
        return bfu;
    }

    public static BelongsFileFolder createBelongsFileFolder(Optional<Map> properties) {
        BelongsFileFolder bff = new BelongsFileFolder(
                UUID.randomUUID(),
                false,
                createFile(),
                createFolder()
        );

        if (properties.isPresent()) {
            if (properties.get().containsKey("bffId")) {
                bff.setBffId((UUID) properties.get().get("bffId"));
            }
            if (properties.get().containsKey("folder")) {
                bff.setFolder((Folder) properties.get().get("folder"));
            }
            if (properties.get().containsKey("file")) {
                bff.setFile((File) properties.get().get("file"));
            }
            if (properties.get().containsKey("deleted")) {
                bff.setDeleted((Boolean) properties.get().get("deleted"));
            }
        }
        return bff;
    }

    private static Folder createFolder() {
        return new Folder(UUID.randomUUID(), "Folder name", false);
    }
}
