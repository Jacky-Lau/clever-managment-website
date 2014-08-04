package edu.zju.bme.clever.website.dao;

import org.springframework.stereotype.Repository;

import edu.zju.bme.clever.website.model.entity.HistoriedArchetypeFile;

@Repository("historiedArchetypeFileDao")
public class HistoriedArchetypeFileDaoImpl extends AbstractGenericDao<HistoriedArchetypeFile,Integer> implements HistoriedArchetypeFileDao{

}
