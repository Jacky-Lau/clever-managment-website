package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.entity.CommitSequence;

@Repository("commitSequenceDao")
public class CommitSequenceDaoImpl extends GenericDaoImpl<CommitSequence,Integer> implements CommitSequenceDao{

}
