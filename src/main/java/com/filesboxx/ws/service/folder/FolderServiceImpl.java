package com.filesboxx.ws.service.folder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.filesboxx.ws.controller.folder.FoldersMapper;
import com.filesboxx.ws.controller.folder.dto.FolderCreateDto;
import com.filesboxx.ws.controller.folder.dto.FolderDto;
import com.filesboxx.ws.controller.folder.dto.FolderListDto;
import com.filesboxx.ws.exceptions.FolderExistsException;
import com.filesboxx.ws.exceptions.InvalidArgumentException;
import com.filesboxx.ws.exceptions.InvalidFolderException;
import com.filesboxx.ws.exceptions.InvalidUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.connections.BelongsFolderUser;
import com.filesboxx.ws.model.folder.Folder;
import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.repository.folder.FolderRepository;
import com.filesboxx.ws.repository.connection.FolderUserRepository;
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
	public FolderDto folder(FolderCreateDto dto, UUID userId) {

		log.info("Called POST method for creating new folder.");

		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}

		Folder exists = folderRepo.findByName(dto.getName());

		if (exists != null) {
			log.error("Folder with forwarded name already exists.");
			throw new FolderExistsException();
		}

		Folder saved = folderRepo.save(FoldersMapper.toFolder(dto));

		log.info("Created folder " + saved.getName() + "!");

		BelongsFolderUser belongs = new BelongsFolderUser();
		belongs.setFolderId(saved.getFolderId());
		belongs.setUserId(userId);
		belongs.setDeleted(false);
		folderUserRepo.save(belongs);
		log.info("Inserted connection folder-user!");

		return FoldersMapper.toFolderDto(saved);
	}
	
	@Override
	public FolderListDto folders(UUID userId) {
		
		log.info("Called GET method for getting folders for forwarded user ID.");
		
		ResponseMessage message =  new ResponseMessage();
		List<Folder> list = new ArrayList<>();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}
		
		List<BelongsFolderUser> belongs = folderUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.info("Forwarded user doesn't have folders.");
			throw new InvalidArgumentException();
		}
		
		for (BelongsFolderUser bfu : belongs) {
			UUID folderId = bfu.getFolderId();
			Folder folder = folderRepo.findByFolderId(folderId);
			list.add(folder);
		}
		
		log.info("Method for getting folders executed.");
		
		return FoldersMapper.toFolderListDto(list);
	}

	@Override
	public ResponseMessage deleteFolder(UUID folderId) {

		log.info("Called DELETE method for deleting folder by folder ID.");

		ResponseMessage message = new ResponseMessage();

		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists or is deleted.");
			throw new InvalidFolderException();
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
