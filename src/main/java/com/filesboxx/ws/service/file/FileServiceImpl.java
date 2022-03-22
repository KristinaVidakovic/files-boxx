package com.filesboxx.ws.service.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.filesboxx.ws.controller.files.FilesMapper;
import com.filesboxx.ws.controller.files.dto.FileDto;
import com.filesboxx.ws.controller.files.dto.FileListDto;
import com.filesboxx.ws.controller.files.dto.FileLocationFolderDto;
import com.filesboxx.ws.controller.files.dto.FileLocationUserDto;
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
	public FileDto file(MultipartFile forwarded, UUID userId) {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}

		file.setName(forwarded.getOriginalFilename());

		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
			throw new InvalidDataException();
		}
		
		file.setDeleted(false);
		File saved = fileRepo.save(file);

		log.info("File " + file.getName() + "entered!");
		
		BelongsFileUser belongs = new BelongsFileUser();
		belongs.setFileId(file.getFileId());
		belongs.setUserId(userId);
		belongs.setDeleted(false);
		fileUserRepo.save(belongs);

		log.info("Inserted connection file-user!");
	
		return FilesMapper.toFileDto(saved);
	}
	
	@Override
	public FileDto fileFolder(MultipartFile forwarded, UUID folderId) {
		
		log.info("Called POST method for inserting new file.");
		
		File file = new File();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists.");
			throw new InvalidFolderException();
		}
		
		file.setName(forwarded.getOriginalFilename());

		try {
			file.setData(forwarded.getBytes());
		} catch (IOException e) {
			log.error("Error getting bytes from forwarded file.");
			e.printStackTrace();
			throw new InvalidDataException();
		}
		
		file.setDeleted(false);
		File saved = fileRepo.save(file);
		log.info("File " + file.getName() + "entered!");
		
		BelongsFileFolder belongs = new BelongsFileFolder();
		belongs.setFileId(file.getFileId());
		belongs.setFolderId(folderId);
		belongs.setDeleted(false);
		fileFolderRepo.save(belongs);
		log.info("Inserted connection file-folder!");
	
		return FilesMapper.toFileDto(saved);
	}
	
	@Override
	public ResponseMessage updateLocation(Optional<FileLocationUserDto> locationUserDto,
										  Optional<FileLocationFolderDto> locationFolderDto) {
		
		log.info("Called PUT method for file location update.");
		
		ResponseMessage message = new ResponseMessage();

		if (locationUserDto.isPresent()) {
		
			if (fileRepo.file(locationUserDto.get().getFileId()) == null) {
				log.error("Forwarded file doesn't exists or is deleted.");
				throw new InvalidFileException();
			}

			if (userRepo.user(locationUserDto.get().getUserId()) == null) {
				log.error("Forwarded user doesn't exists.");
				throw new InvalidUserException();
			}
			
			BelongsFileFolder bff = fileFolderRepo.findByFileIdAndDeletedFalse(locationUserDto.get().getFileId());
			BelongsFileUser bfu = new BelongsFileUser();
			bfu.setFileId(locationUserDto.get().getFileId());
			bfu.setUserId(locationUserDto.get().getUserId());
			bfu.setDeleted(false);
			fileUserRepo.save(bfu);
			log.info("Connection file-user added!");
			bff.setDeleted(true);
			fileFolderRepo.save(bff);
			log.info("Connection file-folder updated!");

		} else {
			
			if (fileRepo.file(locationFolderDto.get().getFileId()) == null) {
				log.error("Forwarded file doesn't exists or is deleted.");
				throw new InvalidFileException();
			}

			if (folderRepo.folder(locationFolderDto.get().getFolderId()) == null) {
				log.error("Forwarded folder doesn't exists or is deleted.");
				throw new InvalidFolderException();
			}
			
			BelongsFileUser bfu = fileUserRepo.findByFileIdAndDeletedFalse(locationFolderDto.get().getFileId());
			if(bfu == null) {
				BelongsFileFolder bff = fileFolderRepo.findByFileIdAndDeletedFalse(locationFolderDto.get().getFileId());
				bff.setDeleted(true);
				fileFolderRepo.save(bff);
				log.info("Connection file-user updated!");
			} else {
				bfu.setDeleted(true);
				fileUserRepo.save(bfu);
				log.info("Connection file-user updated!");
			}
			BelongsFileFolder bff = new BelongsFileFolder();
			bff.setFileId(locationFolderDto.get().getFileId());
			bff.setFolderId(locationFolderDto.get().getFolderId());
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
	public FileListDto files(UUID userId) {
		
		log.info("Called GET method for getting files by user ID.");

		List<File> list = new ArrayList<>();
		
		if (userRepo.user(userId) == null) {
			log.error("Forwarded user doesn't exists.");
			throw new InvalidUserException();
		}
		
		List<BelongsFileUser> belongs = fileUserRepo.findByUserId(userId);
		
		if (belongs.isEmpty()) {
			log.error("Doesn't exists files for forwarded user ID.");
			throw new InvalidArgumentException();
		} 

		for (BelongsFileUser bfu : belongs) {
			if (!bfu.getDeleted() && !fileRepo.findByFileId(bfu.getFileId()).getDeleted()) {
				list.add(fileRepo.findByFileId(bfu.getFileId()));
			}
		}
		
		log.info("Successfully executed GET method.");
		
		return FilesMapper.toFileListDto(list);
	}
	
	@Override
	public FileListDto filesFolder(UUID folderId) {
		
		log.info("Called GET method for getting files from folder by user ID.");

		List<File> list = new ArrayList<>();
		
		if (folderRepo.folder(folderId) == null) {
			log.error("Forwarded folder doesn't exists or is deleted.");
			throw new InvalidFolderException();
		}
		
		List<BelongsFileFolder> belongsFiles = fileFolderRepo.findByFolderId(folderId);
		
		for (BelongsFileFolder bff: belongsFiles) {
			list.add(fileRepo.findByFileId(bff.getFileId()));
		}
		
		log.info("Method for getting files executed.");
		
		return FilesMapper.toFileListDto(list);
	}

	@Override
	public ResponseMessage deleteFile(UUID fileId) {

		log.info("Called DELETE method for deleting file by file ID.");

		ResponseMessage message = new ResponseMessage();

		if (fileRepo.file(fileId) == null) {
			log.error("Forwarded file doesn't exists or is deleted.");
			throw new InvalidFileException();
		}

		File file = fileRepo.findByFileId(fileId);

		BelongsFileFolder bff = fileFolderRepo.findByFileIdAndDeletedFalse(fileId);
		if (bff != null) {
			bff.setDeleted(true);
			fileFolderRepo.save(bff);
			log.info("Belongs file folder deleted!");
		}
		BelongsFileUser bfu = fileUserRepo.findByFileIdAndDeletedFalse(fileId);
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
}
