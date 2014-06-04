package edu.zju.bme.clever.website.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.CommitSequenceDao;
import edu.zju.bme.clever.website.dao.HistoriedArchetypeFileDao;
import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.CommitSequence;
import edu.zju.bme.clever.website.entity.HistoriedArchetypeFile;

@Service("archetypePersistanceService")
@Transactional
public class ArchetypePersistanceServiceImpl implements
		ArchetypePersistanceService {

	@Resource(name = "commitSequenceDao")
	private CommitSequenceDao commitSequenceDao;
	@Resource(name = "archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;
	@Resource(name="historiedArchetypeFileDao")
	private HistoriedArchetypeFileDao historiedArchetypeFileDao;

	@Override
	public CommitSequence getNewCommitSequence() {
		CommitSequence commitSequence = new CommitSequence();
		commitSequence.setCommitTime(Calendar.getInstance());
		this.commitSequenceDao.save(commitSequence);
		return commitSequence;
	}

	@Override
	public CommitSequence getCommitSequenceById(Integer id) {
		return this.commitSequenceDao.findById(id);
	}

	@Override
	public ArchetypeFile getArchetypeByName(String name) {
		return this.archetypeFileDao.findUniqueByProperty("name", name);
	}

	@Override
	public void saveArchetypeFile(ArchetypeFile archetypeFile) {
		HistoriedArchetypeFile historiedArchetypeFile = new HistoriedArchetypeFile();
		historiedArchetypeFile.setName(archetypeFile.getName());
		historiedArchetypeFile.setContent(archetypeFile.getContent());
		historiedArchetypeFile.setCommitSequence(archetypeFile.getCommitSequence());
		historiedArchetypeFile.setDescription(archetypeFile.getDescription());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		this.archetypeFileDao.save(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
	}
	
	@Override
	public void updateArchetypeFile(ArchetypeFile archetypeFile){
		HistoriedArchetypeFile historiedArchetypeFile = new HistoriedArchetypeFile();
		historiedArchetypeFile.setName(archetypeFile.getName());
		historiedArchetypeFile.setContent(archetypeFile.getContent());
		historiedArchetypeFile.setCommitSequence(archetypeFile.getCommitSequence());
		historiedArchetypeFile.setDescription(archetypeFile.getDescription());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		this.archetypeFileDao.update(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
	}
}
