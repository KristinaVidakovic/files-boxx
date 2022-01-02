package com.filesboxx.ws.service.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.filesboxx.ws.model.Body;
import com.filesboxx.ws.model.File;
import com.filesboxx.ws.model.ResponseMessage;
import com.filesboxx.ws.repository.FileFolderRepository;
import com.filesboxx.ws.repository.FileRepository;
import com.filesboxx.ws.repository.FileUserRepository;

@Service
public class FileServiceImpl implements FileService{
	
	static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FileRepository fileRepo;
	
	@Autowired
	private FileUserRepository fileUserRepo;
	
	@Autowired
	private FileFolderRepository fileFolderRepo;
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
		
		List<File> files = new ArrayList<File>();
		
		for (BelongsFileUser bfu : belongs) {
			if (bfu.getDeleted() == false && fileRepo.findByFileId(bfu.getFileId()).getDeleted() == false) {
				files.add(fileRepo.findByFileId(bfu.getFileId()));
			}
		}
		
		log.info("Successfully executet GET method.");
		
		return files;
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
