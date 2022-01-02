package com.filesboxx.ws.service.folder;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.filesboxx.ws.model.BelongsFolderUser;
import com.filesboxx.ws.model.Folder;
import com.filesboxx.ws.repository.FolderRepository;
import com.filesboxx.ws.repository.FolderUserRepository;

@Service
public class FolderServiceImpl implements FolderService {

	static Logger log = LoggerFactory.getLogger(FolderServiceImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private FolderRepository folderRepo;
	
	@Autowired
	private FolderUserRepository folderUserRepo;

	@Override
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

	private Boolean existsUser(String userId) {

		final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = String.format("SELECT USER_ID FROM USER WHERE USER_ID = '%s'", userId);
		List<String> user = jdbcTemplate.queryForList(sql, String.class);

		return !user.isEmpty();
	}

}
