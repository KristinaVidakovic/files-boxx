package com.filesboxx.ws.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.BelongsFileFolder;
import com.filesboxx.ws.model.BelongsFileUser;
import com.filesboxx.ws.model.BelongsFolderUser;
import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.model.User;
import com.filesboxx.ws.repository.FileFolderRepository;
import com.filesboxx.ws.repository.FileRepository;
import com.filesboxx.ws.repository.FileUserRepository;
import com.filesboxx.ws.repository.FolderRepository;
import com.filesboxx.ws.repository.FolderUserRepository;
import com.filesboxx.ws.repository.UserRepository;

@Service
public class FilesBoxxServiceImpl implements FilesBoxxService{

	static Logger log = LoggerFactory.getLogger(FilesBoxxServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private FileUserRepository fileUserRepo;
	
	@Autowired
	private FolderRepository folderRepo;
	
	@Autowired
	private FolderUserRepository folderUserRepo;
	
	@Autowired
	private FileFolderRepository fileFolderRepo;
	
	public User user(User user) {
		
		log.info("Called method POST /post");
		
		if (user.getName() == null || user.getSurname() == null || user.getUserName() == null || user.getPassword() == null) {
			log.error("All attributes must be forwarded.");
			return null;
		}
		
		User exist = userRepo.findByUserName(user.getUserName());
		
		if (exist != null) {
			log.error("User with forwarded username already exists.");
			return null;
		}
		
		userRepo.save(user);
		
		log.info("New user registered: " + user.toString());
		
		return user;
	}

	public File file(MultipartFile forwarded, String userId) {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (!existsUser(userId)) {
			log.error("Forwarded user doesn't exists.");
			return null;
		}
		
		file.setName(forwarded.getOriginalFilename());
		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
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
	
	public File fileFolder(MultipartFile forwarded, String folderId) {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (!existsFolder(folderId)) {
			log.error("Forwarded folder doesn't exists.");
			return null;
		}
		
		file.setName(forwarded.getOriginalFilename());
		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
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
	
	public Folder folder(Folder folder, String userId) {
		
		log.info("Called POST method for creating new folder.");
		
		if (!existsUser(userId)) {
			log.error("Forwarded user doesn't exists.");
			return null;
		}
		
		Folder exists = folderRepo.findByName(folder.getName());
		
		if (exists != null) {
			log.error("Folder with forwarded name already exists.");
			return null;
		}
		
		folder.setDeleted(false);
		folderRepo.save(folder);
		
		log.info("Created folder: " + folder.toString());
		
		BelongsFolderUser belongs = new BelongsFolderUser();
		belongs.setFolderId(folder.getFolderId());
		belongs.setUserId(userId);
		belongs.setDeleted(false);
		folderUserRepo.save(belongs);
		log.info("Inserted connection folder-user: " + belongs.toString());
		
		return folder;
	}
	
	public ResponseMessage updateLocation(Body request) {
		
		log.info("Called PUT method for file location update.");
		
		ResponseMessage message = new ResponseMessage();
		
		if (request.getUserId() != null && request.getFolderId() == null) {
		
			if (!existsFile(request.getFileId()) || !existsUser(request.getUserId())) {
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
			
			if (!existsFile(request.getFileId()) || !existsFolder(request.getFolderId())) {
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
	
	public List<File> files(String userId) {
		
		log.info("Called GET method for getting files by user ID.");
		
		if (!existsUser(userId)) {
			log.error("Forwarded user ID doesn't exists.");
			return null;
		}
		
		List<BelongsFileUser> belongs = fileUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.error("Doesn't exists files for forwarded user ID.");
			return null;
		} 
		
		List<File> files = new ArrayList<>();
		
		for (BelongsFileUser bfu : belongs) {
			if (bfu.getDeleted() == false && fileRepo.findByFileId(bfu.getFileId()).getDeleted() == false) {
				files.add(fileRepo.findByFileId(bfu.getFileId()));
			}
		}
		
		log.info("Successfully executet GET method.");
		
		return files;
	}
	
	@Override
	public List<Folder> folders(String userId) {
		
		log.info("Called GET method for getting folders for forwarded user ID.");
		
		if (!existsUser(userId)) {
			log.error("Forwarded user ID doesn't exists or is deleted.");
			return null;
		}

		List<Folder> folders = new ArrayList<>();
		
		List<BelongsFolderUser> belongs = folderUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.info("Forwarded user doesn't have folders.");
			return folders;
		}
		
		for (BelongsFolderUser bfu : belongs) {
			String folderId = bfu.getFolderId();
			Folder folder = folderRepo.findByFolderId(folderId);
			folders.add(folder);
		}
		
		log.info("Method for getting folders executed.");
		
		return folders;
	}
	
	@Override
	public List<File> filesFolder(String folderId) {
		
		log.info("Called GET method for getting files from folder by user ID.");
		
		if (!existsFolder(folderId)) {
			log.error("Forwarded folder ID doesn't exists");
			return null;
		}
		
		List<File> files = new ArrayList<>();
		
		List<BelongsFileFolder> belongsFiles = fileFolderRepo.findByFolderId(folderId);
		
		for (BelongsFileFolder bff: belongsFiles) {
			files.add(fileRepo.findByFileId(bff.getFileId()));
		}
		
		log.info("Method for getting files executed.");
		
		return files;
	}
	
	private Boolean existsUser(String userId) {
		
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = String.format("SELECT USER_ID FROM USER WHERE USER_ID = '%s'", userId);
		List<String> user = jdbcTemplate.queryForList(sql, String.class);
		
		return !user.isEmpty();
	}

	private Boolean existsFolder(String folderId) {
		
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = String.format("SELECT FOLDER_ID FROM FOLDER WHERE FOLDER_ID = '%s' AND DELETED = FALSE", folderId);
		List<String> folder = jdbcTemplate.queryForList(sql, String.class);
		
		return !folder.isEmpty();
	}

	private Boolean existsFile(String fileId) {
		
		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = String.format("SELECT FILE_ID FROM FILE WHERE FILE_ID = '%s' AND DELETED = FALSE", fileId);
		List<String> file = jdbcTemplate.queryForList(sql, String.class);
		
		return !file.isEmpty();
	}

	

	

	

}
