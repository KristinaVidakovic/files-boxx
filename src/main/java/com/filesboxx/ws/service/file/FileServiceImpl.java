package com.filesboxx.ws.service.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.BelongsFileFolder;
import com.filesboxx.ws.model.BelongsFileUser;
import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.OneOfFile;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.repository.FileFolderRepository;
import com.filesboxx.ws.repository.FileRepository;
import com.filesboxx.ws.repository.FileUserRepository;
import com.filesboxx.ws.repository.FolderRepository;
import com.filesboxx.ws.repository.UserRepository;

@Service
public class FileServiceImpl implements FileService{
	
	static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private FileUserRepository fileUserRepo;
	
	@Autowired
	private FileFolderRepository fileFolderRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FolderRepository folderRepo;
	
	@Override
	public OneOfFile file(MultipartFile forwarded, String userId) {
		
		log.info("Called POST method for inserting new file.");
		
		ResponseMessage message = new ResponseMessage();
		
		File file = new File();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			message.setMessage("Forwarded user doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}
		
		file.setName(forwarded.getOriginalFilename());
		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			message.setMessage("Error getting bytes from forwarded file.");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			return message;
		}
		
		file.setDeleted(false);
		fileRepo.save(file);
		log.info("Forwarded file entered: " + file.toString());
		
		BelongsFileUser belongs = new BelongsFileUser();
		belongs.setFileId(file.getFileId());
		belongs.setUserId(userId);
		belongs.setDeleted(false);
		fileUserRepo.save(belongs);
		log.info("Inserted connection file-user: " + belongs.toString());
	
		return file;
	}
	
	@Override
	public OneOfFile fileFolder(MultipartFile forwarded, String folderId) {
		
		log.info("Called POST method for inserting new file.");
		
		ResponseMessage message = new ResponseMessage();
		
		File file = new File();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists.");
			message.setMessage("Forwarded folder doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}
		
		file.setName(forwarded.getOriginalFilename());
		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			message.setMessage("Error getting bytes from forwarded file.");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
			return message;
		}
		
		file.setDeleted(false);
		fileRepo.save(file);
		log.info("Forwarded file entered: " + file.toString());
		
		BelongsFileFolder belongs = new BelongsFileFolder();
		belongs.setFileId(file.getFileId());
		belongs.setFolderId(folderId);
		belongs.setDeleted(false);
		fileFolderRepo.save(belongs);
		log.info("Inserted connection file-folder: " + belongs.toString());
	
		return file;
	}
	
	@Override
	public ResponseMessage updateLocation(Body request) {
		
		log.info("Called PUT method for file location update.");
		
		ResponseMessage message = new ResponseMessage();
		
		if (request.getUserId() != null && request.getFolderId() == null) {
		
			if (fileRepo.file(request.getFileId()) == null || userRepo.user(request.getUserId()) == null) {
				log.error("File ID or user ID doesn't exists or are deleted.");
				message.setMessage("Forwarded user ID or file ID doesn't exists or are deleted.");
				message.setStatus(HttpStatus.BAD_REQUEST);
				return message;
			}
			
			BelongsFileFolder bff = fileFolderRepo.findByFileId(request.getFileId());
			BelongsFileUser bfu = new BelongsFileUser();
			bfu.setFileId(request.getFileId());
			bfu.setUserId(request.getUserId());
			bfu.setDeleted(false);
			fileUserRepo.save(bfu);
			log.info("Connection file-user added: " + bfu.toString());
			bff.setDeleted(true);
			fileFolderRepo.save(bff);
			log.info("Connection file-folder updated: " + bff.toString());
			message.setStatus(HttpStatus.OK);
			message.setMessage("Successfully changed file location.");
			
			log.info("Executed PUT method.");
			return message;
			
		} else {
			
			if (fileRepo.file(request.getFileId()) == null || folderRepo.folder(request.getFolderId()) == null) {
				log.error("File ID or folder ID doesn't exists or are deleted.");
				message.setMessage("Forwarded folder ID or file ID doesn't exists or are deleted.");
				message.setStatus(HttpStatus.BAD_REQUEST);
				return message;
			}
			
			BelongsFileUser bfu = fileUserRepo.findByFileId(request.getFileId());
			BelongsFileFolder bff = new BelongsFileFolder();
			bff.setFileId(request.getFileId());
			bff.setFolderId(request.getFolderId());
			bff.setDeleted(false);
			fileFolderRepo.save(bff);
			log.info("Connection file-folder added: " + bff.toString());
			bfu.setDeleted(true);
			fileUserRepo.save(bfu);
			log.info("Connection file-user updated: " + bfu.toString());
			message.setStatus(HttpStatus.OK);
			message.setMessage("Successfully changed file location.");
			
			log.info("Executed PUT method.");
			return message;
		} 
		
		
	}
	
	@Override
	public List<OneOfFile> files(String userId) {
		
		log.info("Called GET method for getting files by user ID.");
		
		ResponseMessage message = new ResponseMessage();
		List<OneOfFile> list = new ArrayList<>();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user ID doesn't exists.");
			message.setMessage("Forwarded user ID doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			list.add(message);
			return list;
		}
		
		List<BelongsFileUser> belongs = fileUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.error("Doesn't exists files for forwarded user ID.");
			message.setMessage("Doesn't exists files for forwarded user ID.");
			message.setStatus(HttpStatus.NO_CONTENT);
			list.add(message);
			return list;
		} 
		
		
		for (BelongsFileUser bfu : belongs) {
			if (bfu.getDeleted() == false && fileRepo.findByFileId(bfu.getFileId()).getDeleted() == false) {
				list.add(fileRepo.findByFileId(bfu.getFileId()));
			}
		}
		
		log.info("Successfully executet GET method.");
		
		return list;
	}
	
	@Override
	public List<OneOfFile> filesFolder(String folderId) {
		
		log.info("Called GET method for getting files from folder by user ID.");
		
		ResponseMessage message = new ResponseMessage();
		List<OneOfFile> list = new ArrayList<>();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder ID doesn't exists.");
			message.setMessage("Forwarded folder ID doesn't exists.");
			message.setStatus(HttpStatus.BAD_REQUEST);
			list.add(message);
			return list;
		}
		
		List<BelongsFileFolder> belongsFiles = fileFolderRepo.findByFolderId(folderId);
		
		for (BelongsFileFolder bff: belongsFiles) {
			list.add(fileRepo.findByFileId(bff.getFileId()));
		}
		
		log.info("Method for getting files executed.");
		
		return list;
	}

}
