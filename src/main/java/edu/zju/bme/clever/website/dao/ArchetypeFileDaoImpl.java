package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.ArchetypeFile;

@Repository("archetypeFileDao")
public class ArchetypeFileDaoImpl extends AbstractGenericDao<ArchetypeFile,Integer> implements ArchetypeFileDao{

}
