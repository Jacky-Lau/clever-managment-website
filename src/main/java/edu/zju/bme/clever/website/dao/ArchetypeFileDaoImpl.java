package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.entity.ArchetypeFile;

@Repository("archetypeFileDao")
public class ArchetypeFileDaoImpl extends GenericDaoImpl<ArchetypeFile,Integer> implements ArchetypeFileDao{

}
