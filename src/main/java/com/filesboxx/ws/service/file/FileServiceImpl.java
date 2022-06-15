package com.filesboxx.ws.service.file;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.filesboxx.ws.controller.files.FilesMapper;
import com.filesboxx.ws.controller.files.dto.*;
import com.filesboxx.ws.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filesboxx.ws.model.connections.BelongsFileFolder;
import com.filesboxx.ws.model.connections.BelongsFileUser;
import com.filesboxx.ws.model.file.File;
import com.filesboxx.ws.model.response.ResponseMessage;
import com.filesboxx.ws.repository.connection.FileFolderRepository;
import com.filesboxx.ws.repository.file.FileRepository;
import com.filesboxx.ws.repository.connection.FileUserRepository;
import com.filesboxx.ws.repository.folder.FolderRepository;
import com.filesboxx.ws.repository.user.UserRepository;

@Service
public class FileServiceImpl implements FileService {

	static Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

	private final FileRepository fileRepo;
	private final FileUserRepository fileUserRepo;
	private final FileFolderRepository fileFolderRepo;
	private final UserRepository userRepo;
	private final FolderRepository folderRepo;

	@Autowired
	FileServiceImpl(FileRepository fileRepo, FileUserRepository fileUserRepo, FileFolderRepository fileFolderRepo,
					UserRepository userRepo, FolderRepository folderRepo){
		this.fileRepo = fileRepo;
		this.fileUserRepo = fileUserRepo;
		this.fileFolderRepo = fileFolderRepo;
		this.userRepo = userRepo;
		this.folderRepo = folderRepo;
	}
	
	@Override
	public FileDto save(MultipartFile forwarded, UUID userId) throws InvalidUserException, InvalidDataException {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}

		int index = forwarded.getOriginalFilename().lastIndexOf(".");

