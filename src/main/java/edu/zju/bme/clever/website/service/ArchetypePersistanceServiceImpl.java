package edu.zju.bme.clever.website.service;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.ArchetypeNodeDao;
import edu.zju.bme.clever.website.dao.ArchetypeRelationDao;
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
	@Resource(name = "historiedArchetypeFileDao")
	private HistoriedArchetypeFileDao historiedArchetypeFileDao;
	@Resource(name = "archetypeNodeDao")
	private ArchetypeNodeDao archetypeNodeDao;
	@Resource(name = "archetypeRelationDao")
	private ArchetypeRelationDao archetypeRelationDao;

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
		historiedArchetypeFile.setCommitSequence(archetypeFile
				.getCommitSequence());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		this.archetypeFileDao.save(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
	}

	public void saveArchetypeFileCascade(ArchetypeFile archetypeFile) {
		HistoriedArchetypeFile historiedArchetypeFile = new HistoriedArchetypeFile();
		historiedArchetypeFile.setName(archetypeFile.getName());
		historiedArchetypeFile.setContent(archetypeFile.getContent());
		historiedArchetypeFile.setCommitSequence(archetypeFile
				.getCommitSequence());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		this.archetypeFileDao.save(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
		archetypeFile.getArchetypeNodes().forEach(
				node -> this.archetypeNodeDao.save(node));
	}

	@Override
	public void updateArchetypeFile(ArchetypeFile archetypeFile) {
		archetypeFile.setModifyTime(Calendar.getInstance());
		HistoriedArchetypeFile historiedArchetypeFile = new HistoriedArchetypeFile();
		historiedArchetypeFile.setName(archetypeFile.getName());
		historiedArchetypeFile.setContent(archetypeFile.getContent());
		historiedArchetypeFile.setCommitSequence(archetypeFile
				.getCommitSequence());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		this.archetypeFileDao.update(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
	}

	public void updateArchetypeFileCascade(ArchetypeFile archetypeFile) {
		archetypeFile.setModifyTime(Calendar.getInstance());
		HistoriedArchetypeFile historiedArchetypeFile = new HistoriedArchetypeFile();
		historiedArchetypeFile.setName(archetypeFile.getName());
		historiedArchetypeFile.setContent(archetypeFile.getContent());
		historiedArchetypeFile.setCommitSequence(archetypeFile
				.getCommitSequence());
		historiedArchetypeFile.setHistoriedTime(Calendar.getInstance());
		// Delete the original archetype nodes
		ArchetypeFile originalFile = this.archetypeFileDao
				.findById(archetypeFile.getId());
		originalFile.getArchetypeNodes().forEach(
				node -> this.archetypeNodeDao.delete(node));
		
		this.archetypeFileDao.update(archetypeFile);
		this.historiedArchetypeFileDao.save(historiedArchetypeFile);
		archetypeFile.getArchetypeNodes().forEach(
				node -> this.archetypeNodeDao.saveOrUpdate(node));
	}
}
