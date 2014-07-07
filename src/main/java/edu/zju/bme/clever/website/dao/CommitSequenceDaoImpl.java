package edu.zju.bme.clever.website.dao;

import java.util.Calendar;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.CommitSequence;

@Repository("commitSequenceDao")
public class CommitSequenceDaoImpl extends
		GenericDaoImpl<CommitSequence, Integer> implements CommitSequenceDao {
	@Override
	public void save(CommitSequence entity) {
		entity.setCommitTime(Calendar.getInstance());
		super.save(entity);
	}
}
