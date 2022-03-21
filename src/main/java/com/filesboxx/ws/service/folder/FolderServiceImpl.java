package com.filesboxx.ws.service.folder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.connections.BelongsFolderUser;
import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.OneOfFolder;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.repository.FolderRepository;
import com.filesboxx.ws.repository.connections.FolderUserRepository;
import com.filesboxx.ws.repository.user.UserRepository;

@Service
public class FolderServiceImpl implements FolderService {
	static Logger log = LoggerFactory.getLogger(FolderServiceImpl.class);

	private final FolderRepository folderRepo;
	private final FolderUserRepository folderUserRepo;
	private final UserRepository userRepo;
	
	@Autowired
	FolderServiceImpl(FolderRepository folderRepo, FolderUserRepository folderUserRepo, UserRepository userRepo) {
		this.folderRepo = folderRepo;
		this.folderUserRepo = folderUserRepo;
		this.userRepo = userRepo;
	}

	@Override
	public OneOfFolder folder(Folder folder, UUID userId) {

		log.info("Called POST method for creating new folder.");
		
		ResponseMessage message = new ResponseMessage();

		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			message.setMessage("Forwarded user doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}

		Folder exists = folderRepo.findByName(folder.getName());

		if (exists != null) {
			log.error("Folder with forwarded name already exists.");
			message.setMessage("Folder with forwarded name already exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return null;
		}

		folder.setDeleted(false);
		folderRepo.save(folder);

		log.info("Created folder " + folder.getName() + "!");

		BelongsFolderUser belongs = new BelongsFolderUser();
		belongs.setFolderId(folder.getFolderId());
		belongs.setUserId(userId);
		belongs.setDeleted(false);
		folderUserRepo.save(belongs);
		log.info("Inserted connection folder-user!");

		return folder;
	}
	
	@Override
	public List<OneOfFolder> folders(UUID userId) {
		
		log.info("Called GET method for getting folders for forwarded user ID.");
		
		ResponseMessage message =  new ResponseMessage();
		List<OneOfFolder> list = new ArrayList<>();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user ID doesn't exists or is deleted.");
			message.setMessage("Forwarded user ID doesn't exists or is deleted.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			list.add(message);
			return list;
		}
		
		List<BelongsFolderUser> belongs = folderUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.info("Forwarded user doesn't have folders.");
			message.setMessage("Forwarded user doesn't have folders.");
			message.setStatus(HttpStatus.NO_CONTENT);
			list.add(message);
			return list;
		}
		
		for (BelongsFolderUser bfu : belongs) {
			UUID folderId = bfu.getFolderId();
			Folder folder = folderRepo.findByFolderId(folderId);
			list.add(folder);
		}
		
		log.info("Method for getting folders executed.");
		
		return list;
	}

	@Override
	public ResponseMessage deleteFolder(UUID folderId) {

		log.info("Called DELETE method for deleting folder by folder ID.");

		ResponseMessage message = new ResponseMessage();

		if (folderRepo.folder(folderId) == null) {
			log.error("Folder with forwarded folder ID doesn't exists or is deleted.");
			message.setMessage("Folder with forwarded folder ID doesn't exists or is deleted.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}

		BelongsFolderUser bfu = folderUserRepo.findByFolderIdAndDeletedFalse(folderId);
		if (bfu != null) {
			bfu.setDeleted(true);
			folderUserRepo.save(bfu);
			log.info("Inserted belongs folder user!");
		}

		Folder folder = folderRepo.findByFolderId(folderId);
		folder.setDeleted(true);
		folderRepo.save(folder);

		log.info("Folder deleted.");

		message.setMessage("Folder deleted.");
		message.setStatus(HttpStatus.OK);

		return message;
	}

}