		file.setName(forwarded.getOriginalFilename().substring(0,index));
		file.setExtension(forwarded.getOriginalFilename().substring(index+1));

		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
			throw new InvalidDataException();
		}
		
		file.setDeleted(false);
		file.setDate(new Date());
		file.setSize(convertToMB(forwarded.getSize()) + " MB");
		File saved = fileRepo.save(file);

		log.info("File " + saved.getName() + " entered!");
		
		BelongsFileUser belongs = new BelongsFileUser();
		belongs.setFile(saved);
		belongs.setUser(userRepo.findByUserId(userId));
		belongs.setDeleted(false);
		fileUserRepo.save(belongs);

		log.info("Inserted connection file-user!");
	
		return FilesMapper.toFileDto(saved);
	}
	
	@Override
	public FileDto saveFile(MultipartFile forwarded, UUID folderId) throws InvalidFolderException, InvalidDataException {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists.");
			throw new InvalidFolderException();
		}

		int index = forwarded.getOriginalFilename().lastIndexOf(".");

		file.setName(forwarded.getOriginalFilename().substring(0,index));
		file.setExtension(forwarded.getOriginalFilename().substring(index+1));

		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
			throw new InvalidDataException();
		}
		
		file.setDeleted(false);
		file.setDate(new Date());
		file.setSize(convertToMB(forwarded.getSize()) + " MB");
		File saved = fileRepo.save(file);
		log.info("File " + saved.getName() + "entered!");
		
		BelongsFileFolder belongs = new BelongsFileFolder();
		belongs.setFile(saved);
		belongs.setFolder(folderRepo.findByFolderId(folderId));
		belongs.setDeleted(false);
		fileFolderRepo.save(belongs);
		log.info("Inserted connection file-folder!");
	
		return FilesMapper.toFileDto(saved);
	}
	
	@Override
	public ResponseMessage updateLocation(FileLocationDto dto) throws InvalidFileException, InvalidUserException, InvalidFolderException {
		
		log.info("Called PUT method for file location update.");
		
		ResponseMessage message = new ResponseMessage();

		if (dto.getLocationUser() != null) {
		
			if (fileRepo.file(dto.getLocationUser().getFileId()) == null) {
				log.error("Forwarded file doesn't exists or is deleted.");
				throw new InvalidFileException();
			}

			if (userRepo.user(dto.getLocationUser().getUserId()) == null) {
				log.error("Forwarded user doesn't exists.");
				throw new InvalidUserException();
			}
			
			BelongsFileFolder bff = fileFolderRepo.findByFileFileIdAndDeletedFalse(dto.getLocationUser().getFileId());
			BelongsFileUser bfu = new BelongsFileUser();
			bfu.setFile(fileRepo.findByFileId(dto.getLocationUser().getFileId()));
			bfu.setUser(userRepo.findByUserId(dto.getLocationUser().getUserId()));
			bfu.setDeleted(false);
			fileUserRepo.save(bfu);
			log.info("Connection file-user added!");
			bff.setDeleted(true);
			fileFolderRepo.save(bff);
			log.info("Connection file-folder updated!");

		} else {
			
			if (fileRepo.file(dto.getLocationFolder().getFileId()) == null) {
				log.error("Forwarded file doesn't exists or is deleted.");
				throw new InvalidFileException();
			}

			if (folderRepo.folder(dto.getLocationFolder().getFolderId()) == null) {
				log.error("Forwarded folder doesn't exists or is deleted.");
				throw new InvalidFolderException();
			}
			
			BelongsFileUser bfu = fileUserRepo.findByFileFileIdAndDeletedFalse(dto.getLocationFolder().getFileId());
			if(bfu == null) {
				BelongsFileFolder bff = fileFolderRepo.findByFileFileIdAndDeletedFalse(dto.getLocationFolder().getFileId());
				bff.setDeleted(true);
				fileFolderRepo.save(bff);
				log.info("Connection file-user updated!");
			} else {
				bfu.setDeleted(true);
				fileUserRepo.save(bfu);
				log.info("Connection file-user updated!");
			}
			BelongsFileFolder bff = new BelongsFileFolder();
			bff.setFile(fileRepo.findByFileId(dto.getLocationFolder().getFileId()));
			bff.setFolder(folderRepo.findByFolderId(dto.getLocationFolder().getFolderId()));
			bff.setDeleted(false);
			fileFolderRepo.save(bff);
			log.info("Connection file-folder added!");

		}

		message.setStatus(HttpStatus.OK);
		message.setMessage("Successfully changed file location.");
		log.info("Executed PUT method.");

		return message;

	}
	
	@Override
	public FileListDto list(UUID userId) throws InvalidUserException {
		
		log.info("Called GET method for getting files by user ID.");

		List<File> list = new ArrayList<>();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}
		
		List<BelongsFileUser> belongs = fileUserRepo.findByUserUserId(userId);

		for (BelongsFileUser bfu : belongs) {
			if (!bfu.getDeleted() && !fileRepo.findByFileId(bfu.getFile().getFileId()).getDeleted()) {
				list.add(fileRepo.findByFileId(bfu.getFile().getFileId()));
			}
		}
		
		log.info("Successfully executed GET method.");
		
		return FilesMapper.toFileListDto(list);
	}
	
	@Override
	public FileListDto listFiles(UUID folderId) throws InvalidFolderException {
		
		log.info("Called GET method for getting files from folder by folder ID.");

		List<File> list = new ArrayList<>();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists or is deleted.");
			throw new InvalidFolderException();
		}
		
		List<BelongsFileFolder> belongsFiles = fileFolderRepo.findByFolderFolderIdAndDeletedFalse(folderId);
		
		for (BelongsFileFolder bff: belongsFiles) {
			list.add(fileRepo.findByFileId(bff.getFile().getFileId()));
		}
		
		log.info("Method for getting files executed.");
		
		return FilesMapper.toFileListDto(list);
	}

	@Override
	public ResponseMessage delete(UUID fileId) throws InvalidFileException {

		log.info("Called DELETE method for deleting file by file ID.");

		ResponseMessage message = new ResponseMessage();

		if (fileRepo.file(fileId) == null) {
			log.error("Forwarded file doesn't exists or is deleted.");
			throw new InvalidFileException();
		}

		File file = fileRepo.findByFileId(fileId);

		BelongsFileFolder bff = fileFolderRepo.findByFileFileIdAndDeletedFalse(fileId);
		if (bff != null) {
			bff.setDeleted(true);
			fileFolderRepo.save(bff);
			log.info("Belongs file folder deleted!");
		}
		BelongsFileUser bfu = fileUserRepo.findByFileFileIdAndDeletedFalse(fileId);
		if (bfu != null) {
			bfu.setDeleted(true);
			fileUserRepo.save(bfu);
			log.info("Belongs file user deleted!");
		}

		file.setDeleted(true);
		fileRepo.save(file);

		log.info("File deleted.");

		message.setMessage("File deleted.");
		message.setStatus(HttpStatus.OK);

		return message;
	}

	private String convertToMB (Long bytes) {
		DecimalFormat numberFormat = new DecimalFormat("0.00");
		double MB = bytes*9.537*0.0000001;
		return numberFormat.format(MB);
	}
}
